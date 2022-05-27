package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TablePoolDotElem extends Table {

	public final  String 	ALL_COLUMNS 		=	" dot_elem_line_id, lfd_nr, dot_element_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int dot_elem_line_id[];
	int lfd_nr[]; 		
	int dot_element_id[]; 		

	@Override
	protected String getTableName() {
		return "swpool_dotelem";
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
		lfd_nr = new int[rowCount];
		dot_element_id = new int[rowCount];
	

		try {
			while (rs.next()) {
				
				dot_elem_line_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				dot_element_id[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void insert(int  dot_elem_line_id, int lfd_nr, int dot_element_id) {
		String statement;
			
		if ((dot_elem_line_id > 0) & (lfd_nr > 0) & (dot_element_id > 0) ) {
			
			statement 	= "INSERT INTO swpool_dotelem (" + ALL_COLUMNS + ") VALUES (" + 
					dot_elem_line_id + ", " + lfd_nr + ", " + dot_element_id + ")";
			
			insert(statement);
		}
	}

	public void delete(int dot_elem_line_id, int lfd_nr) {
		String statement;
			
		if ((dot_elem_line_id > 0) & (lfd_nr > 0)) {
	
			statement 	= "DELETE FROM swpool_dotelem WHERE dot_elem_line_id = " + dot_elem_line_id + " and lfd_nr = " + lfd_nr ;
			
			delete(statement);
		}
	}
	
	public int getDotElemLineID(int index) {
		return dot_elem_line_id[index];
	}

	public int getLfdNr(int index) {
		return lfd_nr[index];
	}

	public int getDotElementID(int index) {
		return dot_element_id[index];
	}


}
