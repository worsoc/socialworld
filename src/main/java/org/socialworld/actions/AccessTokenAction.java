package org.socialworld.actions;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.core.IAccessToken;

public final class AccessTokenAction implements IAccessToken
{

	private static AccessTokenAction valid;
	
	static AccessTokenAction getValid() {
		if (valid == null) {
			valid = new AccessTokenAction();
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

