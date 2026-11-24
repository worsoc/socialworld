/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects;


import org.socialworld.calculation.geometry.Vector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionNothing;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.IObjectSender;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.IAccessToken;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.AllWords;
import org.socialworld.core.Event;
import org.socialworld.core.TechnicalEvent;
import org.socialworld.core.EventToPercipient;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.core.SearchActionDescription;
import org.socialworld.core.Simulation;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.concrete.*;
import org.socialworld.objects.connections.Connection;
import org.socialworld.objects.connections.ConnectionType;
import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.properties.IPerceptible;
import org.socialworld.objects.statics.Mapping_Base2LexemIDHigherPart;
import org.socialworld.tools.StringTupel;

/**
 * Every simulation object (actor in the simulation) is inherited by the abstract class SimulationObject.
 * 
 * @author Mathias Sikos (MatWorsoc)
 * 
 */
public abstract class SimulationObject implements IObjectSender, IObjectReceiver, IPerceptible {
	
	//private GroupingOfSimulationObjects goso = GroupingOfSimulationObjects.getInstance();
	
	private   EnumBaseSimObj	belongsTo;
	private		int 			objectID;
	
	private boolean 			justCreated;
	private boolean 			initialized = false;
	
	private Lexem				lexem;
	
	
	private StateSimulationObject state;

	// add on states
	private StatePerceptible statePerceptible = StatePerceptible.getObjectNothing();
	private StateAppearance stateAppearance = StateAppearance.getObjectNothing();
	private StateComposition stateComposition = StateComposition.getObjectNothing();
	
	private 	ActionHandler 	actionHandler;
	
	
	
	private static AccessTokenSimulationObject token = AccessTokenSimulationObject.getValid();

	private int levelObjectSearchBase25;
	private int levelObjectSearchBase9;
	
	private	WriteAccessToSimulationObject guard;
	private GrantedAccessToProperty grantAccessToAllProperties[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////   meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
			new StringTupel("StatePerceptible", PropertyName.statePerceptible.name()),
			new StringTupel("StateAppearance", PropertyName.stateAppearance.name()),
			new StringTupel("StateComposition", PropertyName.stateComposition.name())
			} ;
	private static StringTupel[] propMethodsMetaInfos = new StringTupel[] {} ;

