package org.socialworld.core;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

public class EventToTarget extends Event {

	/**
	 * Constructor
	 */
	public EventToTarget(int eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType,   causer,  time,  position,	  performer);
		eventToCauserItself = false;
		
	}
	
	/**
	 * Constructor
	 */
	public EventToTarget(EventType eventType,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType.getIndex(),   causer,  time,  position,	  performer);
		eventToCauserItself = false;

	}

	/**
	 * Constructor
	 */
	public EventToTarget(int eventType, int priority,  SimulationObject causer, Time time, Position position,	 ActionPerformer performer) {
		
		super(eventType, priority,  causer,  time,  position,	  performer);
		eventToCauserItself = false;

	}

	
}
