package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.StateSeer;

public class Dog extends Mammal {

	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.simobject);
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateSeer.class.getName())) {
			return new StateSeer();
		}
		
		return null;
		
	}

}
