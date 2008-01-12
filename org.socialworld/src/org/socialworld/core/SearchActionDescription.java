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
public class SearchActionDescription extends AbstractAction {

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
	super();
	this.searchByType = false;
	this.searchByMode = false;
	this.searchByMinTime = false;
	this.searchByMaxTime = false;
	this.searchByPriority = false;
	this.searchByTarget = false;
	this.searchByDirection = false;
	this.searchByIntensity = false;
	this.searchByDuration = false;
	this.searchByRemainedDuration = false;
    }

    /**
     * @param type
     *                the type to set
     */
    @Override
    public void setType(final ActionType type) {
	this.type = type;
	this.searchByType = true;
    }

    /**
     * @param mode
     *                the mode to set
     */
    @Override
    public void setMode(final ActionMode mode) {
	this.mode = mode;
	this.searchByMode = true;
    }

    /**
     * @param time
     *                the time to set
     */
    @Override
    public void setMinTime(final Time time) {
	this.minTime = time;
	this.searchByMinTime = true;
    }

    /**
     * @param time
     *                the time to set
     */
    @Override
    public void setMaxTime(final Time time) {
	this.maxTime = time;
	this.searchByMaxTime = true;
    }

    /**
     * @param priority
     *                the priority to set
     */
    @Override
    public void setPriority(final int priority) {
	this.priority = priority;
	this.searchByPriority = true;
    }

    /**
     * @param target
     *                the target to set
     */
    @Override
    public void setTarget(final SimulationObject target) {
	this.target = target;
	this.searchByTarget = true;
    }

    /**
     * @param direction
     *                the direction to set
     */
    @Override
    public void setDirection(final Direction direction) {
	this.direction = direction;
	this.searchByDirection = true;
    }

    /**
     * @param intensity
     *                the intensity to set
     */
    @Override
    public void setIntensity(final double intensity) {
	this.intensity = intensity;
	this.searchByIntensity = true;
    }

    /**
     * @param duration
     *                the duration to set
     */
    @Override
    public void setDuration(final double duration) {
	this.duration = duration;
	this.searchByDuration = true;
    }

    /**
     * @param remainedDuration
     *                the remainedDuration to set
     */
    @Override
    public void setRemainedDuration(final double remainedDuration) {
	this.remainedDuration = remainedDuration;
	this.searchByRemainedDuration = true;
    }

    /**
     * The methods sets the flag, whether an action attribute is used by search
     * 
     * @param flag
     */
    public void setSearchFlag(final ActionDescriptionFlag flag) {
	switch (flag) {
	case searchByType:
	    this.searchByType = true;
	    break;
	case searchByMode:
	    this.searchByMode = true;
	    break;
	case searchByMinTime:
	    this.searchByMinTime = true;
	    break;
	case searchByMaxTime:
	    this.searchByMaxTime = true;
	    break;
	case searchByPriority:
	    this.searchByPriority = true;
	    break;
	case searchByTarget:
	    this.searchByTarget = true;
	    break;
	case searchByDirection:
	    this.searchByDirection = true;
	    break;
	case searchByIntensity:
	    this.searchByIntensity = true;
	    break;
	case searchByDuration:
	    this.searchByDuration = true;
	    break;
	case searchByRemainedDuration:
	    this.searchByRemainedDuration = true;
	    break;

	case dontSearchByType:
	    this.searchByType = false;
	    break;
	case dontSearchByMode:
	    this.searchByMode = false;
	    break;
	case dontSearchByMinTime:
	    this.searchByMinTime = false;
	    break;
	case dontSearchByMaxTime:
	    this.searchByMaxTime = false;
	    break;
	case dontSearchByPriority:
	    this.searchByPriority = false;
	    break;
	case dontSearchByTarget:
	    this.searchByTarget = false;
	    break;
	case dontSearchByDirection:
	    this.searchByDirection = false;
	    break;
	case dontSearchByIntensity:
	    this.searchByIntensity = false;
	    break;
	case dontSearchByDuration:
	    this.searchByDuration = false;
	    break;
	case dontSearchByRemainedDuration:
	    this.searchByRemainedDuration = false;
	    break;
	default:
	}
    }

    /**
     * @return the searchByType
     */
    public boolean isSearchByType() {
	return this.searchByType;
    }

    /**
     * @return the searchByMode
     */
    public boolean isSearchByMode() {
	return this.searchByMode;
    }

    /**
     * @return the searchByMinTime
     */
    public boolean isSearchByMinTime() {
	return this.searchByMinTime;
    }

    /**
     * @return the searchByMaxTime
     */
    public boolean isSearchByMaxTime() {
	return this.searchByMaxTime;
    }

    /**
     * @return the searchByPriority
     */
    public boolean isSearchByPriority() {
	return this.searchByPriority;
    }

    /**
     * @return the searchByTarget
     */
    public boolean isSearchByTarget() {
	return this.searchByTarget;
    }

    /**
     * @return the searchByDirection
     */
    public boolean isSearchByDirection() {
	return this.searchByDirection;
    }

    /**
     * @return the searchByIntensity
     */
    public boolean isSearchByIntensity() {
	return this.searchByIntensity;
    }

    /**
     * @return the searchByDuration
     */
    public boolean isSearchByDuration() {
	return this.searchByDuration;
    }

    /**
     * @return the searchByRemainedDuration
     */
    public boolean isSearchByRemainedDuration() {
	return this.searchByRemainedDuration;
    }
}
