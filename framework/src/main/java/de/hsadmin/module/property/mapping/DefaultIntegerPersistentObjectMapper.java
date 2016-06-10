package de.hsadmin.module.property.mapping;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.util.ReflectionUtil;

public class DefaultIntegerPersistentObjectMapper implements PersistentObjectMapper<Integer> {
	@Override
	public void writeValueToPersistentObject(final Object persistentObject, final String propertyName, final Integer value) throws TechnicalException {
		ReflectionUtil.invokeSetter(persistentObject, propertyName, value);
	}

	@Override
	public Integer readValueFromPersistentObject(final Object persistentObject, final String propertyName)  throws TechnicalException {
		final Object object = ReflectionUtil.invokeGetter(persistentObject, propertyName); 
		if (object instanceof Integer) {
			return (Integer) object;
		} else {
			if (object instanceof String) {
				try {
					return Integer.parseInt((String) object);
				} catch (NumberFormatException e) {
					throw new TechnicalException(e);
				}
			} else {
				return null;
			}
		}
	}
}