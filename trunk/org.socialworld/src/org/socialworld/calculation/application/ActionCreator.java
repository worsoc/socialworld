package org.socialworld.calculation.application;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Animal;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.core.Action;
import org.socialworld.core.Event;

public class ActionCreator  {

	


	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: actor
	 * @param: event
	 */
	public static Action createAction(	final SimulationObject actor,	final Event event) {
		
			if (actor instanceof Animal) 
				return createAnimalReaction(event, (Animal) actor);
			else
				return createReaction(event,  actor);
				
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state.
	 *
	 * @param: actor
	 */
	public static Action createAction(	final SimulationObject actor) {
		
			
			if (actor instanceof Animal) 
				return createAnimalActionByState( (Animal) actor);
			else
				return createActionByState(actor);
	}
	
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
	 * @param: event
	 * @param: actor
	 */
	private static Action createAnimalReaction(final Event event,	final Animal actor) {
		
	
		int eventType;
		int eventReactionType;
		EventReactionDescription eventReactionDescription;
		FunctionByExpression f_CreateReaction;
		Value[] arguments;
		
		eventType = event.getEventType();
		eventReactionType = actor.getReactionType(eventType);
		
		eventReactionDescription = 
				EventReactionAssignment.getInstance().getEventReactionDescription(
					eventType, eventReactionType	);
		
		f_CreateReaction = eventReactionDescription.getFunctionCreateReaction();
		
		arguments = new Value[2];
		
		arguments[0] = new Value(Type.attributeArray, actor.getAttributes());
		arguments[1] = new Value(Type.event, event);
		
		return (Action) f_CreateReaction.calculate(arguments).getValue();

	}

	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: event
	 * @param: actor
	 */
	private static Action createReaction(final Event event,	final SimulationObject actor) {
		// TODO
		return null;
	}
	
	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private static Action createAnimalActionByState(final Animal actor) {

		int state2ActionType;
		State2ActionDescription state2ActionDescription;
		FunctionByExpression f_CreateAction;
		Value[] arguments;
		
		state2ActionType = actor.getState2ActionType();
		
		state2ActionDescription = 
			State2ActionAssignment.getInstance().getState2ActionDescription(state2ActionType);
		
		f_CreateAction = state2ActionDescription.getFunctionCreateAction();
		
		arguments = new Value[1];
		
		arguments[0] = new Value(Type.attributeArray, actor.getAttributes());
		
		return (Action) f_CreateAction.calculate(arguments).getValue();
		

	}

	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private static Action createActionByState(final SimulationObject actor) {
		// TODO
		return null;
	}
	
}
