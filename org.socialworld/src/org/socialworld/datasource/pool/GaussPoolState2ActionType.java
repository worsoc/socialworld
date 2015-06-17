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


public class GaussPoolState2ActionType {

	public static final int CAPACITY_GPS2A_ARRAY = 100;

	private static GaussPoolState2ActionType instance;
	
	private static int[] state2ActionTypesForPositiveIndex;
	private static int[] state2ActionTypesForNegativeIndex;
	
	private GaussPoolState2ActionType() {
		state2ActionTypesForPositiveIndex = new int[CAPACITY_GPS2A_ARRAY];
		state2ActionTypesForNegativeIndex = new int[CAPACITY_GPS2A_ARRAY];
	}
	
	public static GaussPoolState2ActionType getInstance() {
		if (instance == null) {
			instance = new GaussPoolState2ActionType();
		}
		return instance;
	}

	public int getState2ActionType(int index) {
		if (index >= 0)
			if (CAPACITY_GPS2A_ARRAY > index)	return state2ActionTypesForPositiveIndex[index];
			else return 0;
		else {
			index = index * -1;
			if (CAPACITY_GPS2A_ARRAY > index)	return state2ActionTypesForNegativeIndex[index];
			else return 0;
		}	
		
	}
	
}
