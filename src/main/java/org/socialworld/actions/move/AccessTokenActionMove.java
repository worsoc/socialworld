package org.socialworld.actions.move;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenActionMove implements IAccessToken
{

	private static AccessTokenActionMove valid;
	
	static AccessTokenActionMove getValid() {
		if (valid == null) {
			valid = new AccessTokenActionMove();
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

