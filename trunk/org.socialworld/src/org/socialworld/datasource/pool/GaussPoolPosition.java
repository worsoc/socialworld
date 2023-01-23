/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.datasource.pool;


import org.socialworld.attributes.Position;
import org.socialworld.datasource.tablesPool.TableGaussPosition;

public class GaussPoolPosition {
	public static final int CAPACITY_GPPos_ARRAY = 100;

	private static GaussPoolPosition instance;
	
	private static Position positionsForPositiveIndex[];
	private static int capacityForPositiveIndex;
	private static Position positionsForNegativeIndex[];
	private static int capacityForNegativeIndex;

	private GaussPoolPosition () {
		positionsForPositiveIndex = new Position[CAPACITY_GPPos_ARRAY];
		capacityForPositiveIndex = CAPACITY_GPPos_ARRAY;

		positionsForNegativeIndex = new Position[CAPACITY_GPPos_ARRAY];
		capacityForNegativeIndex = CAPACITY_GPPos_ARRAY;
		
		
		loadFromDB();
	}

	public static GaussPoolPosition getInstance() {
		if (instance == null) {
			instance = new GaussPoolPosition();
		}
		return instance;
	}

	public Position getPosition(int index) {
		if (index >= 0)
			if (capacityForPositiveIndex > index ) 
				return positionsForPositiveIndex[index];
			else return Position.getObjectNothing();
		else {
			index = index * -1;
			if (capacityForNegativeIndex > index ) 
				return positionsForNegativeIndex[index];
			else return Position.getObjectNothing();
		}	
	}
	
	private void setPosition(int index, Position position) {
		
		if (index >= 0) 
			if (index < capacityForPositiveIndex && positionsForPositiveIndex[index] == null ) 
				positionsForPositiveIndex[index] = position;
			else ;
		else {
			index = index * -1;
			if (index < capacityForNegativeIndex &&  positionsForNegativeIndex[index] == null ) 
					positionsForNegativeIndex[index] = position;
		}	
		
	}
	
	private void loadFromDB() {
		
			
			TableGaussPosition table;
			Position position;
			int rowCount;
			int row;
			
			int gauss_index;
			int pos_id;

			table = new TableGaussPosition();

			table.select(table.SELECT_ALL_COLUMNS, "", "");
			rowCount = table.rowCount();

			if (rowCount > 0) {
	
				for (row = 0; row < rowCount; row++) {
					
					gauss_index = table.getGaussIndex(row);
					pos_id = table.getPositionID(row);
					
					position = PositionPool.getInstance().getPosition(pos_id);
					setPosition(gauss_index, position);

				}		
					
			}
	}
	
}
