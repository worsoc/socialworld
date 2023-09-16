package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.objects.enums.EnumBird;

public class Mapping_Bird2WordString {

	private static Mapping_Bird2WordString instance;
	
	public static Mapping_Bird2WordString getInstance() {
		if (instance == null) {
			instance = new Mapping_Bird2WordString();
		}
		return instance;
	}

	private  EnumMap<EnumBird,String> mapping = new EnumMap<>(EnumBird.class);
	
	public String get(EnumBird key) {
		if (key == EnumBird.ignore) return "";
		String value = (mapping.containsKey(key) ? mapping.get(key) : "");
		return value;
	}
	
	private Mapping_Bird2WordString() {
	

		mapping.put(EnumBird.Aequorlithornithes, "AEQUORLITHORNIHES");
		mapping.put(EnumBird.Columbaves, "COLUMBAVES");
		mapping.put(EnumBird.Coraciimorphae, "CORACIIMORPHAE");
		mapping.put(EnumBird.Galloanserae, "GALLOANSERAE");
		mapping.put(EnumBird.Palaeognathae, "PALAEOGNATHAE");
		mapping.put(EnumBird.Passeriformes, "PASSERIFORMES");
		mapping.put(EnumBird.Raptor, "RAPTOR");
		mapping.put(EnumBird.Strisores, "STRISORES");
		mapping.put(EnumBird.Stork, "STORK");
		mapping.put(EnumBird.Columbimorphae, "COLUMBIMORPHAE");
		mapping.put(EnumBird.Otidimorphae, "OTIDIMORPHAE");
		mapping.put(EnumBird.Psittaciformes, "PSITTACIFORMES");
		mapping.put(EnumBird.Gruiformes, "GRUIFORMES");
		mapping.put(EnumBird.Ostrich, "OSTRICH");
		mapping.put(EnumBird.Oscines, "OSCINES");
		mapping.put(EnumBird.Suboscines, "SUBOSCINES");
		mapping.put(EnumBird.Accipitriformes, "ACCIPITRIFORMES");
		mapping.put(EnumBird.Falconiformes, "FALCONIFORMES");
		mapping.put(EnumBird.Strigiformes, "STRIGIFORMES");

	}	
}
