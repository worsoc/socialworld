package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class ValueFromValueList extends Expression {

	public ValueFromValueList(String valueListName, String propertyName) {
		
		setOperation(Expression_Function.valueFromValueList);
		setExpression1(new Constant(new Value(Type.string, valueListName)));
		setExpression2(new Constant(new Value(Type.string, propertyName)));
		
		setValid();

	}
}

