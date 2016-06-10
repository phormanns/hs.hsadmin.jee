package de.hsadmin.module.property.mapping;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.util.ReflectionUtil;

public class DefaultStringPersistentObjectMapper implements
		PersistentObjectMapper<String> {
	@Override
	public void writeValueToPersistentObject(final Object persistentObject, final String propertyName, final String value) throws TechnicalException {
		ReflectionUtil.invokeSetter(persistentObject, propertyName, value);
	}

	@Override
	public String readValueFromPersistentObject(final Object persistentObject, final String propertyName)  throws TechnicalException {
		final Object object = ReflectionUtil.invokeGetter(persistentObject, propertyName); 
		if (object instanceof String) {
			return (String) object;
		} else {
			return null;
		}
	}
}