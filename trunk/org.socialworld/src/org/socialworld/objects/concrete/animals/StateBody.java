package org.socialworld.objects.concrete.animals;

import java.awt.Color;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;

public class StateBody extends State {

	public static final String VALUENAME_FACE_COLOUR = "faceColour";
	public static final String VALUENAME_HAIR_COLOUR = "hairColour";

	public static final String METHODNAME_GET_FACE_COLOUR = "getFaceColour";
	public static final String METHODNAME_GET_HAIR_COLOUR = "getHairColour";

	protected  void initPropertyName() {
		setPropertyName(PropertyName.stateBody);
	}

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

	protected ValueProperty getFaceColour() {
		return new ValueProperty(Type.integer,  VALUENAME_FACE_COLOUR, Color.PINK.getRGB());
	}

	protected ValueProperty getHairColour() {
		return new ValueProperty(Type.integer,  VALUENAME_HAIR_COLOUR, Color.BLACK.getRGB());
	}
	
}
