package org.socialworld.calculation.application;

import org.socialworld.core.IAccessToken;

public final class AccessTokenTalkCalculator implements IAccessToken
{

	private static AccessTokenTalkCalculator valid;
	
	static AccessTokenTalkCalculator getValid() {
		if (valid == null) {
			valid = new AccessTokenTalkCalculator();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
