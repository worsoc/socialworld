package org.socialworld.objects.concrete.spells;


import org.socialworld.objects.Magic;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;

public class Lightning extends Magic {

	protected State getInitState(String stateClassName) {
		switch (stateClassName) {
		case "StatePerceptible": 
			return new StatePerceptible();
		}
		
		return null;
		
	}

}
