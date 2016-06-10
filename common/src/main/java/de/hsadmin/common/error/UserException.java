package de.hsadmin.common.error;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UserException extends Exception {

	private static final long serialVersionUID = 1L;

	private final List<UserError> errors; 
	
	public UserException(final Exception e) {
		super(e);
		errors = new ArrayList<>();
	}

	public UserException(final UserErrorList errorList) {
		super(errorList.buildMessage());
		errors = new ArrayList<>();
		final Iterator<UserError> iterator = errorList.errors();
		while (iterator.hasNext()) {
			UserError err = iterator.next();
			errors.add(err);
		}
	}

	public UserException(final UserError userError) {
		super(userError.getLocalizedMessage());
		errors = new ArrayList<>();
		errors.add(userError);
	}

	public boolean hasError(final String errorMessageKey) {
		boolean listContainsError = false;
		for (UserError err : errors) {
			if (err.getMessageKey().equals(errorMessageKey)) {
				listContainsError = true;
			}
		}
		return listContainsError;
	}

}
