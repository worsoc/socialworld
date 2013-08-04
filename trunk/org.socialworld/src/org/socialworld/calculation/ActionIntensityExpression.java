package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionIntensityExpression extends Expression {

	double intensity;
	
	double result;

	@Override
	protected void evaluateSubExpression(AttributeArray attributeArray, boolean conditionIsTrue) {
	
		ActionIntensityExpression expression;
		
		if (conditionIsTrue)
				expression = (ActionIntensityExpression) expressionForTrue;
			else
				expression = (ActionIntensityExpression) expressionForFalse;
			
			result = expression.evaluateExpression(attributeArray, intensity);
	}

	@Override
	protected void addition( ) {
		result = intensity + constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = intensity;

	}

	@Override
	protected void identity( ) {
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
	
	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reaction's intensity
	 * by calling the calculation methods.
	 */
	public double evaluateExpression(AttributeArray attributeArray) {
		
		this.intensity = 0;
		
		evaluateFunction(attributeArray);
		return result;
	}

}
