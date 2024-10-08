package org.socialworld.attributes;

import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;

public class NoSavedValue extends SavedValue {

	private static NoSavedValue valueNothing;
	
	public static NoSavedValue getValueNothing() {
		if (valueNothing == null) {
			valueNothing = new NoSavedValue();
		}
		return valueNothing;
	}
	@Override
	public ISavedValue copyForProperty(IAccessToken token) {
		return valueNothing;
	}

	@Override
	public ValueProperty getProperty(IAccessToken token, PropertyName propName, String valueName) {
		return ValueProperty.getInvalid();
	}

	@Override
	public boolean checkIsObjectNothing() {
		return true;
	}

	@Override
	public Object getReleased(IAccessToken token) {
		// TODO Auto-generated method stub
		return null;
	}

}
