package org.socialworld.attributes;


import java.util.List;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.tools.StringTupel;

public class Direction extends SimProperty {

	private SVVector vector;
	
	float power;

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////  static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(new String[] {"SVVector", PropertyName.direction_vector.name(), PropertyName.direction_vector.toString()}),
			new StringTupel(new String[] {Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.direction_power.name(), PropertyName.direction_power.toString()})
			} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = SimProperty.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}



///////////////////////////////////////////////////////////////////////////////////////////
//////////////////      creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO Herleitung  property name (direction's vector)
	public Direction (PropertyName prop, Vector vector ) {
		this.vector = new SVVector(vector, prop);
		setPropertyName(prop);
	}

	public Direction (PropertyName prop, Vector vector, float power ) {
		this.vector = new SVVector(vector, prop);
		this.power = power;
		setPropertyName(prop);
	}

	public Direction (Type propertyType, Direction original) {
		this.vector = original.getVector();
		this.power = original.getPower();
		setPropertyName(original.getPropertyName().toType(propertyType));
	}
	
	public Direction (PropertyName prop) {
		this.vector = new SVVector(Vector.get0Vector(), prop);
		setPropertyName(prop);
	}

	private Direction(Direction original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		this.vector = original.getVector();
		this.power = original.getPower();
		setPropertyName(original.getPropertyName());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new Direction(this, getPropertyProtection(), cluster);
	}
	
	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String valueName) {
		switch (prop) {
		case direction_vector:
			return new ValueProperty(Type.vector, valueName, getVector());
			// TODO Vector as ISavedValues
			// return this.vector.getAsValue(cluster, valueName);
		case direction_power:
			return new ValueProperty(Type.floatingpoint, valueName, this.power);
			
		default:
			return ValueProperty.getInvalid();
		}

	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Direction  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public final Vector getVector(SimulationCluster cluster) {
		SVVector copy = (SVVector) this.vector.copyForProperty(cluster);
		Vector released = copy.getReleased(cluster);
		return released;
	}

	
	private SVVector getVector() {
		return (SVVector) this.vector.copyForProperty(getPropertyProtection().getCluster());
	}

	public final float getPower() {
		return this.power;
	}

	
}
