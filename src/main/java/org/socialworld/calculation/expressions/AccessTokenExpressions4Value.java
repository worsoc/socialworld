package org.socialworld.calculation.expressions;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenExpressions4Value implements IAccessToken
{

	private static AccessTokenExpressions4Value valid;
	
	static AccessTokenExpressions4Value getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Value();
			SimulationCluster.expressionCalculate.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.expressionCalculate;
	}
	
}
