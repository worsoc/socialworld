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
package org.socialworld.attributes.properties;

/**
 * The four kinds of blow, as the methods of IWeapon and IProtective name them (concept value and protection from
 * the material, step 3, 2026-11-26): stab (a stretching arm), club (both hands from above), stroke (a swung arm
 * from the side) and backhand (a swung arm from the other side).
 */
public enum BlowKind {
	stab,
	club,
	stroke,
	backhand;
}
