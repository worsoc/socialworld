package org.socialworld.calculation;

public class ActionPriorityExpression extends Expression {


	
	@Override
	protected void addition( ) {
		result = (int) value + (int) constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = (int) value;

	}

	@Override
	protected void identity( ) {
		result = (int) value;

	}

	@Override
	protected void multiplication( ) {
		result = (int) value * (int) constant;

	}

	@Override
	protected void replacement() {
		result = (int) constant;

	}


}
