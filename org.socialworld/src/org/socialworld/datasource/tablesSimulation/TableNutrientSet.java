package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.datasource.mariaDB.Table;

public class TableNutrientSet extends Table {

	public final  String 	ALL_COLUMNS 		=	" nutrient_set_id, lfd_nr,  nutrient, portion ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int nutrient_set_id[];
	int lfd_nr[];
	int nutrient[];
	int portion[];

	@Override
	protected String getTableName() {
		return "sw_nutrientset";
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

		setPK1(nutrient_set_id);
		setPK2(lfd_nr);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		nutrient_set_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		nutrient = new int[rowCount];
		portion = new int[rowCount];

		try {
			while (rs.next()) {
				
				nutrient_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				nutrient[row] = rs.getInt(3);
				portion[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int nutrient_set_id, int lfd_nr, int nutrient,  int portion) {
		String statement;
			
		if (nutrient_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ nutrient_set_id  + ", " + lfd_nr  + ", " + nutrient + ", " + portion + ")";
			
			insert(statement);
		}
	}
	
	public void updateNutrient( int nutrient_set_id, int lfd_nr, int nutrient) {
		String statement;
			
		if (nutrient_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"nutrient = " + nutrient  + " " +
					"WHERE nutrient_set_id = " + nutrient_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}
	

	public void updatePortion( int nutrient_set_id, int lfd_nr, int portion) {
		String statement;
			
		if (nutrient_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"portion = " + portion  + " " +
					"WHERE nutrient_set_id = " + nutrient_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getNutrient(int index) {
		return nutrient[index];
	}

	public int getPortion(int index) {
		return portion[index];
	}
	

	public NutrientSet getNutrientSet(int nutrient_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE nutrient_set_id = " + nutrient_set_id, " ORDER BY lfd_nr"); 
		NutrientSet nutrientSet = new NutrientSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				nutrientSet.add(Nutrient.getName(nutrient[row]),  portion[row]);
		}
		return nutrientSet;
	}

}
