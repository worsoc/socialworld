/*
 * Social World
 * Copyright (C) 2023  Mathias Sikos
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
		mapping.put(EnumFood.Veggie, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_VEGGIE);
		mapping.put(EnumFood.Meat, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_MEAT);
		mapping.put(EnumFood.Fish, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FISH);
		mapping.put(EnumFood.Bakery, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BAKERY);
		mapping.put(EnumFood.Dairy, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DAIRY);
		mapping.put(EnumFood.Sweets, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SWEETS);
		mapping.put(EnumFood.Apple, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_APPLE);
		mapping.put(EnumFood.Banana, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BANANA);
		mapping.put(EnumFood.Berries, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BERRIES);
		mapping.put(EnumFood.Turnip, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TURNIP);
		mapping.put(EnumFood.Onion, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_ONION);
		mapping.put(EnumFood.Root, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_ROOT);
		mapping.put(EnumFood.Sausage, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SAUSAGE);
		mapping.put(EnumFood.Ham, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HAM);
		mapping.put(EnumFood.DriedFish, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DRIED_FISH);
		mapping.put(EnumFood.Bread, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BREAD);
		mapping.put(EnumFood.Cake, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CAKE);
		mapping.put(EnumFood.Cheese, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CHEESE);
		mapping.put(EnumFood.Honey, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HONEY);
	}
}
