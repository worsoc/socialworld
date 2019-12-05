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

import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionNothing;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.attack.ActionAttack;
import org.socialworld.actions.bodilyfunctions.ActionBodilyFunction;
import org.socialworld.actions.handle.ActionHandle;
import org.socialworld.actions.hear.ActionHear;
import org.socialworld.actions.move.ActionMove;
import org.socialworld.actions.say.ActionSay;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;

public class ActionCreator extends SocialWorldThread {

	private static ActionCreator instance;
	
	private List<Event> events;
	private List<StateSimulationObject> statesReactor;
	private List<HiddenSimulationObject> hiddenReactors;

	private List<StateSimulationObject> statesActor;
	private List<HiddenSimulationObject> hiddenActors;
	
	private static String namePropertyActionType = "actiontype";
	
	private int sleepTime = 20;
	private int sizeThreashold;
	
	/**
	 * private Constructor. 
	 */
	private ActionCreator() {
		
		this.events = new ArrayList<Event>();
		this.statesReactor = new ArrayList<StateSimulationObject>();
		this.hiddenReactors = new ArrayList<HiddenSimulationObject>();
		
		this.statesActor = new ArrayList<StateSimulationObject>();
		this.hiddenActors = new ArrayList<HiddenSimulationObject>();
		
		String[] actionPropertyNames;
		actionPropertyNames = ActionType.getStandardPropertyNames();
		namePropertyActionType = actionPropertyNames[0];

		sizeThreashold = (int) 1000 / sleepTime;
	}

	public static ActionCreator getInstance() {
		if (instance == null) {
			instance = new ActionCreator();
		}
		return instance;
	}
	
