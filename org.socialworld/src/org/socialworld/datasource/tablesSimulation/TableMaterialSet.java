package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.datasource.mariaDB.Table;

public class TableMaterialSet extends Table {

	public final  String 	ALL_COLUMNS 		=	" material_set_id, lfd_nr, type, material, portion ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int material_set_id[];
	int lfd_nr[];
	int type[];
	int material[];
	int portion[];

	@Override
	protected String getTableName() {
		return "sw_MaterialSet";
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

		setPK1(material_set_id);
		setPK2(lfd_nr);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		material_set_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		type = new int[rowCount];
		material = new int[rowCount];
		portion = new int[rowCount];

		try {
			while (rs.next()) {
				
				material_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				material[row] = rs.getInt(4);
				portion[row] = rs.getInt(5);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int material_set_id, int lfd_nr, int type, int material,  int portion) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ material_set_id  + ", " + lfd_nr + ", " + type + ", " + material + ", " + portion + ")";
			
			insert(statement);
		}
	}
	
	public void updateMaterial( int material_set_id, int lfd_nr, int material) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"material = " + material  + " " +
					"WHERE material_set_id = " + material_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}
	
	public void updateType( int material_set_id, int lfd_nr, int type) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"type = " + type  + " " +
					"WHERE material_set_id = " + material_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public void updatePortion( int material_set_id, int lfd_nr, int portion) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"portion = " + portion  + " " +
					"WHERE material_set_id = " + material_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getMaterial(int index) {
		return material[index];
	}

	public int getPortion(int index) {
		return portion[index];
	}
	
	public int getType(int index) {
		return type[index];
	}

	public MaterialSet getMaterialSet(int material_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE material_set_id = " + material_set_id, " ORDER BY lfd_nr"); 
		MaterialSet MaterialSet = new MaterialSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				MaterialSet.add(Material.getName(material[row]), type[row], portion[row]);
		}
		return MaterialSet;
	}

}
