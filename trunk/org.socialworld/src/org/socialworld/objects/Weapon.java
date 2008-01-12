/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.core.Action;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Weapon extends Item {

    public Weapon() {
	super();
    }

    @Override
    public void handle(final Action action, final SimulationObject user) {
	final ActionMode mode = action.getMode();

	switch (mode) {
	default:
	    super.handle(action, user);
	}
    }
}
