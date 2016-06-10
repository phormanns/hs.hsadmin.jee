package de.hsadmin.module.property.mapping;

import java.util.Arrays;
import java.util.Collection;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.property.StringSet;

public class DefaultStringSetPersistentObjectMapper implements
		PersistentObjectMapper<StringSet> {

	@SuppressWarnings("unchecked")
	@Override
	public StringSet readValueFromPersistentObject(final Object persistentObject,
			final String propertyName) throws TechnicalException, UserException {
		final Object strings = ReflectionUtil.invokeGetter(persistentObject, propertyName);
		final StringSet value = new StringSet();
		if (strings instanceof Collection<?>) {
			value.setStrings(((Collection<String>) strings).toArray(new String[] { }));
		}
		return value;
	}

	@Override
	public void writeValueToPersistentObject(final Object persistentObject,
			final String propertyName, final StringSet value) throws TechnicalException {
		final String[] strings = value.getStrings();
		if (strings != null) {
			ReflectionUtil.invokeSetter(persistentObject, propertyName, Arrays.asList(strings));
		}
	}

}
