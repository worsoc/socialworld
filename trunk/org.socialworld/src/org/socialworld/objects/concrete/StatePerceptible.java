package org.socialworld.objects.concrete;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;

public class StatePerceptible extends State {

	Percipience percipience;
	
	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return this.percipience.checkChanceToBeSeen(possibleSeer);
	}
	
	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		return this.percipience.checkIsPossibleSeer(possibleSeer);
	}

}
