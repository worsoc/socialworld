package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.objects.enums.EnumMammal;

public class Mapping_Mammal2WordString {

	private static Mapping_Mammal2WordString instance;
	
	public static Mapping_Mammal2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Mammal2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumMammal,String> mapping = new EnumMap<>(EnumMammal.class);
	
	public String get(EnumMammal key) {
		if (key == EnumMammal.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Mammal2WordString() {
	

		mapping.put(EnumMammal.Primate, "primate");
		mapping.put(EnumMammal.Human, "human");
		mapping.put(EnumMammal.Dog, "dog");
		mapping.put(EnumMammal.Chihuahua, "chihuahua");
		mapping.put(EnumMammal.Husky, "husky");

	}	
}
