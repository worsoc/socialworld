package org.socialworld.actions.attack;

import org.socialworld.core.IAccessToken;

public final class AccessTokenActionAttack implements IAccessToken
{

	private static AccessTokenActionAttack valid;
	
	static AccessTokenActionAttack getValid() {
		if (valid == null) {
			valid = new AccessTokenActionAttack();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
}
