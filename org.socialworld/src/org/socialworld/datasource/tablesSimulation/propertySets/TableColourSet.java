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

import org.socialworld.attributes.properties.Colour;
import org.socialworld.attributes.properties.ColourSet;

public class TableColourSet extends TableSet {

	public final  String 	ALL_COLUMNS 		=	" colour_set_id, lfd_nr, colour, share ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int colour_set_id[];
	int lfd_nr[];
	int colour[];
	int share[];

	@Override
	protected String getTableName() {
		return "sw_colourset";
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

		setPK1(colour_set_id);
		setPK2(lfd_nr);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		colour_set_id = new int[rowCount];
		lfd_nr = new int[rowCount];
		colour = new int[rowCount];
		share = new int[rowCount];

		try {
			while (rs.next()) {
				
				colour_set_id[row] = rs.getInt(1);
				lfd_nr[row] = rs.getInt(2);
				colour[row] = rs.getInt(3);
				share[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int colour_set_id, int lfd_nr, int colour,  int share) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ colour_set_id  + ", " + lfd_nr + ", " + colour + ", " + share + ")";
			
			insert(statement);
		}
	}
	
	public void updateColour( int colour_set_id, int lfd_nr, int colour) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"colour = " + colour  + " " +
					"WHERE colour_set_id = " + colour_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}
	

	public void updateShare( int colour_set_id, int lfd_nr, int share) {
		String statement;
			
		if (colour_set_id > 0 && lfd_nr > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"share = " + share  + " " +
					"WHERE colour_set_id = " + colour_set_id  + " and lfd_nr = " + lfd_nr ;
			
			update(statement);
		}
	}

	public int getColour(int index) {
		return colour[index];
	}

	public int getShare(int index) {
		return share[index];
	}
	

	public ColourSet getColourSet(int colour_set_id) {
		select(SELECT_ALL_COLUMNS, " WHERE colour_set_id = " + colour_set_id, " ORDER BY lfd_nr"); 
		ColourSet colourSet = new ColourSet();
		for (int row = 0; row < lfd_nr.length; row++) {
				colourSet.add(Colour.getName(colour[row]), share[row]);
		}
		return colourSet;
	}

}
