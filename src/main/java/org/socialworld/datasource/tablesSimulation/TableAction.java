/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

/**
 * @author Mathias Sikos
 *
 */
public class TableAction extends Table {

	public final  String 	ALL_COLUMNS 		=
			" action_id, actor, type, mode, minTime, maxTime, priority, intensity, duration, remainedDuration, target, x, y, z, done ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;

	int action_id[];
	int actor[];
	int type[];
	int mode[];
	long minTime[];
	long maxTime[];
	int priority[];
	float intensity[];
	int duration[];
	int remainedDuration[];
	int target[];
	float x[];
	float y[];
	float z[];
	int done[];

	@Override
	protected String getTableName() {
		return "sw_action";
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
		
		setPK1(action_id);
		
	}

	public void insert(int action_id, int actor, int type, int mode, long minTime, long maxTime, int priority, int done) {
		String statement;
			
		if (action_id > 0) {
	

			statement 	= "INSERT INTO sw_action (action_id, actor, type, mode, minTime, maxTime, priority, done) VALUES (" + 
					action_id + ", " + actor + ", " + type + ", " + mode + ", " + minTime + ", " + maxTime + ", " + priority + ", " + done +")";
			
			insert(statement);
		}
	}

	public void update(int action_id, float x, float y, float z) {
		String statement;
			
		if (action_id > 0) {
	

			statement 	= "UPDATE sw_action SET " +
					"x = " + x  + ", " +
					"y = " + y  + ", " +
					"z = " + z  + " " +
					"WHERE action_id = " + action_id  ;
			
			update(statement);
		}
	}

	public void update(int action_id, int duration, int remaindedDuration) {
		String statement;
			
		if (action_id > 0) {
	

			statement 	= "UPDATE sw_action SET " +
					"duration = " + duration  + ", " +
					"remaindedDuration = " + remaindedDuration  + " " +
					"WHERE action_id = " + action_id  ;
			
			update(statement);
		}
	}

	public void update(int action_id, float intensity) {
		String statement;
			
		if (action_id > 0) {
	

			statement 	= "UPDATE sw_action SET " +
					"intensity = " + intensity  + " " +
					"WHERE action_id = " + action_id  ;
			
			update(statement);
		}
	}

	public void update(int action_id, int target) {
		String statement;
			
		if (action_id > 0) {
	

			statement 	= "UPDATE sw_action SET " +
					"target = " + target  + " " +
					"WHERE action_id = " + action_id  ;
			
			update(statement);
		}
	}

	public void update(int action_id, boolean done) {
		String statement;
		int doneAsInt;
		
		if (action_id > 0) {
	
			if (done )			doneAsInt = 1;
			else				doneAsInt = 0;
			
			statement 	= "UPDATE sw_action SET " +
					"done = " + doneAsInt  + " " +
					"WHERE action_id = " + action_id  ;
			
			update(statement);
		}
	}

	public void delete(int action_id) {
		String statement;
			
		if (action_id > 0) {
	
			statement 	= "DELETE FROM sw_action WHERE action_id = " + action_id  ;
			
			delete(statement);
		}
	}

	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		action_id = new int[rowCount];
		actor = new int[rowCount];
		type = new int[rowCount];
		mode = new int[rowCount];
		minTime = new long[rowCount];
		maxTime = new long[rowCount];
		priority = new int[rowCount];
		intensity = new float[rowCount];
		duration = new int[rowCount];
		remainedDuration = new int[rowCount];
		target = new int[rowCount];
		x = new float[rowCount];
		y = new float[rowCount];
		z = new float[rowCount];
		done = new int[rowCount];

		try {
			while (rs.next()) {
				
				action_id[row] = rs.getInt(1);
				actor[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				mode[row] = rs.getInt(4);
				minTime[row] = rs.getLong(5);
				maxTime[row] = rs.getLong(6);
				priority[row] = rs.getInt(7);
				intensity[row] = rs.getFloat(8);
				duration[row] = rs.getInt(9);
				remainedDuration[row] = rs.getInt(10);
				target[row] = rs.getInt(11);
				x[row] = rs.getFloat(12);
				y[row] = rs.getFloat(13);
				z[row] = rs.getFloat(14);
				done[row] = rs.getInt(15);

				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
}
