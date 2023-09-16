package org.socialworld.objects.concrete.animals.birds.columbaves;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.animals.StateRunning;

import org.socialworld.objects.concrete.animals.birds.Columbaves;
import org.socialworld.objects.enums.EnumBird;

public abstract class Otidimorphae extends Columbaves {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Otidimorphae() {
		super();
		belongsTo = EnumBird.Otidimorphae;
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
