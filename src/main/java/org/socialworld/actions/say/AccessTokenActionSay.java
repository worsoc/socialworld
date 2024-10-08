package org.socialworld.actions.say;

import org.socialworld.core.IAccessToken;

public final class AccessTokenActionSay implements IAccessToken
{

	private static AccessTokenActionSay valid;
	
	static AccessTokenActionSay getValid() {
		if (valid == null) {
			valid = new AccessTokenActionSay();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

