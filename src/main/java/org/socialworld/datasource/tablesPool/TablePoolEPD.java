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
 * Datenbank-Schnittstelle für den EventPerceptionDescriptionPool (swpool_epd).
 * Nutzt primitive Arrays für maximale Performance und allokationsarmes Caching.
 * 
 * @author Mathias Sikos
 */
public class TablePoolEPD extends Table {

	public final String ALL_COLUMNS        = " eventType, perceptionType, jsonEPD";
	public final int    SELECT_ALL_COLUMNS = 1;
	
	int eventType[];
	int perceptionType[];
	String jsonEPD[]; 		

	@Override
	protected String getTableName() {
		return "swpool_epd"; // Der reale Name der kognitiven Wahrnehmungs-Tabelle
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
			case SELECT_ALL_COLUMNS:
				return ALL_COLUMNS;
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
				selectAllColumns(rs);
				break;
			default:
				selectAllColumns(rs);		
		}

		// Registrierung der Primärschlüssel
		setPK1(eventType);
		setPK2(perceptionType);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		// rowCount wird von der vererbten Table-Basisklasse bereitgestellt
		eventType = new int[rowCount];
		perceptionType = new int[rowCount];
		jsonEPD = new String[rowCount];

		try {
			while (rs.next()) {
				eventType[row] = rs.getInt(1);
				perceptionType[row] = rs.getInt(2);
				jsonEPD[row] = rs.getString(3);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}

	public int count(int eventType, int perceptionType) {
		int count = 0;
		if (eventType > 0 && perceptionType >= 0) {
			count = selectScalarInt("SELECT count(*) FROM swpool_epd WHERE eventType = " + eventType + " AND perceptionType = " + perceptionType);
		}
		return count;
	}
	
	public void insert(int eventType, int perceptionType, String jsonEPD) {
		String statement;
			
		if (eventType > 0 && perceptionType >= 0) {
			if (count(eventType, perceptionType) > 0) {
				update(eventType, perceptionType, jsonEPD);
			}
			else {
				statement = "INSERT INTO swpool_epd (eventType, perceptionType, jsonEPD) VALUES (" + 
						eventType + ", " + perceptionType + ", '" + jsonEPD + "')";
				insert(statement);
			}
		}
	}

	public void update(int eventType, int perceptionType, String jsonEPD) {
		String statement;
			
		if (eventType > 0 && perceptionType >= 0) {
			statement = "UPDATE swpool_epd SET jsonEPD = '" + jsonEPD + "'" + 
					" WHERE eventType = " + eventType + " AND perceptionType = " + perceptionType;
			update(statement);
		}
	}

	public void delete(int eventType, int perceptionType) {
		String statement;
			
		if (eventType > 0 && perceptionType >= 0) {
			statement = "DELETE FROM swpool_epd WHERE eventType = " + eventType + " AND perceptionType = " + perceptionType;
			delete(statement);
		}
	}

	public int getEventType(int index) {
		return this.eventType[index];
	}

	public int getPerceptionType(int index) {
		return this.perceptionType[index];
	}

	public String getJsonEPD(int index) {
		return this.jsonEPD[index];
	}
	
	public String getJsonEPD(int eventType, int perceptionType) {
		if (eventType > 0 && perceptionType >= 0) {
			select(SELECT_ALL_COLUMNS, " WHERE eventType = " + eventType + " AND perceptionType = " + perceptionType, "");
			
			// getIndexFor2PK sucht über die von uns registrierten PK-Arrays den exakten Array-Index
			int index = getIndexFor2PK(eventType, perceptionType);
			
			if (index >= 0) {
				return getJsonEPD(index);
			}
		}
		return "";
	}
}
