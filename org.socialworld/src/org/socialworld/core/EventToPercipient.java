package org.socialworld.core;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

public class EventToPercipient extends Event {

	/**
	 * Constructor
	 */
	public EventToPercipient(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,  performer);
		
	}
	
	public EventToPercipient(int eventType,  SimulationObject causer, int priority, Position position) {

		super(eventType,   causer,    priority,	  position);

	}
	
}
