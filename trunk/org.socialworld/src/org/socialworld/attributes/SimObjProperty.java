package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public abstract class SimObjProperty implements ISimObjProperty {

	private SimObjPropertyName propertyName = SimObjPropertyName.unknown;

	protected abstract SimObjProperty copyForProperty();
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    ISimObjProperty  ///////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public void setPropertyName(SimObjPropertyName prop) {
		if (this.propertyName == SimObjPropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public SimObjPropertyName getPropertyName() {
		return this.propertyName;
	}
	
	public Value getAsValue() {
		return new Value(Type.simObjProp, this.propertyName.toString(), copyForProperty());
	}
	
	public Value getAsValue(String name) {
		return new Value(Type.simObjProp, name, copyForProperty());
	}

}
