package org.socialworld.calculation;

public class EventReactionDescription {
	
	private Function f_createReaction;
	

	public EventReactionDescription(Expression startExpression) {

		f_createReaction = new Function(startExpression);

	}

	public Function getFunctionCreateReaction() {
		return f_createReaction;
	}
}
