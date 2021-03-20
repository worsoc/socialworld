package org.socialworld.attributes;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public class Direction extends SimProperty {

	private SVVector vector;
	
	float power;

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////  static instance for meta information    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	private static Direction singletonDummyForGenerationTools;
	private static List<StringPair> listOfPropertyMetaInfo;
	private boolean listOfPropertyMetaInfoIsFilled = false;
	private static StringPair[] propertiesMetaInfos = new StringPair[]{
			new StringPair("SVVector", PropertyName.direction_vector.name()),
			new StringPair(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.direction_power.name())
			} ;

	public static Direction getInstance(Generation calledFromGeneration) {
	if (singletonDummyForGenerationTools == null) {
	singletonDummyForGenerationTools = new Direction(calledFromGeneration);
	}
	return singletonDummyForGenerationTools;
	}
	
	private Direction(Generation calledFromGeneration) 
	{
	
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
	
	public List<StringPair> getPropertiesMetaInfos() {
		if (!listOfPropertyMetaInfoIsFilled) {
			List<StringPair> result = super.getPropertiesMetaInfos();
			for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
				result.add(propertiesMetaInfos[indexAdd]);
			}
			listOfPropertyMetaInfo = result;
			listOfPropertyMetaInfoIsFilled = true;
		}
		return new ArrayList<StringPair>(listOfPropertyMetaInfo);
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
