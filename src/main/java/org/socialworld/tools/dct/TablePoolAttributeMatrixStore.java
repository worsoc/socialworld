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

import org.socialworld.datasource.tablesPool.TableGaussAAACM;
import org.socialworld.datasource.tablesPool.TableGaussACM;
import org.socialworld.datasource.tablesPool.TablePoolMatrix;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

/**
 * Writes to swpool_matrix, swpool_matrix_rowcol, swgauss_acm and swgauss_aa_acm.
 * The connection only prints its errors, so every call checks the affected rows.
 */
public class TablePoolAttributeMatrixStore implements AttributeMatrixStore {

	private final TablePoolMatrix tableMatrix = new TablePoolMatrix();
	private final TablePoolMatrixRowCol tableCell = new TablePoolMatrixRowCol();
	private final TableGaussACM tableGauss = new TableGaussACM();
	private final TableGaussAAACM tableMapping = new TableGaussAAACM();

	@Override
	public void deleteMapping(int gaussIndexAA, int gaussIndexACMDrawn) {
		tableMapping.delete(gaussIndexAA, gaussIndexACMDrawn);
		if (!tableMapping.executionWasSuccessful()) throw new IllegalStateException("mapping could not be deleted");
	}

	@Override
	public void deleteGaussIndex(int gaussIndex) {
		tableGauss.delete(gaussIndex);
		if (!tableGauss.executionWasSuccessful()) throw new IllegalStateException("gauss_index " + gaussIndex + " could not be deleted");
	}

	@Override
	public void deleteCell(int funcID, int row, int column) {
		tableCell.delete(funcID, row, column);
		if (!tableCell.executionWasSuccessful()) throw new IllegalStateException("cell of func_id " + funcID + " could not be deleted");
	}

	@Override
	public void deleteMatrix(int funcID) {
		tableMatrix.delete(funcID);
		if (!tableMatrix.executionWasSuccessful()) throw new IllegalStateException("func_id " + funcID + " could not be deleted");
	}

	@Override
	public void insertMatrix(int funcID, int countRows, int countColumns, int typeShare, int typeOffset) {
		tableMatrix.insert(funcID, countRows, countColumns, typeShare, typeOffset);
		if (tableMatrix.countAffectedRows() != 1) throw new IllegalStateException("func_id " + funcID + " could not be inserted");
	}

	@Override
	public void insertCell(int funcID, int row, int column, float share, int function, float offset, int inputType) {
		tableCell.insert(funcID, row, column, share, function, offset, inputType);
		if (tableCell.countAffectedRows() != 1) {
			throw new IllegalStateException("cell " + row + "/" + column + " of func_id " + funcID + " could not be inserted");
		}
	}

	@Override
	public void insertMapping(int gaussIndexAA, int gaussIndexACMDrawn, int gaussIndexACM) {
		tableMapping.insert(gaussIndexAA, gaussIndexACMDrawn, gaussIndexACM);
		if (tableMapping.countAffectedRows() != 1) throw new IllegalStateException("mapping " + gaussIndexAA + "/" + gaussIndexACMDrawn + " could not be inserted");
	}

	@Override
	public void insertGaussIndex(int gaussIndex, int funcID) {
		tableGauss.insert(gaussIndex, funcID);
		if (tableGauss.countAffectedRows() != 1) throw new IllegalStateException("gauss_index " + gaussIndex + " could not be inserted");
	}
}
