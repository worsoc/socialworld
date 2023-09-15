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
import org.socialworld.objects.enums.EnumBird;

public class Mapping_Bird2LexemIDLowerPart {

	private static Mapping_Bird2LexemIDLowerPart instance;
	
	public static Mapping_Bird2LexemIDLowerPart getInstance() {
		if (instance == null) {
			instance = new Mapping_Bird2LexemIDLowerPart();
		}
		return instance;
	}

	private  EnumMap<EnumBird,Integer> mapping = new EnumMap<>(EnumBird.class);
	
	public int get(EnumBird key) {
		if (key == EnumBird.ignore) return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (int) value;
	}
	
	private Mapping_Bird2LexemIDLowerPart() {
	
		mapping.put(EnumBird.Aequorlithornithes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_AEQUORLITHORNIHES);
		mapping.put(EnumBird.Columbaves, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_COLUMBAVES);
		mapping.put(EnumBird.Coraciimorphae, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CORACIIMORPHAE);
		mapping.put(EnumBird.Galloanserae, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_GALLOANSERAE);
		mapping.put(EnumBird.Palaeognathae, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PALAEOGNATHAE);
		mapping.put(EnumBird.Passeriformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PASSERIFORMES);
		mapping.put(EnumBird.Raptor, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_RAPTOR);
		mapping.put(EnumBird.Strisores, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STRISORES);
		mapping.put(EnumBird.Stork, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STORK);
		mapping.put(EnumBird.Columbimorphae, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_COLUMBIMORPHAE);
		mapping.put(EnumBird.Otidimorphae, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_OTIDIMORPHAE);
		mapping.put(EnumBird.Psittaciformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PSITTACIFORMES);
		mapping.put(EnumBird.Gruiformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_GRUIFORMES);
		mapping.put(EnumBird.Ostrich, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_OSTRICH);
		mapping.put(EnumBird.Oscines, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_OSCINES);
		mapping.put(EnumBird.Suboscines, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SUBOSCINES);
		mapping.put(EnumBird.Accipitriformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_ACCIPITRIFORMES);
		mapping.put(EnumBird.Falconiformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FALCONIFORMES);
		mapping.put(EnumBird.Strigiformes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STRIGIFORMES);
		
	}
}
