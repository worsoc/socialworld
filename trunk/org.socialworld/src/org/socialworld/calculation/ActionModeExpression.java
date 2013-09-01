package org.socialworld.calculation;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.AttributeArray;


public class ActionModeExpression extends Expression {

	ActionMode mode = ActionMode.whisper;
	ActionMode result;

	public void setMode (ActionMode mode) {
		this.mode = mode;
	}
	
	@Override
	protected void evaluateSubExpression(AttributeArray attributeArray, boolean conditionIsTrue) {
	
		ActionModeExpression expression;
		
		if (conditionIsTrue)
				expression = (ActionModeExpression) expressionForTrue;
			else
				expression = (ActionModeExpression) expressionForFalse;
			
			result = expression.evaluateExpression(attributeArray);
	}

	@Override
	protected void addition( ) {
		defaultFunction();

	}

	@Override
	protected void defaultFunction( ) {
		result = mode;
	}

	@Override
	protected void identity( ) {
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
