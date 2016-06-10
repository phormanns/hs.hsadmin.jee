package de.hsadmin.login;

public class TestTicketValidator implements TicketValidator {

	@Override
	public String validate(final String ticket) {
		final int colonPosition = ticket.indexOf(':');
		final String ticketUser = ticket.substring(colonPosition + 1);
		return ticketUser;
	}

}
