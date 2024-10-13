package org.socialworld.calculation.application;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenTalkCalculator implements IAccessToken
{

	private static AccessTokenTalkCalculator valid;
	
	static AccessTokenTalkCalculator getValid() {
		if (valid == null) {
			valid = new AccessTokenTalkCalculator();
			SimulationCluster.talk.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.talk;
	}
	
}
