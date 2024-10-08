package org.socialworld.objects.concrete.animals.birds.galloanserae;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.core.IAccessToken;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.animals.StateRunning;

import org.socialworld.objects.concrete.animals.birds.Galloanserae;
import org.socialworld.objects.enums.EnumBird;

public abstract class Gruiformes extends Galloanserae {

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public Gruiformes() {
		super();
		belongsTo = EnumBird.Gruiformes;
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
	public StateRunning getSavedStateRunning(IAccessToken token) {
		// TODO Auto-generated method stub
		return StateRunning.getObjectNothing();
	}

	@Override
	public ValueProperty getStateRunningAsProperty(IAccessToken token, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
