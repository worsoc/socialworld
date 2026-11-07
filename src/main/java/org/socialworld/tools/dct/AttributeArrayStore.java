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
 * Where the DCT writes the generated attribute arrays to, record by record.
 * The tool knows only this interface, so the tests can run without the database.
 */
public interface AttributeArrayStore {

	void deleteAttribute(int aaID, int attribNr);

	void insertAttribute(int aaID, int attribNr, int value);

	void deleteGaussIndex(int gaussIndex);

	void insertGaussIndex(int gaussIndex, int aaID);

	/**
	 * @return where the data goes to, as it is shown to the user
	 */
	String getDescription();
}
