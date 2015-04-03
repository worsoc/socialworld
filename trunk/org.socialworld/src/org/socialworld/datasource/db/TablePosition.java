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
package org.socialworld.datasource.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathias Sikos
 *
 */
public class TablePosition extends Table {

	public final  String 	ALL_COLUMNS 		= " pos_id, x, y, z, locBase9, locBase25 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	public final  String 	COLUMNS_XYZ			= "  x, y, z ";
	public final  int 		SELECT_COLUMNS_XYZ 	= 2;

	public final  String 	COLUMNS_LOCBASE9			= " locBase9 ";
	public final  int 		SELECT_COLUMNS_LOCBASE9 	= 3;

	public final  String 	COLUMNS_LOCBASE25			= " locBase25 ";
	public final  int 		SELECT_COLUMNS_LOCBASE25 	= 4;

	int pos_id[];
	int x[];
	int y[];
	int z[];
	int locBase9[];
	
	String locBase25[];
	
	
	@Override
	protected  String getTableName() {
		return "sw_position";
	}

	@Override
	protected  String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		case SELECT_COLUMNS_XYZ:
			return  COLUMNS_XYZ;
		case SELECT_COLUMNS_LOCBASE9:
			return  COLUMNS_LOCBASE9;
		case SELECT_COLUMNS_LOCBASE25:
			return  COLUMNS_LOCBASE25;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
//		boolean allRead;
		ResultSet rs;
		int row = 0;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			pos_id = new int[rowCount];
			x = new int[rowCount];
			y = new int[rowCount];
			z = new int[rowCount];
			locBase9 = new int[rowCount];
			
			locBase25 = new String[rowCount];

			try {
				while (rs.next()) {
					
					pos_id[row] = rs.getInt(1);
					x[row] = rs.getInt(2);
					y[row] = rs.getInt(3);
					z[row] = rs.getInt(4);
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

		case SELECT_COLUMNS_XYZ:
			x = new int[rowCount];
			y = new int[rowCount];
			z = new int[rowCount];

			try {
				while (rs.next()) {
					
					x[row] = rs.getInt(1);
					y[row] = rs.getInt(2);
					z[row] = rs.getInt(3);
						
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;

		case SELECT_COLUMNS_LOCBASE9:
			locBase9 = new int[rowCount];
			
			try {
				while (rs.next()) {
					
					locBase9[row] = rs.getInt(1);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;

		case SELECT_COLUMNS_LOCBASE25:
			locBase25 = new String[rowCount];
			
			try {
				while (rs.next()) {
					
					locBase25[row] = rs.getString(1);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;

		default:
			pos_id = new int[rowCount];
			x = new int[rowCount];
			y = new int[rowCount];
			z = new int[rowCount];
			locBase9 = new int[rowCount];
			
			locBase25 = new String[rowCount];	

			try {
				while (rs.next()) {
					
					pos_id[row] = rs.getInt(1);
					x[row] = rs.getInt(2);
					y[row] = rs.getInt(3);
					z[row] = rs.getInt(4);
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
		
//		allRead = (row == rowCount);
	}

	public void insert(int pos_id, int x, int y, int z, int locBase9, String locBase25) {
		String statement;
			
		if (pos_id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "INSERT INTO sw_position (pos_id, x, y, z, locBase9, locBase25) VALUES (" + 
					pos_id + ", " + x + ", " + y + ", " + z + ", " + locBase9 + ", '" + locBase25 + "')";
			
			insert(statement);
		}
	}
	
	public void insert(int pos_id, int x, int y, int z) {
		String statement;
			
		if (pos_id > 0) {
			
			statement 	= "INSERT INTO sw_position (pos_id, x, y, z) VALUES (" + 
					pos_id + ", " + x + ", " + y + ", " + z + ")";
			
			insert(statement);
		}
	}

	public void insert(int pos_id, int locBase9, String locBase25) {
		String statement;
			
		if (pos_id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "INSERT INTO sw_position (pos_id, locBase9, locBase25) VALUES (" + 
					pos_id + ", " + locBase9 + ", '" + locBase25 + "')";
			
			insert(statement);
		}
	}

	public void update(int pos_id, int x, int y, int z, int locBase9, String locBase25) {
		String statement;
			
		if (pos_id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "UPDATE sw_position SET " +
					"x = " + x  + ", " +
					"y = " + y  + ", " +
					"z = " + z  + ", " +
					"locBase9 = " + locBase9 + ", " +
					"locBase25 = '" + locBase25 + "' " +
					"WHERE pos_id = " + pos_id  ;
			
			update(statement);
		}
	}

	public void update(int pos_id, int x, int y, int z) {
		String statement;
			
		if (pos_id > 0) {
	

			statement 	= "UPDATE sw_position SET " +
					"x = " + x  + ", " +
					"y = " + y  + ", " +
					"z = " + z  + " " +
					"WHERE pos_id = " + pos_id  ;
			
			update(statement);
		}
	}
	
	public void update(int pos_id,  int locBase9, String locBase25) {
		String statement;
			
		if (pos_id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "UPDATE sw_position SET " +
					"locBase9 = " + locBase9 + ", " +
					"locBase25 = '" + locBase25 + "' " +
					"WHERE pos_id = " + pos_id  ;
			
			update(statement);
		}
	}

	public void update(int pos_id,  int locBase9) {
		String statement;
			
		if (pos_id > 0) {

			statement 	= "UPDATE sw_position SET " +
					"locBase9 = " + locBase9 + " " +
					"WHERE pos_id = " + pos_id  ;
			
			update(statement);
		}
	}

	public void update(int pos_id,   String locBase25) {
		String statement;
			
		if (pos_id > 0) {
	
			if (locBase25 == null) locBase25 = "NULL";

			statement 	= "UPDATE sw_position SET " +
					"locBase25 = '" + locBase25 + "' " +
					"WHERE pos_id = " + pos_id  ;
			
			update(statement);
		}
	}

	public void delete(int pos_id) {
		String statement;
			
		if (pos_id > 0) {
	
			statement 	= "DELETE FROM sw_position WHERE pos_id = " + pos_id  ;
			
			delete(statement);
		}
	}

}
