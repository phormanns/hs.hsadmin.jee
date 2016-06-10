package de.hsadmin.module.property;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

public interface Property<T> {

	public String getName();
	
	public T getValue() throws TechnicalException;
	
	public void setValue(T value) throws TechnicalException;
	
	public Class<? extends Property<?>> getPropertyType();
	
	public Class<?> getValueType();
	
	public ReadWritePolicy access();

	public SearchPolicy search();
	
	public boolean required();

	public boolean valueIsNullOrEmpty();
	
	public void copyValueFromPersistentObject(final Object persistentObject) throws TechnicalException, UserException; 
	
	public void copyValueToPersistentObject(final Object persistentObject) throws TechnicalException, UserException;

	public void copyValueToParameterMap(final Map<String, Object> rpcParameter) throws TechnicalException, UserException;
	
	public void copyValueFromParameterMap(final Map<String, Object> rpcParameter) throws TechnicalException, UserException;
	
}
 

