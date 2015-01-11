/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.core.Event;

/**
 * The class Item describes all non-living simulation objects. It has a method
 * for handling an item. That implements what happens (in simulation sense) if
 * an item is used by a (living) simulation object.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Item extends SimulationObject {

	public Item(StateSimulationObject state) {
		super(state);
	}

	@Override
	public void changeByEvent(final Event event) {

	}
	
	public void reactToEvent(final Event event) {
		
	}


	/**
	 * The method implements the use of an item by an user. The kind of use is
	 * specified by an action mode.
	 * 
	 * @param mode
	 *            the mode of using
	 * @param user
	 *            the user
	 */
	public void handle(final AbstractAction action, final SimulationObject user) {
		final ActionMode mode = action.getMode();

		// TODO further modes
		
		switch (mode) {
		case examineItem:
			examine(action, user);
			break;
		case takeItem:
			take(action, user);
			break;
		case collectItem:
			collect(action, user);
			break;
		case switchItemToLeftHand:
			switchItemToLeftHand(action, user);
			break;
		case useTwoItems:
			useTwoItems(action, user);
			break;
		case dropItem:
			drop(action, user);
			break;
		default:

		}
	}

	private void use(final AbstractAction action, final SimulationObject user) {

	}

	private void examine(final AbstractAction action, final SimulationObject user) {

	}

	private void take(final AbstractAction action, final SimulationObject user) {

	}

	private void collect(final AbstractAction action, final SimulationObject user) {

	}

	private void switchItemToLeftHand(final AbstractAction action,
			final SimulationObject user) {

	}

	private void useTwoItems(final AbstractAction action, final SimulationObject user) {

	}

	private void drop(final AbstractAction action, final SimulationObject user) {

	}

	@Override
	protected void doAction(final ActionType type, final AbstractAction action) {
	}
}
