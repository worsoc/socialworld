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
package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TableStateFlying extends TableState {

	private static List<TableStateFlying> instances = new ArrayList<TableStateFlying>();

	public static TableStateFlying getInstance() {
		for ( TableStateFlying instance : instances) {
			if (!instance.isLocked()) {
				return instance;
			}
		}
		TableStateFlying newInstance = new TableStateFlying();
		instances.add(newInstance);
		return newInstance;
	}

	public final  String 	ALL_COLUMNS 		=	" id, direction_id, widthWings, numberWings ";

	int direction_id[];
	double widthWings[];
	int numberWings[];

	@Override
	protected String getTableName() {
		return "swstate_flying";
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

		setPK1(id);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		direction_id = new int[rowCount];
		widthWings = new double[rowCount];
		numberWings = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				direction_id[row] = rs.getInt(2);
				widthWings[row] = rs.getDouble(3);
				numberWings[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id, int direction_id, double widthWings, int numberWings) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" + id + ", " + direction_id + ", " + widthWings + ", " + numberWings + ")";
			
			insert(statement);
		}
	}

	public void updateDirectionID(int id, int direction_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"direction_id = " + direction_id  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateSpeed(int id, double widthWings) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"widthWings = " + widthWings  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateNumberLegs(int id, int numberWings) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"numberWings = " + numberWings  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM " + getTableName() + " WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	public int getDirectionID(int index) {
		return direction_id[index];
	}

	public double getWidthWings(int index) {
		return widthWings[index];
	}

	public int getNumberWings(int index) {
		return numberWings[index];
	}

}
