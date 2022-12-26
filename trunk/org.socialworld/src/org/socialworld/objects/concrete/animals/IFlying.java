package org.socialworld.objects.concrete.animals;

import org.socialworld.attributes.Direction;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public interface IFlying {

public static final String NAME = "IFlying";

	public abstract float getWidthWings();
	public abstract float getNumberWings();
	public abstract Direction getDirectionFly();
	
	public StateFlying getSavedStateFlying(SimulationCluster cluster);
	public ValueProperty getStateFlyingAsProperty(SimulationCluster cluster, String name);
}
