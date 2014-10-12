package org.socialworld.calculation;

public class ActionIntensityExpression extends Expression {



	@Override
	protected void addition( ) {
		result = (double) value + (double) constant;
	}

	@Override
	protected void defaultFunction( ) {
		result = (double) value;

	}

	@Override
	protected void identity( ) {
		result = (double) value;

	}
	
	@Override
	protected void multiplication( ) {
		result = (double) value * (double) constant;

	}

	@Override
	protected void replacement() {
		result = constant;

	}

	
}
