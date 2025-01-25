package org.socialworld.objects.concrete.animals;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.core.IAccessToken;

public final class AccessTokenPackageConreteAnimals implements IAccessToken
{

	private static AccessTokenPackageConreteAnimals valid;
	
	static AccessTokenPackageConreteAnimals getValid() {
		if (valid == null) {
			valid = new AccessTokenPackageConreteAnimals();
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

