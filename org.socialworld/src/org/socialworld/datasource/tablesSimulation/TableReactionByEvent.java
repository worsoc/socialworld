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
public class TableReactionByEvent extends Table {

	public final  String 	ALL_COLUMNS 		=	"  id, eventType, reactionType ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int id[];
	int reactionTypes[][];
			
	@Override
	protected String getTableName() {
		return "sw_reactionbyevent";
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
		int column;
		
		int idCount;
		
		rs = connection.executeQuery(statement);

		idCount = rowCount / 256;
		reactionTypes = new int[idCount][256];
		id = new int[idCount];
		
		column = 0;
		row = 0;
		
		try {
			while (rs.next()) {
				if (column == 0 ) id[row] = rs.getInt(1);
				reactionTypes[row][column] = rs.getInt(3);
			
				column++;
				if (column == 256)		{
					row++;
					column = 0;
				}
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
			
		setPK1(id);
	}

	public void insert(int id, int reactionTypes[]) {
		String statement;
		int i;
		int count;
		
		
		if (id > 0) {
	
			count = reactionTypes.length;
			
			for (i = 1; i <= count; i++) {
				statement = "INSERT INTO sw_reactionbyevent (id, eventType, reactionType ) VALUES (" + id + ", " + i + ", " + reactionTypes[i] + ")";
				insert(statement);
			}
				
		}
	}
	
	public void update(int id, int eventType, int reactionType) {
		String statement;
		
		if ( (id > 0) & (eventType > 0) ) {
			
			statement 	= "UPDATE sw_reactionbyevent SET " +
					"reactionType  = " + reactionType +
					" WHERE id = " + id + " AND eventType = " + eventType ;
			
			update(statement);
		}
	}	
	
	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_reactionbyevent WHERE id = " + id  ;
			
			delete(statement);
		}
	}
	
	

	public int[] getTypes(int index) {
		return reactionTypes[index];
	}

}
