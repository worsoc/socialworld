package org.socialworld.objects.concrete.eatable;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;

public abstract class Liquid extends Item {

	protected static int getLexemIdHigherValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_LIQUID;
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

}
