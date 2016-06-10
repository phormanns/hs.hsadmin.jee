package de.hsadmin.service.customer;

import de.hsadmin.xmlrpc.AbstractRemote;
import de.hsadmin.xmlrpc.Remote;


public class SEPADirectDebitRemote extends AbstractRemote<SEPADirectDebitVO> implements Remote {

	@Override
	protected String getModuleLookup() {
		return "SEPADirectDebitServiceLocal";
	}

}
