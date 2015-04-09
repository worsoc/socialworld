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
public class TableKnowledgePool extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, lfd_nr, subject, kfs_id  ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int lfd_nr[];
	int subject[];
	int kfs_id[];

	@Override
	protected String getTableName() {
		return "sw_knowledgepool";
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
	}

	public void insert(int id, int lfd_nr, int subject, int kfs_id) {
		String statement;
			
		if ((id > 0) & (lfd_nr > 0) ) {
			
			statement 	= "INSERT INTO sw_knowledgepool (id, lfd_nr, subject, kfs_id) VALUES (" + 
			id + ", " + lfd_nr + ", " + subject + ", " + kfs_id + ")";
			
			insert(statement);
		}
	}

	public void update(int id, int lfd_nr, int subject, int kfs_id) {
		String statement;
			
		if ( (id > 0) & (lfd_nr > 0) ) {
	

			statement 	= "UPDATE sw_knowledgepool SET " +
					"subject = " + subject  + ", " +
					"kfs_id = " + kfs_id  + " " +
				"WHERE id = " + id + " AND lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public void delete(int id, int lfd_nr) {
		String statement;
			
		if ( (id > 0) & (lfd_nr > 0) ) {
	
			statement 	= "DELETE FROM sw_knowledgepool WHERE id = " + id + " AND lfd_nr = " + lfd_nr ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		lfd_nr = new int[rowCount];
		subject = new int[rowCount];
		kfs_id = new int[rowCount];


		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				subject[row] = rs.getInt(3);
				kfs_id[row] = rs.getInt(4);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

}
