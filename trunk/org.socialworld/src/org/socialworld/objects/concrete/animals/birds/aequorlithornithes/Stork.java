package org.socialworld.objects.concrete.animals.birds.aequorlithornithes;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateDispersibility;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.concrete.animals.birds.Aequorlithornithes;
import org.socialworld.objects.properties.IDispersibility;



public class Stork extends Aequorlithornithes implements IDispersibility , IRunning{

	// add on states
	private StateDispersibility stateDispersibility;
	private StateRunning stateRunning;
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IDispersibility ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getWidthWings() {return 30; };
	public  float getNumberWings() {return 2; };
	public Direction getDirectionFly() {
		return new Direction(PropertyName.stateDispersibility_directionFly , new Vector(1.2F, 1.3F, 1.4F), 30.5F );
	}

	public StateDispersibility getSavedStateDispersibility(SimulationCluster cluster) {
		// make a copy as ValueProperty
		ValueProperty vp = this.stateDispersibility.getAsValue(cluster);
		// the copy is permitted for cluster only
		return (StateDispersibility) vp.getValue();
	}
	
	public ValueProperty getStateDispersibilityAsProperty(SimulationCluster cluster, String name) {
		return this.stateDispersibility.getAsValue(cluster, name);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  float getSpeed() {return 5; };
	public  float getNumberLegs() {return 2; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunnable_directionRun , new Vector(1.3F, 1.4F, 1.5F),30.6F);
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
