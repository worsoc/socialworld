package org.socialworld.objects.concrete;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;

public abstract class HumanCrafted extends Item {

	public static int getLexemIdHigherValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_HUMANCRAFTED;
	}

	@Override
	protected int getLexemIdHighValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_HUMANCRAFTED;
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return null;
	}

}
