package org.socialworld.calculation.descriptions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;

public class State2ActionDescription {
		
		private FunctionByExpression f_createAction;
		
		public State2ActionDescription(Expression startExpression) {

			f_createAction = new FunctionByExpression(startExpression);

		}


		public FunctionByExpression getFunctionCreateAction() {
			return f_createAction;
		}
}