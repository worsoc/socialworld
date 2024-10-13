package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenWithoutRestrictions implements IAccessToken
{

	private static AccessTokenWithoutRestrictions valid;
	
	static AccessTokenWithoutRestrictions getValid() {
		if (valid == null) {
			valid = new AccessTokenWithoutRestrictions();
			SimulationCluster.total.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.total;
	}

}

