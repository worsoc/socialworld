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
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;
import org.socialworld.collections.SimulationObjectArray;
import org.socialworld.datasource.tablesSimulation.TableInfluenceByEvent;
import org.socialworld.datasource.tablesSimulation.TableObject;
import org.socialworld.datasource.tablesSimulation.TablePosition;
import org.socialworld.datasource.tablesSimulation.TableReactionByEvent;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * Its the basic class for loading simulation objects.
 * for inherited classes of class SimulationObject
 *  there exists an inherited class of LoadSimulationObjects.
 *  
 * @author Mathias Sikos (tyloesand) 
 */
public abstract class LoadSimulationObjects {
	
	protected static final Logger logger = Logger.getLogger(LoadSimulationObjects.class);

	SimulationObjectArray allObjects;
	
	TableObject tableObjects;
	int rowTableObjects;	

	TablePosition tablePositions;
	int rowTablePositions;	

	TableInfluenceByEvent tableInfluenceByEvent;
	int rowTableInfluenceByEvent;	

	TableReactionByEvent tableReactionByEvent;
	int rowTableReactionByEvent;	
	
	protected LoadSimulationObjects(SimulationObjectArray allObjects) {
		
		this.allObjects = allObjects;
		
		tableObjects = new TableObject();
		tablePositions = new TablePosition();

	}

	public abstract void createObject(int objectID) ;
	
	public abstract void loadObject(int objectID) ;
	

	protected void load(int objectID) {
		
		
		tableObjects.select(tableObjects.SELECT_ALL_COLUMNS, " WHERE id = " + objectID, "");
		tablePositions.select(tablePositions.SELECT_ALL_COLUMNS, " WHERE id = " + objectID, "");
		tableInfluenceByEvent.select(tableInfluenceByEvent.SELECT_ALL_COLUMNS, " WHERE id = " + objectID, " ORDER BY  eventType");
		tableReactionByEvent.select(tableReactionByEvent.SELECT_ALL_COLUMNS, " WHERE id = " + objectID, " ORDER BY  eventType");

		rowTableObjects = tableObjects.getIndexFor1PK(objectID);
		rowTablePositions = tablePositions.getIndexFor1PK(objectID);
		rowTableInfluenceByEvent = tableInfluenceByEvent.getIndexFor1PK(objectID);
		rowTableReactionByEvent = tableReactionByEvent.getIndexFor1PK(objectID);
		
	}

	protected void initObject(HiddenSimulationObject hiddenObject, int objectID) {
		

	}
	

	protected void initState(HiddenSimulationObject hiddenObject, int objectID) {
		int x,y,z;
	
		
		if (rowTableObjects >= 0) {
			int state2ActionType;
			state2ActionType = tableObjects.getType(rowTableObjects);
			hiddenObject.setState2ActionType(state2ActionType);
		}	
		if (rowTableInfluenceByEvent >= 0) {
			int influenceTypes[];
			influenceTypes = tableInfluenceByEvent.getTypes(rowTableInfluenceByEvent);
			hiddenObject.setInfluenceTypes(influenceTypes);
		}	
		if (rowTableReactionByEvent >= 0) {
			int reactionTypes[];
			reactionTypes = tableReactionByEvent.getTypes(rowTableReactionByEvent);
			hiddenObject.setReactionTypes(reactionTypes);
		}

		if (rowTablePositions >= 0) {
			x = tablePositions.getX(rowTablePositions);
			y = tablePositions.getY(rowTablePositions);
			z = tablePositions.getZ(rowTablePositions);
			hiddenObject.setPosition(new Position(new Vector(x,y,z)));
		}

	}
	
	protected void resetRowIndexes() {
		rowTableObjects = -1;
	}
}
