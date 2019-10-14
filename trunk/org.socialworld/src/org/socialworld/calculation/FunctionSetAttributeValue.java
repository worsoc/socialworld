package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

public class FunctionSetAttributeValue extends FunctionBase {

	private int indexAttribute;
	
	public FunctionSetAttributeValue(int indexAttribute) {
		this.indexAttribute = indexAttribute;
	}
	
	public FunctionSetAttributeValue(Attribute attribute) {
		this.indexAttribute = attribute.getIndex();
	}
	
	@Override
	public Value calculate(Value[] arguments) {
		
		int newAttributeValue;
		
		
		if (arguments.length == 3) {
			if ((arguments[0].getType() == Type.attributeArray) &&
				(arguments[1].getType() == Type.integer) && 
				(arguments[2].getType() == Type.nothing)) 
			{	
				AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
				newAttributeValue = (int)arguments[1].getValueCopy();
				attributes.set(this.indexAttribute, newAttributeValue);
				return new Value(Type.attributeArray, Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES, attributes);
			}
		}
		else if (arguments.length == 2) {
			if ((arguments[0].getType() == Type.attributeArray) &&
				(arguments[1].getType() == Type.integer)) 
			{	
				AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
				newAttributeValue = (int)arguments[1].getValueCopy();
				attributes.set(this.indexAttribute, newAttributeValue);
				return new Value(Type.attributeArray, Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES, attributes);
			}
		}
		
		if ((arguments.length >= 1) && (arguments[0].getType() == Type.attributeArray)) {
			return arguments[0];
		}
		
		return new Value();
	}

	

}



/*
public Value calculate(Value[] arguments) {
	
	int newAttributeValue;
	
	
	if (arguments.length == 3) {
		if ((arguments[0].getType() == Type.attributeArray) &&
			((arguments[1].getType() == Type.floatingpoint) || (arguments[1].getType() == Type.integer)) && 
			(arguments[2].getType() == Type.nothing)) 
		{	
			AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
			if (arguments[1].getType() == Type.floatingpoint)
				newAttributeValue = ((Float)arguments[1].getValueCopy()).intValue();
			else
				newAttributeValue = (int)arguments[1].getValueCopy();
			attributes.set(this.indexAttribute, newAttributeValue);
			return new Value(Type.attributeArray, Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES, attributes);
		}
	}
	else if (arguments.length == 2) {
		if ((arguments[0].getType() == Type.attributeArray) &&
			((arguments[1].getType() == Type.floatingpoint) || (arguments[1].getType() == Type.integer))) 
		{	
			AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
			if (arguments[1].getType() == Type.floatingpoint)
				newAttributeValue = ((Float)arguments[1].getValueCopy()).intValue();
			else
				newAttributeValue = (int)arguments[1].getValueCopy();
			attributes.set(this.indexAttribute, newAttributeValue);
			return new Value(Type.attributeArray, Value.ARGUMENT_VALUE_BY_NAME_ATTRIBUTES, attributes);
		}
	}
	
	if ((arguments.length >= 1) && (arguments[0].getType() == Type.attributeArray)) {
		return arguments[0];
	}
	
	return new Value();
}
*/
