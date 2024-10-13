package org.socialworld.calculation.expressions;

import org.socialworld.core.IAccessToken;
import org.socialworld.calculation.SimulationCluster;

public final class AccessTokenExpressions4Knowledge implements IAccessToken
{

	private static AccessTokenExpressions4Knowledge valid;
	
	static AccessTokenExpressions4Knowledge getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Knowledge();
			SimulationCluster.knowledge.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.knowledge;
	}

}
