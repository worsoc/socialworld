/**
 * 
 */
package org.socialworld.core;

import org.socialworld.objects.ActionMode;
import org.socialworld.objects.Direction;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Time;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Action {

	private ActionType type;
	private Time minTime;
	private Time maxTime;
	private int priority;
	private SimulationObject target;
	private Direction direction;
	private ActionMode mode;
	private double intensity;
	private double duration;
	private double remainedDuration;

	/**
	 * The methods returns the action type.
	 * 
	 * @return type
	 */
	public ActionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ActionType type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public Time getMinTime() {
		return minTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMinTime(Time time) {
		this.minTime = time;
	}

	/**
	 * @return the time
	 */
	public Time getMaxTime() {
		return maxTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMaxTime(Time time) {
		this.maxTime = time;
	}

	/**
	 * The methods returns the action's priority.
	 * 
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the target
	 */
	public SimulationObject getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(SimulationObject target) {
		this.target = target;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the mode
	 */
	public ActionMode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(ActionMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the intensity
	 */
	public double getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * The methods returns the action's remained duration.
	 * 
	 * @return remainedDuration
	 */
	public double getRemainedDuration() {
		return remainedDuration;
	}

	/**
	 * @param remainedDuration
	 *            the remainedDuration to set
	 */
	public void setRemainedDuration(double remainedDuration) {
		this.remainedDuration = remainedDuration;
	}

	/**
	 * The methods decrements the attribute remainedDuration. That means, an
	 * action needs less time to complete.
	 * 
	 * @param decrement
	 */
	public void lowerRemainedDuration(double decrement) {
		remainedDuration -= decrement;
	}

	/**
	 * The methods increments the attribute remainedDuration. That means, an
	 * action needs more time to complete.
	 * 
	 * @param increment
	 */
	public void raiseRemainedDuration(double increment) {
		remainedDuration += increment;
	}
}
