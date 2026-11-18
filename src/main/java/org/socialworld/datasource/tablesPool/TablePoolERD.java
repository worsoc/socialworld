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
 * The event reaction rules: one row per pair of event type and reaction type that has rules,
 * the rule as JSON (JsonEventReactionDescription).
 */
public class TablePoolERD extends Table {

	public final  String 	ALL_COLUMNS 		=	" eventType, reactionType, jsonERD";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int eventType[];
	int reactionType[];
	String jsonERD[];

	@Override
	protected String getTableName() {
		return "swpool_erd";
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

		setPK1(eventType);
		setPK2(reactionType);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;

		eventType = new int[rowCount];
		reactionType = new int[rowCount];
		jsonERD = new String[rowCount];

		try {
			while (rs.next()) {
				eventType[row] = rs.getInt(1);
				reactionType[row] = rs.getInt(2);
				jsonERD[row] = rs.getString(3);
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public int count(int eventType, int reactionType) {
		int count = 0;

		if (eventType > 0 && reactionType >= 0) {
			count = selectScalarInt("SELECT count(*) FROM swpool_erd WHERE eventType = " + eventType
					+ " AND reactionType = " + reactionType);
		}
		return count;
	}

	/** Inserts the rule of the pair, or replaces it if the pair already has one. */
	public void insert(int eventType, int reactionType, String jsonERD) {
		String statement;

		if (eventType > 0 && reactionType >= 0) {
			if (count(eventType, reactionType) > 0) {
				update(eventType, reactionType, jsonERD);
			}
			else {
				statement 	= "INSERT INTO swpool_erd (eventType, reactionType, jsonERD) VALUES (" +
						eventType + ", " + reactionType + ", '" + quote(jsonERD) + "')";
				insert(statement);
			}
		}
	}

	public void update(int eventType, int reactionType, String jsonERD) {
		String statement;

		if (eventType > 0 && reactionType >= 0) {
			statement 	= "UPDATE swpool_erd SET jsonERD = '" + quote(jsonERD) + "'" +
					" WHERE eventType = " + eventType + " AND reactionType = " + reactionType;
			update(statement);
		}
	}

	public void delete(int eventType, int reactionType) {
		String statement;

		if (eventType > 0 && reactionType >= 0) {
			statement 	= "DELETE FROM swpool_erd WHERE eventType = " + eventType + " AND reactionType = " + reactionType;
			delete(statement);
		}
	}

	public int getEventType(int index) {
		return this.eventType[index];
	}

	public int getReactionType(int index) {
		return this.reactionType[index];
	}

	public String getJsonERD(int index) {
		return this.jsonERD[index];
	}

	/** Doubles the single quotes, so that a quote inside the JSON does not end the SQL string. */
	static String quote(String text) {
		return text.replace("'", "''");
	}

}
