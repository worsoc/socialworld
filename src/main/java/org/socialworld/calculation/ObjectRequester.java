/*
 * Social World
 * Copyright (C) 2020  Mathias Sikos
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

package org.socialworld.calculation;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import org.socialworld.core.IAccessToken;
import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.NoAction;
import org.socialworld.actions.handle.Inventory;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Dimension;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.EnumSimProperty;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SimProperty;
import org.socialworld.attributes.percipience.PropsSeer;
import org.socialworld.attributes.properties.ColourSet;
import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.knowledge.KnowledgeItem;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.NoKnowledgeItem;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.concrete.animals.StateFlying;
import org.socialworld.objects.concrete.animals.StateRunning;
import org.socialworld.objects.concrete.animals.StateSeer;

public class ObjectRequester {

	
	private static ObjectRequester instance;

	public static synchronized ObjectRequester getInstance() {
	    if (instance == null) {
	        instance = new ObjectRequester();
	    }
	    return instance;
	}

	// 2. INTERNE KAPSELUNG: Jeder Thread bekommt seinen eigenen Daten-Container
	private static class ThreadData {
		final HashMap<Integer, Object> receivedObjects = new HashMap<>();
		final Queue<Integer> freeRequestIDs = new ArrayDeque<>();
		int maxIDCount = 0;
	}
	
	// Das ThreadLocal verwaltet die Container vollautomatisch pro Thread
	private final ThreadLocal<ThreadData> threadContainer = ThreadLocal.withInitial(ThreadData::new);

	// Privater Konstruktor verhindert unkontrolliertes "ObjectRequester.getInstance()" im System
	private ObjectRequester() {}

//	private Hashtable<Integer, Object> receivedObjects = new Hashtable<Integer, Object>();
//	private List<Integer> reservedRequestIDs = new ArrayList<Integer>();
	
	public void receive(int requestID, Object object) {
		
		// Holt automatisch den Container des aktuell aufrufenden Threads
		ThreadData data = threadContainer.get();

		if (!data.receivedObjects.containsKey(requestID)) {
			data.receivedObjects.put(requestID, object);
		}
	}
	
	private Object getObjectNothing(Type type, EnumSimProperty simProperty) {
		switch (type) {
			case valueList:
				return ValueArrayList.getObjectNothing();
			case attributeArray:
				return AttributeArray.getObjectNothing();
			case enumProp:
				switch (simProperty) {
					case nutrientSet:
						return NutrientSet.getObjectNothing();
					case tasteSet:
						return TasteSet.getObjectNothing();
					case colourSet:
						return ColourSet.getObjectNothing();
					default:
						return NoObject.getNoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
				}
			case simObjProp:
				switch (simProperty) {
					case position:
						return Position.getObjectNothing();
					case direction:
						return Direction.getObjectNothing();
					case dimension:
						return Dimension.getObjectNothing();
					case inventory:
						return Inventory.getObjectNothing();
					case propsSeer:
						return PropsSeer.getObjectNothing();
					case stateSeer:
						return StateSeer.getObjectNothing();
					case stateRunning:
						return StateRunning.getObjectNothing();
					case stateFlying:
						return StateFlying.getObjectNothing();
					default:
						return NoObject.getNoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
				}
			case simulationObject:
				return NoSimulationObject.getObjectNothing();
			case action:
				return NoAction.getObjectNothing();
			case knowledgeElement:
				return KnowledgeElement.getObjectNothing();
			case knowledgeSource:
				return KnowledgeSource.getObjectNothing();
			case knowledgeAtom:
				return NoKnowledgeItem.getObjectNothing();
			case answer:
				return (IAnswer) NoKnowledgeItem.getObjectNothing();

			default:
				return NoObject.getNoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
		}
	}
	
	
	public AttributeArray requestAttributeArray(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (AttributeArray) requestObject(value, Type.attributeArray, EnumSimProperty.noSimProperty, token, receiver);
	}
	
	public SimulationObject requestSimulationObject(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (SimulationObject) requestObject(value, Type.simulationObject, EnumSimProperty.noSimProperty, token, receiver);
	}

	public NutrientSet requestNutrientSet(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.nutrientSet, token, receiver);
		if (simProperty instanceof NutrientSet) {
			return (NutrientSet) simProperty;
		}
		else {
			return NutrientSet.getObjectNothing();
		}
		
	}
	
	public TasteSet requestTasteSet(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.tasteSet, token, receiver);
		if (simProperty instanceof TasteSet) {
			return (TasteSet) simProperty;
		}
		else {
			return TasteSet.getObjectNothing();
		}
		
	}
	
	public ColourSet requestColourSet(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.colourSet, token, receiver);
		if (simProperty instanceof ColourSet) {
			return (ColourSet) simProperty;
		}
		else {
			return ColourSet.getObjectNothing();
		}
		
	}
	
	public Position requestPosition(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		Type type = value.getType();
		if ((type == Type.simObjProp) || (type == Type.eventProp)) {
			SimProperty simProperty = (SimProperty) requestObject(value, type, EnumSimProperty.position, token, receiver);
			if (simProperty instanceof Position) {
				return (Position) simProperty;
			}
			else {
				return Position.getObjectNothing();
			}
		}
		else {
			return Position.getObjectNothing();
		}
		
	}
	
	public Direction requestDirection(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		Type type = value.getType();
		if ((type == Type.simObjProp) || (type == Type.eventProp)) {
		
			SimProperty simProperty = (SimProperty) requestObject(value, type, EnumSimProperty.direction, token, receiver);
			if (simProperty instanceof Direction) {
				return (Direction) simProperty;
			}
			else {
				return Direction.getObjectNothing();
			}
		}
		else {
			return Direction.getObjectNothing();
		}
	}
	
	public Dimension requestDimension(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.dimension, token, receiver);
		if (simProperty instanceof Dimension) {
			return (Dimension) simProperty;
		}
		else {
			return Dimension.getObjectNothing();
		}
		
	}
	
	public Inventory requestInventory(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.inventory, token, receiver);
		if (simProperty instanceof Inventory) {
			return (Inventory) simProperty;
		}
		else {
			return Inventory.getObjectNothing();
		}
		
	}
	
	public PropsSeer requestPropsSeer(IAccessToken token, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.propsSeer, token, receiver);
		if (simProperty instanceof PropsSeer) {
			return (PropsSeer) simProperty;
		}
		else {
			return PropsSeer.getObjectNothing();
		}
		
	}

	public Vector requestVector(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (Vector) requestObject(value, Type.vector, EnumSimProperty.noSimProperty, token, receiver);
	}
	
	public AbstractAction requestAction(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (AbstractAction) requestObject(value, Type.action, EnumSimProperty.noSimProperty, token, receiver);
	}

	public KnowledgeElement requestKnowledgeElement(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (KnowledgeElement) requestObject(value, Type.knowledgeElement, EnumSimProperty.noSimProperty, token, receiver);
	}
	
	public KnowledgeSource requestKnowledgeSource(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (KnowledgeSource) requestObject(value, Type.knowledgeSource, EnumSimProperty.noSimProperty, token, receiver);
	}
	
	public KnowledgeItem requestKnowledgeItem(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (KnowledgeItem) requestObject(value, Type.knowledgeAtom, EnumSimProperty.noSimProperty, token, receiver);
	}

	public IAnswer requestAnswer(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (IAnswer) requestObject(value, Type.answer, EnumSimProperty.noSimProperty, token, receiver);
	}

	public StateSeer requestStateSeer(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (StateSeer) requestObject(value, Type.simObjProp, EnumSimProperty.stateSeer, token, receiver);
	}

	public StateRunning requestStateRunning(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (StateRunning) requestObject(value, Type.simObjProp, EnumSimProperty.stateRunning, token, receiver);
	}

	public StateFlying requestStateFlyng(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (StateFlying) requestObject(value, Type.simObjProp, EnumSimProperty.stateFlying, token, receiver);
	}


	public ValueArrayList requestValueArrayList(IAccessToken token, Value value, IObjectReceiver receiver) {
		return (ValueArrayList) requestObject(value, Type.valueList, EnumSimProperty.noSimProperty, token, receiver);
	}
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public Object requestObject(IAccessToken token, Value value, Type type, IObjectReceiver receiver) {
		return  requestObject(value, type, EnumSimProperty.noSimProperty, token, receiver);
	}

	
	private Object requestObject(Value value, Type type, EnumSimProperty simProperty, IAccessToken token, IObjectReceiver receiver) {
		
		// Holt automatisch den Container des aktuell aufrufenden Threads
		ThreadData data = threadContainer.get();

		Object result;
		int requestResult;
		int requestID = 0;
		
		// O(1) RECYCLING: Nutzt die Queue des aktuellen Threads
		if (!data.freeRequestIDs.isEmpty()) {
			requestID = data.freeRequestIDs.poll();
		} else {
			requestID = data.maxIDCount++;
		}
		
		if (value instanceof ValueProperty) {
			requestResult = ((ValueProperty) value).requestObject(token, receiver, requestID, type);
		} else {
			requestResult = value.requestObject(receiver, requestID, type);
		}

		if (requestResult == IObjectSender.OBJECT_SENDED) {
			result = remove(requestID);
			if (result == null) {  
				// TEST-LOG: Kam das Objekt vielleicht gar nicht synchron an?
		        System.out.println("WARNUNG: Objekt sended, aber remove() lieferte null für ID " + requestID);

				result = getObjectNothing(type, simProperty);
			}
		} else {
			result = getObjectNothing(type, simProperty);
			// ID sofort wieder in die Thread-eigene Queue freigeben
			data.freeRequestIDs.offer(requestID);
		}

		return result;
	}
	
	private Object remove(int requestID) {
		
		// Holt automatisch den Container des aktuell aufrufenden Threads
		ThreadData data = threadContainer.get();
		
		Object requestedObject = data.receivedObjects.remove(requestID);
		
		// ID zurück in die Thread-eigene Queue
		data.freeRequestIDs.offer(requestID); 
		
		return requestedObject;
	}

}
