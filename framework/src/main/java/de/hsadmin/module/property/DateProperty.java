package de.hsadmin.module.property;

import java.util.Date;

import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultDateParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultDatePersistentObjectMapper;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public class DateProperty extends AbstractProperty<Date> implements Property<Date> {

	private static final PersistentObjectMapper<Date> defaultPersistentObjectMapper;
	private static final ParameterMapMapper<Date> defaultParameterMapMapper;
	
	static {
		defaultPersistentObjectMapper = new DefaultDatePersistentObjectMapper();
		defaultParameterMapMapper = new DefaultDateParameterMapMapper();
	}

	public DateProperty(final ValueObject ownerVO, final String propertyName, final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy, final boolean required) {
		super(ownerVO, propertyName, readWritePolicy, searchPolicy, required);
		setPersistentObjectMapper(defaultPersistentObjectMapper);
		setParameterMapMapper(defaultParameterMapMapper);
	}

	@Override
	public Class<?> getValueType() {
		return Date.class;
	}

}
