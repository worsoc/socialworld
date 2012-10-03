package org.socialworld.calculation;

public class EventReactionDescription {
	
	protected EventReactionDelayExpression delayExpression;
	protected EventReactionPriorityExpression priorityExpression;
	protected EventReactionRelativeDirectionExpression relativeDirectionExpression;
	protected EventReactionModeExpression actionModeExpression;
	protected EventReactionTypeExpression actionTypeExpression;
	protected EventReactionIntensityExpression intensityExpression;
	protected EventReactionDurationExpression durationExpression;
	
	public EventReactionDescription() {

	}

	public EventReactionDelayExpression getDelayExpression() { return delayExpression;}
	public EventReactionPriorityExpression getPriorityExpression() { return priorityExpression;}
	public EventReactionRelativeDirectionExpression getRelativeDirectionExpression() { return relativeDirectionExpression;}
	public EventReactionModeExpression getActionModeExpression() { return actionModeExpression;}
	public EventReactionTypeExpression getActionTypeExpression() { return actionTypeExpression;}
	public EventReactionIntensityExpression getintensityExpression() { return intensityExpression;}
	public EventReactionDurationExpression getDurationExpression() { return durationExpression;}
	
}
