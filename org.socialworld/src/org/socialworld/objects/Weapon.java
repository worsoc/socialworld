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
