package org.socialworld.attributes;

import org.socialworld.calculation.Value;

public interface ISimProperty {

	public abstract void setPropertyName(SimPropertyName prop);
	public abstract SimPropertyName getPropertyName();
	public abstract Value getAsValue();
	public abstract Value getAsValue(String name);
	
}
