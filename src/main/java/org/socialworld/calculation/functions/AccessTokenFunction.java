package org.socialworld.calculation.functions;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenFunction implements IAccessToken
{

	private static AccessTokenFunction valid;
	
	static AccessTokenFunction getValid() {
		if (valid == null) {
			valid = new AccessTokenFunction();
			SimulationCluster.function.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.function;
	}

}
