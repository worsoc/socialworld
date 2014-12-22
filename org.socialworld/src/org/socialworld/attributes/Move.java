/**
 * 
 */
package org.socialworld.attributes;

import org.socialworld.calculation.Vector;

/**
 * The class collects information about a
 *         simulation object's movements. A move has an action mode (here it is
 *         a move mode).
 * @author Mathias Sikos (tyloesand) 
 */
public class Move {
	private ActionMode mode;
	private Vector direction;
	
	public Move() {
		this.mode = ActionMode.walk;
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
	
}
