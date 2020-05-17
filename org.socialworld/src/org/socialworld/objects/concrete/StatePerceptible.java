package org.socialworld.objects.concrete;

import org.socialworld.attributes.Position;
import org.socialworld.attributes.SimPropertyName;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;

public class StatePerceptible extends State {

	Percipience percipience;
	
	public StatePerceptible(Percipience percipience) {
		this.percipience = percipience;
	}
	
	private StatePerceptible(StatePerceptible original) {
		// TODO implement copy constructor
	}
	
	protected State copyForProperty(Type propertyType) {
		return new StatePerceptible(this);
	}

	public  Value getProperty(SimPropertyName prop, String name) {
		// TODO implement getProperty()
		return new Value();
	}

	public boolean checkIsPossiblePercipient(Animal possiblePercipient) {
		return this.percipience.checkIsPossiblePercipient(possiblePercipient);
	}

	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return this.percipience.checkChanceToBeSeen(possibleSeer);
	}
	
	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		return this.percipience.checkIsPossibleSeer(possibleSeer);
	}

	public void setPosition(Position position) {
		this.percipience.setPosition(position);
	}

	public void setCuboid(Vector cuboid) {
		this.percipience.setCuboid(cuboid);
	}

}
