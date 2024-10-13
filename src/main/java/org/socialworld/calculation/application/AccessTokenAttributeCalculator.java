package org.socialworld.calculation.application;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenAttributeCalculator implements IAccessToken
{

	private static AccessTokenAttributeCalculator valid;
	
	static AccessTokenAttributeCalculator getValid() {
		if (valid == null) {
			valid = new AccessTokenAttributeCalculator();
			SimulationCluster.attributeArray.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.attributeArray;
	}
	
}

