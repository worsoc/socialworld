package org.socialworld.objects;

import org.socialworld.core.IAccessToken;

public final class AccessTokenSimulationObject implements IAccessToken
{

	private static AccessTokenSimulationObject valid;
	
	static AccessTokenSimulationObject getValid() {
		if (valid == null) {
			valid = new AccessTokenSimulationObject();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}

