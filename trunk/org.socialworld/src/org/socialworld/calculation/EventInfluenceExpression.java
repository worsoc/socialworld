/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

/**
 * @author Mathias Sikos (tyloesand) The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and mathematical operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant to
 *         the attribute value OR the multiplication of a constant with the
 *         attribute value OR the replacement of the attribute value by the
 *         constant. If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.
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

	/**
	 * The method evaluates the expression. If the expression is a comparison
	 * the comparison is evaluated. If the comparison result is evaluated to
	 * true the true-expression is called recursively. In other case the
	 * false-expression is called recursively. If the expression is an addition,
	 * a multiplication or a replacement the attribute value is changed by the
	 * according operation. Finally it is checked that the result value is in
	 * the attribute range.
	 */
	public byte evaluateExpression(AttributeArray attributeArray,
			Attribute targetAttribute) {
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
				result = expressionForTrue.evaluateExpression(attributeArray,
						targetAttribute);
			else
				result = expressionForFalse.evaluateExpression(attributeArray,
						targetAttribute);

			break;
		case addition:
			result = attributeArray.get(targetAttribute.getIndex());
			result += this.constant;
			break;
		case multiplication:
			result = attributeArray.get(targetAttribute.getIndex());
			result *= this.constant;
			break;
		case replacement:
			result = this.constant;
			break;
		default:
			result = attributeArray.get(targetAttribute.getIndex());
		}

		result += 0.5;

		if (result > Attribute.ATTRIBUTE_RANGE)
			return Attribute.ATTRIBUTE_RANGE;
		if (result < 0)
			return 0;

		return (byte) result;
	}
}
