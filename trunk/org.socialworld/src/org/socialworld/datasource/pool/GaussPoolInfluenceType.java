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
package org.socialworld.datasource.pool;
import org.socialworld.core.EventType;

public class GaussPoolInfluenceType {
	public static final int CAPACITY_GPIT_ARRAY = 100;

	private static GaussPoolInfluenceType instance;
	
	private static int[][] influenceTypesForPositiveIndex;
	private static int[][] influenceTypesForNegativeIndex;
	
	private GaussPoolInfluenceType() {
		influenceTypesForPositiveIndex = new int[CAPACITY_GPIT_ARRAY][ EventType.MAX_EVENT_TYPE];
		influenceTypesForNegativeIndex = new int[CAPACITY_GPIT_ARRAY][ EventType.MAX_EVENT_TYPE];
	}
	
	public static GaussPoolInfluenceType getInstance() {
		if (instance == null) {
			instance = new GaussPoolInfluenceType();
		}
		return instance;
	}

	public int[] getInfluenceTypes(int indexByGauss) {
		int types[];
		
		
		if (indexByGauss >= 0)
			types = influenceTypesForPositiveIndex[indexByGauss];
		else {
			indexByGauss = indexByGauss * -1;
			types = influenceTypesForNegativeIndex[indexByGauss];
		}	

		return types;
	}
	
}
