package org.socialworld.objects.concrete.animals.birds;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.animals.Bird;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.enums.EnumBird;

public abstract class Strisores extends Bird {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Strisores() {
		super();
		belongsTo = EnumBird.Strisores;
	}
	
	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STRISORES;
	}
	


	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return State.getObjectNothing();
	}

	@Override
	public float getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getNumberLegs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Direction getDirectionRun() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateRunning getSavedStateRunning(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return StateRunning.getObjectNothing();
	}

	@Override
	public ValueProperty getStateRunningAsProperty(SimulationCluster cluster, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
