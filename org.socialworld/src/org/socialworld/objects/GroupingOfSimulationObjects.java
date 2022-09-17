package org.socialworld.objects;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word_Type;
import org.socialworld.core.AllWords;
import org.socialworld.objects.concrete.animals.mammals.*;
import org.socialworld.objects.concrete.eatable.Fruit;

public final class GroupingOfSimulationObjects {

	public static final String PRAEFIX_SIMOBJECT_GROUPING_NUMBER = "SOGN_";
	
	
	public static final int GROUPING_NUMBER_ALL_OBJECTS 
								= 0b00111110111111111111111111111111;	// 1056964607
	public static final int GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS 
								= 0b00001010111111111111111111111111;	// 184549375
	public static final int GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS 
								= 0b00001100111111111111111111111111;	// 218103807
	public static final int GROUPING_NUMBER_ALL_ANIMALS 
								= 0b00000010111111111111111111111111;	// 50331647
	public static final int GROUPING_NUMBER_ALL_PLANTS 	
								= 0b00000100111111111111111111111111;	// 83886079
	public static final int GROUPING_NUMBER_ALL_ITEMS 	
								= 0b00001000111111111111111111111111;	// 150994943
	public static final int GROUPING_NUMBER_ALL_GODS 	
								= 0b00010000111111111111111111111111;	// 285212671
	public static final int GROUPING_NUMBER_ALL_MAGICS 	
								= 0b00100000111111111111111111111111;	// 553648127

	
	public static final int GROUPING_NUMBER_ALL_MAMMALS
								= 0b00000010000111111111111111111111;	// 35651583
	public static final int GROUPING_NUMBER_ALL_DOGS 
								= 0b00000010000100011111111111111111;	// 34734079
	public static final int GROUPING_NUMBER_ALL_CATS
								= 0b00000010000100101111111111111111;	// 34799615
	
	public static final int GROUPING_NUMBER_ALL_BIRDS
								= 0b00000010001011111111111111111111;	// 36700159

	public static final int GROUPING_NUMBER_ALL_FISHS
								= 0b00000010001111111111111111111111;	// 37748735


	
	public static final int GROUPING_NUMBER_ALL_EATABLES 	
								= 0b00001000000111111111111111111111;	// 136314879
	public static final int GROUPING_NUMBER_ALL_FRUITS 	
								= 0b00001000000100011111111111111111;	// 135397375
	public static final int GROUPING_NUMBER_ALL_VEGGIES 	
								= 0b00001000000100101111111111111111;	// 135462911
	public static final int GROUPING_NUMBER_ALL_MEATS 	
								= 0b00001000000100111111111111111111;	// 135528447
	
	public static final int COUNT_GROUPING_NUMBERS = 17;
	
	
	private static final short GROUPING_NUMBER_PRAEFIX_DOG			 = 0b0000001000010001; // 	529
	private static final short GROUPING_NUMBER_PRAEFIX_CAT			 = 0b0000001000010010; // 	530
	
	private static final short GROUPING_NUMBER_PRAEFIX_FRUIT		 = 0b0000100000010001; // 	2065

	
	private static final Lexem LEXEM_ANIMAL = AllWords.findAndGetWord("animal", Word_Type.noun).getLexem();
	
	private static final Lexem LEXEM_SOMETHING = AllWords.findAndGetWord("something", Word_Type.noun).getLexem();
	private static final Lexem LEXEM_NOTHING = AllWords.findAndGetWord("nothing", Word_Type.noun).getLexem();
	
	
	
	public static  int getGroupNumber(String sogn_element) {
		
		if (sogn_element.startsWith(PRAEFIX_SIMOBJECT_GROUPING_NUMBER)) {
			String number = sogn_element.substring(PRAEFIX_SIMOBJECT_GROUPING_NUMBER.length());
			return Integer.parseInt(number);
		}
		else {
			return -1;
		}
		
	}
	
	public static Lexem getLexemForGroupingNumber(int groupNumber) {

		switch (groupNumber) {
			case GROUPING_NUMBER_ALL_ANIMALS: return LEXEM_ANIMAL;
			
			default:
				if (checkIsValidGroupNumber(groupNumber)) {
					return LEXEM_SOMETHING;
				}
				else
					return LEXEM_NOTHING;
		}
	}
	
	public static boolean checkIsLexemSomething(Lexem lexem) {
		return lexem.equals(LEXEM_SOMETHING);
	}
	
	public static boolean checkIsLexemNothing(Lexem lexem) {
		return lexem.equals(LEXEM_NOTHING);
	}
	
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

	private static boolean checkIsValidGroupNumber(int groupNumber) {
		return true;
	}
}
