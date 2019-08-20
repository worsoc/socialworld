package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class CreateValue extends Expression {

	public CreateValue(Type type, Expression exp1) {
		
		super();
		
		setOperation(Expression_Function.create);
		
		setExpression1(exp1);
		setValue(new Value(Type.integer, type.getIndex()));
		
		setValid();
		
	}
	
}
