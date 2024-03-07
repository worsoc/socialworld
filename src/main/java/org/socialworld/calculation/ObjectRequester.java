package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.socialworld.attributes.AttributeArray;
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
	
	public AttributeArray requestAttributeArray(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		
		AttributeArray result;
		int requestResult;
		
		int requestID;
		for (requestID = 1; requestID <= (receivedObjects.size() + reservedRequestIDs.size()); requestID++) {
			if (!receivedObjects.containsKey(requestID) && !reservedRequestIDs.contains(requestID)) {
				break;
			}
		}
		// reserve key in hash table
		reservedRequestIDs.add(requestID);
		
		if (value instanceof ValueProperty) {
			requestResult = ((ValueProperty) value).requestObject(SimulationCluster.total, receiver, requestID, Type.attributeArray);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result = (AttributeArray) receivedObjects.remove(requestID);
			}
			else {
				return AttributeArray.getObjectNothing();
			}
		}
		else {
			requestResult = value.requestObject(receiver, requestID, Type.attributeArray);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result = (AttributeArray) receivedObjects.remove(requestID);
			}
			else {
				return AttributeArray.getObjectNothing();
			}
		}

		return result;
	}
	
	public SimulationObject requestSimulationObject(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimulationObject result;
		int requestResult;

		int requestID;
		for (requestID = 1; requestID <= (receivedObjects.size() + reservedRequestIDs.size()); requestID++) {
			if (!receivedObjects.containsKey(requestID) && !reservedRequestIDs.contains(requestID)) {
				break;
			}
		}
		// reserve key in hash table
		reservedRequestIDs.add(requestID);

		if (value instanceof ValueProperty) {
			requestResult = ((ValueProperty) value).requestObject(SimulationCluster.total, receiver, requestID, Type.simulationObject);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result = (SimulationObject) receivedObjects.remove(requestID);
			}
			else {
				return NoSimulationObject.getObjectNothing();
			}
		}
		else {
			requestResult = value.requestObject(receiver, requestID, Type.simulationObject);
			if (requestResult == IObjectSender.OBJECT_SENDED) {
				result = (SimulationObject) receivedObjects.remove(requestID);
			}
			else {
				return NoSimulationObject.getObjectNothing();
			}
		}

		return result;
	}

}
