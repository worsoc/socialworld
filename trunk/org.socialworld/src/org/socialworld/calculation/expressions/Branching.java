package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;

public class Branching extends Expression {

	public Branching (Expression exp1, Expression exp2, Expression exp3) {
		
		super();
		
		setOperation(Expression_Function.branching);
		
		setExpression1(exp1);
		setExpression2(exp2);
		setExpression3(exp3);
		
		setValid();
		
	}
	
}
