package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionDelayExpression extends Expression {

	int delay;	
	int result;
	
	@Override
	protected void evaluateSubExpression(AttributeArray attributeArray, boolean conditionIsTrue) {
	
		ActionDelayExpression expression;
		
		if (conditionIsTrue)
				expression = (ActionDelayExpression) expressionForTrue;
			else
				expression = (ActionDelayExpression) expressionForFalse;
			
			result = expression.evaluateExpression(attributeArray, delay);
	}
	
	@Override
	protected void addition( ) {
		result = delay + (int) constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = delay;

	}

	@Override
	protected void identity( ) {
		result = delay;

	}

	@Override
	protected void multiplication( ) {
		result = delay * (int) constant;

	}

	@Override
	protected void replacement() {
		result = (int) constant;

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's delay
	 * by calling the calculation methods.
	 */
	public int evaluateExpression(AttributeArray attributeArray, int delay) {
		
		this.delay = delay;
		
		evaluateFunction(attributeArray);
		return result;
	}

}
