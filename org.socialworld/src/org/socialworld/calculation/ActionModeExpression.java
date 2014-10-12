package org.socialworld.calculation;
import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;


public class ActionModeExpression extends Expression {


	// not used
	@Override
	protected void addition( ) {
		defaultFunction();

	}

	@Override
	protected void defaultFunction( ) {
		result = (ActionMode) value;
	}

	@Override
	protected void identity( ) {
		result = (ActionMode) value;
	}

	// not used
	@Override
	protected void multiplication( ) {
		defaultFunction();

	}

	@Override
	protected void replacement() {
		result = (ActionType) constant;

	}

	
}
