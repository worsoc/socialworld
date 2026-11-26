/*
 * Social World
 * Copyright (C) 2023  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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
	public static final int LEXEMID_LOWERVALUE_FISH	 					= 0b0000100000000000; // 	2048
	public static final int LEXEMID_LOWERVALUE_BAKERY	 				= 0b0001000000000000; // 	4096
	public static final int LEXEMID_LOWERVALUE_DAIRY	 				= 0b0010000000000000; // 	8192
	public static final int LEXEMID_LOWERVALUE_SWEETS	 				= 0b0100000000000000; // 	16384

	// groups of human crafted items (2026-11-23); clothes is the group of cap, shirt, ... below
	public static final int LEXEMID_LOWERVALUE_CLOTHES		 			= 0b0000000100000000; // 	256
	public static final int LEXEMID_LOWERVALUE_TOOL			 			= 0b0000001000000000; // 	512
	public static final int LEXEMID_LOWERVALUE_WEAPON		 			= 0b0000010000000000; // 	1024
	public static final int LEXEMID_LOWERVALUE_CONTAINER	 			= 0b0000100000000000; // 	2048
	public static final int LEXEMID_LOWERVALUE_VALUABLE		 			= 0b0001000000000000; // 	4096
	public static final int LEXEMID_LOWERVALUE_DEVOTIONAL	 			= 0b0010000000000000; // 	8192
	public static final int LEXEMID_LOWERVALUE_WRITING		 			= 0b0100000000000000; // 	16384
	public static final int LEXEMID_LOWERVALUE_PASTIME		 			= 0b1000000000000000; // 	32768

	
	public static final int LEXEMID_LOWERVALUE_CAP		 	 			= 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_SHIRT		 	 		= 0b0000000100100000; // 	288
	public static final int LEXEMID_LOWERVALUE_TROUSERS		 	 		= 0b0000000100110000; // 	304
	public static final int LEXEMID_LOWERVALUE_SHOE		 	 			= 0b0000000101000000; // 	320
	public static final int LEXEMID_LOWERVALUE_SOCK		 	 			= 0b0000000101010000; // 	336
	public static final int LEXEMID_LOWERVALUE_GLOVE	 	 			= 0b0000000101100000; // 	352
	public static final int LEXEMID_LOWERVALUE_DRESS	 	 			= 0b0000000101110000; // 	368
	public static final int LEXEMID_LOWERVALUE_OVERGARMENT	 			= 0b0000000110000000; // 	384

	// concrete clothes (2026-11-23): kind + 1, + 2, ...; since 2026-11-25 (item class or composition) Cap, Shirt, Shoe,
	// Sock, Glove and Dress are concrete themselves and use the value of their kind, and the classes with a material
	// in the name are gone (the material comes from the composition). Free since then: 291, 337, 369, 370; the other
	// freed values went to the new clothes of step 4 (see below the sandal)
	public static final int LEXEMID_LOWERVALUE_HAT = 0b0000000100010001; // 	273 (until 2026-11-25 StrawHat)
	public static final int LEXEMID_LOWERVALUE_HEADSCARF = 0b0000000100010011; // 	275
	public static final int LEXEMID_LOWERVALUE_BERET = 0b0000000100010101; // 	277
	public static final int LEXEMID_LOWERVALUE_HOOD = 0b0000000100010110; // 	278
	public static final int LEXEMID_LOWERVALUE_HELMET = 0b0000000100010111; // 	279
	public static final int LEXEMID_LOWERVALUE_TUNIC = 0b0000000100100010; // 	290 (until 2026-11-25 WoolTunic)
	public static final int LEXEMID_LOWERVALUE_RAGS = 0b0000000100100100; // 	292
	public static final int LEXEMID_LOWERVALUE_WORK_TROUSERS = 0b0000000100110001; // 	305
	public static final int LEXEMID_LOWERVALUE_HOSE = 0b0000000100110010; // 	306
	public static final int LEXEMID_LOWERVALUE_HABIT = 0b0000000101110011; // 	371
	public static final int LEXEMID_LOWERVALUE_APRON = 0b0000000110000001; // 	385
	public static final int LEXEMID_LOWERVALUE_CLOAK = 0b0000000110000011; // 	387
	public static final int LEXEMID_LOWERVALUE_CHAIN_MAIL = 0b0000000110000101; // 	389
	public static final int LEXEMID_LOWERVALUE_BOOT = 0b0000000101000010; // 	322
	public static final int LEXEMID_LOWERVALUE_SANDAL = 0b0000000101000011; // 	323
	// new clothes of step 4 (item class or composition, 2026-11-25) on free values; the neckerchief is a kind of its own
	public static final int LEXEMID_LOWERVALUE_COIF = 0b0000000100010010; // 	274
	public static final int LEXEMID_LOWERVALUE_CROWN = 0b0000000100010100; // 	276
	public static final int LEXEMID_LOWERVALUE_WREATH = 0b0000000100011000; // 	280
	public static final int LEXEMID_LOWERVALUE_DOUBLET = 0b0000000100100001; // 	289
	public static final int LEXEMID_LOWERVALUE_GAMBESON = 0b0000000110000010; // 	386
	public static final int LEXEMID_LOWERVALUE_TABARD = 0b0000000110000100; // 	388
	public static final int LEXEMID_LOWERVALUE_GAUNTLET = 0b0000000101100001; // 	353
	public static final int LEXEMID_LOWERVALUE_CLOGS = 0b0000000101000001; // 	321
	public static final int LEXEMID_LOWERVALUE_NECKERCHIEF = 0b0000000110010000; // 	400

	// tools (2026-11-23): purpose levels of + 16 (numbers only, no classes), each tool + 1
	public static final int LEXEMID_LOWERVALUE_TOOL_FIELD = 0b0000001000010000; // 	528
	public static final int LEXEMID_LOWERVALUE_PITCHFORK = 0b0000001000010001; // 	529
	public static final int LEXEMID_LOWERVALUE_FLAIL = 0b0000001000010010; // 	530
	public static final int LEXEMID_LOWERVALUE_SICKLE = 0b0000001000010011; // 	531
	public static final int LEXEMID_LOWERVALUE_HOE = 0b0000001000010100; // 	532
	public static final int LEXEMID_LOWERVALUE_SHOVEL = 0b0000001000010101; // 	533
	public static final int LEXEMID_LOWERVALUE_PICKAXE = 0b0000001000010110; // 	534
	public static final int LEXEMID_LOWERVALUE_TOOL_CRAFT = 0b0000001000100000; // 	544
	public static final int LEXEMID_LOWERVALUE_AXE = 0b0000001000100001; // 	545
	public static final int LEXEMID_LOWERVALUE_HAMMER = 0b0000001000100010; // 	546
	public static final int LEXEMID_LOWERVALUE_SAW = 0b0000001000100011; // 	547
	public static final int LEXEMID_LOWERVALUE_TONGS = 0b0000001000100100; // 	548
	public static final int LEXEMID_LOWERVALUE_AWL = 0b0000001000100101; // 	549
	public static final int LEXEMID_LOWERVALUE_KNIFE = 0b0000001000100110; // 	550
	public static final int LEXEMID_LOWERVALUE_TOOL_TEXTILE = 0b0000001000110000; // 	560
	public static final int LEXEMID_LOWERVALUE_NEEDLE = 0b0000001000110001; // 	561
	public static final int LEXEMID_LOWERVALUE_SPINDLE = 0b0000001000110010; // 	562
	public static final int LEXEMID_LOWERVALUE_SHEARS = 0b0000001000110011; // 	563
	public static final int LEXEMID_LOWERVALUE_TOOL_HERDING = 0b0000001001000000; // 	576
	public static final int LEXEMID_LOWERVALUE_CROOK = 0b0000001001000001; // 	577
	public static final int LEXEMID_LOWERVALUE_FISHING_NET = 0b0000001001000010; // 	578
	public static final int LEXEMID_LOWERVALUE_OAR = 0b0000001001000011; // 	579
	public static final int LEXEMID_LOWERVALUE_WHIP = 0b0000001001000100; // 	580
	public static final int LEXEMID_LOWERVALUE_CURRYCOMB = 0b0000001001000101; // 	581
	public static final int LEXEMID_LOWERVALUE_TOOL_HOUSEHOLD = 0b0000001001010000; // 	592
	public static final int LEXEMID_LOWERVALUE_ROPE = 0b0000001001010001; // 	593
	public static final int LEXEMID_LOWERVALUE_SCALES = 0b0000001001010010; // 	594
	public static final int LEXEMID_LOWERVALUE_MORTAR = 0b0000001001010011; // 	595
	public static final int LEXEMID_LOWERVALUE_RAZOR = 0b0000001001010100; // 	596
	public static final int LEXEMID_LOWERVALUE_KEY = 0b0000001001010101; // 	597
	public static final int LEXEMID_LOWERVALUE_TOOL_LIGHT = 0b0000001001100000; // 	608
	public static final int LEXEMID_LOWERVALUE_LANTERN = 0b0000001001100001; // 	609
	public static final int LEXEMID_LOWERVALUE_CANDLE = 0b0000001001100010; // 	610
	public static final int LEXEMID_LOWERVALUE_SIGNAL_HORN = 0b0000001001100011; // 	611
	public static final int LEXEMID_LOWERVALUE_TOOL_TRAVEL = 0b0000001001110000; // 	624
	public static final int LEXEMID_LOWERVALUE_STAFF = 0b0000001001110001; // 	625

	// weapons (2026-11-23): weapon + 16 each
	public static final int LEXEMID_LOWERVALUE_CUDGEL = 0b0000010000010000; // 	1040
	public static final int LEXEMID_LOWERVALUE_DAGGER = 0b0000010000100000; // 	1056
	public static final int LEXEMID_LOWERVALUE_SHORT_SWORD = 0b0000010000110000; // 	1072
	public static final int LEXEMID_LOWERVALUE_LONG_SWORD = 0b0000010001000000; // 	1088
	public static final int LEXEMID_LOWERVALUE_SPEAR = 0b0000010001010000; // 	1104
	public static final int LEXEMID_LOWERVALUE_BOW = 0b0000010001100000; // 	1120
	public static final int LEXEMID_LOWERVALUE_SHIELD = 0b0000010001110000; // 	1136

	// containers (2026-11-23): container + 16 each
	public static final int LEXEMID_LOWERVALUE_SACK = 0b0000100000010000; // 	2064
	public static final int LEXEMID_LOWERVALUE_BASKET = 0b0000100000100000; // 	2080
	public static final int LEXEMID_LOWERVALUE_JUG = 0b0000100000110000; // 	2096
	public static final int LEXEMID_LOWERVALUE_WATERSKIN = 0b0000100001000000; // 	2112
	public static final int LEXEMID_LOWERVALUE_BUCKET = 0b0000100001010000; // 	2128
	public static final int LEXEMID_LOWERVALUE_PURSE = 0b0000100001100000; // 	2144
	public static final int LEXEMID_LOWERVALUE_SATCHEL = 0b0000100001110000; // 	2160
	public static final int LEXEMID_LOWERVALUE_BOWL = 0b0000100010000000; // 	2176
	public static final int LEXEMID_LOWERVALUE_POT = 0b0000100010010000; // 	2192
	public static final int LEXEMID_LOWERVALUE_FLASK = 0b0000100010100000; // 	2208
	public static final int LEXEMID_LOWERVALUE_QUIVER = 0b0000100010110000; // 	2224 (step 4, 2026-11-25)

	// valuables (2026-11-23): valuable + 16 each
	public static final int LEXEMID_LOWERVALUE_COINS = 0b0001000000010000; // 	4112
	public static final int LEXEMID_LOWERVALUE_RING = 0b0001000000100000; // 	4128
	public static final int LEXEMID_LOWERVALUE_SIGNET_RING = 0b0001000000110000; // 	4144 (step 4, 2026-11-25)

	// devotional (2026-11-23): devotional + 16 each
	public static final int LEXEMID_LOWERVALUE_ROSARY = 0b0010000000010000; // 	8208
	public static final int LEXEMID_LOWERVALUE_AMULET = 0b0010000000100000; // 	8224
	public static final int LEXEMID_LOWERVALUE_RELIC = 0b0010000000110000; // 	8240

	// writing (2026-11-23): writing + 16 each
	public static final int LEXEMID_LOWERVALUE_BOOK = 0b0100000000010000; // 	16400
	public static final int LEXEMID_LOWERVALUE_SCROLL = 0b0100000000100000; // 	16416
	public static final int LEXEMID_LOWERVALUE_QUILL = 0b0100000000110000; // 	16432

	// pastime (2026-11-23): pastime + 16 each
	public static final int LEXEMID_LOWERVALUE_LUTE = 0b1000000000010000; // 	32784
	public static final int LEXEMID_LOWERVALUE_FLUTE = 0b1000000000100000; // 	32800
	public static final int LEXEMID_LOWERVALUE_DICE = 0b1000000000110000; // 	32816
	public static final int LEXEMID_LOWERVALUE_JUGGLING_BALLS = 0b1000000001000000; // 	32832
	
	public static final int LEXEMID_LOWERVALUE_APPLE		 			= 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_BANANA		 			= 0b0000000100100000; // 	288

	// concrete food (2026-11-23): group + 16 each
	public static final int LEXEMID_LOWERVALUE_BERRIES = 0b0000000100110000; // 	304
	public static final int LEXEMID_LOWERVALUE_TURNIP = 0b0000001000010000; // 	528
	public static final int LEXEMID_LOWERVALUE_ONION = 0b0000001000100000; // 	544
	public static final int LEXEMID_LOWERVALUE_ROOT = 0b0000001000110000; // 	560
	public static final int LEXEMID_LOWERVALUE_SAUSAGE = 0b0000010000010000; // 	1040
	public static final int LEXEMID_LOWERVALUE_HAM = 0b0000010000100000; // 	1056
	public static final int LEXEMID_LOWERVALUE_DRIED_FISH = 0b0000100000010000; // 	2064
	public static final int LEXEMID_LOWERVALUE_BREAD = 0b0001000000010000; // 	4112
	public static final int LEXEMID_LOWERVALUE_CAKE = 0b0001000000100000; // 	4128
	public static final int LEXEMID_LOWERVALUE_CHEESE = 0b0010000000010000; // 	8208
	public static final int LEXEMID_LOWERVALUE_HONEY = 0b0100000000010000; // 	16400

	public static final int LEXEMID_LOWERVALUE_WATER		 			= 0b0000000100010000; // 	272

	// liquids (2026-11-23): + 16 each after water
	public static final int LEXEMID_LOWERVALUE_BEER = 0b0000000100100000; // 	288
	public static final int LEXEMID_LOWERVALUE_WINE = 0b0000000100110000; // 	304

	// parts of plants and animals, solids (2026-11-23): + 16 each (as liquids: water 272)
	public static final int LEXEMID_LOWERVALUE_BRANCH = 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_HERB = 0b0000000100100000; // 	288
	public static final int LEXEMID_LOWERVALUE_FLOWER = 0b0000000100110000; // 	304
	public static final int LEXEMID_LOWERVALUE_HIDE = 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_STONE = 0b0000000100010000; // 	272
	public static final int LEXEMID_LOWERVALUE_CHARCOAL = 0b0000000100100000; // 	288
	public static final int LEXEMID_LOWERVALUE_ORE = 0b0000000100110000; // 	304

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
