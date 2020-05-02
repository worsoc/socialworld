package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public abstract class SimProperty implements ISimProperty {

	private SimPropertyName propertyName = SimPropertyName.unknown;

	protected abstract SimProperty copyForProperty(Type propertyType);
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public final void setPropertyName(SimPropertyName prop) {
		if (this.propertyName == SimPropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public final SimPropertyName getPropertyName() {
		return this.propertyName;
	}
	
	public final  Value getAsValue() {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new Value(propertyType, this.propertyName.toString(), copyForProperty(propertyType));
	}
	
	public final Value getAsValue(String name) {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new Value(propertyType, name, copyForProperty(propertyType));
	}

}
