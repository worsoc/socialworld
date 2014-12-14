package org.socialworld.calculation;

import org.socialworld.objects.SimulationObject;
import org.socialworld.calculation.Action2EventMapping;
import org.socialworld.core.Action;
import org.socialworld.core.Event;

public class EventCreator  {

	
	

	/**
	 * The method creates a new event for an action.
	 */
	public static Event createEvent(
			final SimulationObject actor,
			final Action action) {
		
			
			return createEvent(action, actor);
			
	}
	
	/**
	 * The method creates a new event.
	 * Therefore an according mapping description is used to set the event properties.
	 * 
	 */
	private static Event createEvent(final Action action,	final SimulationObject actor) {
		return Action2EventMapping.createEvent(action, actor);
	}
	
	
}
