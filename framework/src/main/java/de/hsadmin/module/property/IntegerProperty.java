package de.hsadmin.module.property;

import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultIntegerParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultIntegerPersistentObjectMapper;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public class IntegerProperty extends AbstractProperty<Integer> implements Property<Integer> {

	private static final PersistentObjectMapper<Integer> defaultPersistentObjectMapper;
	private static final ParameterMapMapper<Integer> defaultParameterMapMapper;
	
	static {
		defaultPersistentObjectMapper = new DefaultIntegerPersistentObjectMapper();
		defaultParameterMapMapper = new DefaultIntegerParameterMapMapper();
	}

	public IntegerProperty(final ValueObject ownerVO, final String propertyName, final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy, final boolean required) {
		super(ownerVO, propertyName, readWritePolicy, searchPolicy, required);
		setPersistentObjectMapper(defaultPersistentObjectMapper);
		setParameterMapMapper(defaultParameterMapMapper);
	}

	@Override
	public Class<?> getValueType() {
		return Integer.class;
	}

}
