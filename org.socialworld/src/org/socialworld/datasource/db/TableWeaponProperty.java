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
public class TableWeaponProperty extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, mass, hard, sharp, m, n  ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	float mass[];
	float hard[];
	float sharp[];
	float m[];
	float n[];
			
	@Override
	protected String getTableName() {
		return "sw_weaponproperty";
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
			selectAllColumns(rs);

			break;
		default:
			selectAllColumns(rs);
		}
	}

	public void insert(int id, float mass, float hard, float sharp, float m, float n) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO sw_weaponproperty (id, mass, hard, sharp,m, n) VALUES (" +
			id + ", " + mass + ", " + hard + ", " + sharp + ", " + m + ", " + n + ")";
			
			insert(statement);
		}
	}

	public void insert(int id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO sw_weaponproperty (id) VALUES (" + id + ")";
			
			insert(statement);
		}
	}

	public void update(int id, float mass, float hard, float sharp, float m, float n) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"mass = " + mass  + ", " +
					"hard = " + hard  + ", " +
					"sharp = " + sharp + ", " +
					"m = " + m + ", " +
					"n = " + n + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateMass(int id, float mass) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"mass = " + mass  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateHard(int id, float hard) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"hard = " + hard  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateSharp(int id, float sharp) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"sharp = " + sharp  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateM(int id, float m) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"m = " + m  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateN(int id, float n) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "UPDATE sw_weaponproperty SET " +
					"n = " + n  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}


	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_weaponproperty WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		mass = new float[rowCount];
		hard = new float[rowCount];
		sharp = new float[rowCount];
		m = new float[rowCount];
		n = new float[rowCount];


		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				mass[row] = rs.getFloat(2);
				hard[row] = rs.getFloat(3);
				sharp[row] = rs.getFloat(4);
				m[row] = rs.getFloat(3);
				n[row] = rs.getFloat(4);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

}
