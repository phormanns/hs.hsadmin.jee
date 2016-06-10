package de.hsadmin.module.property.mapping;

import java.util.Arrays;
import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.module.property.StringSet;

public class DefaultStringSetParameterMapMapper implements
		ParameterMapMapper<StringSet> {

	@Override
	public void writeValueToParameterMap(final Map<String, Object> rpcParameter,
			final String propertyName, final StringSet value) throws TechnicalException,
			UserException {
		final String[] strings = value.getStrings();
		if (strings != null) {
			rpcParameter.put(propertyName, Arrays.asList(strings));
		}
	}

	@Override
	public StringSet readValueFromParameterMap(
			final Map<String, Object> rpcParameter, final String propertyName)
			throws TechnicalException, UserException {
		final Object rpcParam = rpcParameter.get(propertyName);
		final StringSet value = new StringSet();
		String[] strings = new String[] { };
		if (rpcParam instanceof Object[]) {
			strings = new String[((Object[]) rpcParam).length];
			int idx = 0;
			for (final Object o : (Object[]) rpcParam) {
				strings[idx] = (String) o;
				idx++;
			}
		}
		value.setStrings(strings);
		return value;
	}

}
