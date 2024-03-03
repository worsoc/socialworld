package org.socialworld.objects.concrete.animals.birds.aequorlithornithes;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.concrete.animals.IFlying;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateFlying;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.concrete.animals.birds.Aequorlithornithes;
import org.socialworld.objects.enums.EnumBird;



public class Stork extends Aequorlithornithes implements IFlying , IRunning{

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Stork() {
		super();
		belongsTo = EnumBird.Stork;
	}
	
	// add on states
	private StateFlying stateFlying = StateFlying.getObjectNothing();
	private StateRunning stateRunning = StateRunning.getObjectNothing();
	

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IFlying ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getWidthWings() {return 30; };
	public  float getNumberWings() {return 2; };
	public Direction getDirectionFly() {
		return new Direction(PropertyName.stateFlying_directionFly , new Vector(1.2F, 1.3F, 1.4F), 30.5F );
	}

	public StateFlying getSavedStateFlying(SimulationCluster cluster) {
		// make a copy as ValueProperty
		ValueProperty vp = this.stateFlying.getAsValue(cluster);
		// the copy is permitted for cluster only
		return (StateFlying) vp.getObject();
	}
	
	public ValueProperty getStateFlyingAsProperty(SimulationCluster cluster, String name) {
		return this.stateFlying.getAsValue(cluster, name);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public  float getSpeed() {return 5; };
	public  float getNumberLegs() {return 2; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.3F, 1.4F, 1.5F),30.6F);
	}
	
	
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
	// make a copy as ValueProperty
	ValueProperty vp = this.stateRunning.getAsValue(cluster);
	// the copy is permitted for cluster only
	return (StateRunning) vp.getObject();
	}
	
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunning.getAsValue(cluster, name);
	}



}
