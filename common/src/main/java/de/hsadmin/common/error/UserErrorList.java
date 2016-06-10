package de.hsadmin.common.error;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserErrorList {

	private final List<UserError> errors;
	
	public UserErrorList() {
		errors = new ArrayList<>();
	}
	
	public String buildMessage() {
		final StringBuilder stringBuilder = new StringBuilder();
		final String lineSeparator = System.getProperty("line.separator");
		for (UserError err : errors) {
			stringBuilder.append(err.getLocalizedMessage());
			stringBuilder.append(lineSeparator);
		}
		return stringBuilder.toString();
	}

	public void add(String messageId, String... params) {
		errors.add(new UserError(messageId, params));
	}

	public void raiseException() throws UserException {
		if (!errors.isEmpty()) {
			throw new UserException(this);
		}
	}

	public Iterator<UserError> errors() {
		return errors.iterator();
	}
}
