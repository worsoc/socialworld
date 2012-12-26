package org.socialworld.calculation;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.AttributeArray;

public class ActionTypeExpression extends Expression {

	ActionType type;
	ActionType result;
	
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
