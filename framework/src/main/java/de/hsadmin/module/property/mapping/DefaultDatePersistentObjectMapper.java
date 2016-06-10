package de.hsadmin.module.property.mapping;

import java.text.ParseException;
import java.util.Date;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.util.DateUtil;
import de.hsadmin.common.util.ReflectionUtil;

public class DefaultDatePersistentObjectMapper implements PersistentObjectMapper<Date> {
	
	@Override
	public void writeValueToPersistentObject(final Object persistentObject, final String propertyName, final Date value) throws TechnicalException {
		ReflectionUtil.invokeSetter(persistentObject, propertyName, value);
	}

	@Override
	public Date readValueFromPersistentObject(final Object persistentObject, final String propertyName)  throws TechnicalException {
		final Object object = ReflectionUtil.invokeGetter(persistentObject, propertyName); 
		if (object instanceof Date) {
			return (Date) object;
		} else {
			if (object instanceof String) {
				try {
					return DateUtil.DEFAULT_DATEFORMAT.parse((String) object);
				} catch (ParseException e) {
					throw new TechnicalException(e);
				}
			} else {
				return null;
			}
		}
	}
}