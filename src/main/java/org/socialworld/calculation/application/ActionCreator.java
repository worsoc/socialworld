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
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.descriptions.EventReactionAssignment;
import org.socialworld.calculation.descriptions.EventReactionDescription;
import org.socialworld.calculation.descriptions.State2ActionAssignment;
import org.socialworld.calculation.descriptions.State2ActionDescription;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;

public class ActionCreator extends SocialWorldThread {

	private static ActionCreator instance;
	
	private CapacityQueue<CollectionElementReactor> reactors;

	private CapacityQueue<CollectionElementActor> actors;
	private static AccessTokenActionCreator token = AccessTokenActionCreator.getValid();
	
	private static String namePropertyActionType = Value.VALUE_BY_NAME_ACTION_TYPE;
	
	private int sizeThreashold = 1000;
	
	private static final int REACTOR_POOL_SIZE = 8192;
	private final CollectionElementReactor[] reactorPool = new CollectionElementReactor[REACTOR_POOL_SIZE];
	private int reactorWriteIndex = 0;

	private static final int ACTOR_POOL_SIZE = 8192;
	private final CollectionElementActor[] actorPool = new CollectionElementActor[ACTOR_POOL_SIZE];
	private int actorWriteIndex = 0;

	// Wiederverwendbare Argumentenliste für Berechnungen beim Akteur
	private final ValueArrayList workingActionArguments = new ValueArrayList();

	// Wiederverwendbare Argumentenliste für Berechnungen beim Reakteur
	private final ValueArrayList workingReactionArguments = new ValueArrayList();

	// Allokationsfreier Sandbox-Puffer für die Thread-Isolierung
	private final ThreadLocal<ValueArrayList> localEvalArgs = 
	    ThreadLocal.withInitial(() -> new ValueArrayList(16));

	// Wiederverwendbarer, veränderlicher Value für objectID 
	private final Value objectID =  Value.getMutable(
			Type.integer, Value.VALUE_BY_NAME_OBJECTID, -1);

	/**
	 * private Constructor. 
	 */
	private ActionCreator() {
		
		this.reactors = new CapacityQueue<CollectionElementReactor>("reactors", 5000);
		this.actors = new CapacityQueue<CollectionElementActor>("actors", 5000);
		
		String[] actionPropertyNames;
		actionPropertyNames = ActionType.getStandardPropertyNames();
		namePropertyActionType = actionPropertyNames[0];

		
		for (int i = 0; i < REACTOR_POOL_SIZE; i++) {
			this.reactorPool[i] = new CollectionElementReactor(null, null, null);
		}
		for (int i = 0; i < ACTOR_POOL_SIZE; i++) {
			this.actorPool[i] = new CollectionElementActor(null, null);
		}

	}

	public static ActionCreator getInstance() {
		if (instance == null) {
			instance = new ActionCreator();
		}
		return instance;
	}
	
	@Override
	public void run() {

	    while (isRunning()) {
	        try {

	        	CollectionElementReactor reactor  = this.reactors.poll(
	        			SocialWorldThread.SLEEPTIME_ACTION_CREATOR, java.util.concurrent.TimeUnit.MILLISECONDS);
	            
	            if (reactor != null) {
	            	calculateReaction(reactor);
	            	
					// Speicher-Referenzen kappen gegen Memory Loitering
					reactor.setEvent(null);
					reactor.setState(null);
					reactor.setHidden(null);
            }

	            
	            CollectionElementActor actor  = this.actors.remove();
	            if (actor != null) {
	            	calculateAction(actor);
	            	
					// Speicher-Referenzen kappen
					actor.setState(null);
					actor.setHidden(null);
	            }


	        } catch (InterruptedException e) {
	            // Sauberes Beenden des Threads bei Simulations-Stopp
	            Thread.currentThread().interrupt();
	            break;
	        }
	    }
	}
	
	
	final void  createReaction( final Event event,	final StateSimulationObject stateSimObj,	final HiddenSimulationObject hiddenSimObj) {
		if (event != null && stateSimObj != null && hiddenSimObj != null) {
			if (this.reactors.size() > sizeThreashold && event.getEventType().isEventToPercipient()) {
				// Event gedrosselt/verworfen
			}
			else {
				// Bitmasken-Recycling statt 'new'
				int targetIdx = reactorWriteIndex & (REACTOR_POOL_SIZE - 1);
				CollectionElementReactor pooledReactor = this.reactorPool[targetIdx];
				
				pooledReactor.setEvent(event);
				pooledReactor.setState(stateSimObj);
				pooledReactor.setHidden(hiddenSimObj);
				
				reactorWriteIndex++;

				if (!this.reactors.add(pooledReactor)) {
					reactorWriteIndex--; // Rollback bei voller Queue
				}
			}
		}
	}
	
