package de.hsadmin.module.property.mapping;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.util.ReflectionUtil;

public class DefaultBooleanPersistentObjectMapper implements PersistentObjectMapper<Boolean> {
	
	@Override
	public void writeValueToPersistentObject(final Object persistentObject, final String propertyName, final Boolean value) throws TechnicalException {
		ReflectionUtil.invokeSetter(persistentObject, propertyName, value);
	}

	@Override
	public Boolean readValueFromPersistentObject(final Object persistentObject, final String propertyName)  throws TechnicalException {
		final Object object = ReflectionUtil.invokeGetter(persistentObject, propertyName); 
		if (object instanceof Boolean) {
			return (Boolean) object;
		} else {
			if (object instanceof String) {
				return Boolean.parseBoolean((String) object);
			} else {
				return Boolean.FALSE;
			}
		}
	}
}