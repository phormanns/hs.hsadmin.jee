package de.hsadmin.module.property;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractProperty;
import de.hsadmin.module.property.mapping.DefaultStringSetParameterMapMapper;
import de.hsadmin.module.property.mapping.DefaultStringSetPersistentObjectMapper;
import de.hsadmin.module.property.mapping.ParameterMapMapper;
import de.hsadmin.module.property.mapping.PersistentObjectMapper;

public class StringSetProperty extends AbstractProperty<StringSet> {

	private static final PersistentObjectMapper<StringSet> defaultPersistentObjectMapper;
	private static final ParameterMapMapper<StringSet> defaultParameterMapMapper;

	static {
		defaultPersistentObjectMapper = new DefaultStringSetPersistentObjectMapper();
		defaultParameterMapMapper = new DefaultStringSetParameterMapMapper();
	}

	private final ValueObject owningVO;

	public StringSetProperty(final ValueObject owner, final String propertyName,
			final ReadWritePolicy readWritePolicy, final SearchPolicy searchPolicy,
			final boolean required) {
		super(owner, propertyName, readWritePolicy, searchPolicy, required);
		owningVO = owner;
		setParameterMapMapper(defaultParameterMapMapper);
		setPersistentObjectMapper(defaultPersistentObjectMapper);
	}

	@Override
	public Class<?> getValueType() {
		return StringSet.class;
	}
	
	@Override
	public void setValue(final StringSet value) throws TechnicalException {
		ReflectionUtil.invokeSetter(owningVO, getName(), value.getStrings());
	}

	@Override
	public StringSet getValue() throws TechnicalException {
		final StringSet value = new StringSet();
		final Object stringList = ReflectionUtil.invokeGetter(owningVO, getName());
		if (stringList instanceof String[]) {
			value.setStrings(((String[]) stringList));
		}
		return value;
	}
	
	@Override
	public boolean valueIsNullOrEmpty() {
		try {
			final Object stringList = ReflectionUtil.invokeGetter(owningVO, getName());
			if (stringList == null) {
				return true;
			}
			if (stringList instanceof String[]) {
				return ((String[]) stringList).length == 0;
			}
			return false;
		} catch (TechnicalException e) {
			return false;
		}
	}
	
	@Override
	public void copyValueFromPersistentObject(Object persistentObject)
			throws TechnicalException, UserException {
		// TODO Auto-generated method stub
		super.copyValueFromPersistentObject(persistentObject);
	}
	
	@Override
	public void copyValueToPersistentObject(Object persistentObject)
			throws TechnicalException, UserException {
		// TODO Auto-generated method stub
		super.copyValueToPersistentObject(persistentObject);
	}
	
}
