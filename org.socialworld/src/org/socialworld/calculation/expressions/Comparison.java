package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;

public class Comparison extends Expression {

	public Comparison(Expression_ConditionOperator operator, Expression leftValue, Expression rightValue) {
		
		super();
		
		setOperation(Expression_Function.comparison);
		setOperator(operator);
		setExpression1(leftValue);
		setExpression2(rightValue);
		
		setValid();

	}
	
}
