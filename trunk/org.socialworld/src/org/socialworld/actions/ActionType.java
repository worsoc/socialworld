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
package org.socialworld.actions;

/**
 * The enumeration collects all action types.
 * @author Mathias Sikos (tyloesand) 
 */
public enum ActionType {
	sleep(0),
	move(1),
	examine(2),
	touch(3), 
	itemAndInventory(4),
	handleItem(5),
	useWeapon(6),
	punch(7), 
	hear(8),
	talk(9),
	say(10),
	ignore(11);

	private int arrayIndex;

	private ActionType(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the ActionType.
	 * 
	 * @return attribute's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

}