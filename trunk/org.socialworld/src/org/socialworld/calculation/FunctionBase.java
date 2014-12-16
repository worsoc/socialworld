package org.socialworld.calculation;

public abstract class FunctionBase {
	protected Calculation calculation;

	public abstract Value calculate(Value[] arguments);
}
