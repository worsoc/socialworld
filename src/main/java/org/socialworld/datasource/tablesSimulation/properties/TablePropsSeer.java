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
package org.socialworld.datasource.tablesSimulation.properties;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.PropertyName;
import org.socialworld.attributes.percipience.PropsSeer;
import org.socialworld.datasource.mariaDB.Table;

public class TablePropsSeer extends Table {


	// avpe ... angleViewPerceivingEvents
	// avpo ... angleViewPerceivingObjects
	// sdrt ... sizeDistanceRelationThreshold
	public final  String 	ALL_COLUMNS 		=	" props_seer_id, avpe,  avpo,  sdrt  ";

	private final int PROPS_SEER_ARRAY_MAX_COUNT = 1000;
	
	private static TablePropsSeer instance;
	
	private PropsSeer[] propsSeers;
	
	int props_seer_id[];
	float avpe[];
	float avpo[];
	double sdrt[];
	
	public static TablePropsSeer getInstance() {
		if (instance == null) {
				instance = new TablePropsSeer();
		}
		return instance;
	}

	private TablePropsSeer() {
		propsSeers = new PropsSeer[PROPS_SEER_ARRAY_MAX_COUNT];
		load();
	}

	@Override
	protected String getTableName() {
		return "sw_propsseer";
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

		setPK1(props_seer_id);

	}
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		props_seer_id = new int[rowCount];
		avpe = new float[rowCount];
		avpo = new float[rowCount];
		sdrt = new double[rowCount];

		try {
			while (rs.next()) {
				
				props_seer_id[row] = rs.getInt(1);
				avpe[row] = rs.getFloat(2);
				avpo[row] = rs.getFloat(3);
				sdrt[row] = rs.getDouble(4);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}

	public void insert(int props_seer_id, float avpe, float avpo,  double sdrt) {
		String statement;
			
		if (props_seer_id > 0) {
			
			statement 	= "INSERT INTO " + getTableName() + " (" + ALL_COLUMNS + ") VALUES (" 
					+ props_seer_id  + ", " + avpe +  ", " + avpo + ", " + sdrt  + ")";
			
			insert(statement);
		}
	}
	
	public void updateAngleViewPerceivingEvents( int props_seer_id, float avpe) {
		String statement;
			
		if (props_seer_id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"avpe = " + avpe  + " " +
					"WHERE props_seer_id = " + props_seer_id  ;
			
			update(statement);
		}
	}
	
	public void updateAngleViewPerceivingObjects( int props_seer_id, float avpo) {
		String statement;
			
		if (props_seer_id > 0) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"avpo = " + avpo  + " "  +
					"WHERE props_seer_id = " + props_seer_id  ;
			
			update(statement);
		}
	}

	public void updateSizeDistanceRelationThreshold( int props_seer_id, double sdrt) {
		String statement;
			
		if (props_seer_id > 0 ) {
	

			statement 	= "UPDATE " + getTableName() + " SET " +
					"sdrt = " + sdrt  + " " +
					"WHERE props_seer_id = " + props_seer_id  ;
			
			update(statement);
		}
	}

/*
	public PropsSeer getPropsSeer(int props_seer_id, PropertyName propName) {
		int index;
		select(SELECT_ALL_COLUMNS, " WHERE props_seer_id = " + props_seer_id, "");
		index = getIndexFor1PK(props_seer_id);
		PropsSeer propsSeer = new PropsSeer(propName, avpe[index], avpo[index], sdrt[index]);
		return propsSeer;
	}
*/
	public PropsSeer getPropsSeer(int props_seer_id, PropertyName propName) {
		// ignore property name for now
		if (props_seer_id > 0 && props_seer_id <= propsSeers.length) {
			return propsSeers[props_seer_id];
		}
		return PropsSeer.getObjectNothing();
	}

	private void load() {
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TablePropsSeer.load() Start " + ActualTime.asTime().toString());
		long lockingID = lockWithWait();
		int id = 0;
		float avpe;
		float avpo;
		double sdrt;
		PropsSeer propsSeer = null;
		
		select(SELECT_ALL_COLUMNS, "", " ORDER BY props_seer_id"); 
		
		if (props_seer_id.length > 0) {
			for (int row = 0; row < props_seer_id.length; row++) {
				id  = this.props_seer_id[row];
				avpe =  this.avpe[row];
				avpo = this.avpo[row];
				sdrt = this.sdrt[row];
				propsSeer = new PropsSeer(avpe, avpo, sdrt);
			}
			propsSeers[id] = propsSeer;
		}
		
		unlock(lockingID);
		if (GlobalSwitches.OUTPUT_CREATE_OBJECT) System.out.println("Erstellen SimObj > TablePropsSeer.load() Ende " + ActualTime.asTime().toString());
	}
	
}
