/**
 * 
 */
package org.socialworld.attributes;

/**
 * The class collects information about a
 *         simulation object's movements. A move has an action mode (here it is
 *         a move mode).
 * @author Mathias Sikos (tyloesand) 
 */
public class Move {
	private ActionMode mode;

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

}
