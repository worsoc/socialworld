package org.socialworld.attributes;

import org.socialworld.calculation.ValueProperty;

public interface ISimProperty {

	public abstract void setPropertyName(SimPropertyName prop);
	public abstract SimPropertyName getPropertyName();
	
	public abstract ValueProperty getAsValue();
	public abstract ValueProperty getAsValue(String name);
	
	public abstract ValueProperty getProperty(SimPropertyName simPropName, String valueName);
	public abstract ValueProperty getProperty(String methodName, String valueName);

}