	public void run() {

		while (isRunning()) {
			
			calculateReaction();
			calculateAction();
			
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	final void  createReaction( final Event event,	final StateSimulationObject stateSimObj,	final HiddenSimulationObject hiddenSimObj) {
		if (event != null && stateSimObj != null && hiddenSimObj != null) {
			this.events.add(event);
			this.statesReactor.add(stateSimObj);
			this.hiddenReactors.add(hiddenSimObj);
		}
	}
	
	final void createAction(	final StateSimulationObject stateSimObj, final HiddenSimulationObject hiddenSimObj) {
		if (stateSimObj != null && hiddenSimObj != null) {
			this.statesActor.add(stateSimObj);
			this.hiddenActors.add(hiddenSimObj);
		}
	}

	/**
	 * The method creates a new action as a reaction to an event and adds it to the reactor's action handler.
	 * 
	 * 
	 */
	private void calculateReaction() {

		// TODO fuer Debuggen
		
		if (this.events.size() > sizeThreashold) {
			System.out.println("ActionCreator.calculateReaction(): this.events.size() " + this.events.size());
		}
		
		
		if ((this.events.size() == 0) ||
			(this.statesReactor.size() == 0) ||
			(this.hiddenReactors.size() == 0))  return;
		
		Event event = this.events.remove(0);
		StateSimulationObject stateReactor  = this.statesReactor.remove(0);
		HiddenSimulationObject hiddenReactor = this.hiddenReactors.remove(0);
	
		
		int eventType = event.getEventTypeAsInt();
		int eventReactionType = stateReactor.getReactionType(eventType);
		EventReactionDescription eventReactionDescription = 
				EventReactionAssignment.getInstance().getEventReactionDescription(
					eventType, eventReactionType	);
		int count = eventReactionDescription.countFunctions();
		
		AbstractAction reaction;
		FunctionByExpression f_CreateReaction;
		
		for (int index = 0; index < count; index++) 
		{
			f_CreateReaction = eventReactionDescription.getFunctionCreateAction(index);
			
			if (stateReactor instanceof StateAnimal) {
				reaction = createAnimalReaction(event, (StateAnimal) stateReactor, f_CreateReaction);
			}
			else
				reaction = createNoAnimalReaction(event, stateReactor);
				
			if (reaction != null) {
				if (!reaction.isToBeIgnored())	{
					System.out.println("ActionCreator.calculateReaction(): Obj: " + stateReactor.getObjectID() + ": " + reaction.getType().toString() +  "." + reaction.getMode().toString());
					hiddenReactor.setAction(reaction);
				}
			}
			
		}
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state and adds it to the reactor's action handler.
	 *
	 */
	private void calculateAction() {
		
		if ((this.statesActor.size() == 0) ||
				(this.hiddenActors.size() == 0))  return;

		HiddenSimulationObject hiddenActor = this.hiddenActors.remove(0);
		StateSimulationObject stateActor  = this.statesActor.remove(0);
		
		int state2ActionType = stateActor.getState2ActionType();
		State2ActionDescription state2ActionDescription = 
			State2ActionAssignment.getInstance().getState2ActionDescription(state2ActionType);
		int count = state2ActionDescription.countFunctions();
		
		FunctionByExpression f_CreateAction;
		AbstractAction action;
		
		for (int index = 0; index < count; index++) {
			
			f_CreateAction = state2ActionDescription.getFunctionCreateAction(index);
	
			if (stateActor instanceof StateAnimal) 
				action = createAnimalActionByState((StateAnimal)stateActor, f_CreateAction);
			else
				action = createNoAnimalActionByState(stateActor);
			
			if (!action.isToBeIgnored()) {	
				
				System.out.println("ActionCreator.calculateAction(): " + action.getType().toString() +  "." + action.getMode().toString());
				hiddenActor.setAction(action);
			}
		}
		
	}
	
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
	 * @param: event
	 * @param: stateReactor
	 */
	private AbstractAction createAnimalReaction(Event event, StateAnimal stateReactor, FunctionByExpression f_CreateReaction ) {
		
		ValueArrayList arguments;
		arguments = new ValueArrayList();
		
		arguments.add( stateReactor.getAttributesAsValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES) );
		arguments.add( new Value(Type.event, Value.VALUE_BY_NAME_EVENT, event) );
		if (event.hasOptionalParam()) {
			arguments.add( event.getOptionalParam().getParamListAsValue());
		}
		
		Value result = f_CreateReaction.calculate(arguments);
		return (AbstractAction) result.getValue();

	}

	/**
	 * The method creates a new action as a reaction to an event.
	 * 
	 * @param: event
	 * @param: stateReactor
	 */
	private  AbstractAction createNoAnimalReaction(Event event, StateSimulationObject stateReactor) {
		// TODO implement the creation of a reaction to an event  (for simulation objects which aren't animals)
		return null;
	}
	
	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: stateActor
	 */
	private AbstractAction createAnimalActionByState(StateAnimal stateActor, FunctionByExpression f_CreateAction) {

		ValueArrayList arguments;
		arguments = new ValueArrayList();
		
		arguments.add( stateActor.getAttributesAsValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES) );
		
		Value result = f_CreateAction.calculate(arguments);
		return (AbstractAction) result.getValue();
		

	}

	/**
	 * The method creates a new action as a consequence of a simulation object's state.
	 * The action depends on the object's attributes.
	 * 
	 * @param: stateActor
	 */
	private  AbstractAction createNoAnimalActionByState(StateSimulationObject stateActor) {
		// TODO  implement the creation of an action  (for simulation objects which aren't animals)
		return null;
	}
	
	
	public static AbstractAction createAction(ValueArrayList actionProperties) {
		
		AbstractAction action;
		ActionType type;
		
		type = (ActionType) actionProperties.getValue(namePropertyActionType).getValue();
		
		switch (type) {
		case bodilyFunction: action = new ActionBodilyFunction(actionProperties); break;
		case move: action = new ActionMove(actionProperties); break;
		case examine: action = new ActionHandle(actionProperties); break;
		case touch: action = new ActionHandle(actionProperties); break;
		case equip: action = new ActionHandle(actionProperties); break;
		case handleItem: action = new ActionHandle(actionProperties); break;
		case useWeapon: action = new ActionAttack(actionProperties); break;
		case punch: action = new ActionAttack(actionProperties); break;
		case hear: action = new ActionHear(actionProperties); break;
		case talk: action = new ActionSay(actionProperties); break;
		case say: action = new ActionSay(actionProperties); break;
		default: action = ActionNothing.getInstance(); break;
		}
		
		return action;
	}
	

	
}
