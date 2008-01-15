/**
 * 
 */
package org.socialworld.core;

import org.socialworld.objects.Direction;
import org.socialworld.objects.Position;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Time;

/**
 * @author andre
 * 
 */
public class Event implements Comparable<Event> {

	private byte priority;
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

	/**
	 * Constructor
	 */
	public Event(byte priority) {
		this.priority = priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Event o) {
		if (this.priority < o.getPriority())
			return -1;
		if (this.priority > o.getPriority())
			return 1;
		return 0;
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
	public void setPriority(byte priority) {
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
	 * @return the maxDistance
	 */
	public float getMaxDistance() {
		return maxDistance;
	}

	/**
	 * @param maxDistance
	 *            the maxDistance to set
	 */
	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	/**
	 * @return the maxSee
	 */
	public float getMaxSee() {
		return maxSee;
	}

	/**
	 * @param maxSee
	 *            the maxSee to set
	 */
	public void setMaxSee(float maxSee) {
		this.maxSee = maxSee;
	}

	/**
	 * @return the maxHear
	 */
	public float getMaxHear() {
		return maxHear;
	}

	/**
	 * @param maxHear
	 *            the maxHear to set
	 */
	public void setMaxHear(float maxHear) {
		this.maxHear = maxHear;
	}

	/**
	 * @return the maxSmell
	 */
	public float getMaxSmell() {
		return maxSmell;
	}

	/**
	 * @param maxSmell
	 *            the maxSmell to set
	 */
	public void setMaxSmell(float maxSmell) {
		this.maxSmell = maxSmell;
	}

}