	public static List<StringTupel> getPropertiesMetaInfos() {
		List<StringTupel> listOfPropertyMetaInfo = new ArrayList<StringTupel>();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
		return listOfPropertyMetaInfo;
	}
	
	
	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = new ArrayList<StringTupel>();
		for (int indexAdd = 0; indexAdd < propMethodsMetaInfos.length; indexAdd++) {
			listOfPropMethodMetaInfo.add(propMethodsMetaInfos[indexAdd]);
		}
		return listOfPropMethodMetaInfo;
	}
	
	private static KnowledgeFact_Criterion[] resultingKFCs = new KnowledgeFact_Criterion[] {};
	
	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = new ArrayList<KnowledgeFact_Criterion>();
		for (int indexAdd = 0; indexAdd < resultingKFCs.length; indexAdd++) {
			listOfResultingKFCs.add(resultingKFCs[indexAdd]);
		}
		return listOfResultingKFCs;
	}
	
	public abstract boolean isSimulationObject();
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The constructor creates an incomplete simulation object. It's an "empty" object. There is only the object ID.
	 * 
	 */
	public SimulationObject() {
		
		

		this.objectID = 0;
		this.justCreated = true;
		this.guard = null;
		
		
		this.actionHandler = new ActionHandler(this);
		
		grantAccessToAllProperties = new GrantedAccessToProperty[1];
		grantAccessToAllProperties[0] = GrantedAccessToProperty.all;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;

	}

	public void init() {
		
		if (justCreated) {
			this.lexem = AllWords.getLexem(Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ + getLexemIdHighPart() * GroupingOfSimulationObjects.RANGE_FOR_LOWER_VALUE + getLexemIdLowPart());
			//System.out.println(this.belongsTo.toString() + ". " +  this.lexem.getLexemID());
			
			if (!initialized) {
				initialize();
				initialized = true;
			}
		}
		
	}
	
	protected abstract void initialize();
	
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    SIMULATION OBJECT TYPE     ///////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public final SimulationObject_Type getType() { return getSimObjectType();}
	
	protected abstract SimulationObject_Type getSimObjectType();
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    WRITE ACCESS     /////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The method sets write access to a guard.
	 * This guard can manipulate the object's state by calling methods.
	 * Only the guard has access to object manipulation methods.
	 * There is only one guard for a simulation object.
	 * So a guard can only be set if no guard has been set yet.
	 * 
	 * @param guard
	 */
	final void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}
	
	
	protected final boolean checkGuard(WriteAccessToSimulationObject guard) {
		return (this.guard == guard);
	}


	public final boolean isJustCreated() {
		return this.justCreated;
	}

	public final boolean isInitialized() {
		return this.initialized;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////    OBJECT     ///////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
		
	public final void setObjectID(int objectID) {
		if (this.objectID == 0) {
			this.objectID = objectID;
		}
	}
	
	public final int getObjectID() {
		return objectID;
	}
	
	protected abstract int getLexemIdLowPart() ;  // GroupingNumberSuffix
	// TODO assign Enum to belongsTo ... in getLexemIdLowPart() in sub classes

	protected final void setBaseSimObjEnum(EnumBaseSimObj belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	private int getLexemIdHighPart() {
		return Mapping_Base2LexemIDHigherPart.getInstance().get(belongsTo);
	}
	
	public final Lexem getLexem() {
		return this.lexem;
	}
	
	public void setLevelObjectSearch(int levelBase9, int levelBase25) {
		this.levelObjectSearchBase25 = levelBase25;
		this.levelObjectSearchBase9 = levelBase9;
	}

	public int getLevelObjectSearchBase25() {
		return this.levelObjectSearchBase25;
	}

	public int getLevelObjectSearchBase9() {
		return this.levelObjectSearchBase9;
	}

	public abstract boolean checkObjectBelongsToGroup(int groupNumberSuffix);
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	
	public final ValueProperty getProperty(IAccessToken token, PropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(token, prop, name);
	}
	
	public ValueProperty getProperty(IAccessToken token, PropertyName prop, String name) {
		switch (prop) {
		default:
			return this.state.getProperty(token, prop, name);
		}
	}
	
	
	public ValueProperty getStateProperty(IAccessToken token, PropertyName propState, PropertyName propSub, String name) {
		return this.state.getStateProperty(token, propState, propSub, name);
	}
	
	public ValueProperty getStatePropertyFromMethod(IAccessToken token, PropertyName propState, String methodName, String name) {
		return this.state.getStatePropertyFromMethod(token, propState, methodName, name);
	}

	
	
	public final void refreshState() {
		this.state.refresh();
	}
	
	protected final void setState(StateSimulationObject state, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.state = state;
			assignState(state);
		}
	}

	protected abstract void assignState(StateSimulationObject state);

	protected List<State> createAddOnStates() {
		
		List<State> result = new ArrayList<State>();
		
		this.statePerceptible = (StatePerceptible) getInitState(StatePerceptible.class.getName());
		result.add(this.statePerceptible);

		this.stateAppearance = (StateAppearance) getInitState(StateAppearance.class.getName());
		result.add(this.stateAppearance);

		this.stateComposition = (StateComposition) getInitState(StateComposition.class.getName());
		result.add(this.stateComposition);
	
		return result;
		
	};
	
	
	protected abstract State getInitState(String stateClassName);
	
	protected final boolean checkIsMyState(StateSimulationObject state) {
		return (state == this.state);
	}
	
	protected final StateSimulationObject getState(WriteAccessToSimulationObject guard) {
		if (checkGuard(guard))
			return this.state;
		else
			return null;
		
	}

	/**
	 * The method returns the object's position.
	 * 
	 * @return position
	 */
	public final Position getPosition(IAccessToken token) {
		Position position;
		ValueProperty vp = getProperty(token, PropertyName.simobj_position);
		position = getObjectRequester().requestPosition(token, vp, this);
		return position;
	}

	/**
	 * Puts the object at another place of the running simulation and updates the search by position.
	 * Nothing is saved to the database. Only the Action Creation Tool may do this.
	 * 
	 * @param token the token of the caller
	 * @param position the new position
	 */
	public final void relocate(IAccessToken token, Position position) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act) {
			this.state.setPosition(position, this.guard);
			Simulation.getInstance().changePosition(this);
			letCarriedFollow();
		}
	}

	/**
	 * Tells whether the object performs an action at the moment, sleeping included.
	 * Only the Action Creation Tool may ask, for any other caller the answer is false.
	 *
	 * @param token the token of the caller
	 * @return true while an action has been started and is not done yet
	 */
	public final boolean isActing(IAccessToken token) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act) {
			return actionHandler.isActionRunning();
		}
		return false;
	}

	/**
	 * Inserts an action from outside into the object's action list, the same way as
	 * {@link #setAction(AbstractAction, WriteAccessToSimulationObject)} does for the simulation.
	 * From there it is handled like any other action. Only the Action Creation Tool may do this.
	 *
	 * @param token the token of the caller
	 * @param newAction the action to insert
	 * @return false if the caller may not insert or the same action is in the list already
	 */
	public final boolean insertAction(IAccessToken token, AbstractAction newAction) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act) {
			if (actionHandler.findAction(new SearchActionDescription(newAction)) == ActionNothing.getInstance()) {
				actionHandler.insertAction(newAction);
				return true;
			}
		}
		return false;
	}

	/**
	 * Asks the object's action handler to end the running action, e.g. to wake a sleeping human.
	 * The handler does it at its next second, with one last calculation as for an interruption.
	 * Only the Action Creation Tool may do this.
	 *
	 * @param token the token of the caller
	 * @return false if the caller may not stop or no action is running
	 */
	public final boolean stopActualAction(IAccessToken token) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act) {
			return actionHandler.requestStop();
		}
		return false;
	}

	/**
	 * Tells the mode of the running action, e.g. whether the human sleeps or eats.
	 * Only the workbench (cluster act) may ask, for any other caller the answer is null.
	 *
	 * @param token the token of the caller
	 * @return the mode of the running action, null if none runs or the caller may not ask
	 */
	public final ActionMode getRunningActionMode(IAccessToken token) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act) {
			return actionHandler.getRunningActionMode();
		}
		return null;
	}

	/**
	 * Sets one attribute of an animal or a human from outside, a divine intervention without an event.
	 * Only the workbench (cluster act) may do this; for objects without attributes it does nothing.
	 * The attribute calculator may overwrite it if it is just writing a result for this object.
	 *
	 * @param token the token of the caller
	 * @param attribute the attribute to set
	 * @param value the new value, 0..9999
	 * @return false if the caller may not set it or the object has no attributes
	 */
	public final boolean setAttributeFromOutside(IAccessToken token, Attribute attribute, int value) {
		if (token.isValid() && token.getSimulationCluster() == SimulationCluster.act && this.state instanceof StateAnimal) {
			((StateAnimal) this.state).setAttributeFromOutside(attribute, value);
			return true;
		}
		return false;
	}
	

	
	public final int getReactionType(int eventType) {
	 return this.state.getReactionType(eventType);
	} 

	public final int getInfluenceType(int eventType) {
		 return this.state.getInfluenceType(eventType);
		} 

	public final int getState2ActionType() {
		 return this.state.getState2ActionType();
		} 
	
	public abstract PropertyName[] getUsedStateAppearanceColourPropertyNames();
	public abstract byte[] getStateAppearanceMainColourCalculationColourSetsShares();
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    checking for interface  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public final ValueProperty isA(String nameInterface, String nameValue) {
		
		boolean result = isInterface(nameInterface);
		return new ValueProperty(Type.bool, nameValue, result);
	
	}

	protected boolean isInterface(String nameInterface) {
		
		boolean result;
		
		switch (nameInterface) {
			case IPerceptible.NAME:
				result = (this instanceof IPerceptible);
				break;
			default:
				result = false;
		}
		
		return result;

	}
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////// checking whether simulation object belongs to the set //////////////////////
/////////////	(the set is specified by the set number ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
	public final ValueProperty isElementOf(String setNumber, String nameValue) {
		
		int groupNr = Integer.parseInt(setNumber);
		boolean result = belongsToGroup(groupNr);
		return new ValueProperty(Type.bool, nameValue, result);
	
	}
	
	private boolean belongsToGroup(int groupNr) {
		GroupingOfSimulationObjects goso;
		goso = GroupingOfSimulationObjects.getInstance();
		return goso.checkObjectBelongsToGroup(this, groupNr);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////// checking for dynamic method name (using reflection)  ///////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public final ValueProperty check(String methodName, String nameValue) {
		
		boolean result = false; 

		Method method = getMethod(methodName);
		
		if (method != null) {
			
			Class<?>  cls= method.getReturnType();
			if (cls.toString().equals("boolean")) {
				
				Object got = null;
				try {
					method.setAccessible(true);
					got = method.invoke(this);
	
				// Handle any exceptions thrown by method to be invoked.
				}
				catch (InvocationTargetException ite) {
				    Throwable cause = ite.getCause();
				    System.out.println( cause.getMessage());
				}
				catch (IllegalAccessException iae) {
					iae.printStackTrace();
				} 
	
				if (got != null) {
					
					if (got instanceof Boolean) {
						result =  (Boolean) got ;
					}
					
				}
			}

		}

		return new ValueProperty(Type.bool, nameValue, result);
	
	}
	
	private final Method getMethod(String methodName) {
		Method[] allMethods = this.getClass().getMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.equals(methodName)) return m;
		}
		return null;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    implementing IPerceptible     ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public boolean checkIsPossiblePercipient(Animal possiblePercipient) {
		return this.statePerceptible.checkIsPossibleSeer(possiblePercipient);
	}

	
	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return this.statePerceptible.checkChanceToBeSeen(possibleSeer);
	}
	
	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		return this.statePerceptible.checkIsPossibleSeer(possibleSeer);
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ACTION     ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public final void setAction(AbstractAction newAction, WriteAccessToSimulationObject guard) {

		if (this.guard == guard) {
			if (actionHandler.findAction(new SearchActionDescription(newAction)) == ActionNothing.getInstance()) {
				actionHandler.insertAction(newAction);
			}
		}
	}
	
	
	/**
	 * The method lets a simulation object do an action.
	 * Only the object's action handler is allowed to let the object do the action
	 * 
	 * @param action
	 */
	public final void doAction(AbstractAction action, int milliSecondsPast, ActionHandler myActionHandler) {
		if (this.actionHandler == myActionHandler) {
			action.setActor(this, guard.getMeHidden(grantAccessToAllProperties));
			action.perform();
			action.lowerRemainedDuration(milliSecondsPast);
			action.removeWriteAccess();
		}
	}

	public final void letBePerceived() {
		
     	Event event;
     	
		event = new EventToPercipient(EventType.percipientExistsDistance5000 ,   this /* as causer*/, 
				getSimObjectType().getPercipiencePriority(), this.getPosition(token));
		//System.out.println("SimulationObject.letBePerceived: " + getObjectID() + "'sending' percipientExists" );
		Simulation.getInstance().getEventMaster().addEvent(event);

		event = new EventToPercipient(EventType.percipientExistsDistance1000 ,   this /* as causer*/, 
				getSimObjectType().getPercipiencePriority(), this.getPosition(token));
		//System.out.println("SimulationObject.letBePerceived: " + getObjectID() + "'sending' percipientExists" );
		Simulation.getInstance().getEventMaster().addEvent(event);

	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    EVENT      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * The method determines the influence of an event. It calculates how the
	 * event changes the attributes of the simulation object.
	 * 
	 * @param simulationEvent -
	 *            the event influencing the simulation object
	 */
	public final void changeByEvent(final Event simulationEvent) {
		this.state.calculateEventInfluence(simulationEvent);
	}

	/**
	 * The time step of the technical position movement (movement concept 2026-11-19): called once
	 * per second by the PositionCalculator for every object with a velocity; the object hands in its
	 * own write access, the step does not go through the EventMaster.
	 */
	public final void stepPosition() {
		this.state.calculateTechnicalPositionEvent(TechnicalEvent.POSITION_STEP);
	}

	/** The own move action has ended or was interrupted: the active velocity becomes 0 (concept G4). */
	public final void stopActiveMove() {
		this.state.calculateTechnicalPositionEvent(TechnicalEvent.ACTIVE_MOVE_STOP);
	}

	/**
	 * The method determines the reaction to an event.
	 * It creates an action object and inserts it into the action handler list.
	 * 
	 * @param simulationEvent -
	 *            the event influencing the simulation object
	 */
	public final void reactToEvent(final Event simulationEvent) {
	
		if (simulationEvent.getEventType().isLetMeBePerceivedEvent()) {
//			System.out.println("SimulationObject.reactToEvent: "+ getObjectID() + "reacting to percipientExists" );
		}
		Scheduler.getInstance().createReaction(simulationEvent, state, guard.getMeHidden(grantAccessToPropertyAction));
		
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    CONNECTED OBJECTS  ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final public void connectEqualTo(SimulationObject object, ConnectionType type) {
		this.state.connectEqualTo(object, type, this.guard);
	}

	final public void connectAsMasterTo(SimulationObject object, ConnectionType type) {
		this.state.connectAsMasterTo(object, type, this.guard);
	}

	final public void connectAsSlaveTo(SimulationObject object, ConnectionType type) {
		this.state.connectAsSlaveTo(object, type, this.guard);
	}

	/**
	 * This object carries the other one with the offset (in mm, relative to this object's position): the carried
	 * object follows at once and then after every own position step or relocation of this object (2026-11-23).
	 * An object is carried by one carrier at a time; if it is carried already, nothing happens.
	 * Package-private since 2026-11-24 (protection concept S2): only the state of the carrier calls it, after it has
	 * checked the guard of the write access (grant inventory).
	 */
	final void carry(SimulationObject carried, Vector offset) {
		if (carried == null || !carried.isSimulationObject() || carried == this) return;
		if (carried.isCarried()) return;
		this.state.connectAsMasterTo(carried, ConnectionType.carried, offset, this.guard);
		carried.followCarrier();
	}

	/**
	 * Puts the content into this object (a container), connection inside: it follows the container at its place
	 * (offset 0). Whether it fits (capacity, liquids) is checked by the caller (ContainerContent).
	 * Only the rail inventory may fill a container: a valid token of the cluster inventory (protection concept S2,
	 * 2026-11-24). Where the container lies (carried, on the ground, in reach) is decided by the caller.
	 *
	 * @return false without a valid token of the cluster inventory, if the content is carried or inside something
	 *         already, or is this object
	 */
	final public boolean putInside(IAccessToken token, SimulationObject content) {
		if (!isInventoryToken(token)) return false;
		if (content == null || !content.isSimulationObject() || content == this) return false;
		if (content.isCarried()) return false;
		this.state.connectAsMasterTo(content, ConnectionType.inside, Vector.get0Vector(), this.guard);
		content.followCarrier();
		return true;
	}

	/** Takes the content out of this object; it stays where it is. Only with a valid token of the cluster inventory. */
	final public boolean takeOut(IAccessToken token, SimulationObject content) {
		if (!isInventoryToken(token)) return false;
		if (content == null || content.getCarrier() != this || !content.isInside()) return false;
		this.state.releaseConnectionTo(content, this.guard);
		return true;
	}

	/**
	 * The object leaves the simulation (O1, 2026-11-24): its carrier or container lets go of it (and frees the
	 * inventory place), and it lets go of everything it carries or holds; those stay where they are. Only with the
	 * right to remove objects, for now a valid token of the cluster inventory (as ObjectMaster.mayRemove; the
	 * ObjectMaster calls it before it takes the object out of its lists).
	 *
	 * @return false without the right to remove
	 */
	public final boolean leaveSimulation(IAccessToken token) {
		if (!isInventoryToken(token)) return false;
		if (this.state == null) return true;
		SimulationObject carrier = this.state.getCarrier();
		if (carrier.isSimulationObject()) carrier.letGoOf(this);
		this.state.releaseAllConnections(this.guard);
		return true;
	}

	/**
	 * This object (carrier or container) lets go of the held one, which leaves the simulation: the inventory places
	 * it lies on become free, then the connection is released. Package-private: only leaveSimulation calls it.
	 */
	final void letGoOf(SimulationObject held) {
		if (held == null || held.getCarrier() != this) return;
		if (this.state instanceof StateAnimal) ((StateAnimal) this.state).freeInventoryPlacesOf(held, this.guard);
		this.state.releaseConnectionTo(held, this.guard);
	}

	final public boolean isInside() {
		return this.state != null && this.state.isInside();
	}

	/**
	 * @return what lies inside this object (empty for anything that is no container)
	 */
	final public List<SimulationObject> getContents() {
		return (this.state != null) ? this.state.getContents() : new ArrayList<SimulationObject>();
	}

	// only the rail inventory writes the contents of containers (protection concept S2, 2026-11-24)
	private static boolean isInventoryToken(IAccessToken token) {
		return token != null && token.isValid() && token.getSimulationCluster() == SimulationCluster.inventory;
	}

	/**
	 * This object does not carry the other one any longer; it stays where it is.
	 * Package-private since 2026-11-24 (protection concept S2): only the state of the carrier calls it.
	 */
	final void releaseCarried(SimulationObject carried) {
		if (carried == null || carried.getCarrier() != this) return;
		this.state.releaseConnectionTo(carried, this.guard);
	}

	final public boolean isCarried() {
		return this.state != null && this.state.getCarrier().isSimulationObject();
	}

	/**
	 * @return the object that carries this one, NoSimulationObject if none does
	 */
	final public SimulationObject getCarrier() {
		return (this.state != null) ? this.state.getCarrier() : NoSimulationObject.getObjectNothing();
	}

	/**
	 * The carried objects take this object's position plus their offset (technical event, bypass).
	 * Package-private since 2026-11-24 (protection concept S2): from outside only through the write access of this
	 * object with the grant position (HiddenSimulationObject.letCarriedFollow).
	 */
	final void letCarriedFollow() {
		if (this.state == null) return;
		for (SimulationObject carried : this.state.getCarriedObjects()) carried.followCarrier();
	}

	/** This object takes the position of its carrier plus its offset (technical event, bypass). Package-private. */
	final void followCarrier() {
		this.state.calculateTechnicalPositionEvent(TechnicalEvent.FOLLOW_CARRIER);
	}

	final public void addConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.addConnection(connection, connectedObject, this.guard);
	}
	
	final public void releaseConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.releaseConnection(connection, connectedObject, this.guard);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IAccessToken token, IEventParam paramObject) {
	
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getProperty(token, PropertyName.simobj_position));
		propertiesAsValueList.add(getProperty(token, PropertyName.simobj_directionMove));

		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////   implementing IObjectSender ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public int sendYourselfTo(IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	public int sendYourselfTo(IAccessToken token, IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}

	public IObjectSender copy() {
		return this;
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected final ObjectRequester getObjectRequester() {
	    return ObjectRequester.getInstance();
	}

	@Override
	public int receiveObject(int requestID, Object object) {
		getObjectRequester().receive(requestID, object);
		return 0;
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    toString()  //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		return "" + objectID;
	}
}
