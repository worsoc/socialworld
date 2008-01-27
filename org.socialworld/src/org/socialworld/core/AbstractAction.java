package org.socialworld.core;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Time;
import org.socialworld.objects.SimulationObject;

/**
 * It's the base class for all simulation actions (actions done by simulation
 * objects). It collects all base action attributes. That are action type,
 * action mode, priority, earliest execution time, latest execution time,
 * duration, remained duration, target simulation object, direction, intensity.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public abstract class AbstractAction {

	protected ActionType type;
	protected Time minTime;
	protected Time maxTime;
	protected int priority;
	protected SimulationObject target;
	protected Direction direction;
	protected ActionMode mode;
	protected double intensity;
	protected double duration;
	protected double remainedDuration;

	public AbstractAction() {
		super();
	}

	/**
	 * The methods returns the action type.
	 * 
	 * @return type
	 */
	public ActionType getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final ActionType type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public Time getMinTime() {
		return this.minTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMinTime(final Time time) {
		this.minTime = time;
	}

	/**
	 * @return the time
	 */
	public Time getMaxTime() {
		return this.maxTime;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setMaxTime(final Time time) {
		this.maxTime = time;
	}

	/**
	 * The methods returns the action's priority.
	 * 
	 * @return priority
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}

	/**
	 * @return the target
	 */
	public SimulationObject getTarget() {
		return this.target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(final SimulationObject target) {
		this.target = target;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the mode
	 */
	public ActionMode getMode() {
		return this.mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(final ActionMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the intensity
	 */
	public double getIntensity() {
		return this.intensity;
	}

	/**
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(final double intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(final double duration) {
		this.duration = duration;
	}

	/**
	 * The methods returns the action's remained duration.
	 * 
	 * @return remainedDuration
	 */
	public double getRemainedDuration() {
		return this.remainedDuration;
	}

	/**
	 * @param remainedDuration
	 *            the remainedDuration to set
	 */
	public void setRemainedDuration(final double remainedDuration) {
		this.remainedDuration = remainedDuration;
	}

	@Override
	public String toString() {
		return this.type.toString() + " -> " + this.target.toString(); //$NON-NLS-1$
	}

}