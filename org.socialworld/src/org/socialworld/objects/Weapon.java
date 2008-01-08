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

	public void handle(Action action, SimulationObject user) {
		ActionMode mode = action.getMode();

		switch (mode) {
		default:
			super.handle(action, user);
		}
	}
}
