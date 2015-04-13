/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.datasource.loadObjects;


import org.apache.log4j.Logger;
import org.socialworld.datasource.db.TableObject;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;

/**
 * Its the basic class for loading simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);

	
	TableObject tableObjects;
	int indexTableRow;
	
	public LoadSimulationObjects() {
		
		tableObjects = new TableObject();

	}

	public abstract SimulationObject getObject(int objectID) ;
	
	

	protected void load(int objectID) {
		
		
		tableObjects.select(tableObjects.SELECT_ALL_COLUMNS, " WHERE id = " + objectID, "");
	
		indexTableRow = tableObjects.getIndexForPK(objectID);
		
	}

	protected void initObject(WriteAccessToSimulationObject object) {
		int state2ActionType;

		if (indexTableRow >= 0) {
			
			state2ActionType = tableObjects.getType(indexTableRow);
			object.setState2ActionType(state2ActionType, this);
		}

	}
	

	protected void initState(StateSimulationObject state) {

	}
	
}
