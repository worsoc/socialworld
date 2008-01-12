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
public class SearchActionDescription {

	private ActionType type;
	private ActionMode mode;
	private Time minTime;
	private Time maxTime;
	private int priority;
	private SimulationObject target;
	private Direction direction;
	private double intensity;
	private double duration;
	private double remainedDuration;

	private boolean searchByType;
	private boolean searchByMode;
	private boolean searchByMinTime;
	private boolean searchByMaxTime;
	private boolean searchByPriority;
	private boolean searchByTarget;
	private boolean searchByDirection;
	private boolean searchByIntensity;
	private boolean searchByDuration;
	private boolean searchByRemainedDuration;

	public SearchActionDescription() {
		searchByType = false;
		searchByMode = false;
		searchByMinTime = false;
		searchByMaxTime = false;
		searchByPriority = false;
		searchByTarget = false;
		searchByDirection = false;
		searchByIntensity = false;
		searchByDuration = false;
		searchByRemainedDuration = false;
	}

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
		searchByType = true;
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
		searchByMode = true;
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
		searchByMinTime = true;
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
		searchByMaxTime = true;
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
		searchByPriority = true;
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
		searchByTarget = true;
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
		searchByDirection = true;
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
		searchByIntensity = true;
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
		searchByDuration = true;
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
		searchByRemainedDuration = true;
	}

	/**
	 * The methods sets the flag, whether an action attribute is used by search
	 * 
	 * @param flag
	 */
	public void setSearchFlag(ActionDescriptionFlag flag) {
		switch (flag) {
		case searchByType:
			searchByType = true;
			break;
		case searchByMode:
			searchByMode = true;
			break;
		case searchByMinTime:
			searchByMinTime = true;
			break;
		case searchByMaxTime:
			searchByMaxTime = true;
			break;
		case searchByPriority:
			searchByPriority = true;
			break;
		case searchByTarget:
			searchByTarget = true;
			break;
		case searchByDirection:
			searchByDirection = true;
			break;
		case searchByIntensity:
			searchByIntensity = true;
			break;
		case searchByDuration:
			searchByDuration = true;
			break;
		case searchByRemainedDuration:
			searchByRemainedDuration = true;
			break;

		case dontSearchByType:
			searchByType = false;
			break;
		case dontSearchByMode:
			searchByMode = false;
			break;
		case dontSearchByMinTime:
			searchByMinTime = false;
			break;
		case dontSearchByMaxTime:
			searchByMaxTime = false;
			break;
		case dontSearchByPriority:
			searchByPriority = false;
			break;
		case dontSearchByTarget:
			searchByTarget = false;
			break;
		case dontSearchByDirection:
			searchByDirection = false;
			break;
		case dontSearchByIntensity:
			searchByIntensity = false;
			break;
		case dontSearchByDuration:
			searchByDuration = false;
			break;
		case dontSearchByRemainedDuration:
			searchByRemainedDuration = false;
			break;
		default:
		}
	}

	/**
	 * @return the searchByType
	 */
	public boolean isSearchByType() {
		return searchByType;
	}

	/**
	 * @return the searchByMode
	 */
	public boolean isSearchByMode() {
		return searchByMode;
	}

	/**
	 * @return the searchByMinTime
	 */
	public boolean isSearchByMinTime() {
		return searchByMinTime;
	}

	/**
	 * @return the searchByMaxTime
	 */
	public boolean isSearchByMaxTime() {
		return searchByMaxTime;
	}

	/**
	 * @return the searchByPriority
	 */
	public boolean isSearchByPriority() {
		return searchByPriority;
	}

	/**
	 * @return the searchByTarget
	 */
	public boolean isSearchByTarget() {
		return searchByTarget;
	}

	/**
	 * @return the searchByDirection
	 */
	public boolean isSearchByDirection() {
		return searchByDirection;
	}

	/**
	 * @return the searchByIntensity
	 */
	public boolean isSearchByIntensity() {
		return searchByIntensity;
	}

	/**
	 * @return the searchByDuration
	 */
	public boolean isSearchByDuration() {
		return searchByDuration;
	}

	/**
	 * @return the searchByRemainedDuration
	 */
	public boolean isSearchByRemainedDuration() {
		return searchByRemainedDuration;
	}
}
