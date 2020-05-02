package org.socialworld.attributes;

import org.socialworld.calculation.geometry.Vector;

public class Direction extends SimProperty {

	private Vector vector;
	
	float power;
	
	public Direction (SimPropertyName prop, Vector vector ) {
		this.vector = vector;
		setPropertyName(prop);
	}

	public Direction (SimPropertyName prop, Vector vector, float power ) {
		this.vector = vector;
		this.power = power;
		setPropertyName(prop);
	}

	public Direction (Direction original) {
		this.vector = original.getVector();
		this.power = original.getPower();
		setPropertyName(original.getPropertyName());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISimProperty  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected SimProperty copyForProperty() {
		return new Direction(this);
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
