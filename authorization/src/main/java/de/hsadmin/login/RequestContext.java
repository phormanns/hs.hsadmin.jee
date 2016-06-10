package de.hsadmin.login;

public class RequestContext {

	private final String runAsLogin;
	private final Role runAsRole;
	
	public RequestContext(final String runAs, final Role runAsRole) {
		this.runAsLogin = runAs;
		this.runAsRole = runAsRole;
	}
	
	public String getLoginUser() {
		return runAsLogin;
	}

	public Role getLoginRole() {
		return runAsRole;
	}
	
}
