package de.hsadmin.jscli.console;

import java.io.File;
import java.io.IOException;

import jline.ConsoleReader;
import jline.History;
import jline.SimpleCompletor;
import de.hsadmin.jscli.exception.JSCliException;

public class ConsoleWrapper implements PasswordReader {

	private ConsoleReader cons;
	private String prompt;

	public void open(final String prompt) throws JSCliException {
		this.prompt = prompt;
		try {
			cons = new ConsoleReader();
			cons.setDefaultPrompt(prompt);
			final String userHome = System.getProperty("user.home");
			cons.setHistory(new History(new File(userHome + "/.hsscript_history")));		
		} catch (IOException e) {
			throw new JSCliException(e);
		}
	}

	public String readInput() throws JSCliException {
		try {
			String line = cons.readLine();
			while (line.trim().endsWith("\\")) {
				line = line.substring(0, line.length() - 1) + "\n" + cons.readLine(">");
			}
			return line;
		} catch (IOException e) {
			throw new JSCliException(e);
		}
	}
	
	public void println(final String text) throws JSCliException {
		try {
			if (cons != null) {
				cons.printString(text);
				cons.printNewline();
			} else {
				throw new JSCliException("cannot write console");
			}
		} catch (IOException e) {
			throw new JSCliException(e);
		}
	}

	public String readPassword() throws JSCliException {
		try {
			final String pw = cons.readLine("Password: ", new Character('*'));
			cons.setDefaultPrompt(prompt);
			return pw;
		} catch (IOException e) {
			throw new JSCliException(e);
		}
	}

	public void codeCompletion(final String[] candidateStrings) {
		cons.addCompletor(new SimpleCompletor(candidateStrings));
	}

}
