package org.socialworld.actions.hear;

import org.socialworld.core.IAccessToken;

public final class AccessTokenActionHear implements IAccessToken
{

	private static AccessTokenActionHear valid;
	
	static AccessTokenActionHear getValid() {
		if (valid == null) {
			valid = new AccessTokenActionHear();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

