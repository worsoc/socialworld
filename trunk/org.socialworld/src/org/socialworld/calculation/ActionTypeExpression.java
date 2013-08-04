package org.socialworld.calculation;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.AttributeArray;

public class ActionTypeExpression extends Expression {

	ActionType type = ActionType.say;
	ActionType result;

	@Override
	protected void evaluateSubExpression(AttributeArray attributeArray, boolean conditionIsTrue) {
	
		ActionTypeExpression expression;
		
		if (conditionIsTrue)
				expression = (ActionTypeExpression) expressionForTrue;
			else
				expression = (ActionTypeExpression) expressionForFalse;
			
			result = expression.evaluateExpression(attributeArray);
	}

	@Override
	// not used
	protected void addition( ) {
		defaultFunction( );
	}

	@Override
	protected void defaultFunction( ) {
		result = type;	

	}

	@Override
	protected void identity( ) {
		result = type;	

	}

	@Override
	// not used
	protected void multiplication( ) {
		defaultFunction( );

	}

	@Override
	// not used
	protected void replacement() {
		defaultFunction( );

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally sets the reaction's ActionType 
	 * by calling the method defaultFunction().
	 */
	public ActionType evaluateExpression(AttributeArray attributeArray) {
		
		evaluateFunction(attributeArray);
		return result;
	}	

}
