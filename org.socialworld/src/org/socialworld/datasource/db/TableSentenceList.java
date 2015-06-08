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
public class TableSentenceList extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, partner_id, type, lfd_nr, sentence  ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	public final  String 	COLUMNS_ID_PARTNERID 		=	" id, partner_id  ";
	public final  int 		SELECT_ID_PARTNERID 	= 2;

	int id[];
	int partner_id[];
	int type[];
	int lfd_nr[];
	String sentence[];
	
	@Override
	protected String getTableName() {
		return "sw_sentencelist";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		case SELECT_ID_PARTNERID:
			return  COLUMNS_ID_PARTNERID;
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
		case SELECT_ID_PARTNERID:
			int row = 0;
			id = new int[rowCount];
			partner_id = new int[rowCount];

			try {
				while (rs.next()) {
					
					id[row] = rs.getInt(1);
					partner_id[row] = rs.getInt(2);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			setPK1(id);
			setPK2(partner_id);

			break;
		default:
			selectAllColumns(rs);
		}
	}

	public void insert(int id, int partner_id, int type, int lfd_nr, String sentence) {
		String statement;
			
		if ((id > 0) & (partner_id > 0 ) & (type > 0) & (lfd_nr > 0)) {
	
			if (sentence == null) sentence = "NULL";

			statement 	= "INSERT INTO sw_sentencelist (id, partner_id, type, lfd_nr, sentence) VALUES (" + 
					id + ", " + partner_id + ", " + type + ", " + lfd_nr + ", '" + sentence + "')";
			
			insert(statement);
		}
	}

	public void update(int id, int partner_id, int type, int lfd_nr, String sentence) {
		String statement;
			
		if ( (id > 0) & (partner_id > 0 ) & (type > 0) & (lfd_nr > 0)  ) {

			statement 	= "UPDATE sw_sentencelist SET " +
					"sentence = '" + sentence  + "' " +
				"WHERE id = " + id + 
				" AND partner_id = " + partner_id + " AND type = " + type + " AND lfd_nr = " + lfd_nr ;
		
			update(statement);
		}
	}

	public void delete(int id, int partner_id, int type, int lfd_nr) {
		String statement;
			
		if ( (id > 0) & (partner_id > 0 ) & (type > 0) & (lfd_nr > 0) ) {
	
			statement 	= "DELETE FROM sw_sentencelist WHERE id = " + id + 
					" AND partner_id = " + partner_id + " AND type = " + type + " AND lfd_nr = " + lfd_nr ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		partner_id = new int[rowCount];
		type = new int[rowCount];
		lfd_nr = new int[rowCount];
		sentence = new String[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				partner_id[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				lfd_nr[row] = rs.getInt(4);
				sentence[row] = rs.getString(5);
					
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int getType(int index) {
		return type[index];
	}
	
	public int getLfdNr(int index) {
		return lfd_nr[index];
	}

	public String getSentence(int index) {
		return sentence[index];
	}

}
