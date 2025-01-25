package org.socialworld.objects;

import org.socialworld.core.IAccessToken;
import org.socialworld.calculation.SimulationCluster;

public final class AccessTokenSimulationObject implements IAccessToken
{

	private static AccessTokenSimulationObject valid;
	
	static AccessTokenSimulationObject getValid() {
		if (valid == null) {
			valid = new AccessTokenSimulationObject();
			SimulationCluster.simulationObject.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.simulationObject;
	}


}

