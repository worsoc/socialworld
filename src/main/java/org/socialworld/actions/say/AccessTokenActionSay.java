package org.socialworld.actions.say;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenActionSay implements IAccessToken
{

	private static AccessTokenActionSay valid;
	
	static AccessTokenActionSay getValid() {
		if (valid == null) {
			valid = new AccessTokenActionSay();
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

