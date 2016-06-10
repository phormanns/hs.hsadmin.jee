package de.hsadmin.module.property.mapping;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;

public class ReferredStringPersistentObjectMapper implements PersistentObjectMapper<String>, ReferredPropertyPath {

	private String propertyPath;
	
	@Override
	public void setPropertyPath(final String path) {
		propertyPath = path;
	}

	@Override
	public String readValueFromPersistentObject(final Object persistentObject, final String propertyName) throws TechnicalException, UserException {
		String path = propertyName;
		if (propertyPath != null) {
			path = propertyPath;
		}
		final String[] strings = path.split("\\.");
		Object pathObject = persistentObject;
		for (int idx = 0; idx < strings.length && pathObject != null; idx++) {
			final String propName = strings[idx];
			pathObject = ReflectionUtil.invokeGetter(pathObject, propName);
		}
		return pathObject instanceof String ? (String) pathObject : null;
	}

	@Override
	public void writeValueToPersistentObject(final Object persistentObject,
			final String propertyName, final String value) throws TechnicalException {
		// TODO Auto-generated method stub
		
	}

}
