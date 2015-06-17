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
public class TableGaussAA extends Table {

	public final  String 	ALL_COLUMNS 		=	" gauss_index, aa_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int gauss_index[]; 		
	int aa_id[];

	protected String getTableName() {
		return "swgauss_aa";
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
		setPK1(gauss_index);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		gauss_index = new int[rowCount];
		aa_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				gauss_index[row] = rs.getInt(1);
				aa_id[row] = rs.getInt(2);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int gauss_index, int aa_id) {
		String statement;
			
		if (aa_id > 0)  {
			
			statement 	= "INSERT INTO swgauss_aa (gauss_index, aa_id) VALUES (" + 
					gauss_index + ", " + aa_id   + ")";
			
			insert(statement);
		}
	}

	public void update(int gauss_index, int aa_id) {
		String statement;
			
		if (aa_id > 0)  {
			
			statement 	= "UPDATE swgauss_aa SET aa_id = " + aa_id + 
					" WHERE gauss_index = "  + gauss_index;
			
			update(statement);
		}
	}

	public void delete(int gauss_index) {
		String statement;
			
			
			statement 	= "DELETE FROM swgauss_aa " + 
					" WHERE gauss_index = "  + gauss_index;
			
			delete(statement);
	}

	public int getGaussIndex(int index) {
		return this.gauss_index[index];
	}

	public int getAttributeArrayID(int index) {
		return aa_id[index];
	}

}
