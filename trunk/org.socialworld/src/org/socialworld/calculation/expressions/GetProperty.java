package org.socialworld.calculation.expressions;

import org.socialworld.attributes.SimPropertyName;
import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class GetProperty extends Expression {

	public GetProperty(SimPropertyName simPropName, String propertyName) {
		
		super();
		
		setOperation(Expression_Function.property);
		setValue(new Value(Type.simPropName, simPropName));
		
		Expression exp1 = new Constant(new Value(Type.string, propertyName));
		Expression exp2 = new Constant(new Value(Type.string, ""));
		
		setExpression1(exp1);
		setExpression2(exp2);
		setValid();
			
			
	}

	public GetProperty(String methodName, String propertyName) {
		
		super();
		
		setOperation(Expression_Function.property);
		setValue(new Value(Type.simPropName, SimPropertyName.unknown));
		
		Expression exp1 = new Constant(new Value(Type.string, propertyName));
		Expression exp2 = new Constant(new Value(Type.string, methodName));
		
		setExpression1(exp1);
		setExpression2(exp2);
		setValid();
			
	}

}
