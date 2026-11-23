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
package org.socialworld.objects.enums;

public enum EnumFood {

	ignore("ignore"),
	Fruit("org.socialworld.objects.concrete.eatable.Fruit"),
	Veggie("org.socialworld.objects.concrete.eatable.Veggie"),
	Meat("org.socialworld.objects.concrete.eatable.Meat"),
	Fish("org.socialworld.objects.concrete.eatable.Fish"),
	Bakery("org.socialworld.objects.concrete.eatable.Bakery"),
	Dairy("org.socialworld.objects.concrete.eatable.Dairy"),
	Sweets("org.socialworld.objects.concrete.eatable.Sweets"),
	Apple("org.socialworld.objects.concrete.eatable.fruits.Apple"),
	Banana("org.socialworld.objects.concrete.eatable.fruits.Banana"),

	/* concrete food */
	Berries("org.socialworld.objects.concrete.eatable.fruits.Berries"),
	Turnip("org.socialworld.objects.concrete.eatable.veggies.Turnip"),
	Onion("org.socialworld.objects.concrete.eatable.veggies.Onion"),
	Root("org.socialworld.objects.concrete.eatable.veggies.Root"),
	Sausage("org.socialworld.objects.concrete.eatable.meat.Sausage"),
	Ham("org.socialworld.objects.concrete.eatable.meat.Ham"),
	DriedFish("org.socialworld.objects.concrete.eatable.fish.DriedFish"),
	Bread("org.socialworld.objects.concrete.eatable.bakery.Bread"),
	Cake("org.socialworld.objects.concrete.eatable.bakery.Cake"),
	Cheese("org.socialworld.objects.concrete.eatable.dairy.Cheese"),
	Honey("org.socialworld.objects.concrete.eatable.sweets.Honey");
	
	private String className;

	private EnumFood(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumFood name
	 */
	public static EnumFood getName(String className) {
		for (EnumFood value : EnumFood.values())
			if (value.className == className)
				return value;
		return ignore;
	}
}
