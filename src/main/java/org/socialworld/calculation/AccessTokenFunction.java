package org.socialworld.calculation;

import org.socialworld.core.IAccessToken;

public final class AccessTokenFunction implements IAccessToken
{

	private static AccessTokenFunction valid;
	
	static AccessTokenFunction getValid() {
		if (valid == null) {
			valid = new AccessTokenFunction();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
