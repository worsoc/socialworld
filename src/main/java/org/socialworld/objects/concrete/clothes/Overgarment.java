/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects.concrete.clothes;

import org.socialworld.actions.handle.InventoryPlace;
import org.socialworld.objects.enums.EnumHumanCrafted;

/**
 * Clothes worn over the others: an apron, a cloak, mail.
 */
public abstract class Overgarment extends Clothes {

	public Overgarment() {
		super();
		belongsTo = EnumHumanCrafted.Overgarment;
	}

	public boolean isLeftHandGlove() {return false; }
	public boolean isRightHandGlove() {return false; }

	public boolean isLeftFootSock() {return false; }
	public boolean isRightFootSock() {return false; }

	public boolean isLeftFootShoe() {return false; }
	public boolean isRightFootShoe() {return false; }

	public boolean isShirt() {return false; }
	public boolean isTrousers() {return false; }
	public boolean isCap() {return false; }
	public boolean isOvergarment() {return true; }

	/**
	 * @return the overgarment place it is worn on: overgarmentFront, overgarmentArmour or overgarmentCloak
	 */
	public abstract InventoryPlace getOvergarmentPlace();

}
