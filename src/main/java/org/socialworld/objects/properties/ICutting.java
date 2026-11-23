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
package org.socialworld.objects.properties;

/**
 * A simulation object with a cutting edge (a knife, an axe, a sickle).
 */
public interface ICutting {

	public static final String NAME = "ICutting";

	/**
	 * @return the sharpness, 0 (blunt) .. 100 (razor-sharp)
	 */
	public abstract float getSharpness();

	/**
	 * @return the length of the blade in millimetres
	 */
	public abstract float getBladeLength();

}
