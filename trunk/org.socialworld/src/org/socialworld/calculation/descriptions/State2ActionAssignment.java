package org.socialworld.calculation.descriptions;

import org.socialworld.datasource.State2ActionDescriptionPool;

public class State2ActionAssignment {
	private static State2ActionAssignment instance;
	
	/**
	 * Because of being a singleton the instance is created in a private
	 * constructor.
	 */
	private State2ActionAssignment() {
	}

	/**
	 * The method gets back the only instance of the EventReactionAssignment.
	 * 
	 * @return singleton object of eventReactionAssignment
	 */
	public static State2ActionAssignment getInstance() {
		if (instance == null) {
			instance = new State2ActionAssignment();
		}
		return instance;
	}

	/**
	 * The method gets back the description how an object reacts to the event.
	 * The description depends on the event type and the object' reaction type.
	 * 
	 * @param eventType
	 * @param reactionType	 
	 * @return EventReactionDescription
	 */
	public State2ActionDescription getState2ActionDescription(int state2ActionType) {
		State2ActionDescription state2ActionDescription;
		state2ActionDescription = 
			State2ActionDescriptionPool.getInstance().getDescription(state2ActionType);
		
		return state2ActionDescription;
	}

}
