package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Value;

public class Constant extends Expression {

	public Constant(Value constant) {
		
		super();
		
		setOperation(Expression_Function.value);
		setValue(constant);
		
		setValid();

	}

}
