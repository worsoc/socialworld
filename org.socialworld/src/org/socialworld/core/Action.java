/**
 * 
 */
package org.socialworld.core;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class Action {
	
	private int priority;
	private double remainedDuration;

/**
 * The methods returns the action's priority.
 * @return priority
 */	
	public int getPriority() {
		return priority;
	}
	
	/**
	 * The methods returns the action's remained duration.
	 * @return remainedDuration
	 */	
	public double getRemainedDuration() {
		return remainedDuration;
	}
	
/**
 * The methods decrements the attribute remainedDuration.
 * That means, an action needs less time to complete.
 * @param decrement
 */	
	public void lowerRemainedDuration(double decrement) {
		remainedDuration -= decrement;
	}
	
/**
 * The methods increments the attribute remainedDuration.
 * That means, an action needs more time to complete.
 * @param increment
 */
	public void raiseRemainedDuration(double increment) {
		remainedDuration += increment;
	}	
}
