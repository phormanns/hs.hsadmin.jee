package de.hsadmin.module.property.mapping;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.DateUtil;

public class DefaultDateParameterMapMapper implements ParameterMapMapper<Date> {
	
	@Override
	public void writeValueToParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName, final Date value) 
					throws TechnicalException, UserException {
		if (value != null) {
			rpcParameter.put(propertyName, DateUtil.DEFAULT_DATEFORMAT.format(value));
		}
	}

	@Override
	public Date readValueFromParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName) throws TechnicalException, UserException {
		final Object value = rpcParameter.get(propertyName);
		if (value instanceof Date) {
			return (Date) value;
		}
		if (value instanceof String) {
			try {
				return DateUtil.DEFAULT_DATEFORMAT.parse((String) value);
			} catch (ParseException e) {
				throw new UserException(new UserError(UserError.MSG_INVALID_DATEFORMAT, propertyName, (String) value));
			}
		}
		return null;
	}
}