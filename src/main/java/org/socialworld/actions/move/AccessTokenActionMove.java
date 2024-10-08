package org.socialworld.actions.move;

import org.socialworld.core.IAccessToken;

public final class AccessTokenActionMove implements IAccessToken
{

	private static AccessTokenActionMove valid;
	
	static AccessTokenActionMove getValid() {
		if (valid == null) {
			valid = new AccessTokenActionMove();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

