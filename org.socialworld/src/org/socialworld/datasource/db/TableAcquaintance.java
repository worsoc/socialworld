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
public class TableAcquaintance extends Table {

	public final  String 	ALL_COLUMNS 		=
			" id, partner_id, attrib1, attrib2, attrib3, attrib4, attrib5, attrib6, attrib7 ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int id[];
	int partner_id[];
	int a[];
	int b[];
	int c[];
	int d[];
	int e[];
	int f[];
	int g[];

	@Override
	protected String getTableName() {
		return "sw_acquaintance";
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
			partner_id = new int[rowCount];
			a = new int[rowCount];
			b = new int[rowCount];
			c = new int[rowCount];
			d = new int[rowCount];
			e = new int[rowCount];
			f = new int[rowCount];
			g = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					partner_id[row] = rs.getInt(2);
					a[row] = rs.getInt(3);
					b[row] = rs.getInt(4);
					c[row] = rs.getInt(5);
					d[row] = rs.getInt(6);
					e[row] = rs.getInt(7);
					f[row] = rs.getInt(8);
					g[row] = rs.getInt(9);

					
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
			partner_id = new int[rowCount];
			a = new int[rowCount];
			b = new int[rowCount];
			c = new int[rowCount];
			d = new int[rowCount];
			e = new int[rowCount];
			f = new int[rowCount];
			g = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					partner_id[row] = rs.getInt(2);
					a[row] = rs.getInt(3);
					b[row] = rs.getInt(4);
					c[row] = rs.getInt(5);
					d[row] = rs.getInt(6);
					e[row] = rs.getInt(7);
					f[row] = rs.getInt(8);
					g[row] = rs.getInt(9);

					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		setPK1(id);
		setPK2(partner_id);
	}

	public void insert(int id, int partner_id, int attribs[]) {
		String statement;
		int i;
		int count;
			
		if ( (id > 0) & (partner_id > 0) ) {
	

			count = attribs.length;
			statement = "INSERT INTO sw_acquaintance (";
			
			for (i = 1; i <= count; i++) {
				statement = statement + "attrib" + i + ", ";
			}
			statement = statement + "id, partner_id) VALUES (";
			
			for (i = 0; i < count; i++) {
				statement = statement + attribs[i] +  ", ";
			}
			statement 	= statement	+ id + ", " + partner_id + ") ";
			
			insert(statement);
		}
	}

	public void update(int id, int partner_id, int attribNr, int attribValue) {
		String statement;
		
		if ( (id > 0) & (attribNr > 0) ) {
			
			statement 	= "UPDATE sw_acquaintance SET " +
					"attrib" + attribNr + " = " + attribValue +
					" WHERE id = " + id + " AND partner_id = " + partner_id ;
			
			update(statement);
		}
	}	

	public void delete(int id, int partner_id) {
		String statement;
			
		if ( (id > 0) & (partner_id > 0) ) {
	
			statement 	= "DELETE FROM sw_acquaintance WHERE id = " + id + " AND partner_id = " +  partner_id;
			
			delete(statement);
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
		
		return attributes;
	}

}
