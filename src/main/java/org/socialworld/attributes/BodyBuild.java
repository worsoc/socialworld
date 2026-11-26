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
package org.socialworld.attributes;

/**
 * The body build of a character (column build of characters.csv, body build concept B1, 2026-11-25).
 * It chooses the composition of a human: composition.csv has one recipe "Human.&lt;build&gt;" per build.
 * Characters with the same number have the same build.
 */
public enum BodyBuild {

	/** thin, little muscle, the bones stand out */
	gaunt,
	/** lean and sinewy */
	wiry,
	/** ordinary */
	average,
	/** strong, much muscle and some fat */
	sturdy,
	/** stout */
	stout;

	/** The build of a line without the column. */
	public static final BodyBuild DEFAULT = average;

	/**
	 * @param name the name as it stands in characters.csv (lower case)
	 * @return the build of that name
	 * @throws IllegalArgumentException for an unknown name
	 */
	public static BodyBuild fromName(String name) {
		for (BodyBuild build : values()) {
			if (build.name().equals(name)) return build;
		}
		throw new IllegalArgumentException("unknown body build '" + name + "'");
	}
}
