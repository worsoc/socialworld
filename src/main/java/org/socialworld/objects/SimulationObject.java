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
package org.socialworld.objects;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionNothing;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.ActionHandler;
import org.socialworld.core.AllWords;
import org.socialworld.core.Event;
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
public abstract class SimulationObject implements IPerceptible {
	
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
	
	public abstract boolean checkObjectBelongsToGroup(int groupNumberSuffix);
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE      ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	

	
	public final ValueProperty getProperty(SimulationCluster cluster, PropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(cluster, prop, name);
	}
	
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		switch (prop) {
		default:
			return this.state.getProperty(cluster, prop, name);
		}
	}
	
	
	public ValueProperty getStateProperty(SimulationCluster cluster, PropertyName propState, PropertyName propSub, String name) {
		return this.state.getStateProperty(cluster, propState, propSub, name);
	}
	
	public ValueProperty getStatePropertyFromMethod(SimulationCluster cluster, PropertyName propState, String methodName, String name) {
		return this.state.getStatePropertyFromMethod(cluster, propState, methodName, name);
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
	public final Position getPosition(SimulationCluster cluster) {
		return (Position) getProperty(cluster, PropertyName.simobj_position).getValue();
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
     	
		event = new EventToPercipient(EventType.percipientExists ,   this /* as causer*/, 
				getSimObjectType().getPercipiencePriority(), this.getPosition(SimulationCluster.percipience));
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
	 * The method determines the reaction to an event.
	 * It creates an action object and inserts it into the action handler list.
	 * 
	 * @param simulationEvent -
	 *            the event influencing the simulation object
	 */
	public final void reactToEvent(final Event simulationEvent) {
	
		if (simulationEvent.getEventType() == EventType.percipientExists) {
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

	final public void addConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.addConnection(connection, connectedObject, this.guard);
	}
	
	final public void releaseConnection(Connection connection,  SimulationObject connectedObject) {
		this.state.releaseConnection(connection, connectedObject, this.guard);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(SimulationCluster cluster, IEventParam paramObject) {
	
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getProperty(cluster, PropertyName.simobj_position));
		propertiesAsValueList.add(getProperty(cluster, PropertyName.simobj_directionMove));

		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
	

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    toString()  //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public String toString() {
		return "" + objectID;
	}
}
