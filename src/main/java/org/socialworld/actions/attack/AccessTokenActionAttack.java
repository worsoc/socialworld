package org.socialworld.actions.attack;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenActionAttack implements IAccessToken
{

	private static AccessTokenActionAttack valid;
	
	static AccessTokenActionAttack getValid() {
		if (valid == null) {
			valid = new AccessTokenActionAttack();
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
