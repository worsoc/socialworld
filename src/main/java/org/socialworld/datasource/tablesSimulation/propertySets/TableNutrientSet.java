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
import org.socialworld.attributes.properties.Nutrient;
import org.socialworld.attributes.properties.NutrientSet;

public class TableNutrientSet extends TableSet {

	public final  String 	ALL_COLUMNS 		=	" nutrient_set_id, lfd_nr,  nutrient, share ";

	private final int NUTRIENT_SETS_ARRAY_MAX_COUNT = 1000;
	
	private static TableNutrientSet instance;
	
	private NutrientSet[] nutrientSets;
	
	int nutrient_set_id[];
	int lfd_nr[];
	int nutrient[];
	int share[];

	public static TableNutrientSet getInstance() {
		if (instance == null) {
				instance = new TableNutrientSet();
		}
		return instance;
	}

	private TableNutrientSet() {
		nutrientSets = new NutrientSet[NUTRIENT_SETS_ARRAY_MAX_COUNT];
		load();
	}
	
	@Override
	protected String getTableName() {
		return "swset_nutrient";
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
		share = new int[rowCount];

		try {
			while (rs.next()) {
				
				nutrient_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				nutrient[row] = rs.getInt(3);
				share[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int nutrient_set_id, int lfd_nr, int nutrient,  int share) {
		String statement;
			
		if (nutrient_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ nutrient_set_id  + ", " + lfd_nr  + ", " + nutrient + ", " + share + ")";
			
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
	

	public void updateShare( int nutrient_set_id, int lfd_nr, int share) {
		String statement;
			
		if (nutrient_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"share = " + share  + " " +
					"WHERE nutrient_set_id = " + nutrient_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getNutrient(int index) {
		return nutrient[index];
	}

	public int getShare(int index) {
		return share[index];
	}
	
/*
	public NutrientSet getNutrientSet(int nutrient_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE nutrient_set_id = " + nutrient_set_id, " ORDER BY lfd_nr"); 
		NutrientSet nutrientSet = new NutrientSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				nutrientSet.add(Nutrient.getName(nutrient[row]),  share[row]);
		}
		return nutrientSet;
	}
*/
	public NutrientSet getNutrientSet(int nutrient_set_id) {
		if (nutrient_set_id > 0 && nutrient_set_id <= nutrientSets.length) {
			return nutrientSets[nutrient_set_id];
		}
		return NutrientSet.getObjectNothing();
	}
	
	private void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableNutrientSet.load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		int id = 0;
		int lastID = 0;
		int nutrient;
		int share;
		NutrientSet nutrientSet = null;
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY nutrient_set_id, lfd_nr"); 
		
		if (lfd_nr.length > 0) {
			for (int row = 0; row < lfd_nr.length; row++) {
				id  = this.nutrient_set_id[row];
				if (id > lastID) {
					if (lastID > 0) {
						nutrientSets[lastID] = nutrientSet;
						lastID = id;
					}
					nutrientSet = new NutrientSet();
				}
				nutrient = this.nutrient[row];
				share = this.share[row];
				nutrientSet.add(Nutrient.getName(nutrient), share);
			}
			nutrientSets[id] = nutrientSet;
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TableNutrientet.load() Ende " + ActualTime.asTime().toString());
	}
	
}
