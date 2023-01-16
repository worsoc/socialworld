package org.socialworld.objects.concrete.animals.birds.palaeognathae;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.concrete.animals.birds.Palaeognathae;

public class Ostrich extends Palaeognathae implements IRunning{
	
	private StateRunning stateRunning = StateRunning.getObjectNothing();
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  float getSpeed() {return 70; };
	public  float getNumberLegs() {return 2; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.6F, 1.7F, 1.8F),30.7F);
	}
	
	
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
	// make a copy as ValueProperty
	ValueProperty vp = this.stateRunning.getAsValue(cluster);
	// the copy is permitted for cluster only
	return (StateRunning) vp.getValue();
	}
	
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunning.getAsValue(cluster, name);
	}


}
