package org.socialworld.objects.concrete.animals;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;

public class Ostrich extends Bird implements IRunnable{
	
	private StateRunnable stateRunnable;
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunnable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  float getSpeed() {return 70; };
	public  float getNumberLegs() {return 2; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunnable_directionRun , new Vector(1.6F, 1.7F, 1.8F),30.7F);
	}
	
	
	public StateRunnable getSavedStateRunnable(SimulationCluster cluster) {
	// make a copy as ValueProperty
	ValueProperty vp = this.stateRunnable.getAsValue(cluster);
	// the copy is permitted for cluster only
	return (StateRunnable) vp.getValue();
	}
	
	public ValueProperty getStateRunnableAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunnable.getAsValue(cluster, name);
	}


}
