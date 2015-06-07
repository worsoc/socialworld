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
			" id, lfd_nr, attrib1, attrib2, attrib3, attrib4, attrib5, attrib6, attrib7, attrib8 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int id[];
	int lfd_nr[];
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
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			selectAllColumns(rs);			

			break;
		default:
			selectAllColumns(rs);			
		}

		setPK1(id);
		setPK2(lfd_nr);
	}

	public void insert(int id, int lfd_nr, int attribs[]) {
		String statement;
		int i;
		int count;
			
		if (( id > 0) & (lfd_nr > 0) )  {
	

			count = attribs.length;
			statement = "INSERT INTO sw_state (";
			
			for (i = 1; i <= count; i++) {
				statement = statement + "attrib" + i + ", ";
			}
			statement = statement + "id, lfd_nr) VALUES (";
			
			for (i = 0; i < count; i++) {
				statement = statement + attribs[i] +  ", ";
			}
			statement 	= statement	+ id +  ", " + lfd_nr + ") ";
			
			insert(statement);
		}
	}

	public void update(int id, int lfd_nr, int attribNr, int attribValue) {
		String statement;
		
		if ( (id > 0) & (lfd_nr > 0) & (attribNr > 0) ) {
			
			statement 	= "UPDATE sw_state SET " +
					"attrib" + attribNr + " = " + attribValue +
					" WHERE id = " + id + " AND lfd_nr = " + lfd_nr;
			
			update(statement);
		}
	}	

	public void delete(int id,  int lfd_nr) {
		String statement;
			
		if  ((id > 0) & (lfd_nr > 0)) {
	
			statement 	= "DELETE FROM sw_state WHERE id = " + id + " AND lfd_nr = " + lfd_nr;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		lfd_nr = new int[rowCount];
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
				lfd_nr[row] = rs.getInt(2);
				a[row] = rs.getInt(3);
				b[row] = rs.getInt(4);
				c[row] = rs.getInt(5);
				d[row] = rs.getInt(6);
				e[row] = rs.getInt(7);
				f[row] = rs.getInt(8);
				g[row] = rs.getInt(9);
				h[row] = rs.getInt(10);

				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int[] getAttributes(int index) {
		int attributes[];
				
		attributes = new int[8];
		attributes[0] = a[index];
		attributes[1] = b[index];
		attributes[2] = c[index];
		attributes[3] = d[index];
		attributes[4] = e[index];
		attributes[5] = f[index];
		attributes[6] = g[index];
		attributes[7] = h[index];
		
		return attributes;
	}
	
}
