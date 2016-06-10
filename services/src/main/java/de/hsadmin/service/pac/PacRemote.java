package de.hsadmin.service.pac;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class PacRemote extends AbstractRemote<PacVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "PacServiceLocal";
	}

}
