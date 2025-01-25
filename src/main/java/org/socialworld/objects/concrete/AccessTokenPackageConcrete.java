package org.socialworld.objects.concrete;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenPackageConcrete implements IAccessToken
{

	private static AccessTokenPackageConcrete valid;
	
	static AccessTokenPackageConcrete getValid() {
		if (valid == null) {
			valid = new AccessTokenPackageConcrete();
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
