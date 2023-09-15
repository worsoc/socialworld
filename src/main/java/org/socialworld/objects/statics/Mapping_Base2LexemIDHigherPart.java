package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.Constants;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumBaseSimObj;

public class Mapping_Base2LexemIDHigherPart {

	private static Mapping_Base2LexemIDHigherPart instance;
	
	public static Mapping_Base2LexemIDHigherPart getInstance() {
		if (instance == null) {
			instance = new Mapping_Base2LexemIDHigherPart();
		}
		return instance;
	}

	private  EnumMap<EnumBaseSimObj,Short> mapping = new EnumMap<>(EnumBaseSimObj.class);
	
	public short get(EnumBaseSimObj key) {
		if (key == EnumBaseSimObj.ignore) return GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (short) (int) value;
	}
	
	private Mapping_Base2LexemIDHigherPart() {
	

		mapping.put(EnumBaseSimObj.NoSimulationObject, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_IGNORE);
		mapping.put(EnumBaseSimObj.Animal, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_ANIMAL);
		mapping.put(EnumBaseSimObj.God, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_GOD);
		mapping.put(EnumBaseSimObj.Item, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_ITEM);
		mapping.put(EnumBaseSimObj.Magic, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_MAGIC);
		mapping.put(EnumBaseSimObj.Liquid, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_LIQUID);
		mapping.put(EnumBaseSimObj.Food, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_FOOD);
		mapping.put(EnumBaseSimObj.HumanCrafted, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_HUMANCRAFTED);
		mapping.put(EnumBaseSimObj.Bird, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_BIRD);
		mapping.put(EnumBaseSimObj.Mammal, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_MAMMAL);
		mapping.put(EnumBaseSimObj.Sea, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_SEA);
		mapping.put(EnumBaseSimObj.Weather, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_WEATHER);
		mapping.put(EnumBaseSimObj.Lightning, GroupingOfSimulationObjects.LEXEMID_HIGHERVALUE_LIGHTNING);
		
	}
}
