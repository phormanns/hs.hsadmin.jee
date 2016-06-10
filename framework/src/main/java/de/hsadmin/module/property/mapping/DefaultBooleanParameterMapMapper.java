package de.hsadmin.module.property.mapping;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;

public class DefaultBooleanParameterMapMapper implements
		ParameterMapMapper<Boolean> {
	@Override
	public void writeValueToParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName, final Boolean value) 
					throws TechnicalException, UserException {
		if (value != null) {
			rpcParameter.put(propertyName, value.toString());
		}
	}

	@Override
	public Boolean readValueFromParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName) throws TechnicalException, UserException {
		final Object value = rpcParameter.get(propertyName);
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		if (value instanceof String) {
			String stringValue = (String) value;
			if ("true".equalsIgnoreCase(stringValue)) {
				return Boolean.TRUE;
			}
			if ("false".equalsIgnoreCase(stringValue)) {
				return Boolean.FALSE;
			}
			throw new UserException(new UserError(UserError.MSG_BOOLEAN_VALUE_EXPECTED, propertyName, stringValue));
		}
		return null;
	}
}