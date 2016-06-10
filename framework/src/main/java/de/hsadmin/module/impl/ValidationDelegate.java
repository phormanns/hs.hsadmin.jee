package de.hsadmin.module.impl;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserErrorList;
import de.hsadmin.common.error.UserException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.SearchPolicy;

public class ValidationDelegate<T extends ValueObject> {

	private static final ValidatorFactory vf;
	
	static {
		vf = Validation.buildDefaultValidatorFactory();
	}
	
	public void checkPrototypeIsCreateable(final T prototype) throws UserException, TechnicalException {
		final UserErrorList errors = new UserErrorList();
		final List<Property<?>> properties = prototype.properties();
		for (Property<?> p : properties) {
			if (p.valueIsNullOrEmpty()) {
				if (p.required()) {
					errors.add(UserError.MSG_REQUIRED_FIELD, p.getName());
				}
			} else {
				final ReadWritePolicy rwPolicy = p.access();
				if (rwPolicy != ReadWritePolicy.READWRITE && rwPolicy != ReadWritePolicy.WRITEONCE) {
					errors.add(UserError.MSG_NO_FIELD_WRITEACCESS, p.getName());
				} else {
					Set<ConstraintViolation<T>> validateResults = vf.getValidator().validate(prototype);
					if (!validateResults.isEmpty()) {
						for (ConstraintViolation<T> violation: validateResults) {
							errors.add(UserError.MSG_FIELD_DOESNOT_VALIDATE, violation.getMessage(), violation.getPropertyPath().toString(), violation.getInvalidValue().toString());
						}
					}
				}
			}
		}
		errors.raiseException();
	}

	public void checkPrototypeIsUpdateable(final T prototype) throws UserException, TechnicalException {
		final List<Property<?>> properties = prototype.properties();
		final UserErrorList errors = new UserErrorList();
		for (Property<?> p : properties) {
			final ReadWritePolicy rwPolicy = p.access();
			if (!p.valueIsNullOrEmpty()) {
				if (rwPolicy != ReadWritePolicy.READWRITE) {
					errors.add(UserError.MSG_NO_FIELD_WRITEACCESS, p.getName());
				} else {
					Set<ConstraintViolation<T>> validateResults = vf.getValidator().validate(prototype);
					if (!validateResults.isEmpty()) {
						for (ConstraintViolation<T> violation: validateResults) {
							errors.add(UserError.MSG_FIELD_DOESNOT_VALIDATE, violation.getMessage(), violation.getPropertyPath().toString(), violation.getInvalidValue().toString());
						}
					}
				}
			}
		}
		errors.raiseException();
	}

	public void checkCriteriaIsSearchable(final T criteria) throws UserException, TechnicalException {
		final UserErrorList errors = new UserErrorList();
		final List<Property<?>> properties = criteria.properties();
		for (Property<?> p : properties) {
			final SearchPolicy searchPolicy = p.search();
			if (!p.valueIsNullOrEmpty() && searchPolicy != SearchPolicy.COMPARE && searchPolicy != SearchPolicy.EQUALS && searchPolicy != SearchPolicy.LIKE) {
				errors.add(UserError.MSG_FIELD_NOT_SEARCHABLE, p.getName());
			}
		}
		errors.raiseException();
	}

}
