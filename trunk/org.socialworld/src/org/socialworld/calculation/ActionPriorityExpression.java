package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionPriorityExpression extends Expression {

	int priority;
	
	int constant;
	int result;
	
	@Override
	protected void addition( ) {
		result = priority + constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = priority;

	}

	@Override
	protected void identity( ) {
		result = priority;

	}

	@Override
	protected void multiplication( ) {
		result = priority * constant;

	}

	@Override
	protected void replacement() {
		result = constant;

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's priority
	 * by calling the calculation methods.
	 */
	public int evaluateExpression(AttributeArray attributeArray, int priority) {
		
		this.priority = priority;
		
		evaluateFunction(attributeArray);
		return result;
	}
	
	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's priority
	 * by calling the calculation methods.
	 */
	public int evaluateExpression(AttributeArray attributeArray) {
		
		this.priority = 0;
		
		evaluateFunction(attributeArray);
		return result;
	}

}
