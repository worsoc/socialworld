package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.calculation.Action2EventMapping;

public class EventCreator  {

	private Action2EventMapping mapping;
	private Event event;
	
	public EventCreator() {
		mapping = Action2EventMapping.getInstance();
	}
	

	/**
	 * The method creates a new event for an action.
	 */
	public Event createEvent(
			final SimulationObject actor,
			final Action action) {
		
			this.event = null;
			
			createEvent(action, actor);
			
			return this.event;
	}
	
	/**
	 * The method creates a new event.
	 * Therefore an according mapping description is used to set the event properties.
	 * 
	 */
	private void createEvent(final Action action,	final SimulationObject actor) {
		event = mapping.createEvent(action, actor);
	}
	
	
}
