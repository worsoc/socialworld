package org.socialworld.attributes;

import org.socialworld.core.IAccessToken;

public final class AccessTokenAttributeArray implements IAccessToken
{

	private static AccessTokenAttributeArray valid;
	
	static AccessTokenAttributeArray getValid() {
		if (valid == null) {
			valid = new AccessTokenAttributeArray();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

