package org.socialworld.calculation.expressions;

import org.socialworld.core.IAccessToken;
import org.socialworld.calculation.SimulationCluster;

public final class AccessTokenExpressions4Attributes implements IAccessToken
{

	private static AccessTokenExpressions4Attributes valid;
	
	static AccessTokenExpressions4Attributes getValid() {
		if (valid == null) {
			valid = new AccessTokenExpressions4Attributes();
			SimulationCluster.attributeArray.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
}

