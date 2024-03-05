/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.core.Event;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.connections.Connection;
import org.socialworld.objects.connections.ConnectionList;
import org.socialworld.objects.connections.ConnectionType;
import org.socialworld.propertyChange.ListenedBase;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringTupel;

/**
 * @author Mathias Sikos
 *
 */
public abstract class StateSimulationObject extends ListenedBase {
	
	private SimulationObject object;
	private	WriteAccessToSimulationObject guard;
	
	private List<State> stateAddOns;
	
	private 	Position 		position = Position.getObjectNothing();
	private		Direction	 directionMove;
	
	private ConnectionList connections;

	private int				state2ActionType;
	private	int				influenceTypeForEventType[];
	private	int				reactionTypeForEventType[];
	private	int				perceptionTypeForEventType[];
	
	private GrantedAccessToProperty grantAccessToPropertyPosition[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	
	
///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected List<StringTupel> listOfPropertyMetaInfo;
	private static StringTupel[] propertiesMetaInfos = new StringTupel[]{
		new StringTupel(new String[] {"Position", PropertyName.simobj_position.name(), PropertyName.simobj_position.toString()}),
		new StringTupel(new String[] {"Direction", PropertyName.simobj_directionMove.name(), PropertyName.simobj_directionMove.toString()}),
		} ;
	
	protected StateSimulationObject(Generation calledFromGeneration) {
		listOfPropertyMetaInfo = new ArrayList<StringTupel>();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}
	
	public List<StringTupel> getPropertiesMetaInfos() {
		return new ArrayList<StringTupel>(listOfPropertyMetaInfo);
	}
	
	public List<StringTupel> getPropMethodsMetaInfos() {
		return new ArrayList<StringTupel>();
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	public StateSimulationObject() {
		
		grantAccessToPropertyPosition = new GrantedAccessToProperty[1];
		grantAccessToPropertyPosition[0] = GrantedAccessToProperty.position;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;
		
		this.directionMove = new Direction(PropertyName.simobj_directionMove);
		//stateAddOns = new ArrayList<State>();
		
	}
		
	final void setObject (SimulationObject object, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.object = object;
			this.connections = new ConnectionList(this.object);
		}
	}
	
	final protected HiddenSimulationObject getMeWritableButHidden(GrantedAccessToProperty properties[]) {
		return guard.getMeHidden(properties);
	}
	
	final protected StateSimulationObject getMeReadableOnly() {
		return this;
	}
	
	final public SimulationObject getObject() {
		return object;
	}
	
	final public int getObjectID() {
		return object.getObjectID();
	}
	
	final void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}

	final boolean checkGuard(WriteAccessToSimulationObject guard) {
		return (this.guard == guard);
	}

