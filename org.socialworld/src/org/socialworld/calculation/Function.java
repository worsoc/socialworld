package org.socialworld.calculation;

public class Function {

		Expression startExpression;
		
		public Function(Expression startExpression) {
			
			this.startExpression  = startExpression;
		}
		
		public void setStartExpression(Expression startExpression) {
			
			this.startExpression  = startExpression;
		}
		
		public Value calculate(Argument[] arguments) {
			return startExpression.evaluateExpression(arguments);
		}
}
