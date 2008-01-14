/**
 * 
 */
package org.socialworld.core;

import org.socialworld.objects.Direction;
import org.socialworld.objects.Position;
import org.socialworld.objects.SimulationObject;

/**
 * @author andre
 * 
 */
public class Event implements Comparable<Event> {

	/**
	 * Priority of this event.
	 */
	private byte priority;
	
    SimulationObject causer;
    int strength;
    long time;

    Position position;
    Direction direction;
    
    float effectDistance;
    float effectAngle;
    float maxDistance;
    float maxSee;
    float maxHear;
    float maxSmell;

	/**
	 * Constructor
	 */
	public Event(byte priority) {
		this.priority = priority;
	}

	/**
	 * @return the priority of the event
	 */
	public int getPriority() {
		return priority;
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

}
