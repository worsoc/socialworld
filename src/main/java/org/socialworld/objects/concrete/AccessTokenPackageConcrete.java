package org.socialworld.objects.concrete;

import org.socialworld.core.IAccessToken;

public final class AccessTokenPackageConcrete implements IAccessToken
{

	private static AccessTokenPackageConcrete valid;
	
	static AccessTokenPackageConcrete getValid() {
		if (valid == null) {
			valid = new AccessTokenPackageConcrete();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
