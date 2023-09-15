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
package org.socialworld.objects.enums;

public enum EnumBaseSimObj {

	ignore("ignore"),
	NoSimulationObject("org.socialworld.objects.NoSimulationObject"),
	Animal("org.socialworld.objects.Animal"),
	God("org.socialworld.objects.God"),
	Item("org.socialworld.objects.Item"),
	Magic("org.socialworld.objects.Magic"),
	Liquid("org.socialworld.objects.concrete.eatable.Liquid"),
	Food("org.socialworld.objects.concrete.Food"),
	HumanCrafted("org.socialworld.objects.concrete.HumanCrafted"),
	Bird("org.socialworld.objects.concrete.animals.Bird"),
	Mammal("org.socialworld.objects.concrete.animals.Mammal"),
	Sea("org.socialworld.objects.concrete.gods.Sea"),
	Weather("org.socialworld.objects.concrete.gods.Weather"),
	Lightning("org.socialworld.objects.concrete.spells.Lightning");
	
	private String className;

	private EnumBaseSimObj(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumBaseSimObj name
	 */
	public static EnumBaseSimObj getName(String className) {
		for (EnumBaseSimObj value : EnumBaseSimObj.values())
			if (value.className == className)
				return value;
		return ignore;
	}
}
