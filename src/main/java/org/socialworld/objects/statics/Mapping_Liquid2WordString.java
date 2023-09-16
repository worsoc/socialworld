package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.objects.enums.EnumLiquid;

public class Mapping_Liquid2WordString {

	private static Mapping_Liquid2WordString instance;
	
	public static Mapping_Liquid2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Liquid2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumLiquid,String> mapping = new EnumMap<>(EnumLiquid.class);
	
	public String get(EnumLiquid key) {
		if (key == EnumLiquid.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Liquid2WordString() {
	

		mapping.put(EnumLiquid.Water, "water");

	}	
}
