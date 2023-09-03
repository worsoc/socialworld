package org.socialworld.objects.concrete.gods;

import org.socialworld.objects.God;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;

public class Sea extends God {


	protected static int getLexemIdHigherValue() {return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_SEA; }

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	protected int getGNS()  // GroupingNumberSuffix
	{
		return 1;
	}

	@Override
	protected State getInitState(String stateClassName) {
		// TODO Auto-generated method stub
		return State.getObjectNothing();
	}

}
