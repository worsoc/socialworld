/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.Constants;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumAnimalPart;

public class Mapping_AnimalPart2LexemIDLowerPart {

	private static Mapping_AnimalPart2LexemIDLowerPart instance;
	
	public static Mapping_AnimalPart2LexemIDLowerPart getInstance() {
		if (instance == null) {
			instance = new Mapping_AnimalPart2LexemIDLowerPart();
		}
		return instance;
	}

	private  EnumMap<EnumAnimalPart,Integer> mapping  = new EnumMap<>(EnumAnimalPart.class);
	
	public int get(EnumAnimalPart key) {
		if (key == EnumAnimalPart.ignore) return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (int) value;
	}
	
	private Mapping_AnimalPart2LexemIDLowerPart() {
		mapping.put(EnumAnimalPart.Hide, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HIDE);
		
	}
}
