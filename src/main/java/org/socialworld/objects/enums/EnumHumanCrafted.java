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
package org.socialworld.objects.enums;

public enum EnumHumanCrafted {

	ignore("ignore"),
	Clothes("org.socialworld.objects.concrete.clothes.Clothes"),
	Tool("org.socialworld.objects.concrete.tools.Tool"),
	Weapon("org.socialworld.objects.concrete.weapons.Weapon"),
	Container("org.socialworld.objects.concrete.containers.Container"),
	Valuable("org.socialworld.objects.concrete.valuables.Valuable"),
	Devotional("org.socialworld.objects.concrete.devotional.Devotional"),
	Writing("org.socialworld.objects.concrete.writing.Writing"),
	Pastime("org.socialworld.objects.concrete.pastime.Pastime"),
	Cap("org.socialworld.objects.concrete.clothes.Cap"),
	Shirt("org.socialworld.objects.concrete.clothes.Shirt"),
	Shoe("org.socialworld.objects.concrete.clothes.Shoe"),
	Sock("org.socialworld.objects.concrete.clothes.Sock"),
	Trousers("org.socialworld.objects.concrete.clothes.Trousers"),
	Dress("org.socialworld.objects.concrete.clothes.Dress"),
	Overgarment("org.socialworld.objects.concrete.clothes.Overgarment"),
	Glove("org.socialworld.objects.concrete.clothes.Glove"),

	/* concrete clothes */
	Hat("org.socialworld.objects.concrete.clothes.caps.Hat"),
	Headscarf("org.socialworld.objects.concrete.clothes.caps.Headscarf"),
	Beret("org.socialworld.objects.concrete.clothes.caps.Beret"),
	Hood("org.socialworld.objects.concrete.clothes.caps.Hood"),
	Helmet("org.socialworld.objects.concrete.clothes.caps.Helmet"),
	Tunic("org.socialworld.objects.concrete.clothes.shirts.Tunic"),
	Rags("org.socialworld.objects.concrete.clothes.shirts.Rags"),
	WorkTrousers("org.socialworld.objects.concrete.clothes.trousers.WorkTrousers"),
	Hose("org.socialworld.objects.concrete.clothes.trousers.Hose"),
	Habit("org.socialworld.objects.concrete.clothes.dresses.Habit"),
	Apron("org.socialworld.objects.concrete.clothes.overgarments.Apron"),
	Cloak("org.socialworld.objects.concrete.clothes.overgarments.Cloak"),
	ChainMail("org.socialworld.objects.concrete.clothes.overgarments.ChainMail"),
	Boot("org.socialworld.objects.concrete.clothes.shoes.Boot"),
	Sandal("org.socialworld.objects.concrete.clothes.shoes.Sandal"),
	// new clothes of step 4 (item class or composition, 2026-11-25)
	Coif("org.socialworld.objects.concrete.clothes.caps.Coif"),
	Crown("org.socialworld.objects.concrete.clothes.caps.Crown"),
	Wreath("org.socialworld.objects.concrete.clothes.caps.Wreath"),
	Doublet("org.socialworld.objects.concrete.clothes.shirts.Doublet"),
	Gambeson("org.socialworld.objects.concrete.clothes.overgarments.Gambeson"),
	Tabard("org.socialworld.objects.concrete.clothes.overgarments.Tabard"),
	Gauntlet("org.socialworld.objects.concrete.clothes.gloves.Gauntlet"),
	Clogs("org.socialworld.objects.concrete.clothes.shoes.Clogs"),
	Neckerchief("org.socialworld.objects.concrete.clothes.Neckerchief"),

