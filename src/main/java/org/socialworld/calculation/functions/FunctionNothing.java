package org.socialworld.calculation.functions;


import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Value;
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
