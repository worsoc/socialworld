package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.StateSeer;

public class Dog extends Mammal {

	
	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience();
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateSeer.class.getName())) {
			return new StateSeer();
		}
		
		return null;
		
	}

}
