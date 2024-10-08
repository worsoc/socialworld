package org.socialworld.calculation;

import org.socialworld.core.IAccessToken;

public final class AccessTokenExpression implements IAccessToken
{

	private static AccessTokenExpression valid;
	
	static AccessTokenExpression getValid() {
		if (valid == null) {
			valid = new AccessTokenExpression();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

