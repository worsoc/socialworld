package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableKnowledgeSource extends Table {
	public final  String 	ALL_COLUMNS 		=	" ks_id, sourceType, origin ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int ks_id[];
	int sourceType[];
	int origin[];
	
	@Override
	protected String getTableName() {
		return "sw_knowledgesource";
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
		setPK1(ks_id);

	}

	public void insert(int ks_id, int sourceType, int origin) {
		String statement;
			
		if (ks_id > 0)  {
			
			statement 	= "INSERT INTO sw_knowledgesource (ks_id, sourceType, origin) " +
					"VALUES (" + ks_id +  ", " + sourceType + ", " + origin + ")";
			
			insert(statement);
		}
	}

	public void insert(int ks_id) {
		String statement;
			
		if (ks_id > 0)  {
			
			statement 	= "INSERT INTO sw_knowledgesource (ks_id) VALUES (" + ks_id + ")";
			
			insert(statement);
		}
	}


	public void updateSource(int ks_id,  int sourceType, int origin) {
		String statement;
			
		if ( ks_id > 0 ) {
	

			statement 	= "UPDATE sw_knowledgesource SET " +
					"sourceType = " + sourceType  + ", " +
					"origin = " + origin  + " " +
				"WHERE ks_id = " + ks_id  ;
			
			update(statement);
		}
	}

	public void delete(int ks_id) {
		String statement;
			
		if ( ks_id > 0 ) {
	
			statement 	= "DELETE FROM sw_knowledgesource WHERE ks_id = " + ks_id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		ks_id = new int[rowCount];
		sourceType = new int[rowCount];
		origin = new int[rowCount];


		try {
			while (rs.next()) {
				
				ks_id[row] = rs.getInt(1);
				sourceType[row] = rs.getInt(2);
				origin[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public int getSourceType(int index) {
		return sourceType[index];
	}
	
	public int getOrigin(int index) {
		return origin[index];
	}

}
