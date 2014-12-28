/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.actions;

import org.socialworld.attributes.ActionMode;
import org.socialworld.attributes.ActionProperty;
import org.socialworld.attributes.ActionType;
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
	public static final int MAX_ACTION_PRIORITY = 256;
	
	// must be in byte range
	// because there is used a byte variable for index
	public static final int MAX_ACTION_WAIT_SECONDS = 60;
	
	protected ActionType type;
	protected ActionMode mode;
	protected Time minTime;
	protected Time maxTime;
	protected int priority;
	protected SimulationObject target;
	protected double intensity;
	protected long duration;
	protected long remainedDuration;

	protected boolean done = false;

	protected AbstractAction linkedAction;

	protected void setBaseProperties(final ActionType type, final ActionMode mode,
			final SimulationObject target, 
			final double intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		this.setType(type);
		this.setMode(mode);
		this.setTarget(target);
		this.setIntensity(intensity);
		this.setMinTime(minTime);
		this.setMaxTime(maxTime);
		this.setPriority(priority);
		this.setDuration(duration);
		this.setRemainedDuration(duration);

		this.linkedAction = null;
	}

	protected void setBaseProperties(AbstractAction original) {
		this.type = original.type;
		this.mode = original.mode;
		this.target = original.target;
		this.intensity =original.intensity;
		this.minTime = original.minTime;
		this.maxTime = original.maxTime;
		this.priority = original.priority;
		this.duration = original.duration;
		
	}
	
	
	public abstract Object getConcreteProperty(ActionProperty prop);
	
	/**
	 * The method sets the linked action.
	 * 
	 * @param linked action
	 */
	public void setLinkedAction (AbstractAction linkedAction) {
		this.linkedAction = linkedAction;
	}
	
	/**
	 * The method returns the linked action.
	 * It is null if there is no linked action.
	 * 
	 * @return linked action
	 */
	public AbstractAction getLinkedAction() {
		return linkedAction;
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

	
	
	@Override
	public String toString() {
		return this.type.toString() + " -> " + this.target.toString(); //$NON-NLS-1$
	}
	


}