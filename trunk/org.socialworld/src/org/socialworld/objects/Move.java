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
	 *                the mode to set
     */
    public void setMode(final ActionMode mode) {
	this.mode = mode;
    }

}
