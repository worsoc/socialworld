package org.socialworld.calculation;
import org.socialworld.attributes.ActionType;

public class ActionTypeExpression extends Expression {


	

	// not used
	@Override
	protected void addition( ) {
		defaultFunction( );
	}

	@Override
	protected void defaultFunction( ) {
		result = (ActionType) value;	

	}

	@Override
	protected void identity( ) {
		result = (ActionType) value;	

	}

	// not used
	@Override
	protected void multiplication( ) {
		defaultFunction( );

	}

	@Override
	protected void replacement() {
		result = (ActionType) constant;

	}

	
}
