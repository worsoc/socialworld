package org.socialworld.objects.concrete.gods;


import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.objects.God;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;

public class Weather extends God {

	

	
	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience();
			return new StatePerceptible(percipience);
		}
		
		return null;
		
	}

}
