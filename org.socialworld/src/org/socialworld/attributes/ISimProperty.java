package org.socialworld.attributes;

import org.socialworld.calculation.ValueProperty;

public interface ISimProperty {

	public abstract void setPropertyName(PropertyName prop);
	public abstract PropertyName getPropertyName();
	
	public abstract ValueProperty getAsValue();
	public abstract ValueProperty getAsValue(String name);
	
	public abstract ValueProperty getProperty(PropertyName simPropName, String valueName);
	public abstract ValueProperty getProperty(String methodName, String valueName);

}
