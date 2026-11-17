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
package org.socialworld.tools.dct;

/**
 * Where the generated event influence rules are written to: the database table swpool_eid,
 * or a store in memory for the tests.
 */
public interface EventInfluenceStore {

	/** Removes all rules. */
	void clear();

	void insert(int eventType, int influenceType, String json);

	/**
	 * @return where the data goes to, as it is shown to the user
	 */
	String getDescription();
}
