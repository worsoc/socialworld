package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;

public class Dimension extends SimProperty {

	private short heightInMeters;
	private short heightInMilliMeters;
	private short widthInMeters;
	private short widthInMilliMeters;
	private short depthInMeters;
	private short depthInMilliMeters;

	
///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static Dimension objectNothing;
	
	public static Dimension getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Dimension();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}
	
	private Dimension() {
	
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////


	public Dimension (PropertyName prop, short height_m, short heigt_mm, short width_m, short width_mm, short depth_m, short depth_mm ) {
		this.heightInMeters = height_m; 
		this.heightInMilliMeters = heigt_mm;
		this.widthInMeters = width_m;
		this.widthInMilliMeters = width_mm;
		this.depthInMeters = depth_m;
		this.depthInMilliMeters = depth_mm;
		setPropertyName(prop);
	}

	public Dimension (Type propertyType, Dimension original) {
		this.heightInMeters = original.heightInMeters; 
		this.heightInMilliMeters = original.heightInMilliMeters;
		this.widthInMeters = original.widthInMeters;
		this.widthInMilliMeters = original.widthInMilliMeters;
		this.depthInMeters = original.depthInMeters;
		this.depthInMilliMeters = original.depthInMilliMeters;
		setPropertyName(original.getPropertyName().toType(propertyType));
	}

	public Dimension (PropertyName prop) {
		this.heightInMeters = 0; 
		this.heightInMilliMeters = 0;
		this.widthInMeters = 0;
		this.widthInMilliMeters = 0;
		this.depthInMeters = 0;
		this.depthInMilliMeters = 0;
		setPropertyName(prop);
	}

	private Dimension(Dimension original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		this.heightInMeters = original.heightInMeters; 
		this.heightInMilliMeters = original.heightInMilliMeters;
		this.widthInMeters = original.widthInMeters;
		this.widthInMilliMeters = original.widthInMilliMeters;
		this.depthInMeters = original.depthInMeters;
		this.depthInMilliMeters = original.depthInMilliMeters;
		setPropertyName(original.getPropertyName());
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new Dimension(this, getPropertyProtection(), cluster);
	}
	
	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String valueName) {
		switch (prop) {
		case dimension_height_m:
			return new ValueProperty(Type.integer, valueName, heightInMeters);
		case dimension_height_mm:
			return new ValueProperty(Type.integer, valueName, this.heightInMilliMeters);
		case dimension_width_m:
			return new ValueProperty(Type.integer, valueName, this.widthInMeters);
		case dimension_width_mm:
			return new ValueProperty(Type.integer, valueName, this.widthInMilliMeters);
		case dimension_depth_m:
			return new ValueProperty(Type.integer, valueName, this.depthInMeters);
		case dimension_depth_mm:
			return new ValueProperty(Type.integer, valueName, this.depthInMilliMeters);
		
		default:
			return ValueProperty.getInvalid();
		}
	
	}



}
