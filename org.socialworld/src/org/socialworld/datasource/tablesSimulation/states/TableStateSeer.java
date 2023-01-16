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

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.percipience.PropsSeer;
import org.socialworld.datasource.mariaDB.Table;
import org.socialworld.datasource.tablesSimulation.properties.TableDirection;
import org.socialworld.datasource.tablesSimulation.properties.TablePropsSeer;

public class TableStateSeer extends Table {

	private static List<TableStateSeer> instances = new ArrayList<TableStateSeer>();

	public static TableStateSeer getInstance() {
		for ( TableStateSeer instance : instances) {
			if (!instance.isLocked()) {
				return instance;
			}
		}
		TableStateSeer newInstance = new TableStateSeer();
		instances.add(newInstance);
		return newInstance;
	}

	//bestPercPerp ... bestPercipiencePerpendicular
	public final  String 	ALL_COLUMNS 		=	" id, direction_id, props_seer_id, bestPercPerp ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int id[];
	int direction_id[];
	int props_seer_id[];
	int bestPercPerp[];

	@Override
	protected String getTableName() {
		return "swstate_seer";
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
		direction_id = new int[rowCount];
		props_seer_id = new int[rowCount];
		bestPercPerp = new int[rowCount];

		try {
			while (rs.next()) {
				
				id[row] = rs.getInt(1);
				direction_id[row] = rs.getInt(2);
				props_seer_id[row] = rs.getInt(3);
				bestPercPerp[row] = rs.getInt(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int id, int direction_id, int props_seer_id, int bestPercPerp) {
		String statement;
			
		if (id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" + id + ", " + direction_id + ", " + props_seer_id + ", " + bestPercPerp + ")";
			
			insert(statement);
		}
	}

	public void updateDirectionID(int id, int direction_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"direction_id = " + direction_id  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updatePropsSeerID(int id, int props_seer_id) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"props_seer_id = " + props_seer_id  + " " +
					"WHERE id = " + id  ;
			
			update(statement);
		}
	}

	public void updateBestPercipiencePerpendicular(int id, int bestPercPerp) {
		String statement;
			
		if (id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"bestPercPerp = " + bestPercPerp  + " " +
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

	public int getDirectionID(int index) {
		return direction_id[index];
	}

	public int getPropsSeerID(int index) {
		return props_seer_id[index];
	}

	public int getBestPercipiencePerpendicular(int index) {
		return bestPercPerp[index];
	}
	
	public int loadForObjectID(int objectID) {

		select(SELECT_ALL_COLUMNS, " WHERE id = " + objectID , "");

		int row = getIndexFor1PK(objectID);
		return row;
		
	}

	public Direction getDirectionViewFromRow (int row) {
		
		int id;
		Direction direction = Direction.getObjectNothing();
		if (row >= 0) {
			TableDirection tableDirection = new TableDirection();
			id = getDirectionID(row);
			direction = tableDirection.getDirection(id, PropertyName.stateSeer_directionView);
		}
		return direction;
	}

	public PropsSeer getPropsSeerFromRow (int row) {
		
		int id;
		PropsSeer propsSeer = null;
		if (row >= 0) {
			TablePropsSeer tablePropsSeer = new TablePropsSeer();
			id = getPropsSeerID(row);
			propsSeer = tablePropsSeer.getPropsSeer(id,  PropertyName.stateSeer_propsSeer);
		}
		return propsSeer;
	}

}
