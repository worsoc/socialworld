package org.socialworld.objects.concrete.animals;

import java.awt.Color;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateBody extends State {

	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setProperty(PropertyName propName, ValueProperty property) {
		// TODO Auto-generated method stub

	}

	public ValueProperty getFaceColour() {
		return new ValueProperty(Type.integer,  "faceColour", Color.PINK.getRGB());
	}

	public ValueProperty getHairColour() {
		return new ValueProperty(Type.integer,  "hairColour", Color.BLACK.getRGB());
	}
	
}
