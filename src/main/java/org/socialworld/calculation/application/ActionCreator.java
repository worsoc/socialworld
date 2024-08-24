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
import org.socialworld.GlobalSwitches;
import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionNothing;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.attack.ActionAttack;
import org.socialworld.actions.bodilyfunctions.ActionBodilyFunction;
import org.socialworld.actions.handle.ActionEquip;
import org.socialworld.actions.handle.ActionExamine;
import org.socialworld.actions.handle.ActionHandle;
import org.socialworld.actions.hear.ActionHear;
import org.socialworld.actions.move.ActionMove;
import org.socialworld.actions.say.ActionSay;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;

public class ActionCreator extends SocialWorldThread {

	private static ActionCreator instance;
	
	private CapacityQueue<CollectionElementReactor> reactors;

	private CapacityQueue<CollectionElementActor> actors;
	
	private static String namePropertyActionType = Value.VALUE_BY_NAME_ACTION_TYPE;
	
	private int sizeThreashold;
	
	/**
	 * private Constructor. 
	 */
	private ActionCreator() {
		
		this.sleepTime = SocialWorldThread.SLEEPTIME_ACTION_CREATOR;
		this.reactors = new CapacityQueue<CollectionElementReactor>("reactors", 1000);
		this.actors = new CapacityQueue<CollectionElementActor>("actors", 1000);
		
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
			
			if (this.reactors.size() > 0) calculateReaction();
			if (this.actors.size() > 0) calculateAction();
			if (this.reactors.size() > 0) calculateReaction();
			if (this.reactors.size() > 0) calculateReaction();
			
			try {
				sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	final void  createReaction( final Event event,	final StateSimulationObject stateSimObj,	final HiddenSimulationObject hiddenSimObj) {
		if (event != null && stateSimObj != null && hiddenSimObj != null) {
			if (this.reactors.size() > sizeThreashold && event.getEventType().isEventToPercipient()) {
				
			}
			else {
				if (!this.reactors.add(new CollectionElementReactor(event, stateSimObj, hiddenSimObj))) {
					// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
				};
			}
		}
	}
	
	final void createAction(	final StateSimulationObject stateSimObj, final HiddenSimulationObject hiddenSimObj) {
		if (stateSimObj != null && hiddenSimObj != null) {
			if (!this.actors.add(new CollectionElementActor(stateSimObj, hiddenSimObj))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
	}

	/**
	 * The method creates a new action as a reaction to an event and adds it to the reactor's action handler.
	 * 
	 * 
	 */
	private void calculateReaction() {

		// DEBUG Output falls size zu groß

		
		if (this.reactors.size() > sizeThreashold) {
			System.out.println("ActionCreator.calculateReaction(): this.reactors.size() " + this.reactors.size());
		}
		
		
		CollectionElementReactor reactor = this.reactors.remove();
		if (reactor != null) {

			
			Event event = reactor.getEvent();
			
			StateSimulationObject stateReactor  = reactor.getState();
			HiddenSimulationObject hiddenReactor =  reactor.getHidden();

			int eventType = event.getEventTypeAsInt();
			int eventReactionType = stateReactor.getReactionType(eventType);
			
			EventReactionDescription eventReactionDescription = 
					EventReactionAssignment.getInstance().getEventReactionDescription(
						eventType, eventReactionType	);
				
			
			// TODO try catch
			int count = eventReactionDescription.countFunctions();
			
			
			AbstractAction reaction;
			FunctionByExpression f_CreateReaction;
			
			for (int index = 0; index < count; index++) 
			{
				f_CreateReaction = eventReactionDescription.getFunction(index);
				
				if (stateReactor instanceof StateAnimal) {
					reaction = createAnimalReaction(event, (StateAnimal) stateReactor, f_CreateReaction);
				}
				else
					reaction = createNoAnimalReaction(event, stateReactor);
					
				if (reaction != null) {
					if (!reaction.isToBeIgnored())	{
						// Logging ...
						System.out.println("ActionCreator.calculateReaction(): Obj " + stateReactor.getObjectID() + ": " + reaction.getType().toString() +  "." + reaction.getMode().toString());
						if (reaction.getType() == ActionType.useWeapon) {
							if (((ActionAttack) reaction).getTarget() == null) {
								System.out.println("UseWeapon from object " + stateReactor.getObjectID() + " to target object null"  );
							}
							else {
								System.out.println("UseWeapon from object " + stateReactor.getObjectID() + " to target object " + ((ActionAttack) reaction).getTarget().getObjectID() );
							}
						}
						// ... Logging
						
						hiddenReactor.setAction(reaction);
					}
				}
			}
				
			
		}
		else {
			if (GlobalSwitches.OUTPUT_DEBUG_ACTIONCREATOR_VARIABLE_IS_NULL) {
				System.out.println("ActionCreator.calculateReaction(): reactor is null");
			}
		}
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state and adds it to the reactor's action handler.
	 *
	 */
	private void calculateAction() {
		

		CollectionElementActor actor = this.actors.remove();
		if (actor != null) {
			
			HiddenSimulationObject hiddenActor = actor.getHidden();
			StateSimulationObject stateActor = actor.getState(); 

			int state2ActionType = stateActor.getState2ActionType();
			State2ActionDescription state2ActionDescription = 
				State2ActionAssignment.getInstance().getState2ActionDescription(state2ActionType);
			int count = state2ActionDescription.countFunctions();
			
			FunctionByExpression f_CreateAction;
			AbstractAction action;
			
			for (int index = 0; index < count; index++) {
				
				f_CreateAction = state2ActionDescription.getFunction(index);
		
				if (stateActor instanceof StateAnimal) 
					action = createAnimalActionByState((StateAnimal)stateActor, f_CreateAction);
				else
					action = createNoAnimalActionByState(stateActor);
				
				if (!action.isToBeIgnored()) {	
					
					System.out.println("ActionCreator.calculateAction(): Obj " + stateActor.getObjectID() + ": " + action.getType().toString() +  "." + action.getMode().toString());
					hiddenActor.setAction(action);
				}
				
			}
			
		}
		else {
			System.out.println("ActionCreator.calculateAction(): actor is null");

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
		
		arguments.add( stateReactor.getProperty(SimulationCluster.action, PropertyName.simobj_attributeArray) );
		arguments.add( new Value(Type.event, Value.VALUE_BY_NAME_EVENT, event) );
		if (event.hasOptionalParam()) {
			arguments.add( event.getOptionalParam().getParamListAsValue());
		}
		else {
			arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, event.getProperties()));
		}
		arguments.add( new Value(Type.simulationObject, Value.VALUE_BY_NAME_SIMOBJECT, stateReactor.getObject()) );
		
		Value result = f_CreateReaction.calculate(arguments);
		return objectRequester.requestAction(SimulationCluster.action, result, this);

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
		
		arguments.add( stateActor.getProperty(SimulationCluster.action, PropertyName.simobj_attributeArray) );
		
		Value result = f_CreateAction.calculate(arguments);
		return objectRequester.requestAction(SimulationCluster.action, result, this);
		

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
		
		Value v = actionProperties.getValue(namePropertyActionType);
		Object o = v.getObject(Type.actionType);
		if (o instanceof NoObject) {
			if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
				System.out.println("ActionCreator.createAction > type: o (getObject(Type.actionType)) is NoObject " + ((NoObject)o).getReason().toString() );
			}
			type = ActionType.ignore;
		}
		else {
			type = (ActionType) o;
		}

		switch (type) {
		case bodilyFunction: 
			action = new ActionBodilyFunction(actionProperties); break;
		case move: 
			action = new ActionMove(actionProperties); break;
		case examine: 
			action = new ActionExamine(actionProperties); break;
		case touch: 
			action = new ActionHandle(actionProperties); break;
		case equip: 
			action = new ActionEquip(actionProperties); break;
		case handleItem: 
			action = new ActionHandle(actionProperties); break;
		case useWeapon: 
			action = new ActionAttack(actionProperties); break;
		case punch: 
			action = new ActionAttack(actionProperties); break;
		case hear: 
			action = new ActionHear(actionProperties); break;
		case talk: 
			action = new ActionSay(actionProperties); break;
		case say: 
			action = new ActionSay(actionProperties); break;
		default: 
			action = ActionNothing.getInstance(); break;
		}
		
		return action;
	}
	

	
}
