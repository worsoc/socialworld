package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class Program extends Expression {

	public Program(Expression startExpression) {
		
		setOperation(Expression_Function.oneExpression);
		setExpression1(startExpression);
		setValid();
	}
}
