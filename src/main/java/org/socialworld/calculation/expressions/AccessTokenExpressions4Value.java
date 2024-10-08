package org.socialworld.calculation.expressions;

import org.socialworld.core.IAccessToken;

public final class AccessTokenExpressions4Value implements IAccessToken
{

	private static AccessTokenExpressions4Value valid;
	
	static AccessTokenExpressions4Value getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Value();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
