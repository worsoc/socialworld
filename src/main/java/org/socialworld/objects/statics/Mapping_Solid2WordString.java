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

import org.socialworld.objects.enums.EnumSolid;

public class Mapping_Solid2WordString {

	private static Mapping_Solid2WordString instance;
	
	public static Mapping_Solid2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Solid2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumSolid,String> mapping = new EnumMap<>(EnumSolid.class);
	
	public String get(EnumSolid key) {
		if (key == EnumSolid.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Solid2WordString() {
		mapping.put(EnumSolid.Stone, "stone");
		mapping.put(EnumSolid.Charcoal, "charcoal");
		mapping.put(EnumSolid.Ore, "ore");
	


	}	
}
