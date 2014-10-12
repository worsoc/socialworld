package org.socialworld.core;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Animal;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.EventReactionAssignment;
import org.socialworld.calculation.EventReactionDescription;
import org.socialworld.calculation.State2ActionAssignment;
import org.socialworld.calculation.State2ActionDescription;
import org.socialworld.calculation.Vector;

public class ActionCreator  {

	Action action;
	
	public ActionCreator() {
	}


	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: actor
	 * @param: event
	 */
	public Action createAction(	final SimulationObject actor,	final Event event) {
		
			action = null;
			if (actor instanceof Animal) 
				createAnimalReaction(event, (Animal) actor);
			else
				createReaction(event,  actor);
				
			return action;
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state.
	 *
	 * @param: actor
	 */
	public Action createAction(	final SimulationObject actor) {
		
			action = null;
			
			if (actor instanceof Animal) 
				createAnimalReaction( (Animal) actor);
			else
				createReaction(actor);
			return action;
	}
	
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
	 * @param: event
	 * @param: actor
	 */
	private void createAnimalReaction(final Event event,	final Animal actor) {
		
		EventReactionDescription eventReactionDescription;
		int eventType;
		int eventReactionType;
		
		AttributeArray attributeArray;
		
		ActionType actionType;
		ActionMode actionMode;
		SimulationObject target;
		Direction direction;
		double intensity;
		Time minTime;
		Time maxTime;
		int priority;
		long duration;
		
		Vector relativeDirection;
		
		eventType = event.getEventType();
		eventReactionType = actor.getReactionType(eventType);

		eventReactionDescription = 
			EventReactionAssignment.getInstance().getEventReactionDescription(
				eventType, eventReactionType	);
		
		// gets a copy of the actor's attribute array
		// because it is needed only for reading
		// isn't allowed to be changed
		attributeArray = actor.getAttributes();
		
		actionType = (ActionType) eventReactionDescription.getActionTypeExpression().evaluateExpression(
						attributeArray);
		actionMode = (ActionMode) eventReactionDescription.getActionModeExpression().evaluateExpression(
				attributeArray);
		relativeDirection = (Vector) eventReactionDescription.getRelativeDirectionExpression().evaluateExpression(
				attributeArray);
		intensity = (double) eventReactionDescription.getIntensityExpression().evaluateExpression(
				attributeArray );
		priority = (int) eventReactionDescription.getPriorityExpression().evaluateExpression(
				attributeArray );
		duration = (long) eventReactionDescription.getDurationExpression().evaluateExpression(
				attributeArray );
		
		direction = event.getDirection();
		direction.multiply(relativeDirection);
		
		target = event.getCauser();
		
		minTime = new Time();
		maxTime = new Time(duration);
		
		action = new Action(actionType , actionMode , target,  direction,
				 intensity,  minTime,  maxTime,	 priority,  duration);
	}

	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: event
	 * @param: actor
	 */
	private void createReaction(final Event event,	final SimulationObject actor) {

	}
	
	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private void createAnimalReaction(final Animal actor) {

		State2ActionDescription state2ActionDescription;
		AttributeArray attributeArray;
		int state2ActionType;
		
		ActionType actionType;
		ActionMode actionMode;
		SimulationObject target;
		Direction direction;
		double intensity;
		Time minTime;
		Time maxTime;
		int priority;
		long duration;
		Vector relativeDirection;

		state2ActionType = actor.getState2ActionType();
		
		state2ActionDescription = 
			State2ActionAssignment.getInstance().getState2ActionDescription(state2ActionType);

		// gets a copy of the actor's attribute array
		// because it is needed only for reading
		// isn't allowed to be changed
		attributeArray = actor.getAttributes();

		actionType = (ActionType) state2ActionDescription.getActionTypeExpression().evaluateExpression(
				attributeArray);
		actionMode = (ActionMode) state2ActionDescription.getActionModeExpression().evaluateExpression(
				attributeArray);
		relativeDirection = (Vector) state2ActionDescription.getRelativeDirectionExpression().evaluateExpression(
				attributeArray);
		intensity = (double) state2ActionDescription.getIntensityExpression().evaluateExpression(
				attributeArray );
		priority = (int) state2ActionDescription.getPriorityExpression().evaluateExpression(
				attributeArray );
		duration = (long) state2ActionDescription.getDurationExpression().evaluateExpression(
				attributeArray );

		direction = actor.getMove().getDirection();
		direction.multiply(relativeDirection);
		
		target = null;
		
		minTime = new Time();
		maxTime = new Time(duration);

		action = new Action(actionType , actionMode , target,  direction,
				 intensity,  minTime,  maxTime,	 priority,  duration);

	}

	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private void createReaction(final SimulationObject actor) {

	}
	
}
