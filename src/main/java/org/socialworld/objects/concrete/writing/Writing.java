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
package org.socialworld.objects.concrete.writing;

import org.socialworld.objects.properties.CarrySize;
import org.socialworld.objects.concrete.HumanCrafted;
import org.socialworld.objects.enums.EnumHumanCrafted;

/**
 * A human crafted thing for writing and reading: a book, a scroll, a quill.
 * The class shows the main property only; other abilities come from interfaces (e.g. IWeapon).
 */
public abstract class Writing extends HumanCrafted {

	public Writing() {
		super();
		belongsTo = EnumHumanCrafted.Writing;
	}

	/** small by default (at the belt, in a bag) */
	public CarrySize getCarrySize() {return CarrySize.small; }

}
