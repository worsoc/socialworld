/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.calculation.application;

import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Animal;
import org.socialworld.actions.AbstractAction;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.core.Event;

public class ActionCreator  {

	


	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: actor
	 * @param: event
	 */
	public static AbstractAction createAction(	final SimulationObject actor,	final Event event) {
		
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
	public static AbstractAction createAction(	final SimulationObject actor) {
		
			
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
	private static AbstractAction createAnimalReaction(final Event event,	final Animal actor) {
		
	
		int eventType;
		int eventReactionType;
		EventReactionDescription eventReactionDescription;
		FunctionByExpression f_CreateReaction;
		Value[] arguments;
		
		eventType = event.getEventTypeAsInt();
		eventReactionType = actor.getReactionType(eventType);
		
		eventReactionDescription = 
				EventReactionAssignment.getInstance().getEventReactionDescription(
					eventType, eventReactionType	);
		
		f_CreateReaction = eventReactionDescription.getFunctionCreateReaction();
		
		arguments = new Value[2];
		
		arguments[0] = new Value(Type.attributeArray, actor.getAttributes());
		arguments[1] = new Value(Type.event, event);
		
		return (AbstractAction) f_CreateReaction.calculate(arguments).getValue();

	}

	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: event
	 * @param: actor
	 */
	private static AbstractAction createReaction(final Event event,	final SimulationObject actor) {
		// TODO implement the creation of a reaction to an event  (for simulation objects which aren't animals)
		return null;
	}
	
	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private static AbstractAction createAnimalActionByState(final Animal actor) {

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
		
		return (AbstractAction) f_CreateAction.calculate(arguments).getValue();
		

	}

	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: actor
	 */
	private static AbstractAction createActionByState(final SimulationObject actor) {
		// TODO  implement the creation of an action  (for simulation objects which aren't animals)
		return null;
	}
	
}