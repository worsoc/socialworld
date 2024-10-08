package org.socialworld.calculation.application;

import org.socialworld.core.IAccessToken;

public final class AccessTokenAttributeCalculator implements IAccessToken
{

	private static AccessTokenAttributeCalculator valid;
	
	static AccessTokenAttributeCalculator getValid() {
		if (valid == null) {
			valid = new AccessTokenAttributeCalculator();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

