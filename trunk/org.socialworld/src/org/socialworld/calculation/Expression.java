package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;

/**
 * The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and (mathematical) operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant 
 *          OR a multiplication OR a replacement 
 *         If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.

 * @author Mathias Sikos (tyloesand)
 */
public abstract class Expression {
	ExpressionFunction function;
	ConditionOperator operator;

	int attributeIndex;
	double constant;

	Expression expressionForTrue;
	Expression expressionForFalse;

	public void setTrueExpression(Expression expressionForTrue) {
		this.expressionForTrue = expressionForTrue;
	}

	public void setFalseExpression(Expression expressionForFalse) {
		this.expressionForTrue = expressionForFalse;
	}

	protected abstract void addition();
	protected abstract void multiplication();
	protected abstract void replacement();
	protected abstract void defaultFunction();

	/**
	 * The method evaluates the expression function. 
	 * If the expression function is a comparison
	 * the comparison is evaluated. If the comparison result is evaluated to
	 * true the true-expression is called recursively. In other case the
	 * false-expression is called recursively. If the expression is an addition,
	 * a multiplication or a replacement the attribute value is changed by the
	 * according operation. 
	 */
	protected void evaluateFunction(AttributeArray attributeArray) {
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
				expressionForTrue.evaluateFunction(attributeArray);
			else
				expressionForFalse.evaluateFunction(attributeArray);

			break;
		case addition:
			addition();
			break;
		case multiplication:
			multiplication();
			break;
		case replacement:
			replacement();
			break;
		default:
			defaultFunction();
		}


	}
}
