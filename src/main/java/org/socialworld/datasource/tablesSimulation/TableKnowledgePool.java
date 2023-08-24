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
public class TableKnowledgePool extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, lfd_nr, subject, ks_id, kf_id  ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int lfd_nr[];
	int subject[];
	int ks_id[];
	int kf_id[];

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
		
		setPK1(id);
		setPK2(lfd_nr);
	}

	public void insert(int id, int lfd_nr, int subject, int ks_id, int kf_id) {
		String statement;
			
		if ((id > 0) & (lfd_nr > 0) ) {
			
			statement 	= "INSERT INTO sw_knowledgepool (id, lfd_nr, subject, ks_id, kf_id) VALUES (" + 
			id + ", " + lfd_nr + ", " + subject + ", " + ks_id + ", " + kf_id + ")";
			
			insert(statement);
		}
	}

	public void update(int id, int lfd_nr, int subject, int ks_id, int kf_id) {
		String statement;
			
		if ( (id > 0) & (lfd_nr > 0) ) {
	

			statement 	= "UPDATE sw_knowledgepool SET " +
					"subject = " + subject  + ", " +
					"ks_id = " + ks_id  + ", " +
					"kf_id = " + kf_id  + " " +
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
		ks_id = new int[rowCount];
		kf_id = new int[rowCount];


		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				subject[row] = rs.getInt(3);
				ks_id[row] = rs.getInt(4);
				kf_id[row] = rs.getInt(5);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}


	public int getSubject(int index) {
		return subject[index];
	}

	public int getKSID(int index) {
		return ks_id[index];
	}
	
	public int getKFID(int index) {
		return kf_id[index];
	}
	
}
