package org.socialworld.attributes.percipience;
import org.socialworld.core.IAccessToken;

public final class AccessTokenPercipience implements IAccessToken
{

	private static AccessTokenPercipience valid;
	
	static AccessTokenPercipience getValid() {
		if (valid == null) {
			valid = new AccessTokenPercipience();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
