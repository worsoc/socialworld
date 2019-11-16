/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.actions.handle;


public enum InventoryPlace {

	leftHand(1),
	rightHand(2),
	mouth(3),
	leftHandGlove(11),
	rightHandGlove(12),
	leftFootSock(13),
	rightFootSock(14),
	leftFootShoe(15),
	rightFootShoe(16);
	
	
	private int index;

	private InventoryPlace(int index) {
		this.index = index;
	}

	public static InventoryPlace getName(int index) {
		for (InventoryPlace place : InventoryPlace.values())
			if (place.index == index)
				return place;
		return null;
	}
	
	public int getIndex() {
		return index;
	}


}
