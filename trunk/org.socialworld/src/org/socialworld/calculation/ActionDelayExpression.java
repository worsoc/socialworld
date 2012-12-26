package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionDelayExpression extends Expression {

	int delay;
	
	int constant;
	int result;
	
	@Override
	protected void addition( ) {
		result = delay + constant;
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
		result = delay * constant;

	}

	@Override
	protected void replacement() {
		result = constant;

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
