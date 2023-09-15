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
import org.socialworld.objects.enums.EnumHumanCrafted;

public class Mapping_HumanCrafted2LexemIDLowerPart {


	private static Mapping_HumanCrafted2LexemIDLowerPart instance;
	
	public static Mapping_HumanCrafted2LexemIDLowerPart getInstance() {
		if (instance == null) {
			instance = new Mapping_HumanCrafted2LexemIDLowerPart();
		}
		return instance;
	}

	private  EnumMap<EnumHumanCrafted,Integer> mapping  = new EnumMap<>(EnumHumanCrafted.class);
	
	public int get(EnumHumanCrafted key) {
		if (key == EnumHumanCrafted.ignore) return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (int) value;
	}
	
	private Mapping_HumanCrafted2LexemIDLowerPart() {
		
		mapping.put(EnumHumanCrafted.Cap, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CAP);
		mapping.put(EnumHumanCrafted.Shirt, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHIRT);
		mapping.put(EnumHumanCrafted.Shoe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHOE);
		mapping.put(EnumHumanCrafted.Sock, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SOCK);
		mapping.put(EnumHumanCrafted.Trousers, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TROUSERS);
	}
}
