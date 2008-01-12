/**
 * 
 */
package org.socialworld.core;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Action extends AbstractAction {

    /**
     * The methods decrements the attribute remainedDuration. That means, an
     * action needs less time to complete.
     * 
     * @param decrement
     */
    public void lowerRemainedDuration(final double decrement) {
	this.remainedDuration -= decrement;
    }

    /**
     * The methods increments the attribute remainedDuration. That means, an
     * action needs more time to complete.
     * 
     * @param increment
     */
    public void raiseRemainedDuration(final double increment) {
	this.remainedDuration += increment;
    }
}
