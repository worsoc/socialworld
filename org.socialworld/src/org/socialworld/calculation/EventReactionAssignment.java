package org.socialworld.calculation;

public class EventReactionAssignment {
	private static EventReactionAssignment eventReactionAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventReactionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventReactionAssignment.
	 * 
	 * @return singleton object of eventReactionAssignment
	 */
	public static EventReactionAssignment getInstance() {
		if (eventReactionAssignment == null) {
			eventReactionAssignment = new EventReactionAssignment();
		}
		return eventReactionAssignment;
	}

	/**
	 * The method gets back the description how an object reacts to the event.
	 * The description depends on the event type and the object' reaction type.
	 * 
	 * @param eventTyp
	 * @param influenceType	 
	 * @return EventReactionDescription
	 */
	public EventReactionDescription getEventReactionDescription(
			int eventType,	int reactionType) {
		EventReactionDescription eventReactionDescription;
		// TODO (tyloesand) eventReactionDescription mit Wert belegen 
		eventReactionDescription = null;
		
		return eventReactionDescription;
	}

}
