package de.hsadmin.jscli.exception;


public class JSCliException extends Exception {

	private static final long serialVersionUID = 1L;

	public JSCliException(Exception e) {
		super(e);
	}

	public JSCliException(String message) {
		super(message);
	}

}
