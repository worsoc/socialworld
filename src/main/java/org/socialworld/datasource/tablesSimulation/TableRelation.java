/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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

import org.socialworld.conversation.Relation;
import org.socialworld.datasource.mariaDB.Table;

public class TableRelation extends Table {

	public final  String 	ALL_COLUMNS 		=	" relation_id, lexem_id, cardinality_id, tense_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int relation_id[];
	int lexem_id[];
	int cardinality_id[];
	int tense_id[];
	
	@Override
	protected String getTableName() {
		return "sw_relation";
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
		setPK1(relation_id);

	}

	public void insert(int relation_id, int lexem_id, int cardinality_id,  int tense_id) {
		String statement;
			
		if (relation_id > 0) {
	

			statement 	= "INSERT INTO sw_relation (relation_id, lexem_id, cardinality_id, tense_id) VALUES (" + 
					relation_id +  ", " + lexem_id + ", " + cardinality_id + ", " + tense_id  + ")";
			
			insert(statement);
		}
	}

	public void delete(int relation_id) {
		String statement;
			
		if (relation_id > 0) {
	
			statement 	= "DELETE FROM sw_relation WHERE relation_id = " + relation_id  ;
			
			delete(statement);
		}
	}	
	
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		relation_id = new int[rowCount];
		lexem_id = new int[rowCount];
		cardinality_id = new int[rowCount];
		tense_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				relation_id[row] = rs.getInt(1);
				lexem_id[row] = rs.getInt(2);
				cardinality_id[row] = rs.getInt(3);
				tense_id[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}	
	
	public int getRelationID(int index) {
		return relation_id[index];
	}

	public int getLexemID(int index) {
		return lexem_id[index];
	}

	public int getCardinalityID(int index) {
		return cardinality_id[index];
	}	

	public int getTenseID(int index) {
		return tense_id[index];
	}	
	
	public void fillTableForRelations() {
		
		String statement;
		
		int relationID;
		int lexemID;
		int cardinalityID;
		int tenseID;
		
		statement 	= "DELETE FROM sw_relation";
		delete(statement);
		for(Relation relation : Relation.values()) {
			relationID = relation.getIndex();
			lexemID = relation.getLexemID();
			cardinalityID = relation.getCardinality().getIndex();
			tenseID = relation.getTense().getIndex();
			insert(relationID,  lexemID , cardinalityID, tenseID);
		}
		
	}
	
}
