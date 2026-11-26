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
 * A simulation object that protects its wearer or holder against blows (a helmet, a shield, mail).
 * The four kinds of blow are those of IWeapon.
 */
public interface IProtective {

	public static final String NAME = "IProtective";

	/**
	 * @return the protection against stabs, 0 .. 100
	 */
	public abstract float getProtectionAgainstStab();

	/**
	 * @return the protection against club blows, 0 .. 100
	 */
	public abstract float getProtectionAgainstClub();

	/**
	 * @return the protection against strokes, 0 .. 100
	 */
	public abstract float getProtectionAgainstStroke();

	/**
	 * @return the protection against backhand blows, 0 .. 100
	 */
	public abstract float getProtectionAgainstBackhand();

}
