package org.socialworld.attributes;

import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;

public interface ISavedValues {

	// protection
	public abstract boolean hasPropertyProtection();
	public abstract PropertyProtection getPropertyProtection();
	public abstract void setPropertyProtection(PropertyProtection propertyProtection);
	public abstract boolean checkHasUseAsPermission(PropertyUsingAs useAsPermission);

	// getting a copy as ValueProperty for a simulation cluster (intersection from argument cluster and the "parent" protection)
	public abstract ValueProperty getAsValue(SimulationCluster cluster);
	public abstract ValueProperty getAsValue(SimulationCluster cluster, String valueName);
	public abstract ISavedValues copyForProperty(SimulationCluster cluster); 
	
	// getting a property with inherited protection
	public abstract ValueProperty getProperty (PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod (String methodName, String valueName);
	
	// getting a property with protection intersection from argument cluster and the "parent" protection
	public abstract ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName);
	public abstract ValueProperty getPropertyFromMethod(SimulationCluster cluster, String methodName, String valueName);

	
}
