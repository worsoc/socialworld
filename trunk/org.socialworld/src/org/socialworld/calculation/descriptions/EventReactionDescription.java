package org.socialworld.calculation.descriptions;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.FunctionByExpression;

public class EventReactionDescription {
	
	private FunctionByExpression f_createReaction;
	

	public EventReactionDescription(Expression startExpression) {

		f_createReaction = new FunctionByExpression(startExpression);

	}

	public FunctionByExpression getFunctionCreateReaction() {
		return f_createReaction;
	}
}
