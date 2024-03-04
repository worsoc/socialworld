package org.socialworld.calculation;

import java.util.Hashtable;

import org.socialworld.attributes.AttributeArray;

public class ObjectRequester {

	private Hashtable<Integer, Object> receivedObjects = new Hashtable<Integer, Object>();

	public void receive(int requestID, Object object) {
		if (!receivedObjects.contains(requestID)) {
			receivedObjects.put(requestID, object);
		}
	}
	
	public AttributeArray requestAttributeArray(SimulationCluster cluster, Value value, IObjectReceiver receiver, int requestID) {
		
		AttributeArray result;
		int requestResult;
		
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
}
