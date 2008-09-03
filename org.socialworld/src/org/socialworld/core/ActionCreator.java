package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.calculation.EventReactionAssignment;
import org.socialworld.calculation.EventReactionDescription;

public class ActionCreator extends Semaphore {

	private static ActionCreator creator;

	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private ActionCreator() {
		locker = null;
	}

	/**
	 * The method gets back the only instance of the ActionCreator.
	 * 
	 * @return singleton object of ActionCreator
	 */
	public static ActionCreator getInstance() {
		if (creator == null) {
			creator = new ActionCreator();
		}
		return creator;
	}

	public SemaphoreReturnCode createAction(
			SimulationObject actor,
			Event event,
			Object user) {
		
		EventReactionDescription eventReactionDescription;
		int eventType;
		int eventReactionType;
		
		eventType = event.getEventType();
		eventReactionType = actor.getReactionType(eventType);

		eventReactionDescription = 
			EventReactionAssignment.getInstance().getEventReactionDescription(
				eventType, eventReactionType	);

		return SemaphoreReturnCode.success;
	}
}
