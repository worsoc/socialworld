package org.socialworld.actions;

import org.socialworld.core.IAccessToken;

public final class AccessTokenAction implements IAccessToken
{

	private static AccessTokenAction valid;
	
	static AccessTokenAction getValid() {
		if (valid == null) {
			valid = new AccessTokenAction();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

