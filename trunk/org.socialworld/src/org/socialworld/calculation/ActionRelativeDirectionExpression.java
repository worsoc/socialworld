package org.socialworld.calculation;

public class ActionRelativeDirectionExpression extends Expression {

	
	

	@Override
	protected void addition( ) {
		Vector tmp;
		tmp = (Vector) value;
		tmp.add((Vector) constant);
		result = tmp;
	}

	@Override
	protected void defaultFunction( ) {
		result = (Vector) value;

	}

	@Override
	protected void identity( ) {
		result = (Vector) value;

	}

	@Override
	protected void multiplication( ) {
		Vector tmp;
		tmp = (Vector) value;
		tmp.multiply((Vector) constant);
		result = tmp;
	}

	@Override
	protected void replacement() {
		result = (Vector) constant;
	}


}
