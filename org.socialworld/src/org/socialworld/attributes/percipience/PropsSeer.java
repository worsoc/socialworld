package org.socialworld.attributes.percipience;

import java.util.List;

import org.socialworld.attributes.ISavedValues;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.objects.State;
import org.socialworld.tools.StringTupel;

public class PropsSeer extends SimProperty {

	private float angleViewPerceivingEvents;
	private double  angleViewPerceivingEventsInRadians;
	private float angleViewPerceivingObjects;
	private double  angleViewPerceivingObjectsInRadians;


	private double sizeDistanceRelationThreshold;

	///////////////////////////////////////////////////////////////////////////////////////////
	//////////////////static instance for meta information    ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.propsSeer_angleViewPerceivingEvents.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.propsSeer_angleViewPerceivingEventsInRadians.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.propsSeer_angleViewPerceivingObjects.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.propsSeer_angleViewPerceivingObjectsInRadians.name()),
			new StringTupel(Type.floatingpoint.getIndexWithSWTPraefix(), PropertyName.propsSeer_sizeDistanceRelationThreshold.name())} ;
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;
	
	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = State.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}

	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = State.getPropMethodsMetaInfos();
		for (int indexAdd = 0; indexAdd < propMethodsMetaInfos.length; indexAdd++) {
			listOfPropMethodMetaInfo.add(propMethodsMetaInfos[indexAdd]);
		}
		return listOfPropMethodMetaInfo;
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////// object nothing (abstract method from ISimProperty)    ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static PropsSeer objectNothing;
	
	public static PropsSeer getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new PropsSeer();
			objectNothing.setToObjectNothing();
		}
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}

	private PropsSeer() {
	
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////creating instance for simulation    ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public PropsSeer (PropertyName prop, float angleViewPerceivingEvents, float angleViewPerceivingObjects, 
							double sizeDistanceRelationThreshold) {
		setAngleViewPerceivingEvents(angleViewPerceivingEvents);
		setAngleViewPerceivingObjects(angleViewPerceivingObjects);
		this.sizeDistanceRelationThreshold = sizeDistanceRelationThreshold;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public ISavedValues copyForProperty(SimulationCluster cluster) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName propName, String valueName) {
			
		switch (propName) {
		case propsSeer_angleViewPerceivingEvents:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingEvents);
		case propsSeer_angleViewPerceivingEventsInRadians:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingEventsInRadians);
		case propsSeer_angleViewPerceivingObjects:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingObjects);
		case propsSeer_angleViewPerceivingObjectsInRadians:
			return new ValueProperty(Type.floatingpoint, valueName, this.angleViewPerceivingObjectsInRadians);
		case propsSeer_sizeDistanceRelationThreshold:
			return new ValueProperty(Type.floatingpoint, valueName, this.sizeDistanceRelationThreshold);
		default:
			return ValueProperty.getInvalid();
		}
	}

	public double getSizeDistanceRelationThreshold() {
		return this.sizeDistanceRelationThreshold;
	}
	

	
	public void setAngleViewPerceivingObjects(float angleView) {
		this.angleViewPerceivingObjects = angleView;
		this.angleViewPerceivingObjectsInRadians = Math.toRadians(angleView);
	}

	public void setAngleViewPerceivingEvents(float angleView) {
		this.angleViewPerceivingEvents = angleView;
		this.angleViewPerceivingEventsInRadians = Math.toRadians(angleView);
	}

		
	public float getAngleViewPerceivingObjects() {
		return this.angleViewPerceivingObjects;
	}

	public double getAngleViewPerceivingObjectsInRadians() {
		return this.angleViewPerceivingObjectsInRadians;
	}

	public float getAngleViewPerceivingEvents() {
		return this.angleViewPerceivingEvents;
	}

	public double getAngleViewPerceivingEventsInRadians() {
		return this.angleViewPerceivingEventsInRadians;
	}




}
