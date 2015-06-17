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
package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

/**
 * @author Mathias Sikos
 *
 */
public class TablePoolMatrixRowCol extends Table {

	public final  String 	ALL_COLUMNS 		=	" func_id, row, col, share, function, offset ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int func_id[];
	int row[]; 		
	int col[]; 
	int share[];
	int function[];
	int offset[];
	
	@Override
	protected String getTableName() {
		return "swpool_matrix_rowcol";
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
		setPK1(func_id);

	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		func_id = new int[rowCount];
		this.row = new int[rowCount];
		col = new int[rowCount];
		share = new int[rowCount];
		function = new int[rowCount];
		offset = new int[rowCount];

		try {
			while (rs.next()) {
				
				func_id[row] = rs.getInt(1);
				this.row[row] = rs.getInt(2);
				col[row] = rs.getInt(3);
				share[row] = rs.getInt(4);
				function[row] = rs.getInt(5);
				offset[row] = rs.getInt(6);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void insert(int func_id, int row, int col, int share, int function, int offset) {
		String statement;
			
		if ((func_id > 0) & (row > 0) & (col > 0)) {
			
			statement 	= "INSERT INTO swpool_matrix_rowcol (func_id, row, col, share, function, offset) VALUES (" + 
							func_id + ", " + row + ", " + col + ", " + share + ", " + function + ", " + offset +")";
			
			insert(statement);
		}
	}
	
	public void updateShare(int func_id, int row, int col,  int share) {
		String statement;
			
		if ((func_id > 0) & (row > 0) & (col > 0)) {
	

			statement 	= "UPDATE swpool_matrix_rowcol SET " +
					"share = " + share  + 
					" WHERE func_id = " + func_id + " AND row = " + row + " AND col = " + col;
			
			update(statement);
		}
	}

	public void updateFunction(int func_id, int row, int col,  int function) {
		String statement;
			
		if ((func_id > 0) & (row > 0) & (col > 0)) {
	

			statement 	= "UPDATE swpool_matrix_rowcol SET " +
					"function = " + function  + 
					" WHERE func_id = " + func_id + " AND row = " + row + " AND col = " + col;
			
			update(statement);
		}
	}

	public void updateOffset(int func_id, int row, int col,  int offset) {
		String statement;
			
		if ((func_id > 0) & (row > 0) & (col > 0)) {
	

			statement 	= "UPDATE swpool_matrix_rowcol SET " +
					"offset = " + offset  + 
					" WHERE func_id = " + func_id + " AND row = " + row + " AND col = " + col;
			
			update(statement);
		}
	}

	public void delete(int func_id, int row, int col) {
		String statement;
			
		if ((func_id > 0) & (row > 0) & (col > 0)) {
	
			statement 	= "DELETE FROM swpool_matrix_rowcol " +
					" WHERE func_id = " + func_id + " AND row = " + row + " AND col = " + col;
						
			delete(statement);
		}
	}

	public int getFunctionID(int index) {
		return this.func_id[index];
	}

	public int getRow(int index) {
		return row[index];
	}

	public int getCol(int index) {
		return col[index];
	}

	public int getShare(int index) {
		return share[index];
	}

	public int getFunction(int index) {
		return function[index];
	}

	public int getOffset(int index) {
		return offset[index];
	}

}

