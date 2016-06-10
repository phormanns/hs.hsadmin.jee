package de.hsadmin.service.customer;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class ContactRemote extends AbstractRemote<ContactVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "ContactServiceLocal";
	}

}
