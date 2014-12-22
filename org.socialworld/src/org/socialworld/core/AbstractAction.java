package org.socialworld.core;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
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
	public static final int MAX_ACTION_PRIORITY = 256;
	
	// must be in byte range
	// because there is used a byte variable for index
	public static final int MAX_ACTION_WAIT_SECONDS = 60;
	
	protected ActionType type;
	protected Time minTime;
	protected Time maxTime;
	protected int priority;
	protected SimulationObject target;
	protected Vector direction;
	protected ActionMode mode;
	protected double intensity;
	protected long duration;
	protected long remainedDuration;
	protected boolean done;
	
	public AbstractAction() {
		super();
		done = false;
	}

	public void setDone() {
		done = true;
	}
	
	public boolean isDone() {
		return done;
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
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
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
	public long getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(final long duration) {
		this.duration = duration;
	}

	/**
	 * The methods returns the action's remained duration.
	 * 
	 * @return remainedDuration
	 */
	public long getRemainedDuration() {
		return this.remainedDuration;
	}

	/**
	 * @param remainedDuration
	 *            the remainedDuration to set
	 */
	public void setRemainedDuration(final long remainedDuration) {
		this.remainedDuration = remainedDuration;
	}

	@Override
	public String toString() {
		return this.type.toString() + " -> " + this.target.toString(); //$NON-NLS-1$
	}

}