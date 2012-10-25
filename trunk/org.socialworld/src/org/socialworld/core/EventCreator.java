package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.calculation.ActionEventMapping;

public class EventCreator  {

	private ActionEventMapping mapping;
	private Event event;
	
	public EventCreator() {
		mapping = ActionEventMapping.getInstance();
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
