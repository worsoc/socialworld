package org.socialworld.calculation.expressions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.calculation.Expression_Function;

public class Junction extends Expression {

	public Junction(Expression_ConditionOperator logicalOperator, Expression[] junctionParts) {
		
		super();
		
		if (junctionParts.length > 1) {
			setOperation(Expression_Function.condition);
			setOperator(logicalOperator);
			setExpression1(junctionParts[0]);
			
			if (junctionParts.length > 2) 
				setExpression2(getJunctionTail(logicalOperator, 1, junctionParts));
			else
				setExpression2(junctionParts[1]);
			
			setValid();
			
		}
		
	}
	
	private Expression getJunctionTail(Expression_ConditionOperator logicalOperator, int index, Expression[] junctionParts) {
		
		Expression junction = new Expression();
		
		junction.setOperation(Expression_Function.condition);
		junction.setOperator(logicalOperator);
		
		if (index == (junctionParts.length - 2))
			junction.setExpression2(junctionParts[index + 1]);
		else	
			junction.setExpression2(getJunctionTail(logicalOperator, index + 1, junctionParts));
		
		junction.setExpression1(junctionParts[index]);
		
		return junction;
		
	}
	
}
