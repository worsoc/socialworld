package org.socialworld.calculation;

public class FunctionByExpression extends FunctionBase {
	Expression startExpression;
	
	public FunctionByExpression(Expression startExpression) {
		
		this.startExpression  = startExpression;
	}
	
	public void setStartExpression(Expression startExpression) {
		
		this.startExpression  = startExpression;
	}
	
	public Value calculate(Argument[] arguments) {
		return startExpression.evaluateExpression(arguments);
	}

}