	final void createAction(	final StateSimulationObject stateSimObj, final HiddenSimulationObject hiddenSimObj) {
		if (stateSimObj != null && hiddenSimObj != null) {
			// Bitmasken-Recycling statt 'new'
			int targetIdx = actorWriteIndex & (ACTOR_POOL_SIZE - 1);
			CollectionElementActor pooledActor = this.actorPool[targetIdx];
			
			pooledActor.setState(stateSimObj);
			pooledActor.setHidden(hiddenSimObj);
			
			actorWriteIndex++;

			if (!this.actors.add(pooledActor)) {
				actorWriteIndex--; // Rollback bei voller Queue
			}
		}
	}

	/**
	 * The method creates a new action as a reaction to an event and adds it to the reactor's action handler.
	 * 
	 * 
	 */
	private void calculateReaction(CollectionElementReactor reactor) {

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
						if (GlobalSwitches.OUTPUT_CALCULATE_REACTION) {
							System.out.println("ActionCreator.calculateReaction(): Obj " + stateReactor.getObjectID() + ": " + reaction.getType().toString() +  "." + reaction.getMode().toString());
						}
						if (reaction.getType() == ActionType.useWeapon) {
							if (((ActionAttack) reaction).getTarget() == null) {
								if (GlobalSwitches.OUTPUT_CALCULATE_REACTION) {
									System.out.println("UseWeapon from object " + stateReactor.getObjectID() + " to target object null"  );
								}
							}
							else {
								if (GlobalSwitches.OUTPUT_CALCULATE_REACTION) {
									System.out.println("UseWeapon from object " + stateReactor.getObjectID() + " to target object " + ((ActionAttack) reaction).getTarget().getObjectID() );
								}
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
	private void calculateAction(CollectionElementActor actor) {
		
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
					
					if (GlobalSwitches.OUTPUT_CALCULATE_ACTION) {
						System.out.println("ActionCreator.calculateAction(): Obj " + stateActor.getObjectID() + ": " + action.getType().toString() +  "." + action.getMode().toString());
					}
					hiddenActor.setAction(action);
				}
				
			}
			
		}
		else {
			if (GlobalSwitches.OUTPUT_CALCULATE_ACTION) {
				System.out.println("ActionCreator.calculateAction(): actor is null");
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

		int reactorsObjectID;

		// 1. ALLOKATIONSFREIES RECYCLING 
		workingReactionArguments.clear();
		
		// objectID zum stateReactor als Argument setzen
		reactorsObjectID = stateReactor.getObjectID();
		objectID.changeValue(reactorsObjectID);
		workingReactionArguments.add(objectID);

		workingReactionArguments.add( stateReactor.getProperty(token, PropertyName.simobj_attributeArray) );
		workingReactionArguments.add( new Value(Type.event, Value.VALUE_BY_NAME_EVENT, event) );
		if (event.hasOptionalParam()) {
			workingReactionArguments.add( event.getOptionalParam().getParamListAsValue());
		}
		else {
			workingReactionArguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, event.getProperties()));
		}
		workingReactionArguments.add( new Value(Type.simulationObject, Value.VALUE_BY_NAME_SIMOBJECT, stateReactor.getObject()) );
		
		
		// --- STRATEGISCHER FREEZE AM KETTENSTART ---
		ValueArrayList localArgs = localEvalArgs.get();
		localArgs.clear(); // Alten Puffer-Inhalt restlos leeren
		
		// Start-Argumente allokationsfrei in die thread-sichere Sandbox kopieren
		int startSize = workingReactionArguments.size();
		for (int i = 0; i < startSize; i++) {
			localArgs.add(workingReactionArguments.get(i));
		}

		
		Value result = f_CreateReaction.calculate(localArgs);
		
		// Speicherhygiene gegen Memory Loitering: Kappt alle Objekt-Referenzen im Puffer
		localArgs.clear();

		
		return getObjectRequester().requestAction(token, result, this);

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

		int actorsObjectID;

		// 1. ALLOKATIONSFREIES RECYCLING 
		workingActionArguments.clear();

		// objectID zum stateActor als Argument setzen
		actorsObjectID = stateActor.getObjectID();
		objectID.changeValue(actorsObjectID);
		workingActionArguments.add(objectID);
		
		workingActionArguments.add( stateActor.getProperty(token, PropertyName.simobj_attributeArray) );
		
		// --- STRATEGISCHER FREEZE AM KETTENSTART ---
		ValueArrayList localArgs = localEvalArgs.get();
		localArgs.clear(); // Alten Puffer-Inhalt restlos leeren
		
		// Start-Argumente allokationsfrei in die thread-sichere Sandbox kopieren
		int startSize = workingActionArguments.size();
		for (int i = 0; i < startSize; i++) {
			localArgs.add(workingActionArguments.get(i));
		}

		Value result = f_CreateAction.calculate(localArgs);
		
		// Speicherhygiene gegen Memory Loitering: Kappt alle Objekt-Referenzen im Puffer
		localArgs.clear();

		return getObjectRequester().requestAction(token, result, this);
		

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
