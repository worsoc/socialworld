package org.socialworld.calculation;

public class State2ActionDescription {
		
		private Function f_createAction;
		
		public State2ActionDescription(Expression startExpression) {

			f_createAction = new Function(startExpression);

		}


		public Function getFunctionCreateAction() {
			return f_createAction;
		}
}
