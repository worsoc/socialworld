package org.socialworld.calculation;
import org.socialworld.attributes.AttributeArray;

public class ActionRelativeDirectionExpression extends Expression {

	Vector relativeDirection;
	
	Vector constant;
	double scalar;
	
	Vector result;

	@Override
	protected void evaluateSubExpression(AttributeArray attributeArray, boolean conditionIsTrue) {
	
		ActionRelativeDirectionExpression expression;
		
		if (conditionIsTrue)
				expression = (ActionRelativeDirectionExpression) expressionForTrue;
			else
				expression = (ActionRelativeDirectionExpression) expressionForFalse;
			
			result = expression.evaluateExpression(attributeArray, relativeDirection);
	}

	@Override
	protected void addition( ) {
		relativeDirection.add(constant);
	}

	@Override
	protected void defaultFunction( ) {
		result = relativeDirection;

	}

	@Override
	protected void identity( ) {
		result = relativeDirection;

	}

	@Override
	protected void multiplication( ) {
		relativeDirection.multiply(scalar);

	}

	@Override
	protected void replacement() {
		result = constant;
	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reactions's relative direction 
	 * by calling calculation methods.
	 */
	public Vector evaluateExpression(AttributeArray attributeArray, Vector relativeDirection) {
		
		this.relativeDirection = relativeDirection;
		
		evaluateFunction(attributeArray);
		return result;
	}	

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reactions's relative direction 
	 * by calling calculation methods.
	 */
	public Vector evaluateExpression(AttributeArray attributeArray) {
		
		if (this.relativeDirection == null)
			this.relativeDirection = new Vector(0,0,0);
		else
			this.relativeDirection.reset();
		
		evaluateFunction(attributeArray);
		return result;
	}	

}
