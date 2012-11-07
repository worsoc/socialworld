package org.socialworld.calculation;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.AttributeArray;


public class ActionModeExpression extends Expression {

	ActionMode mode;
	ActionMode result;
	
	@Override
	protected void addition( ) {
		defaultFunction();

	}

	@Override
	protected void defaultFunction( ) {
		result = mode;
	}

	@Override
	protected void multiplication( ) {
		defaultFunction();

	}

	@Override
	protected void replacement() {
		defaultFunction();

	}

	/**
	 * The method evaluates the expression by calling the parent method evaluateFunction().
	 * The method evaluateFunction() finally sets the reaction's ActionMode 
	 * by calling the method defaultFunction().
	 */
	public ActionMode evaluateExpression(AttributeArray attributeArray) {
		
		evaluateFunction(attributeArray);
		return result;
	}	

}
