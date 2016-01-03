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

import org.socialworld.datasource.mariaDB.Table;

/**
 * @author Mathias Sikos
 *
 */
public class TableAnimalsDirections extends Table {

	public final  String 	ALL_COLUMNS 		=
			" id, chestX, chestY, chestZ, viewX, viewY, viewZ, moveX, moveY, moveZ ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	public final  String 	COLUMNS_CHEST 		=
			" id, chestX, chestY, chestZ ";
	public final  int 		SELECT_COLUMNS_CHEST 	= 2;

	public final  String 	COLUMNS_VIEW 		=
			" id, viewX, viewY, viewZ ";
	public final  int 		SELECT_COLUMNS_VIEW 	= 3;

	public final  String 	COLUMNS_MOVE 		=
			" id, moveX, moveY, moveZ ";
	public final  int 		SELECT_COLUMNS_MOVE 	= 4;

	int id[];
	float chestX[];
	float chestY[];
	float chestZ[];
	float viewX[];
	float viewY[];
	float viewZ[];
	float moveX[];
	float moveY[];
	float moveZ[];
	
	@Override
	protected String getTableName() {
		return "sw_animalsdirections";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		case SELECT_COLUMNS_CHEST:
			return  COLUMNS_CHEST;
		case SELECT_COLUMNS_VIEW:
			return  COLUMNS_VIEW;
		case SELECT_COLUMNS_MOVE:
			return  COLUMNS_MOVE;
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
			selectAllColumns(rs);

			break;
		case SELECT_COLUMNS_CHEST:
				id = new int[rowCount];
			chestX = new float[rowCount];
			chestY = new float[rowCount];
			chestZ = new float[rowCount];
	
			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					chestX[row] = rs.getFloat(2);
					chestY[row] = rs.getFloat(3);
					chestZ[row] = rs.getFloat(4);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;
		case SELECT_COLUMNS_VIEW:
			id = new int[rowCount];
			viewX = new float[rowCount];
			viewY = new float[rowCount];
			viewZ = new float[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					viewX[row] = rs.getFloat(2);
					viewY[row] = rs.getFloat(3);
					viewZ[row] = rs.getFloat(4);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	
			break;
		case SELECT_COLUMNS_MOVE:
			id = new int[rowCount];
			moveX = new float[rowCount];
			moveY = new float[rowCount];
			moveZ = new float[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					moveX[row] = rs.getFloat(2);
					moveY[row] = rs.getFloat(3);
					moveZ[row] = rs.getFloat(4);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	
			break;
		default:
			selectAllColumns(rs);
		}

		setPK1(id);
	}

	public void insert(int id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO sw_animalsdirections (id) VALUES (" + id + ")";
			
			insert(statement);
		}
	}

	public void updateChest(int id, float x, float y, float z) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_animalsdirections SET " +
					"chestX = " + x  + ", " +
					"chestY = " + y  + ", " +
					"chestZ = " + z  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateView(int id, float x, float y, float z) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_animalsdirections SET " +
					"viewX = " + x  + ", " +
					"viewY = " + y  + ", " +
					"viewZ = " + z  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateMove(int id, float x, float y, float z) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_animalsdirections SET " +
					"moveX = " + x  + ", " +
					"moveY = " + y  + ", " +
					"moveZ = " + z  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_animalsdirections WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		chestX = new float[rowCount];
		chestY = new float[rowCount];
		chestZ = new float[rowCount];
		viewX = new float[rowCount];
		viewY = new float[rowCount];
		viewZ = new float[rowCount];
		moveX = new float[rowCount];
		moveY = new float[rowCount];
		moveZ = new float[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				chestX[row] = rs.getFloat(2);
				chestY[row] = rs.getFloat(3);
				chestZ[row] = rs.getFloat(4);
				viewX[row] = rs.getFloat(5);
				viewY[row] = rs.getFloat(6);
				viewZ[row] = rs.getFloat(7);
				moveX[row] = rs.getFloat(8);
				moveY[row] = rs.getFloat(9);
				moveZ[row] = rs.getFloat(10);

				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public float[] getChestDirection(int index) {
		float direction[];
				
		direction = new float[3];
		direction[0] = chestX[index];
		direction[1] = chestY[index];
		direction[2] = chestZ[index];
		
		return direction;
	}

	public float[] getViewDirection(int index) {
		float direction[];
				
		direction = new float[3];
		direction[0] = viewX[index];
		direction[1] = viewY[index];
		direction[2] = viewZ[index];
		
		return direction;
	}

	public float[] getMoveDirection(int index) {
		float direction[];
				
		direction = new float[3];
		direction[0] = moveX[index];
		direction[1] = moveY[index];
		direction[2] = moveZ[index];
		
		return direction;
	}

}
