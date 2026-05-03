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

import java.util.HashMap;
import java.util.Map;

public enum InventoryPlace {

	noWhere(0),
	leftHand(1),
	rightHand(2),
	mouth(3),
	leftHandGlove(11),
	rightHandGlove(12),
	leftFootSock(13),
	rightFootSock(14),
	leftFootShoe(15),
	rightFootShoe(16);
	
	private static final Map<String, InventoryPlace> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, InventoryPlace> INDEX_CACHE = new HashMap<>();

	static {
		for (InventoryPlace place : values()) {
			NAME_CACHE.put(place.name(), place);
			INDEX_CACHE.put(place.index, place);
		}
	}

	private final int index;

	private InventoryPlace(int index) {
		this.index = index;
	}

	public static int getMaxIndex() {
		return INDEX_CACHE.size() - 1;
	}

	public static InventoryPlace getName(int index) {
		InventoryPlace place = INDEX_CACHE.get(index);
		return (place != null) ? place : noWhere;
	}
	
	public int getIndex() {
		return index;
	}

	public static InventoryPlace fromName(String name) {
		InventoryPlace place = NAME_CACHE.get(name);
		return (place != null) ? place : noWhere;
	}

}
