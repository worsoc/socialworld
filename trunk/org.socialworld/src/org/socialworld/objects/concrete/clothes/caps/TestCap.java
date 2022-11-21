package org.socialworld.objects.concrete.clothes.caps;

import org.socialworld.objects.State;
import org.socialworld.objects.WriteAccessToSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.clothes.Cap;

public class TestCap extends Cap {

	@Override
	protected int getLexemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkObjectBelongsToGroup(short groupNumberSuffix) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected State getInitState(String stateClassName) {
		State initState = null;
		
		switch (stateClassName) {
			case "StateAppearance":
				initState = new StateAppearance(this);
				
					
				
		}
		return initState;
	}

}
