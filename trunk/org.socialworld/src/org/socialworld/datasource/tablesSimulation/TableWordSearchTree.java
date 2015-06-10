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

/**
 * @author Mathias Sikos
 *
 */
public class TableWordSearchTree extends Table {

	public final  String 	ALL_COLUMNS 		=	" wstn_id, level, children, letters, parent, word ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int wstn_id[];
	int level[];
	int children[];
	String letters[];
	int parent[];
	int word[];
	
	@Override
	protected String getTableName() {
		return "sw_wordsearchtree";
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
		setPK1(wstn_id);
	}

	public void insert(int wstn_id, int level, int children, String letters, int parent, int word) {
		String statement;
			
		if (wstn_id > 0) {
	

			statement 	= "INSERT INTO sw_wordsearchtree (wstn_id, level, children, letters, parent, word) VALUES (" + 
					wstn_id + ", " + level + ", " + children + ", '" + letters + "', " + parent + ", " + word + ")";
			
			insert(statement);
		}
	}

	public void update(int wstn_id,  String letters) {
		String statement;
			
		if (wstn_id > 0) {
	

			statement 	= "UPDATE sw_wordsearchtree SET " +
					"letters = '" + letters  + "' " +
					"WHERE wstn_id = " + wstn_id  ;
			
			update(statement);
		}
	}

	public void update(int wstn_id, int parent) {
		String statement;
			
		if (wstn_id > 0) {
	

			statement 	= "UPDATE sw_wordsearchtree SET " +
					"parent = " + parent  + " " +
					"WHERE wstn_id = " + wstn_id  ;
			
			update(statement);
		}
	}

	public void delete(int wstn_id) {
		String statement;
			
		if (wstn_id > 0) {
	
			statement 	= "DELETE FROM sw_wordsearchtree WHERE wstn_id = " + wstn_id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		wstn_id = new int[rowCount];
		level = new int[rowCount];
		children = new int[rowCount];
		letters = new String[rowCount];
		parent = new int[rowCount];
		word = new int[rowCount];

		try {
			while (rs.next()) {
				
				wstn_id[row] = rs.getInt(1);
				level[row] = rs.getInt(2);
				children[row] = rs.getInt(3);
				letters[row] = rs.getString(4);
				parent[row] = rs.getInt(5);
				word[row] = rs.getInt(6);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int getWSTNID(int index) {
		return wstn_id[index];
	}

	public int getLevel(int index) {
		return level[index];
	}

	public int getChildren(int index) {
		return children[index];
	}

	public int getParent(int index) {
		return parent[index];
	}

	public int getWord(int index) {
		return word[index];
	}
	
	public String getLetters(int index) {
		return letters[index];
	}

}
