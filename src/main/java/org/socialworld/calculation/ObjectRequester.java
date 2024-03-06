package org.socialworld.calculation;

import java.util.Hashtable;

import org.socialworld.attributes.AttributeArray;
import org.socialworld.objects.NoSimulationObject;
import org.socialworld.objects.SimulationObject;

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
	public SimulationObject requestSimulationObject(SimulationCluster cluster, Value value, IObjectReceiver receiver, int requestID) {
		
		SimulationObject result;
		int requestResult;
		
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
