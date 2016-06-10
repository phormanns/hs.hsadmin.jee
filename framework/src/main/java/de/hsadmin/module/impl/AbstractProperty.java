package de.hsadmin.module.impl;

import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public abstract class AbstractProperty<T> implements Property<T> {

	private final ValueObject owningVO;
	private final String name;
	private final ReadWritePolicy policy;
	private final SearchPolicy search;
	private final boolean required;
	
	private PersistentObjectMapper<T> persistentObjectMapper;
	private ParameterMapMapper<T> parameterMapMapper;

	public AbstractProperty(
			final ValueObject owner, 
			final String propertyName, 
			final ReadWritePolicy readWritePolicy, 
			final SearchPolicy searchPolicy, 
			final boolean required) {
		this.owningVO = owner;
		this.name = propertyName;
		this.policy = readWritePolicy;
		this.search = searchPolicy;
		this.required = required;
	}
	
	protected PersistentObjectMapper<T> getPersistentObjectMapper() {
		return persistentObjectMapper;
	}

	protected ParameterMapMapper<T> getParameterMapMapper() {
		return parameterMapMapper;
	}

	@SuppressWarnings("unchecked")
	protected void setPersistentObjectMapper(final PersistentObjectMapper<?> mapper) {
		persistentObjectMapper = (PersistentObjectMapper<T>) mapper;
	}

	@SuppressWarnings("unchecked")
	protected void setParameterMapMapper(ParameterMapMapper<?> mapper) {
		parameterMapMapper = (ParameterMapMapper<T>) mapper;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ReadWritePolicy access() {
		return policy;
	}

	@Override
	public SearchPolicy search() {
		return search;
	}
	
	@Override
	public boolean required() {
		return required;
	}
	
	@SuppressWarnings("unchecked")
	public Class<? extends Property<T>> getPropertyType() {
		return (Class<? extends Property<T>>) getClass();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getValue() throws TechnicalException {
		return (T) ReflectionUtil.invokeGetter(owningVO, getName());
	}

	@Override
	public void setValue(T value) throws TechnicalException {
		ReflectionUtil.invokeSetter(owningVO, getName(), value);
	}

	@Override
	public boolean valueIsNullOrEmpty() {
		try {
			return getValue() == null;
		} catch (TechnicalException e) {
			return true;
		}
	}

	@Override
	public String toString() {
		try {
			return "Property " + name + ": " + getValue();
		} catch (TechnicalException e) {
			return "Property " + name;
		}
	}
	
	@Override
	public void copyValueFromPersistentObject(Object persistentObject) throws TechnicalException, UserException {
		setValue(getPersistentObjectMapper().readValueFromPersistentObject(persistentObject, getName()));
	}

	@Override
	public void copyValueToPersistentObject(Object persistentObject) throws TechnicalException, UserException {
		T newValue = getValue();
		if (newValue != null) {
			getPersistentObjectMapper().writeValueToPersistentObject(persistentObject, getName(), newValue);
		}
	}

	@Override
	public void copyValueToParameterMap(Map<String, Object> rpcParameter)
			throws TechnicalException, UserException {
		getParameterMapMapper().writeValueToParameterMap(rpcParameter, getName(), getValue());
	}

	@Override
	public void copyValueFromParameterMap(Map<String, Object> rpcParameter) throws TechnicalException, UserException {
		setValue(getParameterMapMapper().readValueFromParameterMap(rpcParameter, getName()));
	}

}
