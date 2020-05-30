package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;

public abstract class SimProperty implements ISimProperty {

	private PropertyName propertyName = PropertyName.unknown;

	protected abstract SimProperty copyForProperty(Type propertyType);
	
	///////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public final void setPropertyName(PropertyName prop) {
		if (this.propertyName == PropertyName.unknown) {
			this.propertyName = prop;
		}
	}
	
	public final PropertyName getPropertyName() {
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
	
	public  ValueProperty getProperty(PropertyName simPropName, String valueName) {
		return ValueProperty.getInvalid();
	}
	
	public  ValueProperty getProperty(String methodName, String valueName){
		return ValueProperty.getInvalid();
	}


}
