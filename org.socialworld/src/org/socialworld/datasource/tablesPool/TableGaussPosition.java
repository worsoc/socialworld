package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableGaussPosition extends Table {

	public final  String 	ALL_COLUMNS 		=	" gauss_index, pos_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int gauss_index[]; 		
	int pos_id[];

	protected String getTableName() {
		return "swgauss_position";
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
		setPK1(gauss_index);
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		
		gauss_index = new int[rowCount];
		pos_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				gauss_index[row] = rs.getInt(1);
				pos_id[row] = rs.getInt(2);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}

	public void insert(int gauss_index, int pos_id) {
		String statement;
			
		if (pos_id > 0)  {
			
			statement 	= "INSERT INTO swgauss_position (gauss_index, pos_id) VALUES (" + 
					gauss_index + ", " + pos_id   + ")";
			
			insert(statement);
		}
	}

	public void update(int gauss_index, int pos_id) {
		String statement;
			
		if (pos_id > 0)  {
			
			statement 	= "UPDATE swgauss_position SET pos_id = " + pos_id + 
					" WHERE gauss_index = "  + gauss_index;
			
			update(statement);
		}
	}

	public void delete(int gauss_index) {
		String statement;
			
			
			statement 	= "DELETE FROM swgauss_position " + 
					" WHERE gauss_index = "  + gauss_index;
			
			delete(statement);
	}

	public int getGaussIndex(int index) {
		return this.gauss_index[index];
	}

	public int getPositionID(int index) {
		return pos_id[index];
	}

}
