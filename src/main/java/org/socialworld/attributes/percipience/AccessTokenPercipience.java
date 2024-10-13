package org.socialworld.attributes.percipience;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenPercipience implements IAccessToken
{

	private static AccessTokenPercipience valid;
	
	static AccessTokenPercipience getValid() {
		if (valid == null) {
			valid = new AccessTokenPercipience();
			SimulationCluster.percipience.addToken(valid);
		}
		return valid;
	}
	
	public boolean isValid() {
		return this == valid;
	}
	
	public SimulationCluster getSimulationCluster() {
		return SimulationCluster.action;
	}
	
}
