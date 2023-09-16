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
		mapping.put(EnumFood.Apple, "apple");
		mapping.put(EnumFood.Banana, "banana");

		
	}	
}
