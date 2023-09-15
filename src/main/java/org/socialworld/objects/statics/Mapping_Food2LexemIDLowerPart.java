/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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
package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.Constants;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumFood;

public class Mapping_Food2LexemIDLowerPart {

	
	private static Mapping_Food2LexemIDLowerPart instance;
	
	public static Mapping_Food2LexemIDLowerPart getInstance() {
		if (instance == null) {
			instance = new Mapping_Food2LexemIDLowerPart();
		}
		return instance;
	}

	private  EnumMap<EnumFood,Integer> mapping  = new EnumMap<>(EnumFood.class);
	
	public int get(EnumFood key) {
		if (key == EnumFood.ignore) return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (int) value;
	}
	
	private Mapping_Food2LexemIDLowerPart() {
		
		mapping.put(EnumFood.Fruit, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FRUIT);
		mapping.put(EnumFood.Apple, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_APPLE);
		mapping.put(EnumFood.Banana, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BANANA);
	}
}
