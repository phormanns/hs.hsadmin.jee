package de.hsadmin.service.customer;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class CustomerRemote extends AbstractRemote<CustomerVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "CustomerServiceLocal";
	}

}
