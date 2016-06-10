package de.hsadmin.module.property.mapping;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

public interface PersistentObjectMapper<T> {

	T readValueFromPersistentObject(Object persistentObject, String propertyName) throws TechnicalException, UserException; 
	
	void writeValueToPersistentObject(Object persistentObject, String propertyName, T value) throws TechnicalException, UserException;

}
