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
	 * The method gets back the only instance of the EventPerceptionAssignment.
	 * 
	 * @return singleton object of eventPerceptionAssignment
	 */
	public static EventPerceptionAssignment getInstance() {
		if (eventPerceptionAssignment == null) {
			eventPerceptionAssignment = new EventPerceptionAssignment();
		}
		return eventPerceptionAssignment;
	}

	/**
	 * The method gets back the description how an object perceives an event.
	 * The description depends on the event type and the object' perception type.
	 * 
	 * @param eventType
	 * @param perceptionType	 
	 * @return EventPerceptionDescription
	 */
	public EventPerceptionDescription getEventPerceptionDescription(
			int eventType,	int perceptionType) {
		EventPerceptionDescription eventPerceptionDescription;
		eventPerceptionDescription = 
				EventPerceptionDescriptionPool.getInstance().getDescription(eventType, perceptionType);
		
		return eventPerceptionDescription;
	}

}
