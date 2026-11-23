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
 * A simulation object that can dig or hack the ground (a shovel, a pickaxe, a hoe).
 */
public interface IDigging {

	public static final String NAME = "IDigging";

	/**
	 * @return how well it gets into the ground, 0 .. 100
	 */
	public abstract float getDiggingPower();

	/**
	 * @return the width of its blade or point in millimetres
	 */
	public abstract float getDiggingWidth();

}
