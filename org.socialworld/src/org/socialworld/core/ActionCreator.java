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
import org.socialworld.calculation.Vector;

public class ActionCreator extends Semaphore {

	private static ActionCreator creator;
	Action action;
	
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

	/**
	 * The method creates a new action as a reaction to an event.
	 * The creation is saved by semaphore.
	 *  So only the object, that locked the action creator, can create the reaction object.	  
	 */
	public SemaphoreReturnCode createAction(
			final Animal actor,
			final Event event,
			final Object user) {
		
		if (this.locker == user) {
			
			createReaction(event, actor);
			
			return SemaphoreReturnCode.success;
		}
		if (this.locker == null)
			return SemaphoreReturnCode.notLockedByAnyone;
		return SemaphoreReturnCode.lockedByAnotherUser;
		
		

	}
	
	
	/**
	 * If the caller is the owner of the semaphore (that means the owner of the action object),
	 * the method returns the last created action object.
	 * In other cases it returns null.
	 * 
	 * It is assumed, that the caller locks the semaphore 
	 * and that the method createAction() has been called before the method getAction() is called().
	 */
	public Action getAction(final Object user) {
		if (this.locker == user) 
			return this.action;
		else
			return null;
	}
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
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
}
