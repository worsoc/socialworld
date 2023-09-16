package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.objects.enums.EnumHumanCrafted;

public class Mapping_HumanCrafted2WordString {

	private static Mapping_HumanCrafted2WordString instance;
	
	public static Mapping_HumanCrafted2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_HumanCrafted2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumHumanCrafted,String> mapping = new EnumMap<>(EnumHumanCrafted.class);
	
	public String get(EnumHumanCrafted key) {
		if (key == EnumHumanCrafted.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_HumanCrafted2WordString() {
	

		mapping.put(EnumHumanCrafted.Cap, "cap");
		mapping.put(EnumHumanCrafted.Shirt, "shirt");
		mapping.put(EnumHumanCrafted.Shoe, "shoe");
		mapping.put(EnumHumanCrafted.Sock, "sock");
		mapping.put(EnumHumanCrafted.Trousers, "trousers");

	}
}
