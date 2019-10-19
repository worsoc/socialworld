package org.socialworld.calculation;

import java.util.List;

public class FunctionNothing extends FunctionBase {

	public FunctionNothing() {
		super();
		setInvalid();
	}
	
	@Override
	public Value calculate(List<Value> arguments) {
		return new Value();
	}


}
