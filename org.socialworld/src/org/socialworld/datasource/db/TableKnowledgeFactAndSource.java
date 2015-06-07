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
public class TableKnowledgeFactAndSource extends Table {

	public final  String 	ALL_COLUMNS 		=	" kfs_id, lfd_nr, kfc, value, sourceType, origin ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int kfs_id[];
	int lfd_nr[];
	int kfc[];
	int value[];
	int sourceType[];
	int origin[];
	
	@Override
	protected String getTableName() {
		return "sw_knowledgefactandsource";
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
		setPK1(kfs_id);
		setPK2(lfd_nr);

	}

	public void insert(int kfs_id, int lfd_nr, int kfc, int value, int sourceType, int origin) {
		String statement;
			
		if ((kfs_id > 0) & (lfd_nr > 0) ) {
			
			statement 	= "INSERT INTO sw_knowledgefactandsource (kfd_id, lfd_nr, kfc, value, sourceType, origin) " +
					"VALUES (" + kfs_id + ", " + lfd_nr + ", " + kfc + ", " + value + ", " + sourceType + ", " + origin + ")";
			
			insert(statement);
		}
	}

	public void insert(int kfs_id, int lfd_nr) {
		String statement;
			
		if ((kfs_id > 0) & (lfd_nr > 0) ) {
			
			statement 	= "INSERT INTO sw_knowledgefactandsource (kfd_id, lfd_nr) VALUES (" + kfs_id + ", " + lfd_nr + ")";
			
			insert(statement);
		}
	}

	public void updateValue(int kfs_id, int lfd_nr, int kfc, int value) {
		String statement;
			
		if ( (kfs_id > 0) & (lfd_nr > 0) ) {
	

			statement 	= "UPDATE sw_knowledgefactandsource SET " +
					"kfc = " + kfc  + ", " +
					"value = " + value  + " " +
				"WHERE kfs_id = " + kfs_id + " AND lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public void updateSource(int kfs_id, int lfd_nr, int sourceType, int origin) {
		String statement;
			
		if ( (kfs_id > 0) & (lfd_nr > 0) ) {
	

			statement 	= "UPDATE sw_knowledgefactandsource SET " +
					"sourceType = " + sourceType  + ", " +
					"origin = " + origin  + " " +
				"WHERE kfs_id = " + kfs_id + " AND lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public void delete(int kfs_id, int lfd_nr) {
		String statement;
			
		if ( (kfs_id > 0) & (lfd_nr > 0) ) {
	
			statement 	= "DELETE FROM sw_knowledgefactandsource WHERE kfs_id = " + kfs_id + " AND lfd_nr = " + lfd_nr ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		kfs_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		kfc = new int[rowCount];
		value = new int[rowCount];
		sourceType = new int[rowCount];
		origin = new int[rowCount];


		try {
			while (rs.next()) {
				
				kfs_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				kfc[row] = rs.getInt(3);
				value[row] = rs.getInt(4);
				sourceType[row] = rs.getInt(5);
				origin[row] = rs.getInt(6);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int getSourceType(int index) {
		return sourceType[index];
	}
	
	public int getOrigin(int index) {
		return origin[index];
	}

	public int getKFC(int index) {
		return kfc[index];
	}
	
	public int getValue(int index) {
		return value[index];
	}

}
