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
	public Action createAction(	final Animal actor,	final Event event) {
		
			action = null;
			
			createReaction(event, actor);
			
			return action;
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state.
	 *
	 * @param: actor
	 */
	public Action createAction(	final Animal actor) {
		
			action = null;
			
			createReaction(actor);
			
			return action;
	}
	
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
	 * @param: actor
	 * @param: event
	 */
	private void createReaction(final Event event,	final Animal actor) {
		
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
		
		actionType = eventReactionDescription.getActionTypeExpression().evaluateExpression(
						attributeArray);
		actionMode = eventReactionDescription.getActionModeExpression().evaluateExpression(
				attributeArray);
		relativeDirection = eventReactionDescription.getRelativeDirectionExpression().evaluateExpression(
				attributeArray);
		intensity = eventReactionDescription.getIntensityExpression().evaluateExpression(
				attributeArray );
		priority = eventReactionDescription.getPriorityExpression().evaluateExpression(
				attributeArray );
		duration = eventReactionDescription.getDurationExpression().evaluateExpression(
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
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private void createReaction(final Animal actor) {

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

		actionType = state2ActionDescription.getActionTypeExpression().evaluateExpression(
				attributeArray);
		actionMode = state2ActionDescription.getActionModeExpression().evaluateExpression(
				attributeArray);
		relativeDirection = state2ActionDescription.getRelativeDirectionExpression().evaluateExpression(
				attributeArray);
		intensity = state2ActionDescription.getIntensityExpression().evaluateExpression(
				attributeArray );
		priority = state2ActionDescription.getPriorityExpression().evaluateExpression(
				attributeArray );
		duration = state2ActionDescription.getDurationExpression().evaluateExpression(
				attributeArray );

		direction = actor.getMove().getDirection();
		direction.multiply(relativeDirection);
		
		target = null;
		
		minTime = new Time();
		maxTime = new Time(duration);

		action = new Action(actionType , actionMode , target,  direction,
				 intensity,  minTime,  maxTime,	 priority,  duration);

	}

}
