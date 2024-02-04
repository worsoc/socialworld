package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public class NoSavedValue extends SavedValue {

	private static NoSavedValue valueNothing;
	
	public static NoSavedValue getValueNothing() {
		if (valueNothing == null) {
			valueNothing = new NoSavedValue();
		}
		return valueNothing;
	}
	@Override
	public ISavedValue copyForProperty(SimulationCluster cluster) {
		return valueNothing;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
		return ValueProperty.getInvalid();
	}

	@Override
	public boolean checkIsObjectNothing() {
		return true;
	}

	@Override
	public Object getReleased(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

}
