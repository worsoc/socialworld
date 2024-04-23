package org.socialworld.calculation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
	
	
	public AttributeArray requestAttributeArray(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (AttributeArray) requestObject(value, Type.attributeArray, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public SimulationObject requestSimulationObject(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (SimulationObject) requestObject(value, Type.simulationObject, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public NutrientSet requestNutrientSet(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.nutrientSet, cluster, receiver);
		if (simProperty instanceof NutrientSet) {
			return (NutrientSet) simProperty;
		}
		else {
			return NutrientSet.getObjectNothing();
		}
		
	}
	
	public TasteSet requestTasteSet(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.tasteSet, cluster, receiver);
		if (simProperty instanceof TasteSet) {
			return (TasteSet) simProperty;
		}
		else {
			return TasteSet.getObjectNothing();
		}
		
	}
	
	public ColourSet requestColourSet(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.enumProp, EnumSimProperty.colourSet, cluster, receiver);
		if (simProperty instanceof ColourSet) {
			return (ColourSet) simProperty;
		}
		else {
			return ColourSet.getObjectNothing();
		}
		
	}
	
	public Position requestPosition(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		Type type = value.getType();
		if ((type == Type.simObjProp) || (type == Type.eventProp)) {
			SimProperty simProperty = (SimProperty) requestObject(value, type, EnumSimProperty.position, cluster, receiver);
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
	
	public Direction requestDirection(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		Type type = value.getType();
		if ((type == Type.simObjProp) || (type == Type.eventProp)) {
		
			SimProperty simProperty = (SimProperty) requestObject(value, type, EnumSimProperty.direction, cluster, receiver);
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
	
	public Dimension requestDimension(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.dimension, cluster, receiver);
		if (simProperty instanceof Dimension) {
			return (Dimension) simProperty;
		}
		else {
			return Dimension.getObjectNothing();
		}
		
	}
	
	public Inventory requestInventory(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.inventory, cluster, receiver);
		if (simProperty instanceof Inventory) {
			return (Inventory) simProperty;
		}
		else {
			return Inventory.getObjectNothing();
		}
		
	}
	
	public PropsSeer requestPropsSeer(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		
		SimProperty simProperty = (SimProperty) requestObject(value, Type.simObjProp, EnumSimProperty.propsSeer, cluster, receiver);
		if (simProperty instanceof PropsSeer) {
			return (PropsSeer) simProperty;
		}
		else {
			return PropsSeer.getObjectNothing();
		}
		
	}

	public Vector requestVector(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (Vector) requestObject(value, Type.vector, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public AbstractAction requestAction(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (AbstractAction) requestObject(value, Type.action, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public KnowledgeElement requestKnowledgeElement(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (KnowledgeElement) requestObject(value, Type.knowledgeElement, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public KnowledgeSource requestKnowledgeSource(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (KnowledgeSource) requestObject(value, Type.knowledgeSource, EnumSimProperty.noSimProperty, cluster, receiver);
	}
	
	public KnowledgeItem requestKnowledgeItem(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (KnowledgeItem) requestObject(value, Type.knowledgeAtom, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public IAnswer requestAnswer(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (IAnswer) requestObject(value, Type.answer, EnumSimProperty.noSimProperty, cluster, receiver);
	}

	public StateSeer requestStateSeer(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (StateSeer) requestObject(value, Type.simObjProp, EnumSimProperty.stateSeer, cluster, receiver);
	}

	public StateRunning requestStateRunning(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (StateRunning) requestObject(value, Type.simObjProp, EnumSimProperty.stateRunning, cluster, receiver);
	}

	public StateFlying requestStateFlyng(SimulationCluster cluster, Value value, IObjectReceiver receiver) {
		return (StateFlying) requestObject(value, Type.simObjProp, EnumSimProperty.stateFlying, cluster, receiver);
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
		
		boolean replaceInReservedList = false;
		int requestID = 0;
		for (requestID = 0; requestID < reservedRequestIDs.size(); requestID++) {
			if (reservedRequestIDs.get(requestID) == -1) {
				replaceInReservedList = true;
				break;
			}
		}
		// reserve key in hash table
		if (replaceInReservedList) {
			reservedRequestIDs.set(requestID, requestID);
		}
		else {
			reservedRequestIDs.add(requestID);
		}
		
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
		reservedRequestIDs.set(requestID, -1);
		return requestedObject;
	}

}
