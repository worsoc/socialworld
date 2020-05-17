package org.socialworld.objects.concrete.animals;


import org.socialworld.attributes.Direction;
import org.socialworld.attributes.SimPropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.State;

public class StateSeer extends State {

	private Direction directionView ;

	private float angleViewPerceivingEvents;
	private double  angleViewPerceivingEventsInRadians;
	private float angleViewPerceivingObjects;
	private double  angleViewPerceivingObjectsInRadians;

	private int bestPercipiencePerpendicular;

	private double sizeDistanceRelationThreshold;
	
	public StateSeer() {
		setPropertyName(SimPropertyName.simobj_stateSeer);
		setDirectionView( new Vector(2,1,0));
		setAngleViewPerceivingObjects(20.0F);
		setAngleViewPerceivingEvents(60.0F);
	}
	
	private StateSeer(StateSeer original) {
		// TODO implement copy constructor
		setPropertyName(SimPropertyName.simobj_stateSeer);
		this.angleViewPerceivingEvents = original.getAngleViewPerceivingEvents();
	}
	
	protected State copyForProperty(Type propertyType) {
		return new StateSeer(this);
	}

	public Value getProperty(SimPropertyName prop, String name) {
		
		switch (prop) {
		case simobj_directionView:
			return this.directionView.getAsValue(name);
		default:
			return new Value();
		}
	}


	public double getSizeDistanceRelationThreshold() {
		return this.sizeDistanceRelationThreshold;
	}
	

	public void setDirectionView(Vector directionView) {
		this.directionView = new Direction(SimPropertyName.simobj_directionView, directionView);
		setBestPercipiencePerpendicular();
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

	public int getBestPercipiencePerpendicular() {
		return this.bestPercipiencePerpendicular;
	}

	private void setBestPercipiencePerpendicular() {
		this.bestPercipiencePerpendicular =  VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView.getVector());

	}
	
}
