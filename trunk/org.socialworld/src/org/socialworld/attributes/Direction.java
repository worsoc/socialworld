package org.socialworld.attributes;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.geometry.Vector;

public class Direction extends SimProperty {

	private Vector vector;
	
	float power;
	
	
	public Direction (PropertyName prop, Vector vector ) {
		this.vector = vector;
		setPropertyName(prop);
	}

	public Direction (PropertyName prop, Vector vector, float power ) {
		this.vector = vector;
		this.power = power;
		setPropertyName(prop);
	}

	public Direction (Type propertyType, Direction original) {
		this.vector = original.getVector();
		this.power = original.getPower();
		setPropertyName(original.getPropertyName().toType(propertyType));
	}
	
	public Direction (PropertyName prop) {
		this.vector = vector.get0Vector();
		setPropertyName(prop);
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected SimProperty copyForProperty(Type propertyType) {
		return new Direction(propertyType, this);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Direction  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public final Vector getVector() {
		return new Vector(this.vector);
	}
	
	public final float getPower() {
		return this.power;
	}

	
}
