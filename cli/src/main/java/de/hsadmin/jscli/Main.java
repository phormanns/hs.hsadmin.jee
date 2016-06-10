package de.hsadmin.jscli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import de.hsadmin.jscli.conf.CommandlineParser;
import de.hsadmin.jscli.console.ConsoleWrapper;
import de.hsadmin.jscli.json.JSONFormatter;


public class Main {

	public static void main(String[] args) {
		final ConsoleWrapper console = new ConsoleWrapper();
		final JSONFormatter formatter = new JSONFormatter();
		try {
			final CommandlineParser cmdParser = new CommandlineParser(args);
			final String runAs = cmdParser.getRunAs();
			console.open(runAs + "@hsadmin> ");
			final String user = cmdParser.getUser();
			final ScriptClient scriptClient = new ScriptClient(console, user, runAs, cmdParser.getArgs());
			final String file = cmdParser.getFile();
			if (file != null && file.length() > 0) {
				if ("-".equals(file)) {
					scriptClient.execute(new InputStreamReader(System.in));
					console.println(formatter.format(scriptClient.getLastRpcResult()));
				} else {
					BufferedReader bufferedReader = null;
					try {
						bufferedReader = new BufferedReader(new FileReader(file));
						String inputLine = bufferedReader.readLine();
						boolean isFirstLine = true;
						final StringBuffer scriptFromFile = new StringBuffer();
						while (inputLine != null) {
							if (isFirstLine && inputLine.startsWith("#!")) {
								scriptFromFile.append("//");
								scriptFromFile.append(inputLine.substring(2));
							} else {
								scriptFromFile.append(inputLine);
							}
							scriptFromFile.append("\n");
							isFirstLine = false;
							inputLine = bufferedReader.readLine();
						}
						scriptClient.execute(scriptFromFile.toString());
					} catch (FileNotFoundException e) {
						System.err.println("File not found: " + file);
					} finally {
						if (bufferedReader != null) {
							bufferedReader.close();
						}
					}
				}
			}
			final String expr = cmdParser.getExpression();
			if (expr != null && expr.length() > 0) {
				scriptClient.execute(expr);
				console.println(formatter.format(scriptClient.getLastRpcResult()));
			}
			if (cmdParser.isInteractive()) {
				String command = console.readInput();
				while (!("bye".equals(command.trim()) || "exit".equals(command.trim()) || "quit".equals(command.trim()))) {
					try {
						scriptClient.execute(command);
						console.println(formatter.format(scriptClient.getLastRpcResult()));
					} catch (Exception e) {
						console.println("Error: " + e.getLocalizedMessage() + "\n");
					}
					command = console.readInput();
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

}
