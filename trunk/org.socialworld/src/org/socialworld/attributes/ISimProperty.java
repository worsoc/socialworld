package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public interface ISimProperty {

	public abstract void setPropertyName(PropertyName prop);
	public abstract PropertyName getPropertyName();
	
	public abstract ValueProperty getAsValue(SimulationCluster cluster);
	public abstract ValueProperty getAsValue(SimulationCluster cluster, String name);
	
	public abstract ValueProperty getProperty(SimulationCluster cluster, PropertyName simPropName, String valueName);
	public abstract ValueProperty getProperty(SimulationCluster cluster, String methodName, String valueName);

}
