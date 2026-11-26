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
package org.socialworld.objects.concrete.weapons;

import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.enums.EnumHumanCrafted;

/**
 * A human crafted thing made for fighting: a dagger, a sword, a shield.
 * The class shows the main property only; other abilities come from interfaces (e.g. IWeapon).
 */
public abstract class Weapon extends HumanCrafted {

	public Weapon() {
		super();
		belongsTo = EnumHumanCrafted.Weapon;
	}

	/** weapons are large by default (on the back or in the hands) */
	public CarrySize getCarrySize() {return CarrySize.large; }

}
