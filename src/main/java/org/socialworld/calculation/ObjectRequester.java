package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.socialworld.actions.AbstractAction;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.EnumSimProperty;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.SimProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;

public class ObjectRequester {

	private Hashtable<Integer, Object> receivedObjects = new Hashtable<Integer, Object>();
	private List<Integer> reservedRequestIDs = new ArrayList<Integer>();
	
	public void receive(int requestID, Object object) {
		if (!receivedObjects.contains(requestID)) {
			receivedObjects.put(requestID, object);
		}
	}
	
	private Object getObjectNothing(Type type, EnumSimProperty simProperty) {
		switch (type) {
			case valueList:
				return ValueArrayList.getObjectNothing();
			case attributeArray:
				return AttributeArray.getObjectNothing();
			case simObjProp:
				switch (simProperty) {
					case position:
						return Position.getObjectNothing();
					case direction:
						return Direction.getObjectNothing();
					default:
						return NoObject.getNoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
				}
			case simulationObject:
				return NoSimulationObject.getObjectNothing();

			default:
				return NoObject.getNoObject(NoObjectReason.objectRequesterGetObjectNothingNotImplementedForType);
		}
	}
	
	
	public AttributeArray requestAttributeArray(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (AttributeArray) requestObject(value, Type.attributeArray, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public SimulationObject requestSimulationObject(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (SimulationObject) requestObject(value, Type.simulationObject, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public Position requestPosition(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.position, cluster, receiver);
		if (simProperty instanceof Position) {
			return (Position) simProperty;
		}
		else {
			return Position.getObjectNothing();
		}
		
	}
	
	public Direction requestDirection(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.direction, cluster, receiver);
		if (simProperty instanceof Position) {
			return (Direction) simProperty;
		}
		else {
			return Direction.getObjectNothing();
		}
		
	}
	
	public Vector requestVector(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (Vector) requestObject(value, Type.vector, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public AbstractAction requestAction(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (AbstractAction) requestObject(value, Type.action, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public ValueArrayList requestValueArrayList(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (ValueArrayList) requestObject(value, Type.valueList, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public Object requestObject(SimulationCluster cluster, Value value, Type type, IObjectReceiver receiver) {
		return  requestObject(value, type, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	
	private Object requestObject(Value value, Type type, EnumSimProperty simProperty, SimulationCluster cluster, IObjectReceiver receiver) {
		
		Object result;
		int requestResult;
		
		int requestID;
		for (requestID = 1; requestID <= reservedRequestIDs.size(); requestID++) {
			if (!reservedRequestIDs.contains(requestID)) {
				break;
			}
		}
		// reserve key in hash table
		reservedRequestIDs.add(requestID);

		if (value instanceof ValueProperty) {
			requestResult = ((ValueProperty) value).requestObject(SimulationCluster.total, receiver, requestID, type);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result =  remove(requestID);
				if (result == null) result = getObjectNothing(type, simProperty);
			}
			else {
				result = getObjectNothing(type, simProperty);
			}
		}
		else {
			requestResult = value.requestObject(receiver, requestID, type);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result = remove(requestID);
				if (result == null) result = getObjectNothing(type, simProperty);
			}
			else {
				result = getObjectNothing(type, simProperty);
			}
		}

		return result;
	}
	
	private Object remove(int requestID) {
		Object requestedObject = receivedObjects.remove(requestID);
		int index = reservedRequestIDs.indexOf(requestID);
		if (index >= 0)		reservedRequestIDs.remove(index);
		return requestedObject;
	}

}
