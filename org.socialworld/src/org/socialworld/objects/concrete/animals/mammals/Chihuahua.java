package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IRunnable;
import org.socialworld.objects.concrete.animals.StateRunnable;

public class Chihuahua extends Dog implements IRunnable{

	private StateRunnable stateRunnable;

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunnable ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getSpeed() {return 15; };
	public  float getNumberLegs() {return 4; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunnable_directionRun , new Vector(1.4F, 1.5F, 1.6F),30.4F);
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
