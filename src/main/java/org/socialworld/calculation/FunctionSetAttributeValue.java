package org.socialworld.calculation;


import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.collections.ValueArrayList;

public class FunctionSetAttributeValue extends FunctionBase {

	private int indexAttribute;
	
	public FunctionSetAttributeValue(int indexAttribute) {
		this.indexAttribute = indexAttribute;
	}
	
	public FunctionSetAttributeValue(Attribute attribute) {
		this.indexAttribute = attribute.getIndex();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		
		int newAttributeValue;
		
		
		if (arguments.size() == 3) {
			if ((arguments.get(0).getType() == Type.attributeArray) &&
				(arguments.get(1).getType() == Type.integer) && 
				(arguments.get(2).getType() == Type.nothing)) 
			{	
				AttributeArray attributes = new AttributeArray(objectRequester.requestAttributeArray(SimulationCluster.total, arguments.get(0), this));
				newAttributeValue = (int)arguments.get(1).getObject(Type.integer);
				attributes.set(this.indexAttribute, newAttributeValue);
				return attributes.getAsValue(SimulationCluster.toBeSet);
			}
		}
		else if (arguments.size() == 2) {
			if ((arguments.get(0).getType() == Type.attributeArray) &&
				(arguments.get(1).getType() == Type.integer)) 
			{	
				AttributeArray attributes = new AttributeArray(objectRequester.requestAttributeArray(SimulationCluster.total, arguments.get(0), this));
				newAttributeValue = (int)arguments.get(1).getObject(Type.integer);
				attributes.set(this.indexAttribute, newAttributeValue);
				return attributes.getAsValue(SimulationCluster.toBeSet);
			}
		}
		
		if ((arguments.size() >= 1) && (arguments.get(0).getType() == Type.attributeArray)) {
			return arguments.get(0);
		}
		
		return Value.getValueNothing();
	}


}



