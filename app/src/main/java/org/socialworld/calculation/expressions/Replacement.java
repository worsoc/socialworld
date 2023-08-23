package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

class Replacement extends Expression {

	public Replacement(String name, Expression exp1) {
		
		super();
		
		setOperation(Expression_Function.replacement);
		
		setExpression1(exp1);
		setValue(new Value(Type.string, name));

		// there is no sub list
		Constant getNameSubList = new Constant(new Value(Type.string, "", ""));
		setExpression2(getNameSubList);
		
		setValid();
		
	}
	
	public Replacement(Expression exp1) {
		
		super();
		
		setOperation(Expression_Function.replacement);
		
		setExpression1(exp1);
		setValue(new Value(Type.string, ""));

		// there is no sub list
		Constant getNameSubList = new Constant(new Value(Type.string, "", ""));
		setExpression2(getNameSubList);
		
		setValid();
		
	}
	
	public Replacement(String nameSubList, String name, Expression exp1) {
	
		super();
		
		setOperation(Expression_Function.replacement);
		
		setExpression1(exp1);
		setValue(new Value(Type.string, name));
		
		Constant getNameSubList = new Constant(new Value(Type.string, nameSubList, nameSubList));
		setExpression2(getNameSubList);
		
		setValid();
		
	}

}
