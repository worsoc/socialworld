package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TablePoolDotElemLine extends Table {

	public final  String 	ALL_COLUMNS 		=	" dot_elem_line_id, dotelemline_function, dotelemline_result_value_name, dotelemline_result_type ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int dot_elem_line_id[];
	int dotelemline_function[]; 		
	String dotelemline_result_value_name[]; 
	String dotelemline_result_type[]; 

	@Override
	protected String getTableName() {
		return "swpool_dotelemline";
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
		setPK1(dot_elem_line_id);

	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;


		dot_elem_line_id = new int[rowCount];
		dotelemline_function = new int[rowCount];
		dotelemline_result_value_name = new String[rowCount];
		dotelemline_result_type = new String[rowCount];
	

		try {
			while (rs.next()) {
				
				dot_elem_line_id[row] = rs.getInt(1);
				dotelemline_function[row] = rs.getInt(2);
				dotelemline_result_value_name[row] = rs.getString(3);
				dotelemline_result_type[row] = rs.getString(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int  dot_elem_line_id, int dotelemline_function, String dotelemline_result_value_name, String dotelemline_result_type ) {
		String statement;
			
		if ((dot_elem_line_id > 0) & (dotelemline_function > 0) ) {
			
			statement 	= "INSERT INTO swpool_dotelemline (" + ALL_COLUMNS + ") VALUES (" + 
					dot_elem_line_id + ", " + dotelemline_function + ", '" + dotelemline_result_value_name + "', '" + dotelemline_result_type  + "')";
			
			insert(statement);
		}
	}

	public void delete(int dot_elem_line_id) {
		String statement;
			
		if (dot_elem_line_id > 0) {
	
			statement 	= "DELETE FROM swpool_dotelemline WHERE dot_elem_line_id = " + dot_elem_line_id  ;
			
			delete(statement);
		}
	}

	public int getDotElemLineID(int index) {
		return dot_elem_line_id[index];
	}

	public int getFunction(int index) {
		return dotelemline_function[index];
	}

	public String getResultValueName(int index) {
		return dotelemline_result_value_name[index];
	}

	public String getResultType(int index) {
		return dotelemline_result_type[index];
	}

}
