package de.hsadmin.jscli.console;

import de.hsadmin.jscli.exception.JSCliException;

public interface PasswordReader {

	String readPassword() throws JSCliException;
	
}
