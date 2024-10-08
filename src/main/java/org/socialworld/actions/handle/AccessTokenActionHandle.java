package org.socialworld.actions.handle;

import org.socialworld.core.IAccessToken;

public final class AccessTokenActionHandle implements IAccessToken
{

	private static AccessTokenActionHandle valid;
	
	static AccessTokenActionHandle getValid() {
		if (valid == null) {
			valid = new AccessTokenActionHandle();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

