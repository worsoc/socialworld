/*
 * Social World
 * Copyright (C) 2023  Mathias Sikos
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
package org.socialworld.objects.statics;

import java.util.EnumMap;

import org.socialworld.Constants;
import org.socialworld.objects.GroupingOfSimulationObjects;
import org.socialworld.objects.enums.EnumHumanCrafted;

public class Mapping_HumanCrafted2LexemIDLowerPart {


	private static Mapping_HumanCrafted2LexemIDLowerPart instance;
	
	public static Mapping_HumanCrafted2LexemIDLowerPart getInstance() {
		if (instance == null) {
			instance = new Mapping_HumanCrafted2LexemIDLowerPart();
		}
		return instance;
	}

	private  EnumMap<EnumHumanCrafted,Integer> mapping  = new EnumMap<>(EnumHumanCrafted.class);
	
	public int get(EnumHumanCrafted key) {
		if (key == EnumHumanCrafted.ignore) return GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_IGNORE;
		Integer value = (mapping.containsKey(key) ? mapping.get(key) : Constants.MAPPING_NO_ENTRY_FOR_KEY);
		return (int) value;
	}
	
	private Mapping_HumanCrafted2LexemIDLowerPart() {
		
		mapping.put(EnumHumanCrafted.Clothes, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CLOTHES);
		mapping.put(EnumHumanCrafted.Tool, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TOOL);
		mapping.put(EnumHumanCrafted.Weapon, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WEAPON);
		mapping.put(EnumHumanCrafted.Container, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CONTAINER);
		mapping.put(EnumHumanCrafted.Valuable, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_VALUABLE);
		mapping.put(EnumHumanCrafted.Devotional, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DEVOTIONAL);
		mapping.put(EnumHumanCrafted.Writing, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WRITING);
		mapping.put(EnumHumanCrafted.Pastime, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PASTIME);
		mapping.put(EnumHumanCrafted.Cap, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CAP);
		mapping.put(EnumHumanCrafted.Shirt, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHIRT);
		mapping.put(EnumHumanCrafted.Shoe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHOE);
		mapping.put(EnumHumanCrafted.Sock, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SOCK);
		mapping.put(EnumHumanCrafted.Trousers, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TROUSERS);
		mapping.put(EnumHumanCrafted.Dress, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DRESS);
		mapping.put(EnumHumanCrafted.Overgarment, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_OVERGARMENT);
		mapping.put(EnumHumanCrafted.Glove, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_GLOVE);
		mapping.put(EnumHumanCrafted.StrawHat, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STRAW_HAT);
		mapping.put(EnumHumanCrafted.WoolCap, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WOOL_CAP);
		mapping.put(EnumHumanCrafted.Headscarf, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HEADSCARF);
		mapping.put(EnumHumanCrafted.FeltHat, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FELT_HAT);
		mapping.put(EnumHumanCrafted.Beret, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BERET);
		mapping.put(EnumHumanCrafted.Hood, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HOOD);
		mapping.put(EnumHumanCrafted.Helmet, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HELMET);
		mapping.put(EnumHumanCrafted.LinenShirt, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LINEN_SHIRT);
		mapping.put(EnumHumanCrafted.WoolTunic, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WOOL_TUNIC);
		mapping.put(EnumHumanCrafted.SilkShirt, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SILK_SHIRT);
		mapping.put(EnumHumanCrafted.Rags, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_RAGS);
		mapping.put(EnumHumanCrafted.WorkTrousers, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WORK_TROUSERS);
		mapping.put(EnumHumanCrafted.Hose, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HOSE);
		mapping.put(EnumHumanCrafted.LinenDress, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LINEN_DRESS);
		mapping.put(EnumHumanCrafted.FineDress, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FINE_DRESS);
		mapping.put(EnumHumanCrafted.Habit, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HABIT);
		mapping.put(EnumHumanCrafted.Apron, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_APRON);
		mapping.put(EnumHumanCrafted.LeatherApron, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LEATHER_APRON);
		mapping.put(EnumHumanCrafted.Cloak, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CLOAK);
		mapping.put(EnumHumanCrafted.FurCloak, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FUR_CLOAK);
		mapping.put(EnumHumanCrafted.ChainMail, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CHAIN_MAIL);
		mapping.put(EnumHumanCrafted.LeatherShoe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LEATHER_SHOE);
		mapping.put(EnumHumanCrafted.Boot, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BOOT);
		mapping.put(EnumHumanCrafted.Sandal, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SANDAL);
		mapping.put(EnumHumanCrafted.WoolSock, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WOOL_SOCK);
		mapping.put(EnumHumanCrafted.LeatherGlove, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LEATHER_GLOVE);
		mapping.put(EnumHumanCrafted.Pitchfork, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PITCHFORK);
		mapping.put(EnumHumanCrafted.Flail, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FLAIL);
		mapping.put(EnumHumanCrafted.Sickle, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SICKLE);
		mapping.put(EnumHumanCrafted.Hoe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HOE);
		mapping.put(EnumHumanCrafted.Shovel, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHOVEL);
		mapping.put(EnumHumanCrafted.Pickaxe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PICKAXE);
		mapping.put(EnumHumanCrafted.Axe, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_AXE);
		mapping.put(EnumHumanCrafted.Hammer, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_HAMMER);
		mapping.put(EnumHumanCrafted.Saw, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SAW);
		mapping.put(EnumHumanCrafted.Tongs, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_TONGS);
		mapping.put(EnumHumanCrafted.Awl, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_AWL);
		mapping.put(EnumHumanCrafted.Knife, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_KNIFE);
		mapping.put(EnumHumanCrafted.Needle, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_NEEDLE);
		mapping.put(EnumHumanCrafted.Spindle, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SPINDLE);
		mapping.put(EnumHumanCrafted.Shears, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHEARS);
		mapping.put(EnumHumanCrafted.Crook, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CROOK);
		mapping.put(EnumHumanCrafted.FishingNet, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FISHING_NET);
		mapping.put(EnumHumanCrafted.Oar, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_OAR);
		mapping.put(EnumHumanCrafted.Whip, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WHIP);
		mapping.put(EnumHumanCrafted.Currycomb, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CURRYCOMB);
		mapping.put(EnumHumanCrafted.Rope, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_ROPE);
		mapping.put(EnumHumanCrafted.Scales, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SCALES);
		mapping.put(EnumHumanCrafted.Mortar, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_MORTAR);
		mapping.put(EnumHumanCrafted.Razor, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_RAZOR);
		mapping.put(EnumHumanCrafted.Key, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_KEY);
		mapping.put(EnumHumanCrafted.Lantern, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LANTERN);
		mapping.put(EnumHumanCrafted.Candle, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CANDLE);
		mapping.put(EnumHumanCrafted.SignalHorn, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SIGNAL_HORN);
		mapping.put(EnumHumanCrafted.Staff, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_STAFF);
		mapping.put(EnumHumanCrafted.Cudgel, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_CUDGEL);
		mapping.put(EnumHumanCrafted.Dagger, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DAGGER);
		mapping.put(EnumHumanCrafted.ShortSword, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHORT_SWORD);
		mapping.put(EnumHumanCrafted.LongSword, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LONG_SWORD);
		mapping.put(EnumHumanCrafted.Spear, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SPEAR);
		mapping.put(EnumHumanCrafted.Bow, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BOW);
		mapping.put(EnumHumanCrafted.Shield, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SHIELD);
		mapping.put(EnumHumanCrafted.Sack, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SACK);
		mapping.put(EnumHumanCrafted.Basket, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BASKET);
		mapping.put(EnumHumanCrafted.Jug, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_JUG);
		mapping.put(EnumHumanCrafted.Waterskin, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_WATERSKIN);
		mapping.put(EnumHumanCrafted.Bucket, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BUCKET);
		mapping.put(EnumHumanCrafted.Purse, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_PURSE);
		mapping.put(EnumHumanCrafted.Satchel, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SATCHEL);
		mapping.put(EnumHumanCrafted.Bowl, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BOWL);
		mapping.put(EnumHumanCrafted.Pot, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_POT);
		mapping.put(EnumHumanCrafted.Flask, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FLASK);
		mapping.put(EnumHumanCrafted.Coins, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_COINS);
		mapping.put(EnumHumanCrafted.Ring, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_RING);
		mapping.put(EnumHumanCrafted.Rosary, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_ROSARY);
		mapping.put(EnumHumanCrafted.Amulet, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_AMULET);
		mapping.put(EnumHumanCrafted.Relic, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_RELIC);
		mapping.put(EnumHumanCrafted.Book, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_BOOK);
		mapping.put(EnumHumanCrafted.Scroll, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_SCROLL);
		mapping.put(EnumHumanCrafted.Quill, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_QUILL);
		mapping.put(EnumHumanCrafted.Lute, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_LUTE);
		mapping.put(EnumHumanCrafted.Flute, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_FLUTE);
		mapping.put(EnumHumanCrafted.Dice, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_DICE);
		mapping.put(EnumHumanCrafted.JugglingBalls, GroupingOfSimulationObjects.LEXEMID_LOWERVALUE_JUGGLING_BALLS);
	}
}
