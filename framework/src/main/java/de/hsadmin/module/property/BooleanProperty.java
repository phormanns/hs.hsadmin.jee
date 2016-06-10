package de.hsadmin.module.property;

import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultBooleanParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultBooleanPersistentObjectMapper;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public class BooleanProperty extends AbstractProperty<Boolean> implements Property<Boolean> {

	private static final PersistentObjectMapper<Boolean> defaultPersistentObjectMapper;
	private static final ParameterMapMapper<Boolean> defaultParameterMapMapper;
	
	static {
		defaultPersistentObjectMapper = new DefaultBooleanPersistentObjectMapper();
		defaultParameterMapMapper = new DefaultBooleanParameterMapMapper();
	}

	public BooleanProperty(final ValueObject ownerVO, final String propertyName, final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy, final boolean required) {
		super(ownerVO, propertyName, readWritePolicy, searchPolicy, required);
		setParameterMapMapper(defaultParameterMapMapper);
		setPersistentObjectMapper(defaultPersistentObjectMapper);
	}

	@Override
	public Class<?> getValueType() {
		return Boolean.class;
	}

}
