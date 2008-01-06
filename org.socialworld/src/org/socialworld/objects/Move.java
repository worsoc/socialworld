/**
 * 
 */
package org.socialworld.objects;

/**
 * @author Mathias Sikos (tyloesand)
 *
 */
public class Move {
	private ActionMode mode;
	
	public Move() {
		mode = ActionMode.walk;
	}

	/**
	 * @return the mode
	 */
	public ActionMode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(ActionMode mode) {
		this.mode = mode;
	}
	
	
}
