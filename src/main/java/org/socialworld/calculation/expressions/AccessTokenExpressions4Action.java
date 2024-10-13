package org.socialworld.calculation.expressions;

import org.socialworld.core.IAccessToken;
import org.socialworld.calculation.SimulationCluster;

public final class AccessTokenExpressions4Action implements IAccessToken
{

	private static AccessTokenExpressions4Action valid;
	
	static AccessTokenExpressions4Action getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Action();
			SimulationCluster.action.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
} 