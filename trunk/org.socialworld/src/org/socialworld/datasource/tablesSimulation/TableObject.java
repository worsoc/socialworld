/*
* Social World
* Copyright (C) 2015  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathias Sikos
 *
 */
public class TableObject extends Table {
	
	public final  String 	ALL_COLUMNS 		= " id, type, position, state2act, locBase9, locBase25 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	public final  String 	COLUMNS_ID 		= " id ";
	public final  int 		SELECT_COLUMN_ID 	= 2;

	int id[];
	int type[];
	int position[];
	int state2act[];
	int locBase9[];
	
	String locBase25[];

	@Override
	protected String getTableName() {
		return "sw_object";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		case SELECT_COLUMN_ID:
			return  COLUMNS_ID;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
		ResultSet rs;
		int row = 0;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			id = new int[rowCount];
			type = new int[rowCount];
			position = new int[rowCount];
			state2act = new int[rowCount];
			locBase9 = new int[rowCount];
			
			locBase25 = new String[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					type[row] = rs.getInt(2);
					position[row] = rs.getInt(3);
					state2act[row] = rs.getInt(4);
					locBase9[row] = rs.getInt(5);

					locBase25[row] = rs.getString(6);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;

		case SELECT_COLUMN_ID:
			id = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;


		default:
			id = new int[rowCount];
			type = new int[rowCount];
			position = new int[rowCount];
			state2act = new int[rowCount];
			locBase9 = new int[rowCount];
			
			locBase25 = new String[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					type[row] = rs.getInt(2);
					position[row] = rs.getInt(3);
					state2act[row] = rs.getInt(4);
					locBase9[row] = rs.getInt(5);

					locBase25[row] = rs.getString(6);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		}

		setPK1(id);
	}

	public void insert(int id, int type, int position, int state2act, int locBase9, String locBase25) {
		String statement;
			
		if (id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";
			
			statement 	= "INSERT INTO sw_object (id, type, position, state2act, locBase9, locBase25) VALUES (" + 
					id + ", " + type + ", " + position + ", " + state2act + ", " + locBase9 + ", '" + locBase25 + "')";
			
			insert(statement);
		}
	}

	public void insert(int id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO sw_object (id) VALUES (" + id + ")";
			
			insert(statement);
		}
	}

	public void update(int id, int type, int position, int state2act, int locBase9, String locBase25) {
		String statement;
			
		if (id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "UPDATE sw_object SET " +
					"type = " + type  + ", " +
					"position = " + position  + ", " +
					"state2act = " + state2act  + ", " +
					"locBase9 = " + locBase9 + ", " +
					"locBase25 = '" + locBase25 + "' " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void update(int id,  int position, int locBase9, String locBase25) {
		String statement;
			
		if (id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "UPDATE sw_object SET " +
					"position = " + position  + ", " +
					"locBase9 = " + locBase9 + ", " +
					"locBase25 = '" + locBase25 + "' " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_object WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	public int getType(int index) {
		return type[index];
	}
	
	public int getPosition(int index) {
		return position[index];
	}

	public int getState2Act(int index) {
		return state2act[index];
	}
	
	public int getLocBase9(int index) {
		return locBase9[index];
	}

	public String getLocBase25(int index) {
		return locBase25[index];
	}

}
