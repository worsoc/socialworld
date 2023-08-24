package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableRelation extends Table {

	public final  String 	ALL_COLUMNS 		=	" relation_id, lexem_id, tense_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int relation_id[];
	int lexem_id[];
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

	public void insert(int relation_id, int lexem_id,   int tense_id) {
		String statement;
			
		if (relation_id > 0) {
	

			statement 	= "INSERT INTO sw_relation (relation_id, lexem_id, tense_id) VALUES (" + 
					relation_id +  ", " + lexem_id + ", " + tense_id  + ")";
			
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
		tense_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				relation_id[row] = rs.getInt(1);
				lexem_id[row] = rs.getInt(1);
				tense_id[row] = rs.getInt(3);
				
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
	
	public int getTenseID(int index) {
		return tense_id[index];
	}	
	

}
