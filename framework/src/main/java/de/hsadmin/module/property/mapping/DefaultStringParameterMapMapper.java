package de.hsadmin.module.property.mapping;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

public class DefaultStringParameterMapMapper implements ParameterMapMapper<String> {
	@Override
	public void writeValueToParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName, final String value) 
					throws TechnicalException, UserException {
		if (value != null) {
			rpcParameter.put(propertyName, value);
		}
	}

	@Override
	public String readValueFromParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName) throws TechnicalException, UserException {
		final Object value = rpcParameter.get(propertyName);
		if (value instanceof String) {
			return (String) value;
		}
		return null;
	}
}