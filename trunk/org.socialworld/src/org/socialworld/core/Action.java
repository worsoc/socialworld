/**
 * 
 */
package org.socialworld.core;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.SimulationObject;

/**
 * The class is an implementation of the abstract class AbstractAction. It adds
 * methods for manipulating the duration of an action.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class Action extends AbstractAction {

	private Action linkedAction;
	
/**
 * The constructor creates an action object with its attributes.
 * @param type
 * @param mode
 * @param target
 * @param direction
 * @param intensity
 * @param minTime
 * @param maxTime
 * @param priority
 * @param duration
 */	
	public Action(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final double intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		this.setType(type);
		this.setMode(mode);
		this.setTarget(target);
		this.setDirection(direction);
		this.setIntensity(intensity);
		this.setMinTime(minTime);
		this.setMaxTime(maxTime);
		this.setPriority(priority);
		this.setDuration(duration);
		this.setRemainedDuration(duration);

		this.linkedAction = null;
	}
	
	public Action(Action original) {
		this.type = original.type;
		this.mode = original.mode;
		this.target = original.target;
		this.direction = original.direction;
		this.intensity =original.intensity;
		this.minTime = original.minTime;
		this.maxTime = original.maxTime;
		this.priority = original.priority;
		this.duration = original.duration;
	}
	
	
	/**
	 * The method decrements the attribute remainedDuration. That means, an
	 * action needs less time to complete.
	 * 
	 * @param decrement
	 */
	public void lowerRemainedDuration(final long decrement) {
		this.remainedDuration -= decrement;
		
		if (this.remainedDuration <= 0) done = true;
	}

	/**
	 * The method increments the attribute remainedDuration. That means, an
	 * action needs more time to complete.
	 * 
	 * @param increment
	 */
	public void raiseRemainedDuration(final long increment) {
		this.remainedDuration += increment;
	}
	
	/**
	 * The method sets the linked action.
	 * 
	 * @param linked action
	 */
	public void setLinkedAction (Action linkedAction) {
		this.linkedAction = linkedAction;
	}
	
	/**
	 * The method returns the linked action.
	 * It is null if there is no linked action.
	 * 
	 * @return linked action
	 */
	public Action getLinkedAction() {
		return linkedAction;
	}
}
