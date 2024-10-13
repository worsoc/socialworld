package org.socialworld.core;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;

public interface IAccessToken {

	public boolean isValid();
	
	default public SimulationCluster getSimulationCluster() {return SimulationCluster.expressionEvaluate;}
	default  PropertyUsingAs[] getPossibleUsingAs() {return new PropertyUsingAs[0];}
	default boolean isWithoutRestrictions() {return true;}
	
}