	final void initAddOnStates(List<State> states, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			
			if (this.stateAddOns == null) {
				this.stateAddOns = states;
			}
		}
	}

	final void addState(State state, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			
			if (this.stateAddOns == null) {
				this.stateAddOns = new ArrayList<State>();
			}
			
			String className = state.getClass().getName();
			for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
				if (stateAddOns.get(nrStateAddOn).getClass().getName().equals(className)) return;
			}
			this.stateAddOns.add(state);
			
		}
	}
	

	public final ValueProperty getProperty(SimulationCluster cluster, PropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(cluster, prop, name);
	}
	
	public ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String name) {
		
		ValueProperty result;
		PropertyName parentStatePropName;
		
		switch (prop) {
		case simobj_position:
			result = this.position.getAsValue(cluster, name); break;
		case simobj_directionMove:
			result = this.directionMove.getAsValue(cluster, name); break;
		default:
			
			parentStatePropName = getParentStatePropertyName ( cluster, prop,  name);
			if (parentStatePropName != PropertyName.unknown) {
				result = getStateProperty( cluster, parentStatePropName, prop,  name);
			}
			else {
				result = getStateAsProperty( cluster,  prop,  name);
			}
			
		}
		
		return result;
	}


	final void setPosition(Position position, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.position = position;
			setStateProperty(PropertyName.statePerceptible, PropertyName.statePerceptible_position, new ValueProperty(Type.object, PropertyName.statePerceptible_position.name(), position),  guard);
		}
	}
	
	
	
	
	final public Value _getPositionVectorAsValue(String valueName) {
		return Value.getValueNothing();
				//Type.vector, valueName, new Vector(this.position.getProperty(PropertyName.position_vector, "").getVector()) );
		
	}
	
	
	final void setMove(Direction direction, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.directionMove =  direction;
		}
		
	}
	
	final void setInfluenceTypes (int types[], WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.influenceTypeForEventType = types;
		}
	}

	final public int getInfluenceType(int eventType) {
		 return this.influenceTypeForEventType[eventType];
	} 

	final void setReactionTypes (int types[], WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.reactionTypeForEventType = types;
		}
	}

	final public int getReactionType(int eventType) {
		 return this.reactionTypeForEventType[eventType];
	} 

	final void setPerceptionTypes (int types[], WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.perceptionTypeForEventType = types;
		}
	}

	final public int getPerceptionType(int eventType) {
		 return this.perceptionTypeForEventType[eventType];
	} 

	final void setState2ActionType (int type, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.state2ActionType = type;
		}
	}

	final public int getState2ActionType() {
		 return this.state2ActionType;
	} 

	void refresh() {
		
		
	}
	
	void calculateEventInfluence(Event event) {
		
		Scheduler.getInstance().calculatePositionChangedByEvent(event, getMeReadableOnly(), getMeWritableButHidden(grantAccessToPropertyPosition));
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    CONNECTED OBJECTS  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	final void connectEqualTo(SimulationObject object, ConnectionType type, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.connections.connectEqualTo(object, type);
		}
	}

	final void connectAsMasterTo(SimulationObject object, ConnectionType type, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.connections.connectAsMasterTo(object, type);
		}
	}

	final void connectAsSlaveTo(SimulationObject object, ConnectionType type, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.connections.connectAsSlaveTo(object, type);
		}
	}

	final void addConnection(Connection connection, SimulationObject connectedObject, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.connections.add(connection, connectedObject);
		}
	}
	
	final void releaseConnection(Connection connection, SimulationObject connectedObject, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.connections.release(connection, connectedObject);
		}
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////    State Add Ons  /////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	ValueProperty getStateProperty(SimulationCluster cluster, PropertyName propState, PropertyName propSub, String name) {
		
		State stateAddOn;
		ValueProperty result = ValueProperty.getInvalid();
		
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
		
			if (stateAddOn == null) {
				System.out.println("StateSimulationObject.getStateProperty(): stateAddon ist null !!!!!!!!!!! PropertyName: " + propSub.toString());
			}
			else {
				if (stateAddOn.getPropertyName() == propState) {
					result = stateAddOn.getProperty(cluster, propSub, name);
					break;
				}
				
			}
			
		}
		return result;

	}

	ValueProperty getStateProperty(SimulationCluster cluster, PropertyName propSubToTry, String name) {
		
		State stateAddOn;
		ValueProperty result = ValueProperty.getInvalid();
		
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
		
			result = stateAddOn.getProperty(cluster, propSubToTry, name);
			
			if (result.isValid()) {
				break;
			}
			
		}
		
		return result;

	}
	
	ValueProperty getStatePropertyFromMethod(SimulationCluster cluster, PropertyName propState, String methodName, String name) {
		
		State stateAddOn;
		ValueProperty result = ValueProperty.getInvalid();
		
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
		
			if (stateAddOn.getPropertyName() == propState) {
				result = stateAddOn.getPropertyFromMethod(cluster, methodName, name);
				break;
			}
			
		}
		return result;
		
	}

	
	final void setStateProperty(PropertyName propState, PropertyName propSub, ValueProperty something, WriteAccessToSimulationObject guard) {
		
		if (checkGuard(guard)) {
			
			State stateAddOn;
			for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
				
				stateAddOn = stateAddOns.get(nrStateAddOn);
				if (stateAddOn == null){
					System.out.println("stateAddOn ist null");
					continue;
				}
				if (stateAddOn.getPropertyName() == propState) {
					stateAddOn.setProperty(propSub, something );
				}
				
			}
		}
	}

	final void setStateProperty(State stateAddOn, PropertyName propSub, ValueProperty something, WriteAccessToSimulationObject guard) {
		
		if (checkGuard(guard)) {
			
			if (stateAddOn == null){
				System.out.println("stateAddOn ist null");
			}
			else {
				stateAddOn.setProperty(propSub, something );
			}
				
		}
	}

	
	private ValueProperty getStateAsProperty(SimulationCluster cluster, PropertyName prop, String name) {
		State stateAddOn;
		ValueProperty result = ValueProperty.getInvalid();
		
		
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
		
			if ( stateAddOn.getPropertyName() == prop) {
				result = stateAddOn.getAsValue(cluster, name);
				break;
			}
			
		}
		return result;
	}
	
	private PropertyName getParentStatePropertyName(SimulationCluster cluster, PropertyName prop, String name) {
		State stateAddOn;
		
		
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
		
			if (  stateAddOn.getPropertyName() == prop.parentState() ) {
				return stateAddOn.getPropertyName();
			}
			
		}
		return PropertyName.unknown;
	}
	
	
	
	private void callMethod(String stateClassName, String methodName) {
		State stateAddOn;
		for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
			
			stateAddOn = stateAddOns.get(nrStateAddOn);
			
			if (stateAddOn.getClass().getName().equals(stateClassName)) {
				Method method = stateAddOn.getMethod(methodName);
				if (method != null) {
					try {
						method.setAccessible(true);
					    method.invoke(stateAddOn);
		
					// Handle any exceptions thrown by method to be invoked.
					}
					catch (InvocationTargetException ite) {
					    Throwable cause = ite.getCause();
					    System.out.println( cause.getMessage());
					}
					catch (IllegalAccessException iae) {
						iae.printStackTrace();
					} 
					
					// assumption: there is only one method in all state add ons
					break;
				}
			}
		}
	
	}
	
	protected final void setSomething(String stateClassName, String methodName, Object something, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			
			State stateAddOn;
			String classNameStateAddOn;
			
			for (int nrStateAddOn = 0; nrStateAddOn < stateAddOns.size(); nrStateAddOn++) {
				
				stateAddOn = stateAddOns.get(nrStateAddOn);
				if (stateAddOn == null){
					System.out.println("stateAddOn ist null");
					continue;
				}
				classNameStateAddOn = stateAddOn.getClass().getName();
				if (classNameStateAddOn.equals(stateClassName)) {
					stateAddOn.setSomething(methodName, something, this, guard );
				}
			}
		}
	}
		
}
