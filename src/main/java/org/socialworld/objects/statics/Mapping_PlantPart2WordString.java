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

import org.socialworld.objects.enums.EnumPlantPart;

public class Mapping_PlantPart2WordString {

	private static Mapping_PlantPart2WordString instance;
	
	public static Mapping_PlantPart2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_PlantPart2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumPlantPart,String> mapping = new EnumMap<>(EnumPlantPart.class);
	
	public String get(EnumPlantPart key) {
		if (key == EnumPlantPart.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_PlantPart2WordString() {
		mapping.put(EnumPlantPart.Branch, "branch");
		mapping.put(EnumPlantPart.Herb, "herb");
		mapping.put(EnumPlantPart.Flower, "flower");
	


	}	
}
