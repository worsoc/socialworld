package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IRunnable;
import org.socialworld.objects.concrete.animals.StateRunnable;

public class Husky extends Dog implements IRunnable {
	
	private StateRunnable stateRunnable;

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunnable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getSpeed() {return 50; };
	public  float getNumberLegs() {return 4; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunnable_directionRun , new Vector(1.7F, 1.8F, 1.9F),30.8F);
	}
	
	
	public StateRunnable getSavedStateRunnable(SimulationCluster cluster) {
	//make a copy as ValueProperty
	ValueProperty vp = this.stateRunnable.getAsValue(cluster);
	//the copy is permitted for cluster only
	return (StateRunnable) vp.getValue();
	}
	
	public ValueProperty getStateRunnableAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunnable.getAsValue(cluster, name);
	}
	
	
}
