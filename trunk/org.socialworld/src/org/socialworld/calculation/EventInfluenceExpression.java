/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and mathematical operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant to
 *         the attribute value OR the multiplication of a constant with the
 *         attribute value OR the replacement of the attribute value by the
 *         constant. If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.

 * @author Mathias Sikos (tyloesand)
 */
public class EventInfluenceExpression extends Expression {
/*	ExpressionFunction function;
	ConditionOperator operator;
	*/
	
	AttributeArray attributeArray;
	int targetAttributeIndex;
	double result;

	/*
	EventInfluenceExpression expressionForTrue;
	EventInfluenceExpression expressionForFalse;

	public void setTrueExpression(EventInfluenceExpression expressionForTrue) {
		this.expressionForTrue = expressionForTrue;
	}

	public void setFalseExpression(EventInfluenceExpression expressionForFalse) {
		this.expressionForTrue = expressionForFalse;
	}
*/
	
	/**
	 * The constructor initializes the expression as the identity function
	 */
	/*public EventInfluenceExpression() {
		
		function = ExpressionFunction.identity;

	}*/
	
	protected  void addition() {
		result = this.attributeArray.get(targetAttributeIndex);
		result += this.constant;
	}

	protected  void multiplication() {
		result = this.attributeArray.get(targetAttributeIndex);
		result *= this.constant;
	}

	protected  void replacement( ) {
		result = this.constant;
	}

	protected  void identity() {
		result = this.attributeArray.get(targetAttributeIndex);
	}
	
	protected  void defaultFunction() {
		result = this.attributeArray.get(targetAttributeIndex);
	}

		
	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() calls the implementation of calculation functions.
	 * Finally the new attribute value is returned.
	 */
	public byte evaluateExpression(AttributeArray attributeArray,
			int targetAttributeIndex) {
		this.attributeArray = attributeArray;
		this.targetAttributeIndex = targetAttributeIndex;
		
		evaluateFunction(attributeArray);
		
		result += 0.5;

		if (result > Attribute.ATTRIBUTE_RANGE)
			return Attribute.ATTRIBUTE_RANGE;
		if (result < 0)
			return 0;
		
		return (byte) result;
	}
	
/*	
	public byte _evaluateExpression(AttributeArray attributeArray,
			int targetAttributeIndex) {
		double result;
		int operandValue;
		boolean conditionIsTrue = false;

		switch (this.function) {
		case condition:
			operandValue = attributeArray.get(this.attributeIndex);

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
				result = expressionForTrue.evaluateExpression(attributeArray,
						targetAttributeIndex);
			else
				result = expressionForFalse.evaluateExpression(attributeArray,
						targetAttributeIndex);

			break;
		case addition:
			result = attributeArray.get(targetAttributeIndex);
			result += this.constant;
			break;
		case multiplication:
			result = attributeArray.get(targetAttributeIndex);
			result *= this.constant;
			break;
		case replacement:
			result = this.constant;
			break;
		default:
			result = attributeArray.get(targetAttributeIndex);
		}

		result += 0.5;

		if (result > Attribute.ATTRIBUTE_RANGE)
			return Attribute.ATTRIBUTE_RANGE;
		if (result < 0)
			return 0;

		return (byte) result;
	}
	*/
}
