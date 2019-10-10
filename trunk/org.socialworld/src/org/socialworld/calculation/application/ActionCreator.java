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
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionNothing;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.attack.ActionAttack;
import org.socialworld.actions.handle.ActionHandle;
import org.socialworld.actions.hear.ActionHear;
import org.socialworld.actions.move.ActionMove;
import org.socialworld.actions.say.ActionSay;
import org.socialworld.actions.sleep.ActionSleep;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;

public class ActionCreator extends SocialWorldThread {

	private static ActionCreator instance;
	
	private List<Event> events;
	private List<StateSimulationObject> statesReactor;
	private List<HiddenSimulationObject> hiddenReactors;

	private List<StateSimulationObject> statesActor;
	private List<HiddenSimulationObject> hiddenActors;
	

	/**
	 * private Constructor. 
	 */
	private ActionCreator() {

		this.events = new ArrayList<Event>();
		this.statesReactor = new ArrayList<StateSimulationObject>();
		this.hiddenReactors = new ArrayList<HiddenSimulationObject>();
		
		this.statesActor = new ArrayList<StateSimulationObject>();
		this.hiddenActors = new ArrayList<HiddenSimulationObject>();

	}

	public static ActionCreator getInstance() {
		if (instance == null) {
			instance = new ActionCreator();
		}
		return instance;
	}
	
	public void run() {
		int i=0;
		while (isRunning()) {
			
			i++;
			if (i < 1000) {
				Scheduler.getInstance().setThreadName("Action ");
				Scheduler.getInstance().increment();
			}
			
			
			calculateReaction();
			calculateAction();
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	final void  createReaction( final Event event,	final StateSimulationObject stateSimObj,	final HiddenSimulationObject hiddenSimObj) {
		this.events.add(event);
		this.statesReactor.add(stateSimObj);
		this.hiddenReactors.add(hiddenSimObj);
	}
	
	final void createAction(	final StateSimulationObject stateSimObj, final HiddenSimulationObject hiddenSimObj) {
		this.statesActor.add(stateSimObj);
		this.hiddenActors.add(hiddenSimObj);
	}

	/**
	 * The method creates a new action as a reaction to an event and adds it to the reactor's action handler.
	 * 
	 * 
	 */
	private void calculateReaction() {
	
		
		if (this.hiddenReactors.size() == 0) return;
		
		Event event = this.events.remove(0);
		StateSimulationObject stateReactor  = this.statesReactor.remove(0);
		HiddenSimulationObject hiddenReactor = this.hiddenReactors.remove(0);
		
		
		AbstractAction reaction;
		
		
		if (stateReactor instanceof StateAnimal) 
			reaction = createAnimalReaction(event, (StateAnimal) stateReactor);
		else
			reaction = createNoAnimalReaction(event, stateReactor);
				
		if (!reaction.isToBeIgnored())	hiddenReactor.setAction(reaction);
		
	}
	
	/**
	 * The method creates a new action as a consequence of an simulation object's state and adds it to the reactor's action handler.
	 *
	 */
	private void calculateAction() {
		
		if (this.hiddenActors.size() == 0) return;

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
			
			if (!action.isToBeIgnored())	
				hiddenActor.setAction(action);
			
		}
		
	}
	
	
	/**
	 * The method creates a new action as a reaction to an event.
	 * Therefore an according event reaction description is used to set the (re)action properties.
	 * 
	 * @param: event
	 * @param: stateReactor
	 */
	private AbstractAction createAnimalReaction(Event event, StateAnimal stateReactor ) {
		
	
		int eventType;
		int eventReactionType;
		EventReactionDescription eventReactionDescription;
		FunctionByExpression f_CreateReaction;
		Value[] arguments;
		
		eventType = event.getEventTypeAsInt();
		eventReactionType = stateReactor.getReactionType(eventType);
		
		eventReactionDescription = 
				EventReactionAssignment.getInstance().getEventReactionDescription(
					eventType, eventReactionType	);
		
		f_CreateReaction = eventReactionDescription.getFunctionCreateReaction();
		
		arguments = new Value[2];
		
		arguments[0] = new Value(Type.attributeArray, stateReactor.getAttributes());
		arguments[1] = new Value(Type.event, event);
		
		return (AbstractAction) f_CreateReaction.calculate(arguments).getValue();

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

		Value[] arguments;
		arguments = new Value[1];
		
		arguments[0] = new Value(Type.attributeArray, "attributes", stateActor.getAttributes());
		
		Value result = f_CreateAction.calculate(arguments);
		return (AbstractAction) result.getValue();
//		return (AbstractAction) f_CreateAction.calculate(arguments).getValue();
		

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
	
	
	public static AbstractAction createAction(final ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		
		AbstractAction action;
		
		switch (type) {
		case sleep: action = new ActionSleep(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case move: action = new ActionMove(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case examine: action = new ActionHandle(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case touch: action = new ActionHandle(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case itemAndInventory: action = new ActionHandle(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case handleItem: action = new ActionHandle(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case useWeapon: action = new ActionAttack(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case punch: action = new ActionAttack(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case hear: action = new ActionHear(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case talk: action = new ActionSay(type, mode, intensity, minTime, maxTime, priority, duration); break;
		case say: action = new ActionSay(type, mode, intensity, minTime, maxTime, priority, duration); break;
		default: action = ActionNothing.getInstance(); break;
		}
		
		return action;
	}
	
}
