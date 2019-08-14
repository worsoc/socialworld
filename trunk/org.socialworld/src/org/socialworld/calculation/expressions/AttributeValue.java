package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class AttributeValue extends Expression {

	private static AttributeValue instance0;
	private static AttributeValue instance1;
	private static AttributeValue instance2;
	private static AttributeValue instance3;
	private static AttributeValue instance4;
	private static AttributeValue instance5;
	private static AttributeValue instance6;
	private static AttributeValue instance7;
	private static AttributeValue instance8;
	
	private AttributeValue(int attributeIndex) {
		super();
		setOperation(Expression_Function.attributeValue);
		setValue(new Value(Type.integer, attributeIndex));
		
		setValid();
	}
	
	public static AttributeValue getInstance(int attributeIndex) {
		
		switch (attributeIndex) {
		case 0:
		
			if (instance0 == null) {
				instance0 = new AttributeValue(attributeIndex);
			}
			return instance0;
		case 1:
			
			if (instance1 == null) {
				instance1 = new AttributeValue(attributeIndex);
			}
			return instance1;
		case 2:
			
			if (instance2 == null) {
				instance2 = new AttributeValue(attributeIndex);
			}
			return instance2;
		case 3:
			
			if (instance3 == null) {
				instance3 = new AttributeValue(attributeIndex);
			}
			return instance3;
		case 4:
			
			if (instance4 == null) {
				instance4 = new AttributeValue(attributeIndex);
			}
			return instance4;
		case 5:
			
			if (instance5 == null) {
				instance5 = new AttributeValue(attributeIndex);
			}
			return instance5;
		case 6:
			
			if (instance6 == null) {
				instance6 = new AttributeValue(attributeIndex);
			}
			return instance6;
		case 7:
			
			if (instance7 == null) {
				instance7 = new AttributeValue(attributeIndex);
			}
			return instance7;
		case 8:
			
			if (instance8 == null) {
				instance8 = new AttributeValue(attributeIndex);
			}
			return instance8;

		}
		
		return null;
	}

	
	/*
	public AttributeValue(int attributeIndex ) {
		
		super();
		
		setOperation(Expression_Function.attributeValue);
		setValue(new Value(Type.integer, attributeIndex));
		
		setValid();
		
	}
	
	*/
}
