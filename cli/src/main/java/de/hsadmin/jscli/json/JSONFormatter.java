package de.hsadmin.jscli.json;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JSONFormatter {
	
	private int indent = 1;

	public String format(final Object object) {
		if (object == null) return "";
		if (object instanceof List<?>) {
			return formatList((List<?>) object);
		}
		if (object instanceof Map<?, ?>) {
			return formatMap((Map<?, ?>) object);
		}
		if (object instanceof String) {
			return formatString((String) object);
		}
		if (object instanceof Boolean) {
			return ((Boolean) object).toString();
		}
		if (object instanceof Object[]) {
			return formatArr((Object[]) object);
		}
		return "an instance of " + object.getClass().getCanonicalName();
	}

	public String formatMap(final Map<?, ?> map) {
		final StringBuffer result = new StringBuffer();
		result.append('{');
		incr();
		result.append(newline());
		final StringBuffer formattedMap = new StringBuffer();
		final List<String> listOfKeys = Arrays.asList(map.keySet().toArray(new String[] {}));
		Collections.sort(listOfKeys);
		for (final Object key : listOfKeys) {
			if (formattedMap.length() > 0) {
				formattedMap.append(',');
				formattedMap.append(newline());
			}
			formattedMap.append(key.toString());
			formattedMap.append(':');
			formattedMap.append(format(map.get(key)));
		}
		result.append(formattedMap.toString());
		decr();
		result.append(newline());
		result.append('}');
		return result.toString();
	}
	
	public String formatString(final String str) {
		return "'" + str + "'";
	}
	
	public String formatList(final List<?> list) {
		return formatArr(list.toArray());
	}
	
	public String formatArr(final Object[] arr) {
		StringBuffer result = new StringBuffer();
		result.append('[');
		incr();
		result.append(newline());
		StringBuffer formattedList = new StringBuffer();
		for (int idx = 0; idx < arr.length; idx ++) {
			if (formattedList.length() > 0) {
				formattedList.append(',');
				formattedList.append(newline());
			}
			formattedList.append(format(arr[idx]));
		}
		result.append(formattedList.toString());
		decr();
		if (formattedList.length() > 0) {
			result.append(newline());
		}
		result.append(']');
		return result.toString();
	}
	
	private void incr() {
		indent += 3;
	}

	private void decr() {
		if (indent > 3) {
			indent -= 3;
		}
	}
	
	private String newline() {
		int ind = indent;
		if (ind > 52) {
			ind = 52;
		}
		return "\n                                                   ".substring(0, indent);
	}

}
