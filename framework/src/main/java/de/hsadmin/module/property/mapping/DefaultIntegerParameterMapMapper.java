package de.hsadmin.module.property.mapping;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;

public class DefaultIntegerParameterMapMapper implements ParameterMapMapper<Integer> {
	
	@Override
	public void writeValueToParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName, Integer value) 
					throws TechnicalException, UserException {
		if (value != null) {
			rpcParameter.put(propertyName, value.toString());
		}
	}

	@Override
	public Integer readValueFromParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName) 
					throws TechnicalException, UserException {
		final Object value = rpcParameter.get(propertyName);
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				throw new UserException(new UserError(UserError.MSG_INT_VALUE_EXPECTED, propertyName, (String) value));
			}
		}
		return null;
	}
}