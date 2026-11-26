/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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
package org.socialworld.objects.properties;

/**
 * A simulation object that has a value for its owner and for others (coins, a ring, a book).
 */
public interface IValuable {

	public static final String NAME = "IValuable";

	/**
	 * Since 2026-11-26 from the material (Item.calculateValue: value per kg of the material x craft factor of the
	 * class); until the mass comes it is a value per kg.
	 *
	 * @return the value, 0 and open to the top
	 */
	public abstract float getValue();

}
