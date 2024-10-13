package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenAttributeArray implements IAccessToken
{

	private static AccessTokenAttributeArray valid;
	
	static AccessTokenAttributeArray getValid() {
		if (valid == null) {
			valid = new AccessTokenAttributeArray();
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

