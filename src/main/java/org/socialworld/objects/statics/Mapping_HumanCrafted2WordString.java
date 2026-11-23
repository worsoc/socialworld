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
	

		mapping.put(EnumHumanCrafted.Clothes, "clothes");
		mapping.put(EnumHumanCrafted.Tool, "tool");
		mapping.put(EnumHumanCrafted.Weapon, "weapon");
		mapping.put(EnumHumanCrafted.Container, "container");
		mapping.put(EnumHumanCrafted.Valuable, "valuable");
		mapping.put(EnumHumanCrafted.Devotional, "devotional");
		mapping.put(EnumHumanCrafted.Writing, "writing");
		mapping.put(EnumHumanCrafted.Pastime, "pastime");
		mapping.put(EnumHumanCrafted.Cap, "cap");
		mapping.put(EnumHumanCrafted.Shirt, "shirt");
		mapping.put(EnumHumanCrafted.Shoe, "shoe");
		mapping.put(EnumHumanCrafted.Sock, "sock");
		mapping.put(EnumHumanCrafted.Trousers, "trousers");
		mapping.put(EnumHumanCrafted.Dress, "dress");
		mapping.put(EnumHumanCrafted.Overgarment, "overgarment");
		mapping.put(EnumHumanCrafted.Glove, "glove");
		mapping.put(EnumHumanCrafted.StrawHat, "strawHat");
		mapping.put(EnumHumanCrafted.WoolCap, "woolCap");
		mapping.put(EnumHumanCrafted.Headscarf, "headscarf");
		mapping.put(EnumHumanCrafted.FeltHat, "feltHat");
		mapping.put(EnumHumanCrafted.Beret, "beret");
		mapping.put(EnumHumanCrafted.Hood, "hood");
		mapping.put(EnumHumanCrafted.Helmet, "helmet");
		mapping.put(EnumHumanCrafted.LinenShirt, "linenShirt");
		mapping.put(EnumHumanCrafted.WoolTunic, "woolTunic");
		mapping.put(EnumHumanCrafted.SilkShirt, "silkShirt");
		mapping.put(EnumHumanCrafted.Rags, "rags");
		mapping.put(EnumHumanCrafted.WorkTrousers, "workTrousers");
		mapping.put(EnumHumanCrafted.Hose, "hose");
		mapping.put(EnumHumanCrafted.LinenDress, "linenDress");
		mapping.put(EnumHumanCrafted.FineDress, "fineDress");
		mapping.put(EnumHumanCrafted.Habit, "habit");
		mapping.put(EnumHumanCrafted.Apron, "apron");
		mapping.put(EnumHumanCrafted.LeatherApron, "leatherApron");
		mapping.put(EnumHumanCrafted.Cloak, "cloak");
		mapping.put(EnumHumanCrafted.FurCloak, "furCloak");
		mapping.put(EnumHumanCrafted.ChainMail, "chainMail");
		mapping.put(EnumHumanCrafted.LeatherShoe, "leatherShoe");
		mapping.put(EnumHumanCrafted.Boot, "boot");
		mapping.put(EnumHumanCrafted.Sandal, "sandal");
		mapping.put(EnumHumanCrafted.WoolSock, "woolSock");
		mapping.put(EnumHumanCrafted.LeatherGlove, "leatherGlove");
		mapping.put(EnumHumanCrafted.Pitchfork, "pitchfork");
		mapping.put(EnumHumanCrafted.Flail, "flail");
		mapping.put(EnumHumanCrafted.Sickle, "sickle");
		mapping.put(EnumHumanCrafted.Hoe, "hoe");
		mapping.put(EnumHumanCrafted.Shovel, "shovel");
		mapping.put(EnumHumanCrafted.Pickaxe, "pickaxe");
		mapping.put(EnumHumanCrafted.Axe, "axe");
		mapping.put(EnumHumanCrafted.Hammer, "hammer");
		mapping.put(EnumHumanCrafted.Saw, "saw");
		mapping.put(EnumHumanCrafted.Tongs, "tongs");
		mapping.put(EnumHumanCrafted.Awl, "awl");
		mapping.put(EnumHumanCrafted.Knife, "knife");
		mapping.put(EnumHumanCrafted.Needle, "needle");
		mapping.put(EnumHumanCrafted.Spindle, "spindle");
		mapping.put(EnumHumanCrafted.Shears, "shears");
		mapping.put(EnumHumanCrafted.Crook, "crook");
		mapping.put(EnumHumanCrafted.FishingNet, "fishingNet");
		mapping.put(EnumHumanCrafted.Oar, "oar");
		mapping.put(EnumHumanCrafted.Whip, "whip");
		mapping.put(EnumHumanCrafted.Currycomb, "currycomb");
		mapping.put(EnumHumanCrafted.Rope, "rope");
		mapping.put(EnumHumanCrafted.Scales, "scales");
		mapping.put(EnumHumanCrafted.Mortar, "mortar");
		mapping.put(EnumHumanCrafted.Razor, "razor");
		mapping.put(EnumHumanCrafted.Key, "key");
		mapping.put(EnumHumanCrafted.Lantern, "lantern");
		mapping.put(EnumHumanCrafted.Candle, "candle");
		mapping.put(EnumHumanCrafted.SignalHorn, "signalHorn");
		mapping.put(EnumHumanCrafted.Staff, "staff");
		mapping.put(EnumHumanCrafted.Cudgel, "cudgel");
		mapping.put(EnumHumanCrafted.Dagger, "dagger");
		mapping.put(EnumHumanCrafted.ShortSword, "shortSword");
		mapping.put(EnumHumanCrafted.LongSword, "longSword");
		mapping.put(EnumHumanCrafted.Spear, "spear");
		mapping.put(EnumHumanCrafted.Bow, "bow");
		mapping.put(EnumHumanCrafted.Shield, "shield");
		mapping.put(EnumHumanCrafted.Sack, "sack");
		mapping.put(EnumHumanCrafted.Basket, "basket");
		mapping.put(EnumHumanCrafted.Jug, "jug");
		mapping.put(EnumHumanCrafted.Waterskin, "waterskin");
		mapping.put(EnumHumanCrafted.Bucket, "bucket");
		mapping.put(EnumHumanCrafted.Purse, "purse");
		mapping.put(EnumHumanCrafted.Satchel, "satchel");
		mapping.put(EnumHumanCrafted.Bowl, "bowl");
		mapping.put(EnumHumanCrafted.Pot, "pot");
		mapping.put(EnumHumanCrafted.Flask, "flask");
		mapping.put(EnumHumanCrafted.Coins, "coins");
		mapping.put(EnumHumanCrafted.Ring, "ring");
		mapping.put(EnumHumanCrafted.Rosary, "rosary");
		mapping.put(EnumHumanCrafted.Amulet, "amulet");
		mapping.put(EnumHumanCrafted.Relic, "relic");
		mapping.put(EnumHumanCrafted.Book, "book");
		mapping.put(EnumHumanCrafted.Scroll, "scroll");
		mapping.put(EnumHumanCrafted.Quill, "quill");
		mapping.put(EnumHumanCrafted.Lute, "lute");
		mapping.put(EnumHumanCrafted.Flute, "flute");
		mapping.put(EnumHumanCrafted.Dice, "dice");
		mapping.put(EnumHumanCrafted.JugglingBalls, "jugglingBalls");

	}
}
