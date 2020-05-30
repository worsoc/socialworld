package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;

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
	
	public final  ValueProperty getAsValue() {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new ValueProperty(propertyType, this.propertyName, this.propertyName.toString(), copyForProperty(propertyType));
	}
	
	public final ValueProperty getAsValue(String name) {
		Type propertyType;
		propertyType = this.propertyName.getType();
		return new ValueProperty(propertyType, this.propertyName, name, copyForProperty(propertyType));
	}
	
	public  ValueProperty getProperty(SimPropertyName simPropName, String valueName) {
		return ValueProperty.getInvalid();
	}
	
	public  ValueProperty getProperty(String methodName, String valueName){
		return ValueProperty.getInvalid();
	}


}
