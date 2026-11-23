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
 * A simulation object that makes a sound when it is played (a lute, a flute, a horn).
 */
public interface IPlayable {

	public static final String NAME = "IPlayable";

	/**
	 * @return the loudness, 0 .. 100
	 */
	public abstract float getLoudness();

	/**
	 * @return the pitch, 0 (deep) .. 100 (high)
	 */
	public abstract float getPitch();

}
