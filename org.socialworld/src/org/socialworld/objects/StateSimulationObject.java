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

import org.socialworld.attributes.Position;
import org.socialworld.attributes.SimObjPropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.Scheduler;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.core.Event;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.connections.Connection;
import org.socialworld.objects.connections.ConnectionList;
import org.socialworld.objects.connections.ConnectionType;
import org.socialworld.propertyChange.ListenedBase;

/**
 * @author Mathias Sikos
 *
 */
public abstract class StateSimulationObject extends ListenedBase {
	
	private SimulationObject object;
	private	WriteAccessToSimulationObject guard;
	
	private List<State> stateAddOns;
	
	private 	Position 		position;
	private		Vector directionMove;
	private 	float powerMove;
	
	private ConnectionList connections;

	private int				state2ActionType;
	private	int				influenceTypeForEventType[];
	private	int				reactionTypeForEventType[];
	private	int				perceptionTypeForEventType[];
	
	private GrantedAccessToProperty grantAccessToPropertyPosition[];
	private GrantedAccessToProperty grantAccessToPropertyAction[];
	
	public StateSimulationObject() {
		
		grantAccessToPropertyPosition = new GrantedAccessToProperty[1];
		grantAccessToPropertyPosition[0] = GrantedAccessToProperty.position;

		grantAccessToPropertyAction = new GrantedAccessToProperty[1];
		grantAccessToPropertyAction[0] = GrantedAccessToProperty.action;
		
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
	
	final protected SimulationObject getObject() {
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
	
	public final Value getProperty(SimObjPropertyName prop) {
		String name;
		name = prop.toString();
		return getProperty(prop, name);
	}
	
	public Value getProperty(SimObjPropertyName prop, String name) {
		
		switch (prop) {
		case position:
			return this.position.getAsValue(name);
		default:
			return new Value();
		}
	}


	final void setPosition(Position position, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.position = position;
			setSomething(StatePerceptible.class.getName(), "setPosition", position, guard);
		}
	}
	
	
	final public Position getPosition() {
		return new Position(this.position);
	}
	
	
	final public Value getPositionVectorAsValue(String valueName) {
		return new Value(Type.vector, valueName, new Vector(this.position.getVector()) );
		
	}
	
	
	final void setMove(Vector direction, float power, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.directionMove = direction;
			this.powerMove = power;
		}
		
	}
	
	final public Value getDirectionMoveAsValue(String valueName) {
		return new Value( Type.vector, valueName, new Vector(this.directionMove) );
	}

		
	final public Value getPowerMoveAsValue(String valueName) {
		return new Value( Type.floatingpoint, valueName, powerMove );
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
	
	final void setSomething(String stateClassName, String methodName, Object something, WriteAccessToSimulationObject guard) {
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
					Method method = stateAddOn.getMethod(methodName);
					if (method != null) {
						try {
							method.setAccessible(true);
						    method.invoke(stateAddOn, something);
			
						// Handle any exceptions thrown by method to be invoked.
						}
						catch (InvocationTargetException ite) {
						    Throwable cause = ite.getCause();
						    System.out.println( cause.getMessage());
						}
						catch (IllegalAccessException iae) {
							iae.printStackTrace();
						} 
						catch (IllegalArgumentException iarge) {
							iarge.printStackTrace();
						}
						// assumption: there is only one method in all state add ons
						break;
					}
				}
			}
		}
	}
		
}
