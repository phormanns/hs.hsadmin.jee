package de.hsadmin.module.property;

import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultListParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultListPersistentObjectMapper;

public class ListProperty<VO extends ValueObject> extends AbstractProperty<List<ValueObject>> implements Property<List<ValueObject>>, ContainsElements {

	private Class<? extends ValueObject> elementsType;
	
	public ListProperty(final ValueObject ownerVO, final String propertyName, final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy, final boolean required) {
		super(ownerVO, propertyName, readWritePolicy, searchPolicy, required);
	}

	@Override
	public Class<?> getValueType() {
		return List.class;
	}

	@Override
	public boolean valueIsNullOrEmpty() {
		List<ValueObject> val = null;
		try {
			val = getValue();
		} catch (TechnicalException e) {
			return true;
		}
		return val == null || val.isEmpty();
	}

	@Override
	public Class<? extends ValueObject> getElementsType() {
		return elementsType;
	}

	@Override
	public void setElementsType(final Class<? extends ValueObject> elementsType) {
		this.elementsType = elementsType;
		setPersistentObjectMapper(new DefaultListPersistentObjectMapper<ValueObject>(elementsType));
		setParameterMapMapper(new DefaultListParameterMapMapper<VO>(elementsType));
	}

}
