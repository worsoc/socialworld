package org.socialworld.calculation;

public class State2ActionDescription {
		
		protected ActionDelayExpression delayExpression;
		protected ActionPriorityExpression priorityExpression;
		protected ActionRelativeDirectionExpression relativeDirectionExpression;
		protected ActionModeExpression actionModeExpression;
		protected ActionTypeExpression actionTypeExpression;
		protected ActionIntensityExpression intensityExpression;
		protected ActionDurationExpression durationExpression;
		
		public State2ActionDescription() {

		}

		public ActionDelayExpression getDelayExpression() { return delayExpression;}
		public ActionPriorityExpression getPriorityExpression() { return priorityExpression;}
		public ActionRelativeDirectionExpression getRelativeDirectionExpression() { return relativeDirectionExpression;}
		public ActionModeExpression getActionModeExpression() { return actionModeExpression;}
		public ActionTypeExpression getActionTypeExpression() { return actionTypeExpression;}
		public ActionIntensityExpression getIntensityExpression() { return intensityExpression;}
		public ActionDurationExpression getDurationExpression() { return durationExpression;}
		

}
