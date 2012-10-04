package org.socialworld.calculation;

import org.socialworld.attributes.AttributeArray;


public class EventReactionRelativeDirectionExpression extends Expression {

	Vector relativeDirection;
	
	Vector constant;
	double scalar;
	
	Vector result;
	
	@Override
	protected void addition( ) {
		relativeDirection.add(constant);
	}

	@Override
	protected void defaultFunction( ) {
		result = relativeDirection;

	}

	@Override
	protected void multiplication( ) {
		relativeDirection.multiplicate(scalar);

	}

	@Override
	protected void replacement() {
		result = constant;
	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally calculates the reactions's relativedirection 
	 * by calling calculation methods.
	 */
	public Vector evaluateExpression(AttributeArray attributeArray, Vector relativeDirection) {
		
		this.relativeDirection = relativeDirection;
		
		evaluateFunction(attributeArray);
		return result;
	}	
	
}
