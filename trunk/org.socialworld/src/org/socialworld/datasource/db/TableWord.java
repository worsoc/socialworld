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
public class TableWord extends Table {

	public final  String 	ALL_COLUMNS 		=	" word_id, word, type, kfc, subjectable, pronoun ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int word_id[];
	String word[];
	int type[];
	int kfc[];
	int subjectable[];
	int pronoun[];
	
	@Override
	protected String getTableName() {
		return "sw_word";
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
		setPK1(word_id);
	}

	public void insert(int word_id, String word, int type, int kfc, int subjectable, int pronoun) {
		String statement;
			
		if (word_id > 0) {
	

			statement 	= "INSERT INTO sw_word (word_id, word, type, kfc, subjectable, pronoun) VALUES (" + 
					word_id + ", '" + word + "', " + type + ", " + kfc + ", " + subjectable + ", " + pronoun  +")";
			
			insert(statement);
		}
	}

	public void delete(int word_id) {
		String statement;
			
		if (word_id > 0) {
	
			statement 	= "DELETE FROM sw_word WHERE word_id = " + word_id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		word_id = new int[rowCount];
		word = new String[rowCount];
		type = new int[rowCount];
		kfc = new int[rowCount];
		subjectable = new int[rowCount];
		pronoun = new int[rowCount];

		try {
			while (rs.next()) {
				
				word_id[row] = rs.getInt(1);
				word[row] = rs.getString(2);
				type[row] = rs.getInt(3);
				kfc[row] = rs.getInt(4);
				subjectable[row] = rs.getInt(5);
				pronoun[row] = rs.getInt(6);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
	
	
	
	public int getWordID(int index) {
		return word_id[index];
	}

	public int getType(int index) {
		return type[index];
	}

	public int getKFC(int index) {
		return kfc[index];
	}
	
	public int getSubjectable(int index) {
		return subjectable[index];
	}

	public String getWord(int index) {
		return word[index];
	}

	public int getPronoun(int index) {
		return pronoun[index];
	}

}
