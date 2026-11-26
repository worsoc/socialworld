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

import org.socialworld.objects.enums.EnumFood;

public class Mapping_Food2WordString {

	private static Mapping_Food2WordString instance;
	
	public static Mapping_Food2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Food2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumFood,String> mapping = new EnumMap<>(EnumFood.class);
	
	public String get(EnumFood key) {
		if (key == EnumFood.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Food2WordString() {
	

		mapping.put(EnumFood.Fruit, "fruit");
		mapping.put(EnumFood.Veggie, "veggie");
		mapping.put(EnumFood.Meat, "meat");
		mapping.put(EnumFood.Fish, "fish");
		mapping.put(EnumFood.Bakery, "bakery");
		mapping.put(EnumFood.Dairy, "dairy");
		mapping.put(EnumFood.Sweets, "sweets");
		mapping.put(EnumFood.Apple, "apple");
		mapping.put(EnumFood.Banana, "banana");
		mapping.put(EnumFood.Berries, "berries");
		mapping.put(EnumFood.Turnip, "turnip");
		mapping.put(EnumFood.Onion, "onion");
		mapping.put(EnumFood.Root, "root");
		mapping.put(EnumFood.Sausage, "sausage");
		mapping.put(EnumFood.Ham, "ham");
		mapping.put(EnumFood.DriedFish, "driedFish");
		mapping.put(EnumFood.Bread, "bread");
		mapping.put(EnumFood.Cake, "cake");
		mapping.put(EnumFood.Cheese, "cheese");
		mapping.put(EnumFood.Honey, "honey");

		
	}	
}
