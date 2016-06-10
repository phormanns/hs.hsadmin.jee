package de.hsadmin.module.property.mapping;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;

public interface ParameterMapMapper<T> {

	void writeValueToParameterMap(Map<String, Object> rpcParameter, String propertyName, T value) throws TechnicalException, UserException;
	
	T readValueFromParameterMap(Map<String, Object> rpcParameter, String propertyName) throws TechnicalException, UserException;
	
}
