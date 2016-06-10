package de.hsadmin.login;

import javax.ejb.Local;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

@Local
public interface LoginServiceLocal {

	RequestContext createContext(String ticket, String runAsUser) throws UserException, TechnicalException;

}
