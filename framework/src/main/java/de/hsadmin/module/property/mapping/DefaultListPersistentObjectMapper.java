package de.hsadmin.module.property.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.ValueObject;

public class DefaultListPersistentObjectMapper<VO extends ValueObject> implements PersistentObjectMapper<List<VO>> {
	
	private final Class<? extends VO> elementsType;

	public DefaultListPersistentObjectMapper(Class<? extends VO> elementsType) {
		this.elementsType = elementsType;
	}

	@Override
	public void writeValueToPersistentObject(Object persistentObject, String propertyName, List<VO> value) throws TechnicalException, UserException {
		final List<VO> valueFromPersistentObject = readValueFromPersistentObject(persistentObject, propertyName);
		valueFromPersistentObject.clear();
//		for (final VO elem : value) {
//			valueFromPersistentObject.add(elem);
//			elem.
//		}
//		ReflectionUtil.invokeSetter(persistentObject, propertyName, ???;
	}

	@Override
	public List<VO> readValueFromPersistentObject(Object persistentObject, String propertyName)  throws TechnicalException, UserException {
		final Object object = ReflectionUtil.invokeGetter(persistentObject, propertyName); 
		if (object instanceof Collection<?>) {
			final List<VO> valueList = new ArrayList<>();
			final Collection<?> coll = (Collection<?>) object;
			for (Object o : coll) {
				try {
					final VO newInstance = elementsType.newInstance();
					newInstance.copyPropertiesFromPersistentObject(o);
					valueList.add(newInstance);
				} catch (InstantiationException | IllegalAccessException e) {
					throw new TechnicalException(e);
				}
			}
			return valueList;
		} else {
			return null;
		}
	}
}