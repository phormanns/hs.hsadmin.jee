package de.hsadmin.common.error;

public class TechnicalException extends Exception {

	private static final long serialVersionUID = 1L;

	public TechnicalException(Exception e) {
		super(e);
	}

	public TechnicalException(String message) {
		super(message);
	}

}
