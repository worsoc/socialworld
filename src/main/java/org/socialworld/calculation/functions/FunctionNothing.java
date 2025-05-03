package org.socialworld.calculation.functions;


import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

public class FunctionNothing extends FunctionBase {

	private static FunctionNothing instance;

	public static FunctionNothing getInstance() {
		if (instance == null) {
			instance = new FunctionNothing();
		}
		return instance;
		
	}

	private FunctionNothing() {
		super();
		setInvalid();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		return Value.getValueNothing();
	}


}
