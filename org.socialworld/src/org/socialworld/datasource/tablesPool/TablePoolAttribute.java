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
public class TablePoolAttribute extends Table {

	public final  String 	ALL_COLUMNS 		=	" index, attrib_nr, value ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int index[];
	int attrib_nr[]; 		
	int value[];

	@Override
	protected String getTableName() {
		return "swpool_attribute";
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
		int row = 0;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			index = new int[rowCount];
			attrib_nr = new int[rowCount];
			value = new int[rowCount];

			try {
				while (rs.next()) {
					
					index[row] = rs.getInt(1);
					attrib_nr[row] = rs.getInt(2);
					value[row] = rs.getInt(3);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;
		default:
			index = new int[rowCount];
			attrib_nr = new int[rowCount];
			value = new int[rowCount];

			try {
				while (rs.next()) {
					
					index[row] = rs.getInt(1);
					attrib_nr[row] = rs.getInt(2);
					value[row] = rs.getInt(3);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		setPK1(index);
		setPK2(attrib_nr);

	}

	public void insert(int index, int attrib_nr, int value) {
		String statement;
			
		if ((index > 0) & (attrib_nr > 0)) {
			
			statement 	= "INSERT INTO swpool_attribute (index, attrib_nr, value) VALUES (" + 
							index + ", " + attrib_nr + ", " + value + ")";
			
			insert(statement);
		}
	}
	
	public void update(int index, int attrib_nr, int value) {
		String statement;
			
		if ((index > 0) & (attrib_nr > 0)) {
	

			statement 	= "UPDATE swpool_attribute SET " +
					"value = " + value  + 
					" WHERE index = " + index + " AND attrib_nr = " + attrib_nr ;
			
			update(statement);
		}
	}

	public void delete(int index, int attrib_nr) {
		String statement;
			
		if ((index > 0) & (attrib_nr > 0)) {
	
			statement 	= "DELETE FROM swpool_attribute " +
					" WHERE index = " + index + " AND attrib_nr = " + attrib_nr ;
			
			delete(statement);
		}
	}

	public int getIndex(int index) {
		return this.index[index];
	}

	public int getAttribNr(int index) {
		return attrib_nr[index];
	}

	public int getValue(int index) {
		return value[index];
	}

}
