package de.hsadmin.login;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

public interface TicketValidator {

	String validate(String ticket) throws UserException, TechnicalException;

}
