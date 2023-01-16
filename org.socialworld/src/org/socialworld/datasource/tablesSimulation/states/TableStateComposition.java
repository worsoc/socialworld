package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.datasource.tablesSimulation.propertySets.TableMaterialSet;

public class TableStateComposition extends Table {

	private static List<TableStateComposition> instances = new ArrayList<TableStateComposition>();

	public static TableStateComposition getInstance() {
		for ( TableStateComposition instance : instances) {
			if (!instance.isLocked()) {
				return instance;
			}
		}
		TableStateComposition newInstance = new TableStateComposition();
		instances.add(newInstance);
		return newInstance;
	}
	
	
	public final  String 	ALL_COLUMNS 		=	" id, material_set_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int material_set_id[];

	@Override
	protected String getTableName() {
		return "swstate_composition";
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
		material_set_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				material_set_id[row] = rs.getInt(2);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id, int material_set_id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (id, material_set_id) VALUES (" + id + ", " + material_set_id + ")";
			
			insert(statement);
		}
	}

	public void updateMaterialSetID(int id, int material_set_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"material_set_id = " + material_set_id  + " " +
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

	public int getMaterialSetID(int index) {
		return material_set_id[index];
	}

	public int loadForObjectID(int objectID) {

		select(SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");

		int row = getIndexFor1PK(objectID);
		return row;
		
	}

	public MaterialSet getMaterialSetFromRow (int row) {
		
		int setID;
		MaterialSet set = MaterialSet.getObjectNothing();
		if (row >= 0) {
			TableMaterialSet tableSet = new TableMaterialSet();
			setID = getMaterialSetID(row);
			set = tableSet.getMaterialSet(setID);
		}
		return set;
	}
	

	
}
