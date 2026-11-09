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
 * Where the DCT writes the generated matrices to, record by record.
 */
public interface AttributeMatrixStore {

	void deleteMapping(int gaussIndexAA, int gaussIndexACMDrawn);

	void deleteGaussIndex(int gaussIndex);

	void deleteCell(int funcID, int row, int column);

	void deleteMatrix(int funcID);

	void insertMatrix(int funcID, int countRows, int countColumns, int typeShare, int typeOffset);

	void insertCell(int funcID, int row, int column, float share, int function, float offset, int inputType);

	void insertGaussIndex(int gaussIndex, int funcID);

	void insertMapping(int gaussIndexAA, int gaussIndexACMDrawn, int gaussIndexACM);
}