	/* tools */
	Pitchfork("org.socialworld.objects.concrete.tools.Pitchfork"),
	Flail("org.socialworld.objects.concrete.tools.Flail"),
	Sickle("org.socialworld.objects.concrete.tools.Sickle"),
	Hoe("org.socialworld.objects.concrete.tools.Hoe"),
	Shovel("org.socialworld.objects.concrete.tools.Shovel"),
	Pickaxe("org.socialworld.objects.concrete.tools.Pickaxe"),
	Axe("org.socialworld.objects.concrete.tools.Axe"),
	Hammer("org.socialworld.objects.concrete.tools.Hammer"),
	Saw("org.socialworld.objects.concrete.tools.Saw"),
	Tongs("org.socialworld.objects.concrete.tools.Tongs"),
	Awl("org.socialworld.objects.concrete.tools.Awl"),
	Knife("org.socialworld.objects.concrete.tools.Knife"),
	Needle("org.socialworld.objects.concrete.tools.Needle"),
	Spindle("org.socialworld.objects.concrete.tools.Spindle"),
	Shears("org.socialworld.objects.concrete.tools.Shears"),
	Crook("org.socialworld.objects.concrete.tools.Crook"),
	FishingNet("org.socialworld.objects.concrete.tools.FishingNet"),
	Oar("org.socialworld.objects.concrete.tools.Oar"),
	Whip("org.socialworld.objects.concrete.tools.Whip"),
	Currycomb("org.socialworld.objects.concrete.tools.Currycomb"),
	Rope("org.socialworld.objects.concrete.tools.Rope"),
	Scales("org.socialworld.objects.concrete.tools.Scales"),
	Mortar("org.socialworld.objects.concrete.tools.Mortar"),
	Razor("org.socialworld.objects.concrete.tools.Razor"),
	Key("org.socialworld.objects.concrete.tools.Key"),
	Lantern("org.socialworld.objects.concrete.tools.Lantern"),
	Candle("org.socialworld.objects.concrete.tools.Candle"),
	SignalHorn("org.socialworld.objects.concrete.tools.SignalHorn"),
	Staff("org.socialworld.objects.concrete.tools.Staff"),

	/* weapons */
	Cudgel("org.socialworld.objects.concrete.weapons.Cudgel"),
	Dagger("org.socialworld.objects.concrete.weapons.Dagger"),
	ShortSword("org.socialworld.objects.concrete.weapons.ShortSword"),
	LongSword("org.socialworld.objects.concrete.weapons.LongSword"),
	Spear("org.socialworld.objects.concrete.weapons.Spear"),
	Bow("org.socialworld.objects.concrete.weapons.Bow"),
	Shield("org.socialworld.objects.concrete.weapons.Shield"),

	/* containers */
	Sack("org.socialworld.objects.concrete.containers.Sack"),
	Basket("org.socialworld.objects.concrete.containers.Basket"),
	Jug("org.socialworld.objects.concrete.containers.Jug"),
	Waterskin("org.socialworld.objects.concrete.containers.Waterskin"),
	Bucket("org.socialworld.objects.concrete.containers.Bucket"),
	Purse("org.socialworld.objects.concrete.containers.Purse"),
	Satchel("org.socialworld.objects.concrete.containers.Satchel"),
	Bowl("org.socialworld.objects.concrete.containers.Bowl"),
	Pot("org.socialworld.objects.concrete.containers.Pot"),
	Flask("org.socialworld.objects.concrete.containers.Flask"),
	Quiver("org.socialworld.objects.concrete.containers.Quiver"),

	/* valuables */
	Coins("org.socialworld.objects.concrete.valuables.Coins"),
	Ring("org.socialworld.objects.concrete.valuables.Ring"),
	SignetRing("org.socialworld.objects.concrete.valuables.SignetRing"),

	/* devotional */
	Rosary("org.socialworld.objects.concrete.devotional.Rosary"),
	Amulet("org.socialworld.objects.concrete.devotional.Amulet"),
	Relic("org.socialworld.objects.concrete.devotional.Relic"),

	/* writing */
	Book("org.socialworld.objects.concrete.writing.Book"),
	Scroll("org.socialworld.objects.concrete.writing.Scroll"),
	Quill("org.socialworld.objects.concrete.writing.Quill"),

	/* pastime */
	Lute("org.socialworld.objects.concrete.pastime.Lute"),
	Flute("org.socialworld.objects.concrete.pastime.Flute"),
	Dice("org.socialworld.objects.concrete.pastime.Dice"),
	JugglingBalls("org.socialworld.objects.concrete.pastime.JugglingBalls");

	private String className;

	private EnumHumanCrafted(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumHumanCrafted name
	 */
	public static EnumHumanCrafted getName(String className) {
		for (EnumHumanCrafted value : EnumHumanCrafted.values())
			if (value.className == className)
				return value;
		return ignore;
	}
}
