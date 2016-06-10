package de.hsadmin.module;

import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.module.property.Property;

public interface ValueObject {

	public List<Property<?>> properties() throws UserException, TechnicalException;
	
	public Property<?> get(String propertyName) throws UserException;
	
	public boolean hasProperty(String propertyName) throws UserException;

	public void copyPropertiesToPersistentObject(Object persistentObject) 
			throws UserException, TechnicalException;
	
	public void copyPropertiesFromPersistentObject(Object persistentObject) 
			throws UserException, TechnicalException;
	
}
