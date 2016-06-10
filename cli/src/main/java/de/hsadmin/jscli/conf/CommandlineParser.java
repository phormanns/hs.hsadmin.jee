package de.hsadmin.jscli.conf;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import de.hsadmin.jscli.exception.JSCliException;

public class CommandlineParser {


	private CommandLine cmd;
	private Options opts;

	public CommandlineParser(String[] args) throws JSCliException {
		opts = new Options();
		opts.addOption("h", "help", false, "print this message");
		opts.addOption("u", "user", true, "specify login user");
		opts.addOption("r", "runas", true, "specify run-as user");
		opts.addOption("e", "expr", true, "expression to execute");
		opts.addOption("f", "file", true, "script file to execute");
		opts.addOption("i", "interactive", false, "interactive shell");
		PosixParser parser = new PosixParser();
		try {
			if (args.length < 1) {
				printHelp();
				System.exit(0);
			}
			cmd = parser.parse(opts, args);
			if (cmd.hasOption("help")) {
				printHelp();
				System.exit(0);
			}
		} catch (ParseException e) {
			throw new JSCliException(e); 
		}
	}
	
	public String getUser() {
		final String systemUser = System.getProperty("user.name");
		final String configUser = Config.getInstance().getProperty("userName", systemUser);
		return cmd.getOptionValue("user", configUser);
	}
	
	public String getRunAs() {
		return cmd.getOptionValue("runas", getUser());
	}
	
	public String getExpression() {
		return cmd.getOptionValue("expr", null);
	}
	
	public String getFile() {
		return cmd.getOptionValue("file", null);
	}
	
	public boolean isInteractive() {
		return cmd.hasOption("interactive");
	}
	
	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("hsscript", opts);
	}
	
	public String[] getArgs() {
		return cmd.getArgs();
	}
	
}
