package org.socialworld.visualize;

import org.socialworld.core.IAccessToken;

public final class AccessTokenVisualize implements IAccessToken
{

	private static AccessTokenVisualize valid;
	
	static AccessTokenVisualize getValid() {
		if (valid == null) {
			valid = new AccessTokenVisualize();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

