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
package org.socialworld.datasource.tablesSimulation.states;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.properties.NutrientSet;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.datasource.tablesSimulation.propertySets.TableNutrientSet;
import org.socialworld.datasource.tablesSimulation.propertySets.TableTasteSet;

public class TableStateEatable extends Table {

	private static List<TableStateEatable> instances = new ArrayList<TableStateEatable>();

	public static TableStateEatable getInstance() {
		for ( TableStateEatable instance : instances) {
			if (!instance.isLocked()) {
				return instance;
			}
		}
		TableStateEatable newInstance = new TableStateEatable();
		instances.add(newInstance);
		return newInstance;
	}
	
	
	public final  String 	ALL_COLUMNS 		=	" id, nutrient_set_id, taste_set_id ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int nutrient_set_id[];
	int taste_set_id[];

	@Override
	protected String getTableName() {
		return "swstate_eatable";
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
		nutrient_set_id = new int[rowCount];
		taste_set_id = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				nutrient_set_id[row] = rs.getInt(2);
				taste_set_id[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id, int nutrient_set_id, int taste_set_id) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" + id + ", " + nutrient_set_id + ", " + taste_set_id + ")";
			
			insert(statement);
		}
	}

	public void updateNutrientSetID(int id, int nutrient_set_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"nutrient_set_id = " + nutrient_set_id  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateTasteSetID(int id, int taste_set_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"taste_set_id = " + taste_set_id  + " " +
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

	public int getNutrientSetID(int index) {
		return nutrient_set_id[index];
	}

	public int getTasteSetID(int index) {
		return taste_set_id[index];
	}

	
	
	public int loadForObjectID(int objectID) {

		select(SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");

		int row = getIndexFor1PK(objectID);
		return row;
		
	}

	public NutrientSet getNutrientSetFromRow (int row) {
		
		int setID;
		NutrientSet set = null;
		if (row >= 0) {
			TableNutrientSet tableSet = new TableNutrientSet();
			setID = getNutrientSetID(row);
			set = tableSet.getNutrientSet(setID);
		}
		return set;
	}
	
	public TasteSet getTasteSetFromRow (int row) {
		
		int setID;
		TasteSet set = null;
		if (row >= 0) {
			TableTasteSet tableSet = new TableTasteSet();
			setID = getTasteSetID(row);
			set = tableSet.getTasteSet(setID);
		}
		return set;
	}

}
