package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableStateAppearance extends Table {

	public final  String 	ALL_COLUMNS 		=	" id, colour_set_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int colour_set_id[];

	@Override
	protected String getTableName() {
		return "sw_state_appearance";
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

		setPK1(id);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		id = new int[rowCount];
		colour_set_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				colour_set_id[row] = rs.getInt(2);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id, int colour_set_id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (id, colour_set_id) VALUES (" + id + ", " + colour_set_id + ")";
			
			insert(statement);
		}
	}

	public void updateColourSetID(int id, int colour_set_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"colour_set_id = " + colour_set_id  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM " + getTableName() + " WHERE id = " + id  ;
			
			delete(statement);
		}
	}

	public int getColourSetID(int index) {
		return colour_set_id[index];
	}

}
