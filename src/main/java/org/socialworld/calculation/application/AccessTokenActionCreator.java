package org.socialworld.calculation.application;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenActionCreator implements IAccessToken
{

	private static AccessTokenActionCreator valid;
	
	static AccessTokenActionCreator getValid() {
		if (valid == null) {
			valid = new AccessTokenActionCreator();
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

