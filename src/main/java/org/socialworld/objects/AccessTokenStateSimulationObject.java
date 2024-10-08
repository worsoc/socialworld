package org.socialworld.objects;

import org.socialworld.core.IAccessToken;

public final class AccessTokenStateSimulationObject implements IAccessToken
{

	private static AccessTokenStateSimulationObject valid;
	
	static AccessTokenStateSimulationObject getValid() {
		if (valid == null) {
			valid = new AccessTokenStateSimulationObject();
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
}
