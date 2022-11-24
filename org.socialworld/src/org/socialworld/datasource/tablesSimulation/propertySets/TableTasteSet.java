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

import org.socialworld.attributes.properties.Taste;
import org.socialworld.attributes.properties.TasteSet;
import org.socialworld.datasource.mariaDB.Table;

public class TableTasteSet extends Table {

	public final  String 	ALL_COLUMNS 		=	" taste_set_id, lfd_nr,  taste, portion ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int taste_set_id[];
	int lfd_nr[];
	int taste[];
	int portion[];

	@Override
	protected String getTableName() {
		return "sw_tasteset";
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

		setPK1(taste_set_id);
		setPK2(lfd_nr);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		taste_set_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		taste = new int[rowCount];
		portion = new int[rowCount];

		try {
			while (rs.next()) {
				
				taste_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				taste[row] = rs.getInt(3);
				portion[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int taste_set_id, int lfd_nr, int taste,  int portion) {
		String statement;
			
		if (taste_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ taste_set_id  + ", " + lfd_nr  + ", " + taste + ", " + portion + ")";
			
			insert(statement);
		}
	}
	
	public void updateTaste( int taste_set_id, int lfd_nr, int taste) {
		String statement;
			
		if (taste_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"taste = " + taste  + " " +
					"WHERE taste_set_id = " + taste_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}
	

	public void updatePortion( int taste_set_id, int lfd_nr, int portion) {
		String statement;
			
		if (taste_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"portion = " + portion  + " " +
					"WHERE taste_set_id = " + taste_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getTaste(int index) {
		return taste[index];
	}

	public int getPortion(int index) {
		return portion[index];
	}
	

	public TasteSet getTasteSet(int taste_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE taste_set_id = " + taste_set_id, " ORDER BY lfd_nr"); 
		TasteSet tasteSet = new TasteSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				tasteSet.add(Taste.getName(taste[row]),  portion[row]);
		}
		return tasteSet;
	}

}
