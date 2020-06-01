package org.socialworld.attributes;

import org.socialworld.calculation.ValueProperty;

public interface ISavedValues {

	void setPropertyProtection(ValueProperty protection);
	boolean checkHasPropertyProtection();
	ValueProperty getProperty (PropertyName propName, String valueName);
	
}
