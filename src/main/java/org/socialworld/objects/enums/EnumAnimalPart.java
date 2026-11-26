/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
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

public enum EnumAnimalPart {

	ignore("ignore"),
	Hide("org.socialworld.objects.concrete.animalparts.Hide");
	
	private String className;

	private EnumAnimalPart(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumAnimalPart name
	 */
	public static EnumAnimalPart getName(String className) {
		for (EnumAnimalPart value : EnumAnimalPart.values())
			if (value.className == className)
				return value;
		return ignore;
	}
}
