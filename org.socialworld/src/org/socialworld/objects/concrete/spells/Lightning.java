package org.socialworld.objects.concrete.spells;


import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.Magic;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;

public class Lightning extends Magic {

	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.dynamic);
			return new StatePerceptible(percipience);
		}
		
		return null;
		
	}

}
