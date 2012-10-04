package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;


public class EventReactionIntensityExpression extends Expression {

	double intensity;
	
	double constant;
	double result;
	
	@Override
	protected void addition( ) {
		result = intensity + constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = intensity;

	}

	@Override
	protected void multiplication( ) {
		result = intensity * constant;

	}

	@Override
	protected void replacement() {
		result = constant;

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's intensity
	 * by calling the calculation methods.
	 */
	public double evaluateExpression(AttributeArray attributeArray, double intensity) {
		
		this.intensity = intensity;
		
		evaluateFunction(attributeArray);
		return result;
	}
}
