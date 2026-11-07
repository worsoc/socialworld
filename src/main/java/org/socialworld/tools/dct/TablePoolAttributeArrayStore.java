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

import org.socialworld.datasource.tablesPool.TableGaussAA;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;

/**
 * Writes to the tables swpool_attribute and swgauss_aa.
 * The connection only prints its errors, so every call checks the affected rows.
 */
public class TablePoolAttributeArrayStore implements AttributeArrayStore {

	private final TablePoolAttribute tableAttribute = new TablePoolAttribute();
	private final TableGaussAA tableGauss = new TableGaussAA();

	@Override
	public void deleteAttribute(int aaID, int attribNr) {
		tableAttribute.delete(aaID, attribNr);
		if (!tableAttribute.executionWasSuccessful()) {
			throw new IllegalStateException("attribute " + attribNr + " of aa_id " + aaID + " could not be deleted");
		}
	}

	@Override
	public void insertAttribute(int aaID, int attribNr, int value) {
		tableAttribute.insert(aaID, attribNr, value);
		if (tableAttribute.countAffectedRows() != 1) {
			throw new IllegalStateException("attribute " + attribNr + " of aa_id " + aaID + " could not be inserted");
		}
	}

	@Override
	public void deleteGaussIndex(int gaussIndex) {
		tableGauss.delete(gaussIndex);
		if (!tableGauss.executionWasSuccessful()) {
			throw new IllegalStateException("gauss_index " + gaussIndex + " could not be deleted");
		}
	}

	@Override
	public void insertGaussIndex(int gaussIndex, int aaID) {
		tableGauss.insert(gaussIndex, aaID);
		if (tableGauss.countAffectedRows() != 1) {
			throw new IllegalStateException("gauss_index " + gaussIndex + " could not be inserted");
		}
	}

	@Override
	public String getDescription() {
		return "tables swpool_attribute and swgauss_aa";
	}
}
