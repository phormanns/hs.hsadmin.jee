package de.hsadmin.login;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.hsadmin.common.config.Config;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;

@Stateless 
public class LoginService implements LoginServiceLocal {

	@PersistenceContext(name = "hsar")
	private EntityManager entityManager;
	
	private TicketValidator ticketValidator;

	@Override
	public RequestContext createContext(final String ticket, final String runAsUser) throws UserException, TechnicalException {
		if (ticket != null && !ticket.isEmpty()) {
			final String ticketUser = validateTicket(ticket);
			String runAs = runAsUser;
			if (runAs == null || runAs.isEmpty()) {
				runAs = ticketUser;
			} else {
				checkRunAsAllowed(ticketUser, runAs);
			}
			Role scope = findRunAsRole(runAs);
			return new RequestContext(runAsUser, scope);
		}
		throw new UserException(new UserError(UserError.MSG_INVALID_TICKET, ticket));
	}

	private Role findRunAsRole(final String login) throws TechnicalException {
		Role resultRole = Role.NONE;
		if (hasHostmasterRole(login)) {
			resultRole = Role.SYSTEM;
		} else {
			if (hasCustomerRole(login)) {
				resultRole = Role.CUSTOMER;
			} else {
				if (hasPacAdminRole(login)) {
					resultRole = Role.PACKET;
				}
			}
		}
		return resultRole;
	}

	private boolean hasCustomerRole(final String login) throws TechnicalException {
		final String customersPac = Config.getInstance().getProperty(Config.PAC_CUSTOMER_ACCOUNTS);
		return login.startsWith(customersPac) && login.length() == 9 && login.charAt(5) == '-';
	}

	private boolean hasHostmasterRole(final String login) throws TechnicalException {
		final String hostmastersPac = Config.getInstance().getProperty(Config.PAC_HOSTMASTER_ACCOUNTS);
		return login.length() == 2 || ( login.startsWith(hostmastersPac) && login.length() == 8 && login.charAt(5) == '-' );
	}

	private boolean hasPacAdminRole(final String login) throws TechnicalException {
		return login.length() == 5;
	}

	private void checkRunAsAllowed(final String ticketUser, final String runAsUser) 
			throws UserException {
		assert ticketUser != null && !ticketUser.isEmpty();
		assert runAsUser != null && !runAsUser.isEmpty();
		if (ticketUser.length() != 2 && !runAsUser.startsWith(ticketUser)) {
			throw new UserException(new UserError(
					UserError.MSG_FORBIDDEN_RUNAS, ticketUser, runAsUser));
		}
	}

	private String validateTicket(final String ticket) throws TechnicalException, UserException {
		final TicketValidator ticketValidator = getTicketValidator();
		return ticketValidator.validate(ticket);
	}

	private TicketValidator getTicketValidator() throws TechnicalException {
		if (ticketValidator == null) {
			try {
				final String property = Config.getInstance().getProperty(Config.TICKETVALIDATOR_CLASS);
				final Class<?> validatorClass = Class.forName(property);
				ticketValidator = (TicketValidator) validatorClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new TechnicalException(e);
			}
		}
		return ticketValidator;
	}

}
