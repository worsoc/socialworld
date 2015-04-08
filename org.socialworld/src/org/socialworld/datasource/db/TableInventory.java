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
public class TableInventory extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, leftHand, rightHand ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	public final  String 	COLUMNS_LEFTHAND 		=	" id, leftHand ";
	public final  int 		SELECT_COLUMNS_LEFTHAND 	= 2;

	public final  String 	COLUMNS_RIGHTHAND 		=	" id, rightHand ";
	public final  int 		SELECT_COLUMNS_RIGHTHAND 	= 3;


	int id[];
	int leftHand[];
	int rightHand[];
	
	@Override
	protected String getTableName() {
		return "sw_inventory";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		case SELECT_COLUMNS_LEFTHAND:
			return  COLUMNS_LEFTHAND;
		case SELECT_COLUMNS_RIGHTHAND:
			return  COLUMNS_RIGHTHAND;
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
		case SELECT_COLUMNS_LEFTHAND:
			selectLeftHand(rs);

			break;
		case SELECT_COLUMNS_RIGHTHAND:
			selectRightHand(rs);

			break;
		default:
			selectAllColumns(rs);
		}

	}

	public void updateHands(int id, int leftHand, int rightHand) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_inventory SET " +
					"leftHand = " + leftHand  + ", " +
					"rightHand = " + rightHand  + " " +
				"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateLeft(int id, int leftHand) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_inventory SET " +
					"leftHand = " + leftHand  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateRight(int id, int rightHand) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE sw_inventory SET " +
					"rightHand = " + rightHand  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_inventory WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		leftHand = new int[rowCount];
		rightHand = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				leftHand[row] = rs.getInt(2);
				rightHand[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	private void selectLeftHand(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		leftHand = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				leftHand[row] = rs.getInt(2);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	private void selectRightHand(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		rightHand = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				rightHand[row] = rs.getInt(2);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

}
