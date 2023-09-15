/*
* Social World
* Copyright (C) 2023  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.objects;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;
import org.socialworld.conversation.Word_Type;
import org.socialworld.core.AllWords;
import org.socialworld.objects.concrete.animals.Bird;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.mammals.*;
import org.socialworld.objects.concrete.eatable.Liquid;

public final class GroupingOfSimulationObjects {

	private static GroupingOfSimulationObjects instance;
	public static GroupingOfSimulationObjects getInstance() {
		if (instance == null) {
			instance = new GroupingOfSimulationObjects();
		}
		return instance;
	}

	private GroupingOfSimulationObjects() {
		Word word;
		
		
		word = AllWords.findAndGetWord("something", Word_Type.noun);
		if (word != null) LEXEM_SOMETHING = word.getLexem();

		word = AllWords.findAndGetWord("nothing", Word_Type.noun);
		if (word != null) LEXEM_NOTHING = word.getLexem();

	}
	
	public static final String PRAEFIX_SIMOBJECT_GROUPING_NUMBER = "SOGN_";
	
	public static final int RANGE_FOR_LOWER_VALUE = 100000;
	
	public static final int GROUPING_NUMBER_SUFFIX_TEST = 0;

	/***************************************************************************************************
	*
	 * 	grouping number totals
	 *
	 ***************************************************************************************************/
	
	
	public static final int GROUPING_NUMBER_ALL_OBJECTS = 0b01111110111111111111111111111111;	// 2130706431
	
	public static final int GROUPING_NUMBER_ALL_ANIMALS_AND_ITEMS 	= 0b00001010111111111111111111111111;	// 184549375
	public static final int GROUPING_NUMBER_ALL_PLANTS_AND_ITEMS 	= 0b00001100111111111111111111111111;	// 218103807
	public static final int GROUPING_NUMBER_ALL_ANIMALS 			= 0b00000010111111111111111111111111;	// 50331647
	public static final int GROUPING_NUMBER_ALL_PLANTS 				= 0b00000100111111111111111111111111;	// 83886079
	public static final int GROUPING_NUMBER_ALL_ITEMS 				= 0b00001000111111111111111111111111;	// 150994943
	public static final int GROUPING_NUMBER_ALL_GODS 				= 0b00010000111111111111111111111111;	// 285212671
	public static final int GROUPING_NUMBER_ALL_MAGICS 				= 0b00100000111111111111111111111111;	// 553648127

	/**************************************************************************	
	*	animals	
	**************************************************************************/	
	
	public static final int GROUPING_NUMBER_ALL_MAMMALS				= 0b00000010000000011111111111111111;	// 33685503
	public static final int GROUPING_NUMBER_ALL_BIRDS				= 0b00000010000000101111111111111111;	// 33751039
	public static final int GROUPING_NUMBER_ALL_FISHS				= 0b00000010000001001111111111111111;	// 33882111

	
	/**************************************************************************	
	*	mammals	
	**************************************************************************/	
	
	public static final int GROUPING_NUMBER_ALL_MARSUPIALS			= 0b00000010000000010000000111111111;	// 33620479
	public static final int GROUPING_NUMBER_ALL_PRIMATES			= 0b00000010000000010000001011111111;	// 33620735
	public static final int GROUPING_NUMBER_ALL_RODENTS				= 0b00000010000000010000010011111111;	// 33621247
	public static final int GROUPING_NUMBER_ALL_BATS				= 0b00000010000000010000100011111111;	// 33622271
	public static final int GROUPING_NUMBER_ALL_PREDATORS			= 0b00000010000000010001000011111111;	// 33624319
	public static final int GROUPING_NUMBER_ALL_WHALES				= 0b00000010000000010010000011111111;	// 33628415
	public static final int GROUPING_NUMBER_ALL_UNGULATES			= 0b00000010000000010100000011111111;	// 33636607
	public static final int GROUPING_NUMBER_ALL_ELSEMAMMALS			= 0b00000010000000011000000011111111;	// 33652991
	
	/**************************************************************************	
	*	primates	
	**************************************************************************/	

	public static final int GROUPING_NUMBER_ALL_HUMANS				= 0b00000010000000010000001000011111;	// 33620511

	/**************************************************************************	
	*	predators	
	**************************************************************************/	
	
	public static final int GROUPING_NUMBER_ALL_DOGS 				= 0b00000010000000010001000000011111;	// 33624095
	public static final int GROUPING_NUMBER_ALL_CATS				= 0b00000010000000010001000000101111;	// 33624111

	
	
	/**************************************************************************	
	*	birds	
	**************************************************************************/	
	
	public static final int GROUPING_NUMBER_ALL_PALAEOGNATHAE		= 0b00000010000000100000000111111111;	// 33686015
	public static final int GROUPING_NUMBER_ALL_STRISORES			= 0b00000010000000100000001011111111;	// 33686271
	public static final int GROUPING_NUMBER_ALL_GALLOANSERAE		= 0b00000010000000100000010011111111;	// 33686783
	public static final int GROUPING_NUMBER_ALL_AEQUORLITHORNIHES	= 0b00000010000000100000100011111111;	// 33687807
	public static final int GROUPING_NUMBER_ALL_RAPTORS				= 0b00000010000000100001000011111111;	// 33689855
	public static final int GROUPING_NUMBER_ALL_CORACIIMORPHAE		= 0b00000010000000100010000011111111;	// 33693951
	public static final int GROUPING_NUMBER_ALL_COLUMBAVES			= 0b00000010000000100100000011111111;	// 33702143
	public static final int GROUPING_NUMBER_ALL_PASSERIFORMES		= 0b00000010000000101000000011111111;	// 33718527
	

	/**************************************************************************	
	*	galloanserae	
	**************************************************************************/	

	public static final int GROUPING_NUMBER_ALL_GRUIFORMES			= 0b00000010000000100000010000011111;	// 33686559

	/**************************************************************************	
	*	raptors	
	**************************************************************************/	

	public static final int GROUPING_NUMBER_ALL_ACCIPITRIFORMES		= 0b00000010000000100001000000011111;	// 33689631
	public static final int GROUPING_NUMBER_ALL_STRIGIFORMES		= 0b00000010000000100001000000101111;	// 33689647
	public static final int GROUPING_NUMBER_ALL_FALCONIFORMES		= 0b00000010000000100001000000111111;	// 33689663

	/**************************************************************************	
	*	coraciimorphae	
	**************************************************************************/	

	public static final int GROUPING_NUMBER_ALL_PSITTACIFORMES		= 0b00000010000000100010000000011111;	// 33693727

	/**************************************************************************	
	*	columbavas	
	**************************************************************************/	
	
	public static final int GROUPING_NUMBER_ALL_OTIDIMORPHAE		= 0b00000010000000100100000000011111;	// 33701919
	public static final int GROUPING_NUMBER_ALL_COLUMBIMORPHAE		= 0b00000010000000100100000000101111;	// 33701935

	/**************************************************************************	
	*	passeriformes	
	**************************************************************************/	

	public static final int GROUPING_NUMBER_ALL_SUBOSCINES			= 0b00000010000000101000000011111111;	// 33718527
	public static final int GROUPING_NUMBER_ALL_OSCINES				= 0b00000010000000101000000101111111;	// 33718655


	
	public static final int GROUPING_NUMBER_ALL_LIQUIDS 			= 0b00001000000000011111111111111111;	// 134348799
	
	
	public static final int GROUPING_NUMBER_ALL_FOODS 				= 0b00001000000100001111111111111111;	// 135331839
	public static final int GROUPING_NUMBER_ALL_FRUITS 				= 0b00001000000100000000000111111111;	// 135266815
	public static final int GROUPING_NUMBER_ALL_VEGGIES 			= 0b00001000000100000000001011111111;	// 135267071
	public static final int GROUPING_NUMBER_ALL_MEATS 				= 0b00001000000100000000001111111111;	// 135267327
	
	
	public static final int GROUPING_NUMBER_ALL_HUMANCRAFTEDS 		= 0b00001000001000001111111111111111;	// 136380415
	
	public static final int GROUPING_NUMBER_ALL_CLOTHES 			= 0b00001000001000000000000111111111;	// 136315391
	public static final int GROUPING_NUMBER_ALL_CAPS 				= 0b00001000001000000000000100011111;	// 136315167
	public static final int GROUPING_NUMBER_ALL_SHIRTS 				= 0b00001000001000000000000100101111;	// 136315183
	public static final int GROUPING_NUMBER_ALL_TROUSERS 			= 0b00001000001000000000000100111111;	// 136315199
	public static final int GROUPING_NUMBER_ALL_SHOES 				= 0b00001000001000000000000101001111;	// 136315215
	public static final int GROUPING_NUMBER_ALL_SOCKS 				= 0b00001000001000000000000101011111;	// 136315231
	public static final int GROUPING_NUMBER_ALL_GLOVES 				= 0b00001000001000000000000101101111;	// 136315247
	

	/***************************************************************************************************
	*
	 * 	grouping number praefixes
	 *
	 ***************************************************************************************************/
	
	public static final int GROUPING_NUMBER_PRAEFIX_NOSIMOBJ			 = 0b0000000000000000; // 	0

	

	public static final int GROUPING_NUMBER_PRAEFIX_MAMMAL		 		= 0b0000001000000001; // 	513
	public static final int GROUPING_NUMBER_PRAEFIX_BIRD		 		= 0b0000001000000010; // 	514
	public static final int GROUPING_NUMBER_PRAEFIX_FISH		 		= 0b0000001000000100; // 	516
	public static final int GROUPING_NUMBER_PRAEFIX_REPTILE	 			= 0b0000001000001000; // 	520
	public static final int GROUPING_NUMBER_PRAEFIX_AMPHIBIAN	 		= 0b0000001000010000; // 	528

	public static final int GROUPING_NUMBER_PRAEFIX_TREE		 		= 0b0000010000000001; // 	1025
	public static final int GROUPING_NUMBER_PRAEFIX_FLOWER	 			= 0b0000010000000010; // 	1026
	
	public static final int GROUPING_NUMBER_PRAEFIX_LIQUID		 		= 0b0000100000000001; // 	2049
	public static final int GROUPING_NUMBER_PRAEFIX_SOLID		 		= 0b0000100000000010; // 	2050
	public static final int GROUPING_NUMBER_PRAEFIX_ANIMALPART	 		= 0b0000100000000100; // 	2052
	public static final int GROUPING_NUMBER_PRAEFIX_PLANTPART		 	= 0b0000100000001000; // 	2056
	public static final int GROUPING_NUMBER_PRAEFIX_EATABLE		 		= 0b0000100000010000; // 	2064
	public static final int GROUPING_NUMBER_PRAEFIX_HUMANCRAFTED	 	= 0b0000100000100000; // 	2080

	/***************************************************************************************************
	*
	 * 	lexem id higher values
	 *
	 ***************************************************************************************************/

	public static final short LEXEMID_HIGHERVALUE_IGNORE			= 0;

	
	public static final short LEXEMID_HIGHERVALUE_ANIMAL			= 20;
	public static final short LEXEMID_HIGHERVALUE_MAMMAL			= 21;
	public static final short LEXEMID_HIGHERVALUE_BIRD				= 22;
	public static final short LEXEMID_HIGHERVALUE_FISH				= 23;
	public static final short LEXEMID_HIGHERVALUE_REPTILE			= 24;
	public static final short LEXEMID_HIGHERVALUE_AMPHIBIAN			= 25;

	
	public static final short LEXEMID_HIGHERVALUE_PLANT			 	= 30;
	public static final short LEXEMID_HIGHERVALUE_TREE			 	= 31;
	public static final short LEXEMID_HIGHERVALUE_FLOWER		 	= 32;
	
	public static final short LEXEMID_HIGHERVALUE_ITEM			 	= 40;
	public static final short LEXEMID_HIGHERVALUE_LIQUID		 	= 41;
	public static final short LEXEMID_HIGHERVALUE_SOLID		 		= 42;
	public static final short LEXEMID_HIGHERVALUE_ANIMALPART		= 43;
	public static final short LEXEMID_HIGHERVALUE_PLANTPART		 	= 44;
	public static final short LEXEMID_HIGHERVALUE_FOOD		 		= 45;
	public static final short LEXEMID_HIGHERVALUE_HUMANCRAFTED	 	= 46;
	
	public static final short LEXEMID_HIGHERVALUE_GOD			 	= 50;
	public static final short LEXEMID_HIGHERVALUE_SEA				= 51;
	public static final short LEXEMID_HIGHERVALUE_WEATHER			= 52;
	
	public static final short LEXEMID_HIGHERVALUE_MAGIC			 	= 60;
	public static final short LEXEMID_HIGHERVALUE_LIGHTNING		 	= 61;

	/***************************************************************************************************
	*
	 * 	lexem id lower values  (group number suffix)
	 *
	 ***************************************************************************************************/

	public static final int LEXEMID_LOWERVALUE_IGNORE				 	= 0b0000000000000000; // 	0

	public static final int LEXEMID_LOWERVALUE_PRIMATE				 	= 0b0000001000000000; // 	512
	
	public static final int LEXEMID_LOWERVALUE_HUMAN				 	= 0b0000001000010000; // 	528
	
	public static final int LEXEMID_LOWERVALUE_DOG				 		= 0b0001000000010000; // 	4112
	public static final int LEXEMID_LOWERVALUE_CAT				 		= 0b0001000000100000; // 	4128
	
	
	public static final int LEXEMID_LOWERVALUE_PALAEOGNATHAE	 		= 0b0000000100000000; // 	256
	public static final int LEXEMID_LOWERVALUE_STRISORES	 			= 0b0000001000000000; // 	512
	public static final int LEXEMID_LOWERVALUE_GALLOANSERAE	 			= 0b0000010000000000; // 	1024
	public static final int LEXEMID_LOWERVALUE_AEQUORLITHORNIHES 		= 0b0000100000000000; // 	2048
	public static final int LEXEMID_LOWERVALUE_RAPTOR 					= 0b0001000000000000; // 	4096
	public static final int LEXEMID_LOWERVALUE_CORACIIMORPHAE 			= 0b0010000000000000; // 	8192
	public static final int LEXEMID_LOWERVALUE_COLUMBAVES 				= 0b0100000000000000; // 	16384
	public static final int LEXEMID_LOWERVALUE_PASSERIFORMES 			= 0b1000000000000000; // 	32768
	
	public static final int LEXEMID_LOWERVALUE_OTIDIMORPHAE		 		= 0b0100000000010000; // 	16400
	public static final int LEXEMID_LOWERVALUE_COLUMBIMORPHAE	 		= 0b0100000000100000; // 	16416
	public static final int LEXEMID_LOWERVALUE_GRUIFORMES		 		= 0b0000010000010000; // 	1040
	public static final int LEXEMID_LOWERVALUE_ACCIPITRIFORMES	 		= 0b0001000000010000; // 	4112
	public static final int LEXEMID_LOWERVALUE_STRIGIFORMES		 		= 0b0001000000100000; // 	4128
	public static final int LEXEMID_LOWERVALUE_FALCONIFORMES	 		= 0b0001000000110000; // 	4144
	public static final int LEXEMID_LOWERVALUE_PSITTACIFORMES			= 0b0010000000010000; // 	8208
	public static final int LEXEMID_LOWERVALUE_SUBOSCINES		 		= 0b1000000000010000; // 	32784
	public static final int LEXEMID_LOWERVALUE_OSCINES			 		= 0b1000000000100000; // 	32800
	
	public static final int LEXEMID_LOWERVALUE_FRUIT		 			= 0b0000000100000000; // 	256
	public static final int LEXEMID_LOWERVALUE_VEGGIE	 				= 0b0000001000000000; // 	512
	public static final int LEXEMID_LOWERVALUE_MEAT	 					= 0b0000010000000000; // 	1024

	
	public static final int LEXEMID_LOWERVALUE_CAP		 	 			= 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_SHIRT		 	 		= 0b0000000100100000; // 	288
	public static final int LEXEMID_LOWERVALUE_TROUSERS		 	 		= 0b0000000100110000; // 	304
	public static final int LEXEMID_LOWERVALUE_SHOE		 	 			= 0b0000000101000000; // 	320
	public static final int LEXEMID_LOWERVALUE_SOCK		 	 			= 0b0000000101010000; // 	336
	public static final int LEXEMID_LOWERVALUE_GLOVE	 	 			= 0b0000000101100000; // 	352
	
	public static final int LEXEMID_LOWERVALUE_APPLE		 			= 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_BANANA		 			= 0b0000000100100000; // 	288

	public static final int LEXEMID_LOWERVALUE_WATER		 			= 0b0000000100010000; // 	272

	public static final int LEXEMID_LOWERVALUE_HUSKY				 	= 0b0001000000010001; // 	4113
	public static final int LEXEMID_LOWERVALUE_CHIHUAHUA			 	= 0b0001000000010010; // 	4114
	
	public static final int LEXEMID_LOWERVALUE_OSTRICH	 				= 0b0000000100010001; // 	273

	public static final int LEXEMID_LOWERVALUE_STORK			 		= 0b0000100000010001; // 	2065
	
	private static  Lexem LEXEM_SOMETHING;
	private static  Lexem LEXEM_NOTHING; 
	
	
	
	public static  int getGroupNumber(String sogn_element) {
		
		if (sogn_element.startsWith(PRAEFIX_SIMOBJECT_GROUPING_NUMBER)) {
			String number = sogn_element.substring(PRAEFIX_SIMOBJECT_GROUPING_NUMBER.length());
			return Integer.parseInt(number);
		}
		else {
			return -1;
		}
		
	}
	
	public  Lexem getLexemForGroupingNumber(int groupNumber) {

		int gnp = getHigherPart(groupNumber); // grouping number praefix
		int gns = getLowerPart(groupNumber);	// grouping number suffix --> lexem ID lower value
		
		int lexemIdHigherValue = translateGNP2lexemIdHigherPart(gnp);
		
		if (lexemIdHigherValue > 0) {
			Lexem lexem = AllWords.getLexem(Lexem.OFFSET_LEXEMID_NOUN_SIMOBJ + lexemIdHigherValue * RANGE_FOR_LOWER_VALUE + gns);
			return lexem;
		}
		else {
			if (checkIsValidGroupNumber(groupNumber)) {
				return LEXEM_SOMETHING;
			}
			else
				return LEXEM_NOTHING;
		}
		
	}
	
	public  boolean checkIsLexemSomething(Lexem lexem) {
		return lexem.equals(LEXEM_SOMETHING);
	}
	
	public  boolean checkIsLexemNothing(Lexem lexem) {
		return lexem.equals(LEXEM_NOTHING);
	}
	
	boolean checkObjectBelongsToGroup(SimulationObject simObject, int groupNumber) {
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
		case GROUPING_NUMBER_ALL_HUMANS:
			if (simObject instanceof Human) return true;
		default:
			int preafix = getHigherPart(groupNumber);
			int suffix = getLowerPart(groupNumber);
			return checkObjectBelongsToGroup(simObject,  preafix,  suffix);
		}
	}
	
	private  boolean checkObjectBelongsToGroup(SimulationObject simObject, int groupNumberPreafix,  int groupNumberSuffix) {
		
		switch (groupNumberPreafix) {
		case GROUPING_NUMBER_PRAEFIX_MAMMAL:
			if (simObject instanceof Mammal) return simObject.checkObjectBelongsToGroup(groupNumberSuffix);
			break;
		case GROUPING_NUMBER_PRAEFIX_BIRD:
			if (simObject instanceof Bird) return simObject.checkObjectBelongsToGroup(groupNumberSuffix);
			break;
		case GROUPING_NUMBER_PRAEFIX_LIQUID:
			if (simObject instanceof Liquid) return simObject.checkObjectBelongsToGroup(groupNumberSuffix);
			break;
		default:
			return false;
		}
		
		return false;

	}
	
	private static int getLowerPart(int number) {
		return ( int) (number % 0b10000000000000000);
	}
	
	private static int getHigherPart(int number) {
		return ( int) (number >> 16);
	}

	private static int translateGNP2lexemIdHigherPart(int groupNumberPraefix) {
		
		short firstByte = (short) (groupNumberPraefix >> 8);
		short secondByte = (short) (groupNumberPraefix % 0b100000000);
		
		int valueSimObjType = 0;
		int valueFirstLevel = 0;
		
		
		switch (firstByte) {
			case 1: valueSimObjType = 10; break;
			case 2: valueSimObjType = 20; break;
			case 4: valueSimObjType = 30; break;
			case 8: valueSimObjType = 40; break;
			case 16: valueSimObjType = 50; break;
			case 32: valueSimObjType = 60; break;
			case 64: valueSimObjType = 70; break;
			case 128: valueSimObjType = 80; break;
			default: return 0;
		}
		
		switch (secondByte) {
			case 1: valueFirstLevel = 1; break;
			case 2: valueFirstLevel = 2; break;
			case 4: valueFirstLevel = 3; break;
			case 8: valueFirstLevel = 4; break;
			case 16: valueFirstLevel = 5; break;
			case 32: valueFirstLevel = 6; break;
			case 64: valueFirstLevel = 7; break;
			case 128: valueFirstLevel = 8; break;
			default: return 0;
		}
		
		return valueSimObjType + valueFirstLevel;
		
	}
	
	private static boolean checkIsValidGroupNumber(int groupNumber) {
		return true;
	}
}
