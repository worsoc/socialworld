package org.socialworld.objects;

import org.socialworld.objects.concrete.animals.mammals.*;
import org.socialworld.objects.concrete.eatable.Fruit;

public final class GroupingOfSimulationObjects {

	public static final int GROUPING_NUMBER_ALL_OBJECTS 
								= 0b00111110111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS 
								= 0b00001010111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS 
								= 0b00001100111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_ANIMALS 
								= 0b00000010111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_PLANTS 	
								= 0b00000100111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_ITEMS 	
								= 0b00001000111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_GODS 	
								= 0b00010000111111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_MAGICS 	
								= 0b00100000111111111111111111111111;

	
	public static final int GROUPING_NUMBER_ALL_MAMMALS
								= 0b00000010000111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_DOGS 
								= 0b00000010000100011111111111111111;
	public static final int GROUPING_NUMBER_ALL_CATS
								= 0b00000010000100101111111111111111;
	
	public static final int GROUPING_NUMBER_ALL_BIRDS
								= 0b00000010001011111111111111111111;

	public static final int GROUPING_NUMBER_ALL_FISHS
								= 0b00000010001111111111111111111111;

	
	public static final int GROUPING_NUMBER_ALL_EATABLES 	
								= 0b00001000000111111111111111111111;
	public static final int GROUPING_NUMBER_ALL_FRUITS 	
								= 0b00001000000100011111111111111111;
	public static final int GROUPING_NUMBER_ALL_VEGGIES 	
								= 0b00001000000100101111111111111111;
	public static final int GROUPING_NUMBER_ALL_MEATS 	
								= 0b00001000000100111111111111111111;
	
	private static final short GROUPING_NUMBER_PRAEFIX_DOG			 = 0b0000001000010001;
	private static final short GROUPING_NUMBER_PRAEFIX_CAT			 = 0b0000001000010010;
	
	private static final short GROUPING_NUMBER_PRAEFIX_FRUIT		 = 0b0000100000010001;

	static boolean checkObjectBelongsToGroup(SimulationObject simObject, int groupNumber) {
		//String className = simObject.getClass().getSimpleName();

		
		switch (groupNumber) {
		case GROUPING_NUMBER_ALL_OBJECTS:
			return true;
		case GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS:
			if ((simObject instanceof Animal) || (simObject instanceof Item)) return true;
		case GROUPING_NUMBER_ALL_ANIMALS:
			if (simObject instanceof Animal) return true;
		case GROUPING_NUMBER_ALL_ITEMS:
			if (simObject instanceof Item) return true;
		case GROUPING_NUMBER_ALL_GODS:
			if (simObject instanceof God) return true;
		case GROUPING_NUMBER_ALL_MAGICS:
			if (simObject instanceof Magic) return true;
		case GROUPING_NUMBER_ALL_DOGS:
			if (simObject instanceof Dog) return true;
		default:
			short preafix = getHigherShort(groupNumber);
			short suffix = getLowerShort(groupNumber);
			return checkObjectBelongsToGroup(simObject,  preafix,  suffix);
		}
	}
	
	private static boolean checkObjectBelongsToGroup(SimulationObject simObject, short groupNumberPreafix,  short groupNumberSuffix) {
		
		switch (groupNumberPreafix) {
		case GROUPING_NUMBER_PRAEFIX_DOG:
			if (simObject instanceof Dog) return simObject.checkObjectBelongsToGroup(groupNumberSuffix);
			break;
		case GROUPING_NUMBER_PRAEFIX_FRUIT:
			if (simObject instanceof Fruit) return simObject.checkObjectBelongsToGroup(groupNumberSuffix);
			break;
		default:
			return false;
		}
		
		return false;

	}
	
	private static short getLowerShort(int number) {
		return ( short) (number % 0b10000000000000000);
	}
	
	private static short getHigherShort(int number) {
		return ( short) (number >> 16);
	}

}
