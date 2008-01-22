/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class EventInfluenceExpression {
	ExpressionFunction function;
	ConditionOperator operator;
	Attribute attribute;
	double constant;
	
	EventInfluenceExpression expressionForTrue;
	EventInfluenceExpression expressionForFalse;
	
	
	public void setTrueExpression(EventInfluenceExpression expressionForTrue) {
		this.expressionForTrue = expressionForTrue;
	}

	public void setFalseExpression(EventInfluenceExpression expressionForFalse) {
		this.expressionForTrue = expressionForFalse;
	}
	
	public byte evaluateExpression(AttributeArray attributeArray, Attribute targetAttribute) {
		double result;
		byte operandValue;
		boolean conditionIsTrue = false;
		
		switch (this.function) {
		case condition:
			operandValue = attributeArray.get(attribute.getIndex());
				
			switch (this.operator) {
			case equal:
				if (operandValue == this.constant)
					conditionIsTrue = true;
				break;
			case notEqual:
				if (operandValue != this.constant)
					conditionIsTrue = true;
				break;
			case less:
				if (operandValue < this.constant)
					conditionIsTrue = true;
				break;
			case lessEqual:
				if (operandValue <= this.constant)
					conditionIsTrue = true;
				break;
			case greaterEqual:
				if (operandValue >= this.constant)
					conditionIsTrue = true;
				break;
			case greater:
				if (operandValue > this.constant)
					conditionIsTrue = true;
				break;
			}
			
			if (conditionIsTrue) 
				result = expressionForTrue.evaluateExpression(attributeArray, targetAttribute);
			else
				result = expressionForFalse.evaluateExpression(attributeArray, targetAttribute);
				
			break;
		case addition:
			result = attributeArray.get(targetAttribute.getIndex());
			result +=  this.constant;
			break;
		case multiplication:
			result = attributeArray.get(targetAttribute.getIndex());
			result *=  this.constant;
			break;
		case replacement:
			result = this.constant;
			break;
		default:
			result = attributeArray.get(targetAttribute.getIndex());
		}
		
		result += 0.5;
		
		if (result > 100) return 100;
		if (result < 0) return 0;
		
		return (byte) result;
	}
}
