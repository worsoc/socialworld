/**
 * 
 */
package org.socialworld.core;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

/**
 * The class collects the properties of an simulation event. A simulation event
 * is released by simulation objects and effects on simulation objects
 * {@link SimulationObject}. An event has a priority (by that an event queue is
 * ordered), an simulation object that causes the event, the release time, the
 * position, the direction, the strength and distanced and ranges where the
 * event has effects to other objects.
 * 
 * @author Andre Schade (circlesmiler), Mathias Sikos (tyloesand)
 */
public class Event implements Comparable<Event> {

	private int		eventType;		
	
	private int priority;
	private SimulationObject causer;
	private Time time;
	private Position position;
	private Direction direction;

	private int strength;

	private float effectDistance;
	private float effectAngle;
	private float maxDistance;
	private float maxSee;
	private float maxHear;
	private float maxSmell;
	private float maxFeel;

	public static final int MAX_EVENT_TYPE = 256;

	/**
	 * Constructor
	 */
	public Event(int eventType, int priority, SimulationObject causer, Time time, Position position,
			Direction direction, int strength, 
			float effectDistance, float effectAngle) {
		
		this.eventType = eventType;		
		
		this.priority = priority;
		this.causer = causer;
		this.time = time;
		this.position = position;
		this.direction = direction;

		this.strength = strength;

		this.effectDistance = effectDistance;
		this.effectAngle = effectAngle;
		
		setDistancesOfNotice();
	}	

	/**
	 * Constructor
	 */
	public Event(int eventType, int priority, int strength, 
			float effectDistance, float effectAngle,
			float maxDistance, float maxSee, float maxHear, float maxSmell, float maxFeel) {
		
		this.eventType = eventType;		
		
		this.priority = priority;

		this.strength = strength;

		this.effectDistance = effectDistance;
		this.effectAngle = effectAngle;
		
		this.maxSee = maxSee;
		this.maxHear = maxHear;
		this.maxSmell = maxSmell;
		this.maxFeel = maxFeel;
		
		if (maxDistance > 0 ) this.maxDistance = maxDistance;
		else {
			maxDistance = maxSee;
			if (maxHear > maxDistance) maxDistance = maxHear;
			if (maxSmell > maxDistance) maxDistance = maxSmell;
			if (maxFeel > maxDistance) maxDistance = maxFeel;
		}
	}	
	
	/**
	 * Constructor
	 */
	public Event(int priority) {
		this.priority = priority;
	}

	private void setDistancesOfNotice( ) {
		this. maxDistance = 100;
		this.maxSee = 100;
		this.maxHear = 100;
		this.maxSmell = 2;
		this.maxFeel = 1;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Event event) {
		if (this.priority < event.getPriority())
			return -1;
		if (this.priority > event.getPriority())
			return 1;
		return 0;
	}

	/**
	 * @return the event type
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the number of the event type to set
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the priority of the event
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the causer
	 */
	public SimulationObject getCauser() {
		return causer;
	}

	/**
	 * @param causer
	 *            the causer to set
	 */
	public void setCauser(SimulationObject causer) {
		this.causer = causer;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * @param strength
	 *            the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * @return the effectDistance
	 */
	public float getEffectDistance() {
		return effectDistance;
	}

	/**
	 * @param effectDistance
	 *            the effectDistance to set
	 */
	public void setEffectDistance(float effectDistance) {
		this.effectDistance = effectDistance;
	}

	/**
	 * @return the effectAngle
	 */
	public float getEffectAngle() {
		return effectAngle;
	}

	/**
	 * @param effectAngle
	 *            the effectAngle to set
	 */
	public void setEffectAngle(float effectAngle) {
		this.effectAngle = effectAngle;
	}

	/**
	 * @return the maxDistance (the distance where the event is able to be
	 *         perceived)
	 */
	public float getMaxDistance() {
		return maxDistance;
	}

	/**
	 * @param maxDistance
	 *            the distance where the event is able to be perceived
	 */
	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	/**
	 * @return the maxSee (the distance where the event is able to be seen)
	 */
	public float getMaxSee() {
		return maxSee;
	}

	/**
	 * @param maxSee
	 *            the distance where the event is able to be seen
	 */
	public void setMaxSee(float maxSee) {
		this.maxSee = maxSee;
	}

	/**
	 * @return the maxHear (the distance where the event is able to be heard)
	 */
	public float getMaxHear() {
		return maxHear;
	}

	/**
	 * @param maxHear
	 *            the distance where the event is able to be heard
	 */
	public void setMaxHear(float maxHear) {
		this.maxHear = maxHear;
	}

	/**
	 * @return the maxSmell (the distance where the event is able to be smelled)
	 */
	public float getMaxSmell() {
		return maxSmell;
	}

	/**
	 * @param maxSmell
	 *            the distance where the event is able to be smelled
	 */
	public void setMaxSmell(float maxSmell) {
		this.maxSmell = maxSmell;
	}

	/**
	 * @return the maxFeel (the distance where the event is able to be felt)
	 */
	public float getMaxFeel() {
		return maxFeel;
	}

	/**
	 * @param maxFeel
	 *            the distance where the event is able to be felt
	 */
	public void setMaxFeel(float maxFeel) {
		this.maxFeel = maxFeel;
	}

	public String toString() {
		return this.eventType + " , " + this.position.toString();
	}
}
