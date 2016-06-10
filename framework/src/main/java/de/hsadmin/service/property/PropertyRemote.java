package de.hsadmin.service.property;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class PropertyRemote extends AbstractRemote<PropertyVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "PropertyServiceLocal";
	}

}
