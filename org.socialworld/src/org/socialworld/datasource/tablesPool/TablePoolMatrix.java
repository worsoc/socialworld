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
public class TablePoolMatrix extends Table {

	public final  String 	ALL_COLUMNS 		=	" func_id, anzRow, anzCol, typeShare, typeOffset ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int func_id[];
	int anzRow[]; 		
	int anzCol[]; 
	int typeShare[]; 
	int typeOffset[]; 
	
	@Override
	protected String getTableName() {
		return "swpool_matrix";
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
		anzRow = new int[rowCount];
		anzCol = new int[rowCount];
		typeShare = new int[rowCount];
		typeOffset = new int[rowCount];
			
		try {
			while (rs.next()) {
				
				func_id[row] = rs.getInt(1);
				anzRow[row] = rs.getInt(2);
				anzCol[row] = rs.getInt(3);
				typeShare[row] = rs.getInt(4);
				typeOffset[row] = rs.getInt(5);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void insert(int func_id, int anzRow, int anzCol, int typeShare, int typeOffset) {
		String statement;
			
		if ((func_id > 0) & (anzRow > 0) & (anzCol > 0)) {
			
			statement 	= "INSERT INTO swpool_matrix (func_id, anzRow, anzCol, typeShare, typeOffset) VALUES (" + 
							func_id + ", " + anzRow + ", " + anzCol + ", " + typeShare + ", " + typeOffset + ")";
			
			insert(statement);
		}
	}
	
	public void updateAnzRow(int func_id, int anzRow) {
		String statement;
			
		if ((func_id > 0) & (anzRow > 0) ) {

			statement 	= "UPDATE swpool_matrix SET " +
					"anzRow = " + anzRow  + 
					" WHERE func_id = " + func_id ;
			
			update(statement);
		}
	}

	public void updateAnzCol(int func_id, int anzCol) {
		String statement;
			
		if ((func_id > 0) & (anzCol > 0) ) {

			statement 	= "UPDATE swpool_matrix SET " +
					"anzCol = " + anzCol  + 
					" WHERE func_id = " + func_id ;
			
			update(statement);
		}
	}

	public void updateTypeShare(int func_id, int typeShare) {
		String statement;
			
		if ((func_id > 0) & (typeShare > 0) ) {

			statement 	= "UPDATE swpool_matrix SET " +
					"typeShare = " + typeShare  + 
					" WHERE func_id = " + func_id ;
			
			update(statement);
		}
	}

	public void updateTypeOffset(int func_id, int typeOffset) {
		String statement;
			
		if ((func_id > 0) & (typeOffset > 0) ) {

			statement 	= "UPDATE swpool_matrix SET " +
					"typeOffset = " + typeOffset  + 
					" WHERE func_id = " + func_id ;
			
			update(statement);
		}
	}
	
	public void delete(int func_id) {
		String statement;
			
		if ( func_id > 0)  {
	
			statement 	= "DELETE FROM swpool_matrix " +
					" WHERE func_id = " + func_id ;
						
			delete(statement);
		}
	}

	public int getFunctionID(int index) {
		return this.func_id[index];
	}

	public int getAnzRow(int index) {
		return anzRow[index];
	}

	public int getAnzCol(int index) {
		return anzCol[index];
	}

	public int getTypeShare(int index) {
		return typeShare[index];
	}

	public int getTypeOffset(int index) {
		return typeOffset[index];
	}
	
}
