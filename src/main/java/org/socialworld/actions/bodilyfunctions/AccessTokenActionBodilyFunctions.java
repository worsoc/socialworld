package org.socialworld.actions.bodilyfunctions;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenActionBodilyFunctions implements IAccessToken
{

	private static AccessTokenActionBodilyFunctions valid;
	
	static AccessTokenActionBodilyFunctions getValid() {
		if (valid == null) {
			valid = new AccessTokenActionBodilyFunctions();
			SimulationCluster.action.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.action;
	}
	
}

