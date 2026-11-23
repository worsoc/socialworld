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

import org.socialworld.objects.enums.EnumBaseSimObj;

public class Mapping_Base2WordString {

	private static Mapping_Base2WordString instance;
	
	public static Mapping_Base2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Base2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumBaseSimObj,String> mapping = new EnumMap<>(EnumBaseSimObj.class);
	
	public String get(EnumBaseSimObj key) {
		if (key == EnumBaseSimObj.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Base2WordString() {
	

		mapping.put(EnumBaseSimObj.NoSimulationObject, "");
		mapping.put(EnumBaseSimObj.Animal, "animal");
		mapping.put(EnumBaseSimObj.God, "god");
		mapping.put(EnumBaseSimObj.Item, "item");
		mapping.put(EnumBaseSimObj.Magic, "magic");
		mapping.put(EnumBaseSimObj.Liquid, "liquid");
		mapping.put(EnumBaseSimObj.Food, "food");
		mapping.put(EnumBaseSimObj.HumanCrafted, "humanCrafted");
		mapping.put(EnumBaseSimObj.Solid, "solid");
		mapping.put(EnumBaseSimObj.AnimalPart, "animalPart");
		mapping.put(EnumBaseSimObj.PlantPart, "plantPart");
		mapping.put(EnumBaseSimObj.Bird, "bird");
		mapping.put(EnumBaseSimObj.Mammal, "mammal");
		mapping.put(EnumBaseSimObj.Sea, "sea");
		mapping.put(EnumBaseSimObj.Weather, "weather");
		mapping.put(EnumBaseSimObj.Lightning, "lightning");
		
	}	
}
