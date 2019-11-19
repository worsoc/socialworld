package org.socialworld.core;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

public class EventToCauser extends Event {

	/**
	 * Constructor
	 */
	public EventToCauser(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,	  performer);
		eventToCauserItself = true;
		
	}
	
	/**
	 * Constructor
	 */
	public EventToCauser(EventType eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType.getIndex(),   causer,  time,  position,	  performer);
		eventToCauserItself = true;

	}

	/**
	 * Constructor
	 */
	public EventToCauser(int eventType, int priority,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType, priority,  causer,  time,  position,	  performer);
		eventToCauserItself = true;

	}

	
}
