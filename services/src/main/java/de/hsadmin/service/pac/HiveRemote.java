package de.hsadmin.service.pac;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class HiveRemote extends AbstractRemote<HiveVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "HiveServiceLocal";
	}

}
