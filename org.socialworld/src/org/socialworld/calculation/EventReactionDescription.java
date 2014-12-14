package org.socialworld.calculation;

public class EventReactionDescription {
	
	private FunctionByExpression f_createReaction;
	

	public EventReactionDescription(Expression startExpression) {

		f_createReaction = new FunctionByExpression(startExpression);

	}

	public FunctionByExpression getFunctionCreateReaction() {
		return f_createReaction;
	}
}
