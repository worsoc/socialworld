/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.core;

import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.objects.Animal;
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

	final static int LOWEST_EVENT_PRIORITY = 0;
	final static int HIGHEST_EVENT_PRIORITY = 100;
	
	private int		eventTypeAsInt;		
	private EventType eventType;
	
	private int priority;
	private SimulationObject causer;
	private Time time;
	private Position position;

	private IEventParam optionalParam;
	private boolean hasOptionalParams = false;
	
	private Event_Percipience percipience;
	

	/**
	 * Constructor
	 */
	public Event(int eventType,  SimulationObject causer, Time time, Position position,	 IEventParam param) {
		
		this.eventType = EventType.getEventType(eventType);
		this.eventTypeAsInt = eventType;		
		
		this.causer = causer;
		this.time = time;
		this.position = position;

		this.optionalParam = param;
		hasOptionalParams = true;
		
		this.percipience = new Event_Percipience();
	}
	
	/**
	 * Constructor
	 */
	public Event(int eventType, int priority, SimulationObject causer, Time time, Position position,	 IEventParam param) {
		
		this.eventType = EventType.getEventType(eventType);
		this.eventTypeAsInt = eventType;		
		
		this.priority = priority;
		this.causer = causer;
		this.time = time;
		this.position = position;

		this.optionalParam = param;
		hasOptionalParams = true;

		
		this.percipience = new Event_Percipience();
	}	

	/**
	 * Constructor
	 */
	public Event(int eventType,  SimulationObject causer, Time time, Position position) {
		
		this.eventType = EventType.getEventType(eventType);
		this.eventTypeAsInt = eventType;		
		
		this.causer = causer;
		this.time = time;
		this.position = position;


		
		this.percipience = new Event_Percipience();
	}
	
	/**
	 * Constructor
	 */
	public Event(int eventType, int priority) {
		
		this.eventType = EventType.getEventType(eventType);
		this.eventTypeAsInt = eventType;		
		
		this.priority = priority;



		this.percipience = new Event_Percipience();

	}	
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Event event) {
		if (event == null) return 1;
		
		if (this.priority < event.getPriority())
			return -1;
		
		if (this.priority > event.getPriority())
			return 1;
		
		return 0;
	}

	/**
	 * @return the event type
	 */
	public int getEventTypeAsInt() {
		return eventTypeAsInt;
	}

	/**
	 * @return the event type
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            the number of the event type to set
	 */
	public void setEventType(int eventType) {
		this.eventType = EventType.getEventType(eventType);
		this.eventTypeAsInt = eventType;		
	}

	/**
	 * @param eventType
	 *            the number of the event type to set
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType ;
		this.eventTypeAsInt = eventType.getIndex();		
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

	public Vector getDirection() {
		Value direction;;
		
		if (hasOptionalParam()) {
			direction = optionalParam.getParam("direction");
			if (direction.isValid()) 
				return (Vector) direction.getValueCopy();
			
		}
		if (this.causer instanceof Animal)
			return ((Animal) causer).getDirectionChest();
		
		return new Vector(0, 0, 0);
	}

	/**
	 * @return the strength
	 */
	public float getStrength() {
		Value strength;
		if (hasOptionalParam()) {
			strength = optionalParam.getParam("intensity");
			if (strength.isValid()) 
				return (float) strength.getValueCopy();
			
		}
		return 0;
	}


	/**
	 * @return the effectDistance
	 */
	public float getEffectDistance() {
		return this.eventType.getEffectDistance();
	}


	/**
	 * @return the effectAngle
	 */
	public float getEffectAngle() {
		return this.eventType.getEffectAngle();
	}


	public void setPercipience(float maxDistance, float maxSee, float maxHear, float maxSmell, float maxFeel ) {
		this.percipience  = new Event_Percipience( maxDistance,  maxSee,  maxHear,  maxSmell,  maxFeel );
	}

	public boolean hasOptionalParam() {
		return hasOptionalParams;
	}
	
	public void evaluateOptionalParam() {
		if (hasOptionalParams) {
			this.optionalParam.evaluate();
		}
	}
	
	public IEventParam getOptionalParam() {
		return this.optionalParam;
	}
	
	public String toString() {
		return this.eventType + " , " + this.position.toString();
	}
}
