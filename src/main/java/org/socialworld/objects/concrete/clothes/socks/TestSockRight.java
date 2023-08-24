package org.socialworld.objects.concrete.clothes.socks;

import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.clothes.Sock;

public class TestSockRight extends Sock {

	@Override
	public boolean isLeftFootSock() {
		return false;
	}

	@Override
	public boolean isRightFootSock() {
		return true;
	}

	@Override
	protected int getLexemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkObjectBelongsToGroup(short groupNumberSuffix) {
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
