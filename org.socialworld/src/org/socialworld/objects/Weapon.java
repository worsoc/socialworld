/**
 * 
 */
package org.socialworld.objects;

import org.socialworld.attributes.ActionMode;
import org.socialworld.core.Action;

/**
 * A weapon is an item that has special properties. It has its functionality in
 * the hands of humans.
 * 
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
