package de.hsadmin.module.property;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class StringSet {

	private Set<String> stringSet;
	
	public StringSet() {
		stringSet = null;
	}
	
	public String[] getStrings() {
		if (stringSet == null) {
			return null;
		}
		return stringSet.toArray(new String[] { } );
	}

	public void setStrings(final String[] changedStringSet) {
		stringSet = null;
		if (changedStringSet != null) {
			stringSet = new TreeSet<String>();
			stringSet.addAll(Arrays.asList(changedStringSet));
		}
	}
	
}
