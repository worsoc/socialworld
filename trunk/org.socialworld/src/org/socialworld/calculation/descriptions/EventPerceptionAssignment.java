package org.socialworld.calculation.descriptions;

import org.socialworld.datasource.pool.EventPerceptionDescriptionPool;

public class EventPerceptionAssignment {

	private static EventPerceptionAssignment eventPerceptionAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventPerceptionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventReactionAssignment.
	 * 
	 * @return singleton object of eventReactionAssignment
	 */
	public static EventPerceptionAssignment getInstance() {
		if (eventPerceptionAssignment == null) {
			eventPerceptionAssignment = new EventPerceptionAssignment();
		}
		return eventPerceptionAssignment;
	}

	/**
	 * The method gets back the description how an object reacts to the event.
	 * The description depends on the event type and the object' reaction type.
	 * 
	 * @param eventType
	 * @param reactionType	 
	 * @return EventPerceptionDescription
	 */
	public EventPerceptionDescription getEventPerceptionDescription(
			int eventType,	int reactionType) {
		EventPerceptionDescription eventPerceptionDescription;
		eventPerceptionDescription = 
				EventPerceptionDescriptionPool.getInstance().getDescription(eventType, reactionType);
		
		return eventPerceptionDescription;
	}

}
