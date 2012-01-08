package org.socialworld.calculation;


public class EventInfluenceAssignment {

	private static EventInfluenceAssignment eventInfluenceAssignment;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private EventInfluenceAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventInfluenceAssignment.
	 * 
	 * @return singleton object of EventInfluenceAssignment
	 */
	public static EventInfluenceAssignment getInstance() {
		if (eventInfluenceAssignment == null) {
			eventInfluenceAssignment = new EventInfluenceAssignment();
		}
		return eventInfluenceAssignment;
	}

	/**
	 * The method gets back the description how the event effects to the object.
	 * The description depends on the event type and the type of the influence to the object
	 * 
	 * @param eventTyp
	 * @param influenceType	 
	 * @return EventInfluenceDescription
	 */
	public EventInfluenceDescription getEventInfluenceDescription(
			int eventType,	int influenceType) {
		EventInfluenceDescription eventInfluenceDescription;
		// TODO (tyloesand) eventInfluenceDescription mit Wert belegen 
		eventInfluenceDescription= null;
		return eventInfluenceDescription;
	}
}
