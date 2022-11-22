package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableColourSet extends Table {

	public final  String 	ALL_COLUMNS 		=	" colour_set_id, lfd_nr, type, colour, portion ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int colour_set_id[];
	int lfd_nr[];
	int type[];
	int colour[];
	int portion[];

	@Override
	protected String getTableName() {
		return "sw_colourset";
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

		setPK1(colour_set_id);
		setPK2(lfd_nr);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		colour_set_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		type = new int[rowCount];
		colour = new int[rowCount];
		portion = new int[rowCount];

		try {
			while (rs.next()) {
				
				colour_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				colour[row] = rs.getInt(4);
				portion[row] = rs.getInt(5);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int colour_set_id, int lfd_nr, int type, int colour,  int portion) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ colour_set_id  + ", " + lfd_nr + ", " + type + ", " + colour + ", " + portion + ")";
			
			insert(statement);
		}
	}
	
	public void updateColour( int colour_set_id, int lfd_nr, int colour) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"colour = " + colour  + " " +
					"WHERE colour_set_id = " + colour_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}
	
	public void updateType( int colour_set_id, int lfd_nr, int type) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"type = " + type  + " " +
					"WHERE colour_set_id = " + colour_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public void updatePortion( int colour_set_id, int lfd_nr, int portion) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"portion = " + portion  + " " +
					"WHERE colour_set_id = " + colour_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

}
