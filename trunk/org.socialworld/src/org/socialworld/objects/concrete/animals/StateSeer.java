package org.socialworld.objects.concrete.animals;

import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.State;

public class StateSeer extends State {

	private Vector directionView ;

	private float angleViewPerceivingEvents;
	private double  angleViewPerceivingEventsInRadians;
	private float angleViewPerceivingObjects;
	private double  angleViewPerceivingObjectsInRadians;

	private int bestPercipiencePerpendicular;

	private double sizeDistanceRelationThreshold;
	
	public StateSeer() {
		setDirectionView( new Vector(2,1,0));
		setAngleViewPerceivingObjects(20.0F);
		setAngleViewPerceivingEvents(60.0F);
	}
	
	public double getSizeDistanceRelationThreshold() {
		return this.sizeDistanceRelationThreshold;
	}
	
	public Vector getDirectionView() {
		return new Vector(this.directionView);
	}

	public void setDirectionView(Vector directionView) {
		this.directionView = directionView;
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
		this.bestPercipiencePerpendicular =  VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView);

	}
	
}