package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.concrete.animals.IRunning;
import org.socialworld.objects.concrete.animals.StateRunning;

public class Husky extends Dog implements IRunning {
	
	private StateRunning stateRunning = StateRunning.getObjectNothing();

	
	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HUSKY;
	}
	
	@Override
	protected int getLexemIdLowValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HUSKY;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// implementing IRunning ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public  float getSpeed() {return 50; };
	public  float getNumberLegs() {return 4; };
	public Direction getDirectionRun() {
		return new Direction(PropertyName.stateRunning_directionRun , new Vector(1.7F, 1.8F, 1.9F),30.8F);
	}
	
	
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
	//make a copy as ValueProperty
	ValueProperty vp = this.stateRunning.getAsValue(cluster);
	//the copy is permitted for cluster only
	return (StateRunning) vp.getValue();
	}
	
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
	return this.stateRunning.getAsValue(cluster, name);
	}
	
	
}
