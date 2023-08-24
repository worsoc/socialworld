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
public class TablePoolEID extends Table {

	public final  String 	ALL_COLUMNS 		=	" eventType, influenceType, exp_lfd_nr, exp_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int eventType[];
	int influenceType[];
	int exp_lfd_nr[]; 		
	int exp_id[]; 		

	@Override
	protected String getTableName() {
		return "swpool_eid";
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

		setPK1(eventType);
		setPK2(influenceType);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		eventType = new int[rowCount];
		influenceType = new int[rowCount];
		exp_lfd_nr = new int[rowCount];
		exp_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				eventType[row] = rs.getInt(1);
				influenceType[row] = rs.getInt(2);
				exp_lfd_nr[row] = rs.getInt(3);
				exp_id[row] = rs.getInt(4);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int eventType, int influenceType, int exp_lfd_nr, int exp_id) {
		String statement;
			
		if ((eventType > 0) & (influenceType > 0) & (exp_lfd_nr > 0)) {
			
			statement 	= "INSERT INTO swpool_eid (eventType, influenceType , exp_lfd_nr, exp_id) VALUES (" + 
					eventType + ", " + influenceType + ", " + exp_lfd_nr + ", " + exp_id +")";
			
			insert(statement);
		}
	}

	public void update(int eventType, int influenceType, int exp_lfd_nr, int exp_id) {
		String statement;
			
		if ((eventType > 0) & (influenceType > 0) & (exp_lfd_nr > 0)) {
	
			statement 	= "UPDATE swpool_eid SET " +
					"exp_id = " + exp_id  + 
					" WHERE eventType = " + eventType  + " AND influenceType = " + influenceType + " AND exp_lfd_nr = " + exp_lfd_nr;
			
			update(statement);
		}
	}

	public void delete(int eventType, int influenceType, int exp_lfd_nr) {
		String statement;
			
		if ((eventType > 0) & (influenceType > 0) & (exp_lfd_nr > 0)) {
	
			statement 	= "DELETE FROM swpool_eid " +
					" WHERE eventType = " + eventType  + " AND influenceType = " + influenceType + " AND exp_lfd_nr = " + exp_lfd_nr;
						
			delete(statement);
		}
	}

	public int getEventType(int index) {
		return this.eventType[index];
	}

	public int getInfluenceType(int index) {
		return this.influenceType[index];
	}

	public int getExpLfdNr(int index) {
		return this.exp_lfd_nr[index];
	}
	
	public int getExpression(int index) {
		return this.exp_id[index];
	}

}
