/*
* Social World
* Copyright (C) 2022  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.datasource.tablesSimulation.propertySets;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;
import org.socialworld.datasource.mariaDB.Table;

public class TableMaterialSet extends Table {

	public final  String 	ALL_COLUMNS 		=	" material_set_id, lfd_nr, material, portion ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int material_set_id[];
	int lfd_nr[];
	int material[];
	int portion[];

	@Override
	protected String getTableName() {
		return "sw_materialset";
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
		material = new int[rowCount];
		portion = new int[rowCount];

		try {
			while (rs.next()) {
				
				material_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				material[row] = rs.getInt(3);
				portion[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int material_set_id, int lfd_nr, int material,  int portion) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ material_set_id  + ", " + lfd_nr  + ", " + material + ", " + portion + ")";
			
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
	

	public MaterialSet getMaterialSet(int material_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE material_set_id = " + material_set_id, " ORDER BY lfd_nr"); 
		MaterialSet MaterialSet = new MaterialSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				MaterialSet.add(Material.getName(material[row]), portion[row]);
		}
		return MaterialSet;
	}

}
