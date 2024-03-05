package org.socialworld.calculation;


import org.socialworld.collections.ValueArrayList;

public class FunctionNothing extends FunctionBase {

	public FunctionNothing() {
		super();
		setInvalid();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		return Value.getValueNothing();
	}


}
