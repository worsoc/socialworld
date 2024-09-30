package org.socialworld.objects.concrete.clothes.caps;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.clothes.Cap;

public class TestCap extends Cap {



	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		if (groupNumberSuffix == GroupingOfSimulationObjects.GROUPING_NUMBER_SUFFIX_TEST) {
			return true;
		}
		if (groupNumberSuffix == GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CAP) {
			return true;
		}
		
		return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public TestCap() {
		super();
		setLevelObjectSearch(9 /* base 9: 0.2 meters */ , 6 /* base 25: 0,26 meters */);
	}

	@Override
	protected State getInitState(String stateClassName) {
		
		State initState = State.getObjectNothing();
		
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			initState = new StatePerceptible(this);
		}
		else if (stateClassName.equals(StateAppearance.class.getName())) {
			initState = new StateAppearance(this);
		}
		else if (stateClassName.equals(StateComposition.class.getName())) {
			initState = new StateComposition(this);
		}
		
		return initState;
	}

}
