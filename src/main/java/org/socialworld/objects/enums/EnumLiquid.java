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

public enum EnumLiquid {

	ignore("ignore"),
	Water("org.socialworld.objects.concrete.eatable.Water"),
	Beer("org.socialworld.objects.concrete.eatable.liquids.Beer"),
	Wine("org.socialworld.objects.concrete.eatable.liquids.Wine");
	
	private String className;

	private EnumLiquid(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumLiquid name
	 */
	public static EnumLiquid getName(String className) {
		for (EnumLiquid value : EnumLiquid.values())
			if (value.className == className)
				return value;
		return ignore;
	}}
