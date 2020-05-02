package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public abstract class SimProperty implements ISimProperty {

	private SimPropertyName propertyName = SimPropertyName.unknown;

	protected abstract SimProperty copyForProperty();
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public void setPropertyName(SimPropertyName prop) {
		if (this.propertyName == SimPropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public SimPropertyName getPropertyName() {
		return this.propertyName;
	}
	
	public Value getAsValue() {
		return new Value(Type.simObjProp, this.propertyName.toString(), copyForProperty());
	}
	
	public Value getAsValue(String name) {
		return new Value(Type.simObjProp, name, copyForProperty());
	}

}
