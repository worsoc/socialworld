package org.socialworld.calculation.expressions;

import org.socialworld.core.IAccessToken;

public final class AccessTokenExpressions4Knowledge implements IAccessToken
{

	private static AccessTokenExpressions4Knowledge valid;
	
	static AccessTokenExpressions4Knowledge getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Knowledge();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
