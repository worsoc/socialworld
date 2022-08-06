package org.socialworld.objects.properties;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.concrete.animals.StateDispersibility;

public interface IDispersibility {

public static final String NAME = "IDispersibility";

	public abstract float getWidthWings();
	public abstract float getNumberWings();
	public abstract Direction getDirectionFly();
	
	public StateDispersibility getSavedStateDispersibility(SimulationCluster cluster);
	public ValueProperty getStateDispersibilityAsProperty(SimulationCluster cluster, String name);
}
