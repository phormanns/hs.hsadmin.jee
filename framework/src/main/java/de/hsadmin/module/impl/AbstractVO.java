package de.hsadmin.module.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.ContainsElements;
import de.hsadmin.module.property.ElementsType;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Required;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.module.property.mapping.Mapping;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;
import de.hsadmin.module.property.mapping.ReferredPropertyPath;

public abstract class AbstractVO implements ValueObject {

	private final Map<String, Property<?>> propertiesMap;

	public AbstractVO() throws TechnicalException {
		propertiesMap = new HashMap<>();
		final Field[] fields = getClass().getDeclaredFields();
		for (Field f : fields) {
			if (isPropertyField(f)) {
				initializeProperty(f);
			}
		}
	}

	@Override
	public List<Property<?>> properties() throws UserException, TechnicalException {
		final List<Property<?>> result = new ArrayList<>();
		try {
			final Field[] fields = getClass().getDeclaredFields();
			for (Field f : fields) {
				if (isPropertyField(f)) {
					result.add(get(f.getName()));
				}
			}
		} catch (IllegalArgumentException e) {
			throw new TechnicalException(e);
		}
		return result;
	}

	@Override
	public Property<?> get(final String propertyName) throws UserException {
		assert propertyName != null && propertyName.length() > 0;
		return propertiesMap.get(propertyName);
	}
	
	@Override
	public boolean hasProperty(final String propertyName) throws UserException {
		assert propertyName != null && propertyName.length() > 0;
		return ReflectionUtil.hasProperty(getClass(), propertyName);
	}

	@Override
	public void copyPropertiesToPersistentObject(final Object persistentObject) throws UserException, TechnicalException {
		assert persistentObject != null;
		final List<Property<?>> properties = properties();
		for (Property<?> p : properties) {
			p.copyValueToPersistentObject(persistentObject);
		}
	}

	@Override
	public void copyPropertiesFromPersistentObject(final Object persistentObject) throws UserException, TechnicalException {
		assert persistentObject != null;
		final List<Property<?>> properties = properties();
		for (Property<?> p : properties) {
			p.copyValueFromPersistentObject(persistentObject);
		}
	}

	private boolean isPropertyField(final Field f) {
		ReadWrite annotation = f.getAnnotation(ReadWrite.class);
		return annotation != null;
	}

	private void initializeProperty(final Field f) throws TechnicalException {
		try {
			String simplePropertyClassName = f.getType().getSimpleName();
			if ("int".equals(simplePropertyClassName)) {
				simplePropertyClassName = "Integer";
			}
			if ("boolean".equals(simplePropertyClassName)) {
				simplePropertyClassName = "Boolean";
			}
			final Class<?> type = Class.forName("de.hsadmin.module.property." + simplePropertyClassName + "Property");
			final Constructor<?> constructor = type.getConstructor(ValueObject.class, String.class, ReadWritePolicy.class, SearchPolicy.class, boolean.class);
			final Property<?> newInstance = (Property<?>) constructor.newInstance(this, f.getName(), getReadWritePolicy(f), getSearchPolicy(f), isRequired(f));
			set(f.getName(), newInstance);
			if (ContainsElements.class.isAssignableFrom(type)) {
				ContainsElements container = (ContainsElements) newInstance;
				container.setElementsType(getElementsType(f));
			}
			Mapping mapping = f.getAnnotation(Mapping.class);
			if (mapping != null && newInstance instanceof AbstractProperty<?>) {
				AbstractProperty<?> prop = (AbstractProperty<?>) newInstance;
				PersistentObjectMapper<?> persistentObjectMapper = mapping.boMapping().newInstance();
				ParameterMapMapper<?> parameterMapMapper = mapping.rpcMapping().newInstance();
				prop.setParameterMapMapper(parameterMapMapper);
				prop.setPersistentObjectMapper(persistentObjectMapper);
				if (persistentObjectMapper instanceof ReferredPropertyPath) {
					((ReferredPropertyPath) persistentObjectMapper).setPropertyPath(mapping.boMappingPath());;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException 
				| NoSuchMethodException | SecurityException | UserException | ClassNotFoundException e) {
			throw new TechnicalException(e);
		}
	}

	private void set(final String propertyName, final Property<?> propertyInstance) throws UserException {
		assert propertyName != null && propertyName.length() > 0;
		assert propertyInstance != null;
		propertiesMap.put(propertyName, propertyInstance);
	}

	private SearchPolicy getSearchPolicy(final Field f) {
		final Search search = f.getAnnotation(Search.class);
		SearchPolicy searchPolicy = SearchPolicy.NONE;
		if (search != null) {
			searchPolicy = search.value();
		}
		return searchPolicy;
	}

	private Class<? extends ValueObject> getElementsType(final Field f) throws TechnicalException {
		final ElementsType annotation = f.getAnnotation(ElementsType.class);
		if (annotation != null) {
			return annotation.value();
		}
		throw new TechnicalException("annotation ElementsType required");
	}

	private boolean isRequired(final Field f) {
		final Required requiredAnn = f.getAnnotation(Required.class);
		boolean required = false;
		if (requiredAnn != null) {
			required = requiredAnn.value();
		}
		return required;
	}

	private ReadWritePolicy getReadWritePolicy(final Field f) {
		final ReadWrite readWrite = f.getAnnotation(ReadWrite.class);
		ReadWritePolicy rwPolicy = ReadWritePolicy.NONE;
		if (readWrite != null) {
			rwPolicy = readWrite.value();
		}
		return rwPolicy;
	}
	
}
