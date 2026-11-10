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
package org.socialworld.datasource.mariaDB;

import static org.assertj.core.api.Assumptions.assumeThat;

import java.util.function.Supplier;

import org.socialworld.datasource.tablesPool.TableGaussAA;
import org.socialworld.datasource.tablesPool.TableGaussAAACM;
import org.socialworld.datasource.tablesPool.TableGaussACM;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;
import org.socialworld.datasource.tablesPool.TablePoolMatrixRowCol;

/**
 * Lets a test skip itself when there is nothing to read: no database, a missing table or an empty table.
 * The test calls it before reading, outside of any try block,
 * so that errors caused by the data in a filled table let the test fail. Only reads.
 */
public final class DatabaseAssumptions {

	private DatabaseAssumptions() {
	}

	/**
	 * The tables that TablePoolAttributeDataReader reads.
	 */
	public static void assumeAttributeTablesAreFilled() {
		assumeTablesAreFilled(TablePoolAttribute::new, TableGaussAA::new, TableGaussACM::new,
				TablePoolMatrixRowCol::new, TableGaussAAACM::new);
	}

	@SafeVarargs
	public static void assumeTablesAreFilled(Supplier<? extends Table>... tables) {
		for (Supplier<? extends Table> table : tables) {
			assumeIsFilled(table);
		}
	}

	private static void assumeIsFilled(Supplier<? extends Table> supplier) {
		String tableName;
		int rows;
		try {
			Table table = supplier.get();
			tableName = table.getTableName();
			rows = table.selectScalarInt("SELECT count(*) FROM " + tableName);
		} catch (RuntimeException e) {
			assumeThat(false).as("no database available or table missing: " + e).isTrue();
			return;
		}
		assumeThat(rows).as("rows in " + tableName).isGreaterThan(0);
	}
}
