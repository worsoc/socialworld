/*
* Social World
* Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.datasource.tablesSimulation.properties;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.datasource.mariaDB.Table;

public class TableDirection extends Table {

	public final  String 	ALL_COLUMNS 		=	" direction_id, x, y, z, power ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int direction_id[];
	float x[];
	float y[];
	float z[];
	float power[];
	
	@Override
	protected String getTableName() {
		return "sw_direction";
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

		setPK1(direction_id);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		direction_id = new int[rowCount];
		x = new float[rowCount];
		y = new float[rowCount];
		z = new float[rowCount];
		power = new float[rowCount];

		try {
			while (rs.next()) {
				
				direction_id[row] = rs.getInt(1);
				x[row] = rs.getFloat(2);
				y[row] = rs.getFloat(3);
				z[row] = rs.getFloat(4);
				power[row] = rs.getFloat(5);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int direction_id, float x, float y,  float z, float power) {
		String statement;
			
		if (direction_id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ direction_id  + ", " + x + ", " + y + ", " + z + ", " + power  + ")";
			
			insert(statement);
		}
	}
	
	public void updateVector( int direction_id, float x, float y, float z) {
		String statement;
			
		if (direction_id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"x = " + x  + " " + ", y = " + y  + " " + "z = " + z  + " " +
					"WHERE direction_id = " + direction_id  ;
			
			update(statement);
		}
	}
	

	public void updatePower( int direction_id, float power) {
		String statement;
			
		if (direction_id > 0 ) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"power = " + power  + " " +
					"WHERE direction_id = " + direction_id  ;
			
			update(statement);
		}
	}

	public Vector getVector(int index) {
		return new Vector(x[index], y[index], z[index]);
	}

	public float getPower(int index) {
		return power[index];
	}
	

	public Direction getDirection(int index, PropertyName propName) {
		Direction direction = new Direction(propName, getVector(index), power[index]);
		return direction;
	}

}
