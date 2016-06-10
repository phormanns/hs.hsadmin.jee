package de.hsadmin.module.property;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultStringParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultStringPersistentObjectMapper;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public class StringProperty extends AbstractProperty<String> implements Property<String> {

	private static final PersistentObjectMapper<String> defaultPersistentObjectMapper;
	private static final ParameterMapMapper<String> defaultParameterMapMapper;
	
	static {
		defaultPersistentObjectMapper = new DefaultStringPersistentObjectMapper();
		defaultParameterMapMapper = new DefaultStringParameterMapMapper();
	}

	public StringProperty(final ValueObject ownerVO, final String propertyName, final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy, final boolean required) {
		super(ownerVO, propertyName, readWritePolicy, searchPolicy, required);
		setParameterMapMapper(defaultParameterMapMapper);
		setPersistentObjectMapper(defaultPersistentObjectMapper);
	}

	@Override
	public Class<?> getValueType() {
		return String.class;
	}

	@Override
	public boolean valueIsNullOrEmpty() {
		String val = null;
		try {
			val = getValue();
		} catch (TechnicalException e) {
			return true;
		}
		return val == null || val.isEmpty();
	}

}
