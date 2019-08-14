package org.socialworld.calculation;

public class FunctionNothing extends FunctionBase {

	public FunctionNothing() {
		super();
		setInvalid();
	}
	
	@Override
	public Value calculate(Value[] arguments) {
		return new Value();
	}

}
