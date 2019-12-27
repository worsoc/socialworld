package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;

public class Dog extends Mammal {

	
	protected State getInitState(String stateClassName) {
		switch (stateClassName) {
		case "StatePerceptible": 
			return new StatePerceptible();
		}
		
		return null;
		
	}

}
