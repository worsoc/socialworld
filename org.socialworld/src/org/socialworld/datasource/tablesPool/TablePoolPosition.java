package org.socialworld.datasource.tablesPool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TablePoolPosition extends Table {

	public final  String 	ALL_COLUMNS 		=	" pos_id, x, y,z ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;


	int pos_id[];
	int x[]; 		
	int y[];
	int z[];

	@Override
	protected String getTableName() {
		return "swpool_position";
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
		int row = 0;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			pos_id = new int[rowCount];
			x = new int[rowCount];
			y = new int[rowCount];
			z = new int[rowCount];

			try {
				while (rs.next()) {
					
					pos_id[row] = rs.getInt(1);
					x[row] = rs.getInt(2);
					y[row] = rs.getInt(3);
					z[row] = rs.getInt(4);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			setPK1(pos_id);

			break;
		default:
			pos_id = new int[rowCount];
			x = new int[rowCount];
			y = new int[rowCount];
			z = new int[rowCount];

			try {
				while (rs.next()) {
					
					pos_id[row] = rs.getInt(1);
					x[row] = rs.getInt(2);
					y[row] = rs.getInt(3);
					z[row] = rs.getInt(3);
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			setPK1(pos_id);
			
		}

	}

	public void insert(int pos_id, int x, int y, int z) {
		String statement;
			
		if ( pos_id > 0)  {
			
			statement 	= "INSERT INTO swpool_position (pos_id, x, y, z) VALUES (" + 
							pos_id + ", " + x + ", " + y + ", " + z +")";
			
			insert(statement);
		}
	}
	
	public void update(int pos_id, int x, int y, int z) {
		String statement;
			
		if ( pos_id > 0)  {
	

			statement 	= "UPDATE swpool_position SET " +
					"x = " + x  + 
					"y = " + y  + 
					"z = " + z  + 
					" WHERE pos_id = " + pos_id;
			
			update(statement);
		}
	}

	public void delete(int pos_id) {
		String statement;
			
		if ( pos_id > 0 ) {
	
			statement 	= "DELETE FROM swpool_position " +
					" WHERE pos_id = " + pos_id ;
			
			delete(statement);
		}
	}

	public int getPositionID(int index) {
		return this.pos_id[index];
	}

	public int getX(int index) {
		return x[index];
	}

	public int getY(int index) {
		return y[index];
	}

	public int getZ(int index) {
		return z[index];
	}
	
}
