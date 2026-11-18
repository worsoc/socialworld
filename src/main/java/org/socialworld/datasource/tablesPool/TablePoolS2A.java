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
package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

/**
 * The state-to-action rules: one row per state-to-action type (character number - 1),
 * the rule as JSON (JsonState2ActionDescription).
 */
public class TablePoolS2A extends Table {

	public final  String 	ALL_COLUMNS 		=	" state2ActionType, jsonS2A";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int state2ActionType[];
	String jsonS2A[];

	@Override
	protected String getTableName() {
		return "swpool_s2a";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
		ResultSet rs;

		rs = connection.executeQuery(statement);

		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			selectAllColumns( rs);
			break;
		default:
			selectAllColumns( rs);
		}

		setPK1(state2ActionType);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;

		state2ActionType = new int[rowCount];
		jsonS2A = new String[rowCount];

		try {
			while (rs.next()) {
				state2ActionType[row] = rs.getInt(1);
				jsonS2A[row] = rs.getString(2);
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public int count(int state2ActionType) {
		int count = 0;

		if (state2ActionType >= 0) {
			count = selectScalarInt("SELECT count(*) FROM swpool_s2a WHERE state2ActionType = " + state2ActionType);
		}
		return count;
	}

	/** Inserts the rule of the type, or replaces it if the type already has one. */
	public void insert(int state2ActionType, String jsonS2A) {
		String statement;

		if (state2ActionType >= 0) {
			if (count(state2ActionType) > 0) {
				update(state2ActionType, jsonS2A);
			}
			else {
				statement 	= "INSERT INTO swpool_s2a (state2ActionType, jsonS2A) VALUES (" +
						state2ActionType + ", '" + quote(jsonS2A) + "')";
				insert(statement);
			}
		}
	}

	public void update(int state2ActionType, String jsonS2A) {
		String statement;

		if (state2ActionType >= 0) {
			statement 	= "UPDATE swpool_s2a SET jsonS2A = '" + quote(jsonS2A) + "'" +
					" WHERE state2ActionType = " + state2ActionType;
			update(statement);
		}
	}

	public void delete(int state2ActionType) {
		String statement;

		if (state2ActionType >= 0) {
			statement 	= "DELETE FROM swpool_s2a WHERE state2ActionType = " + state2ActionType;
			delete(statement);
		}
	}

	public int getState2ActionType(int index) {
		return this.state2ActionType[index];
	}

	public String getJsonS2A(int index) {
		return this.jsonS2A[index];
	}

	/** Doubles the single quotes, so that a quote inside the JSON does not end the SQL string. */
	static String quote(String text) {
		return text.replace("'", "''");
	}

}
