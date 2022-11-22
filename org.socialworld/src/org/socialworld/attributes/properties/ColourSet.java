package org.socialworld.attributes.properties;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.PropertyProtection;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;

public class ColourSet extends SimProperty {

	private List<Colour> colours;
	private List<Integer> portions;
	private List<Integer> types;
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private ColourSet(ColourSet original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		this.colours = original.getColours();
		this.portions = original.getPortions();
		this.types = original.getTypes();
		setPropertyName(original.getPropertyName());
	}

	@Override
	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new ColourSet(this, getPropertyProtection(), cluster);
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		switch (propName) {
		case colourSet_mainColour:
			return new ValueProperty(Type.object, valueName, getMainColour());
		
/*		case colourSet_colours:
			return new ValueProperty(Type.vector, valueName, getVector());
		case colourSet_portions:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
		case colourSet_types:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
*/			
		default:
			return ValueProperty.getInvalid();
		}
	}

	private List<Colour> getColours( ) {
		List<Colour> copy = new ArrayList<Colour>();
		for (Colour colour : this.colours) {
			copy.add(colour);
		}
		return copy;
	}
	
	private List<Integer> getPortions( ) {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer portion : this.portions) {
			copy.add(portion);
		}
		return copy;
	}
	
	private List<Integer> getTypes( ) {
		List<Integer> copy = new ArrayList<Integer>();
		for (Integer type : this.types) {
			copy.add(type);
		}
		return copy;
	}

	private Colour getMainColour() {
		int maxPortion = 0;
		Colour colour = null;
		for (int index = 0; index < types.size() ; index++) {
			if (types.get(index) == 1) {
				if (portions.get(index) > maxPortion) {
					maxPortion = portions.get(index);
					colour = colours.get(index);
				}
			}
		}
		return colour;
	}
}
