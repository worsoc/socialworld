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
public class TableState extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, array_nr, attrib_nr, value ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int id[];
	int array_nr[];
	int attrib_nr[];
	int value[];

	@Override
	protected String getTableName() {
		return "sw_attribute";
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
		setPK2(array_nr);
	}

	public void insert(int id, int array_nr, int attribs[]) {
		String statement;
		int i;
		int count;
			
		if (( id > 0) & (array_nr > 0) )  {
	
				
				count = attribs.length;
				
				for (i = 1; i <= count; i++) {
					statement = "INSERT INTO sw_attribute (id, array_nr, attrib_nr, value ) VALUES ("
								+ id + ", " + array_nr + ", " + i + ", " + attribs[i-1] + ")";
					insert(statement);
				}
					
		}
	}

	public void update(int id, int array_nr, int attrib_nr, int value) {
		String statement;
		
		if ( (id > 0) & (array_nr > 0) & (attrib_nr > 0) ) {
			
			statement 	= "UPDATE sw_attribute SET " +
					"value = " + value +
					" WHERE id = " + id + " AND array_nr = " + array_nr + " AND attrib_nr = " + attrib_nr;
			
			update(statement);
		}
	}	

	public void delete(int id,  int array_nr) {
		String statement;
			
		if  ((id > 0) & (array_nr > 0)) {
	
			statement 	= "DELETE FROM sw_attribute WHERE id = " + id + " AND array_nr = " + array_nr;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		array_nr = new int[rowCount];
		attrib_nr = new int[rowCount];
		value = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				array_nr[row] = rs.getInt(2);
				attrib_nr[row] = rs.getInt(3);
				value[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int[] getAttributes() {
		return value;
	}
	
}
