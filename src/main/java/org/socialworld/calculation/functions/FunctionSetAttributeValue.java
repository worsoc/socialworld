package org.socialworld.calculation.functions;


import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

public class FunctionSetAttributeValue extends FunctionBase {

	private int indexAttribute;
	
	private static AccessTokenFunction token = AccessTokenFunction.getValid();
	
	public FunctionSetAttributeValue(int indexAttribute) {
		this.indexAttribute = indexAttribute;
	}
	
	public FunctionSetAttributeValue(Attribute attribute) {
		this.indexAttribute = attribute.getIndex();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		
		int newAttributeValue;
		Value v;
		Object o;
		
		if (arguments.size() == 3) {
			if ((arguments.get(0).getType() == Type.attributeArray) &&
				(arguments.get(1).getType() == Type.integer) && 
				(arguments.get(2).getType() == Type.nothing)) 
			{	
				AttributeArray attributes = new AttributeArray(objectRequester.requestAttributeArray(token, arguments.get(0), this));
				
				v = arguments.get(1);
				o = v.getObject(Type.integer);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("FunctionSetAttributeValue.calculate > newAttributeValue (size == 3): o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					
					// return the unchanged attribute array as ValueProperty
					return attributes.getAsValue(token);
				}
				else {
					newAttributeValue = (int) o;
					attributes.set(this.indexAttribute, newAttributeValue);
					return attributes.getAsValue(token);
				}

			}
		}
		else if (arguments.size() == 2) {
			if ((arguments.get(0).getType() == Type.attributeArray) &&
				(arguments.get(1).getType() == Type.integer)) 
			{	
				AttributeArray attributes = new AttributeArray(objectRequester.requestAttributeArray(token, arguments.get(0), this));
				
				v = arguments.get(1);
				o = v.getObject(Type.integer);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("FunctionSetAttributeValue.calculate > newAttributeValue (size == 2): o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					
					// return the unchanged attribute array as ValueProperty
					return attributes.getAsValue(token);
				}
				else {
					newAttributeValue = (int) o;
					attributes.set(this.indexAttribute, newAttributeValue);
					return attributes.getAsValue(token);
				}
				
			}
		}
		
		if ((arguments.size() >= 1) && (arguments.get(0).getType() == Type.attributeArray)) {
			return arguments.get(0);
		}
		
		return Value.getValueNothing();
	}


}



