package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TablePoolParseInput extends Table {

	public final  String 	ALL_COLUMNS 		=	" parse_input_id, parse_input_type, parse_input_ref_id, parse_input_string ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int parse_input_id[];
	int parse_input_type[];
	int parse_input_ref_id[];
	String parse_input_string[]; 		

	@Override
	protected String getTableName() {
		return "swpool_parseinputs";
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
		setPK1(parse_input_id);

	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		

		parse_input_id = new int[rowCount];
		parse_input_type = new int[rowCount];
		parse_input_ref_id = new int[rowCount];
		parse_input_string = new String[rowCount];
	

		try {
			while (rs.next()) {
				
				parse_input_id[row] = rs.getInt(1);
				parse_input_type[row] = rs.getInt(2);
				parse_input_ref_id[row] = rs.getInt(3);
				parse_input_string[row] = rs.getString(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int  parse_input_id, int  parse_input_type, int  parse_input_ref_id, String parse_input_string ) {
		String statement;
			
		if ((parse_input_id > 0) & (parse_input_type > 0)  & (parse_input_string.length() > 0)) {
			
			statement 	= "INSERT INTO swpool_parseinputs (" + ALL_COLUMNS + ") VALUES (" + 
					parse_input_id + ", " + parse_input_type + ", " + parse_input_ref_id + ", '" + parse_input_string + "')";
			
			insert(statement);
		}
	}

	public void delete(int parse_input_id) {
		String statement;
			
		if (parse_input_id > 0) {
	
			statement 	= "DELETE FROM swpool_parseinputs WHERE parse_input_id = " + parse_input_id  ;
			
			delete(statement);
		}
	}

	public int getParseInputID(int index) {
		return parse_input_id[index];
	}

	public int getType(int index) {
		return parse_input_type[index];
	}

	public int getParseInputRefID(int index) {
		return parse_input_ref_id[index];
	}

	public String getInputString(int index) {
		return parse_input_string[index];
	}

}
