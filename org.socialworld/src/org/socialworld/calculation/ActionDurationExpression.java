package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionDurationExpression extends Expression {
	long duration;
	
	long constant;
	long result;
	
	@Override
	protected void addition( ) {
		result = duration + constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = duration;

	}

	@Override
	protected void multiplication( ) {
		result = duration * constant;

	}

	@Override
	protected void replacement() {
		result = constant;

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's duration
	 * by calling the calculation methods.
	 */
	public long evaluateExpression(AttributeArray attributeArray, long duration) {
		
		this.duration = duration;
		
		evaluateFunction(attributeArray);
		return result;
	}
	
	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's duration
	 * by calling the calculation methods.
	 */
	public long evaluateExpression(AttributeArray attributeArray) {
		
		this.duration = 0;
		
		evaluateFunction(attributeArray);
		return result;
	}

}
