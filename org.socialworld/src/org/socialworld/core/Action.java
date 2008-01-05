/**
 * 
 */
package org.socialworld.core;

import org.socialworld.objects.Direction;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Action {

    private ActionType type;
    private int priority;
    private SimulationObject target;
    private Direction direction;
    private int mode;
    private double intensity;

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
     *                the type to set
     */
    public void setType(ActionType type) {
	this.type = type;
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
     *                the priority to set
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
     *                the target to set
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
     *                the direction to set
     */
    public void setDirection(Direction direction) {
	this.direction = direction;
    }

    /**
     * @return the mode
     */
    public int getMode() {
	return mode;
    }

    /**
     * @param mode
     *                the mode to set
     */
    public void setMode(int mode) {
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
     *                the intensity to set
     */
    public void setIntensity(double intensity) {
	this.intensity = intensity;
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
     *                the remainedDuration to set
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
