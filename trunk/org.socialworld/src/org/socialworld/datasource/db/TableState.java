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
public class TableState extends Table {

	public final  String 	ALL_COLUMNS 		=
			" id, attrib1, attrib2, attrib3, attrib4, attrib5, attrib6, attrib7, attrib8 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int id[];
	int a[];
	int b[];
	int c[];
	int d[];
	int e[];
	int f[];
	int g[];
	int h[];

	@Override
	protected String getTableName() {
		return "sw_state";
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
			id = new int[rowCount];
			a = new int[rowCount];
			b = new int[rowCount];
			c = new int[rowCount];
			d = new int[rowCount];
			e = new int[rowCount];
			f = new int[rowCount];
			g = new int[rowCount];
			h = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					a[row] = rs.getInt(2);
					b[row] = rs.getInt(3);
					c[row] = rs.getInt(4);
					d[row] = rs.getInt(5);
					e[row] = rs.getInt(6);
					f[row] = rs.getInt(7);
					g[row] = rs.getInt(8);
					h[row] = rs.getInt(9);

					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			break;
		default:
			id = new int[rowCount];
			a = new int[rowCount];
			b = new int[rowCount];
			c = new int[rowCount];
			d = new int[rowCount];
			e = new int[rowCount];
			f = new int[rowCount];
			g = new int[rowCount];
			h = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					a[row] = rs.getInt(2);
					b[row] = rs.getInt(3);
					c[row] = rs.getInt(4);
					d[row] = rs.getInt(5);
					e[row] = rs.getInt(6);
					f[row] = rs.getInt(7);
					g[row] = rs.getInt(8);
					h[row] = rs.getInt(9);

					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}

	}

	public void insert(int id, int attribs[]) {
		String statement;
		int i;
		int count;
			
		if ( id > 0)   {
	

			count = attribs.length;
			statement = "INSERT INTO sw_state (";
			
			for (i = 1; i <= count; i++) {
				statement = statement + "attrib" + i + ", ";
			}
			statement = statement + "id) VALUES (";
			
			for (i = 0; i < count; i++) {
				statement = statement + attribs[i] +  ", ";
			}
			statement 	= statement	+ id +  ") ";
			
			insert(statement);
		}
	}

	public void update(int id, int attribNr, int attribValue) {
		String statement;
		
		if ( (id > 0) & (attribNr > 0) ) {
			
			statement 	= "UPDATE sw_state SET " +
					"attrib" + attribNr + " = " + attribValue +
					" WHERE id = " + id ;
			
			update(statement);
		}
	}	

	public void delete(int id) {
		String statement;
			
		if  (id > 0)  {
	
			statement 	= "DELETE FROM sw_state WHERE id = " + id ;
			
			delete(statement);
		}
	}

}
