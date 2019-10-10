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
		
		if (arguments.length == 3) {
			if ((arguments[0].getType() == Type.attributeArray) &&
				(arguments[1].getType() == Type.integer) && 
				(arguments[2].getType() == Type.nothing)) 
			{	
				AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
				attributes.set(this.indexAttribute, (int)arguments[1].getValueCopy());
				return new Value(Type.attributeArray, attributes);
			}
		}
		else if (arguments.length == 2) {
			if ((arguments[0].getType() == Type.attributeArray) &&
				(arguments[1].getType() == Type.integer)) 
			{	
				AttributeArray attributes = new AttributeArray((AttributeArray) arguments[0].getValue());
				attributes.set(this.indexAttribute, (int)arguments[1].getValueCopy());
				return new Value(Type.attributeArray, attributes);
			}
		}
		else if ((arguments.length == 1) && (arguments[0].getType() == Type.attributeArray)) {
			return arguments[0];
		}
		return new Value();
	}

}
