package org.socialworld.attributes;

import org.socialworld.calculation.Value;

public interface ISimObjProperty {

	public abstract void setPropertyName(SimObjPropertyName prop);
	public abstract SimObjPropertyName getPropertyName();
	public abstract Value getAsValue();
	public abstract Value getAsValue(String name);
	
}
