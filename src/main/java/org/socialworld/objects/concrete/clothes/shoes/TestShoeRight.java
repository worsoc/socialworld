package org.socialworld.objects.concrete.clothes.shoes;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.clothes.Shoe;

public class TestShoeRight extends Shoe {

	public static int getLexemIdHigherValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_IGNORE;
	}
	public static int getLexemIdLowerValue() {
		return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
	}

	@Override
	protected int getLexemIdHighValue() {
		return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_IGNORE;
	}

	
	@Override
	public boolean isLeftFootShoe() {
		return false;
	}

	@Override
	public boolean isRightFootShoe() {
		return true;
	}

	@Override
	public boolean checkObjectBelongsToGroup(int groupNumberSuffix) {
		if (groupNumberSuffix == GroupingOfSimulationObjects.GROUPING_NUMBER_SUFFIX_TEST) {
			return true;
		}
		return false;
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
