package org.socialworld.calculation;

public class ActionDurationExpression extends Expression {


	
	@Override
	protected void addition( ) {
		result = (long) value + (long) constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = (long) value;

	}

	@Override
	protected void identity( ) {
		result = (long) value;

	}
	
	@Override
	protected void multiplication( ) {
		result = (long) value * (long) constant;

	}

	@Override
	protected void replacement() {
		result = (long) constant;

	}



}
