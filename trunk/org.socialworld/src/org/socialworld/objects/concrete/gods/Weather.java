package org.socialworld.objects.concrete.gods;


import org.socialworld.objects.God;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;

public class Weather extends God {

	

	
	protected State getInitState(String stateClassName) {
		switch (stateClassName) {
		case "StatePerceptible": 
			return new StatePerceptible();
		}
		
		return null;
		
	}

}
