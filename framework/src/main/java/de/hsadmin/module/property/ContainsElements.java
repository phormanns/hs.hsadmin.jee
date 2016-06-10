package de.hsadmin.module.property;

import de.hsadmin.module.ValueObject;

public interface ContainsElements {

	public Class<? extends ValueObject> getElementsType();
	
	public void setElementsType(Class<? extends ValueObject> elementsType);
	
}
