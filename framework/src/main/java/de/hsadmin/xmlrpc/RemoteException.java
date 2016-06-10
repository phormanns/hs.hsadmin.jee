package de.hsadmin.xmlrpc;

public class RemoteException extends Exception {

	private static final long serialVersionUID = 1L;

	public RemoteException(Exception e) {
		super(e);
	}

}
