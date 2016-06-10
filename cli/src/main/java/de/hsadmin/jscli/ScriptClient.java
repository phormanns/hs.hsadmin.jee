package de.hsadmin.jscli;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.hsadmin.jscli.cas.CASTicketProvider;
import de.hsadmin.jscli.console.ConsoleWrapper;
import de.hsadmin.jscli.exception.JSCliException;

public class ScriptClient {

	final private ScriptEngine engine;
	final private Set<String> completionStrings;

	public ScriptClient(final ConsoleWrapper console, final String user, final String runAs, final String... arguments) throws JSCliException {
		final CASTicketProvider ticketProvider = new CASTicketProvider(console, user, runAs);
		final RpcClient rpcClient = new RpcClient(ticketProvider);
		final ScriptEngineManager engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("js");
		engine.put("casgrantingticket", ticketProvider);
		engine.put("xmlrpcclient", rpcClient);
		engine.put("xmlrpcLastResult", null);
		completionStrings = new HashSet<String>();
		completionStrings.add("set");
		completionStrings.add("where");
		considerArguments(arguments);
		try {
			final InputStream inputResource = getClass().getClassLoader().getResourceAsStream("js/functions.js");
			engine.eval(new InputStreamReader(inputResource));
		} catch (ScriptException e) {
			throw new JSCliException(e);
		}
		final List<String> methods = rpcClient.listMethods();
		for (final String method : methods) {
			final String[] parts = method.split("\\.");
			if (parts.length == 2) {
				final String module = parts[0];
				final String function = parts[1];
				if ("system".equals(module) || "getModuleLookup".equals(function) || "createValueObject".equals(function)) {
					continue;
				}
				completionStrings.add(module);
				final String jsFunctionIdent;
				if ("delete".equals(function)) {
					jsFunctionIdent = module + "['remove']";
					completionStrings.add(module + ".remove");
				} else {
					jsFunctionIdent = module + "['" + function + "']";
					completionStrings.add(module + "." + function);
				}
				try {
					engine.eval(
						"if (typeof " + module + " === 'undefined')" +
						"	{ var " + module + " = { }; };\n" +
					    jsFunctionIdent + 
					    "   = function(json) { return hsaModuleCall('" + module + "', '" + function + "', json); }"
					);
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}
		}
		console.codeCompletion(getCodeCompletionStrings());
	}

	public String[] getCodeCompletionStrings() {
		final String[] codeCompletionStrings = new String[completionStrings.size()];
		int idx = 0;
		for (final String s : completionStrings) {
			codeCompletionStrings[idx] = s;
			idx++;
		}
		return codeCompletionStrings;
	}

	public Object execute(final String snippet) throws JSCliException {
		try {
			engine.put("xmlrpcLastResult", null);
			return engine.eval(snippet);
		} catch (ScriptException e) {
			throw new JSCliException(e);
		}
	}
	
	public Object execute(final Reader rd) throws JSCliException {
		try {
			engine.put("xmlrpcLastResult", null);
			return engine.eval(rd);
		} catch (ScriptException e) {
			throw new JSCliException(e);
		}
	}
	
	public Object getLastRpcResult() {
		return engine.get("xmlrpcLastResult");
	}

	private void considerArguments(final String... arguments)
			throws JSCliException {
		final StringBuilder argsBuilder = new StringBuilder("var arguments = [ ");
		boolean isFirstArg = true;
		for (final String arg : arguments) {
			if (!isFirstArg) {
				argsBuilder.append(", ");
			}
			argsBuilder.append('\'');
			argsBuilder.append(arg);
			argsBuilder.append('\'');
			isFirstArg = false;
		}
		argsBuilder.append(" ];");
		try {
			engine.eval(argsBuilder.toString());
		} catch (ScriptException e) {
			throw new JSCliException(e);
		}
	}
	
}
