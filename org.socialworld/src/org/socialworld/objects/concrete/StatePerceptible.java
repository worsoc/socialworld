package org.socialworld.objects.concrete;

import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.State;

public class StatePerceptible extends State {

	Percipience percipience;
	
	public StatePerceptible(Percipience percipience) {
		super();
		this.percipience = percipience;
	}
	
	private StatePerceptible( StatePerceptible original, PropertyProtection protectionOriginal, SimulationCluster cluster) {
		super(protectionOriginal, cluster);
		// TODO implement copy constructor
	}
	
	public State copyForProperty(SimulationCluster cluster) {
		return new StatePerceptible(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		// TODO implement getProperty()
		return ValueProperty.getInvalid();
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
