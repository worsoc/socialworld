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

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.properties.Material;
import org.socialworld.attributes.properties.MaterialSet;

public class TableMaterialSet extends TableSet {

	public final  String 	ALL_COLUMNS 		=	" material_set_id, lfd_nr, material, share ";

	private final int MATERIAL_SETS_ARRAY_MAX_COUNT = 1000;
	
	private static TableMaterialSet instance;
	
	private MaterialSet[] materialSets;
	
	int material_set_id[];
	int lfd_nr[];
	int material[];
	int share[];

	public static TableMaterialSet getInstance() {
		if (instance == null) {
				instance = new TableMaterialSet();
		}
		return instance;
	}

	private TableMaterialSet() {
		materialSets = new MaterialSet[MATERIAL_SETS_ARRAY_MAX_COUNT];
		load();
	}
	
	@Override
	protected String getTableName() {
		return "swset_material";
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
		share = new int[rowCount];

		try {
			while (rs.next()) {
				
				material_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				material[row] = rs.getInt(3);
				share[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int material_set_id, int lfd_nr, int material,  int share) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ material_set_id  + ", " + lfd_nr  + ", " + material + ", " + share + ")";
			
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
	

	public void updateShare( int material_set_id, int lfd_nr, int share) {
		String statement;
			
		if (material_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"share = " + share  + " " +
					"WHERE material_set_id = " + material_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getMaterial(int index) {
		return material[index];
	}

	public int getShare(int index) {
		return share[index];
	}
	
/*
	public MaterialSet getMaterialSet(int material_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE material_set_id = " + material_set_id, " ORDER BY lfd_nr"); 
		MaterialSet MaterialSet = new MaterialSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				MaterialSet.add(Material.getName(material[row]), share[row]);
		}
		return MaterialSet;
	}
*/
	public MaterialSet getMaterialSet(int material_set_id) {
		if (material_set_id > 0 && material_set_id <= materialSets.length) {
			return materialSets[material_set_id];
		}
		return MaterialSet.getObjectNothing();
	}

	private void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableMaterialSet.load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		int id = 0;
		int lastID = 0;
		int colour;
		int share;
		MaterialSet materialSet = null;
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY material_set_id, lfd_nr"); 
		
		if (lfd_nr.length > 0) {
			for (int row = 0; row < lfd_nr.length; row++) {
				id  = this.material_set_id[row];
				if (id > lastID) {
					if (lastID > 0) {
						materialSets[lastID] = materialSet;
						lastID = id;
					}
					materialSet = new MaterialSet();
				}
				colour = this.material[row];
				share = this.share[row];
				materialSet.add(Material.getName(colour), share);
			}
			materialSets[id] = materialSet;
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableMaterialSet.load() Ende " + ActualTime.asTime().toString());
	}
	
}
