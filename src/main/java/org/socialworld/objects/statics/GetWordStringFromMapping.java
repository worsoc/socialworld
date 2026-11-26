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

import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.objects.enums.EnumSolid;
import org.socialworld.objects.enums.EnumAnimalPart;
import org.socialworld.objects.enums.EnumPlantPart;

public class GetWordStringFromMapping {

	
	public static String getForClassName(String className) {
		
		{
			EnumBaseSimObj key = EnumBaseSimObj.getName(className);
			if (key != EnumBaseSimObj.ignore) {
				return Mapping_Base2WordString.getInstance().get(key);
			}
		}


		{
			EnumBird key = EnumBird.getName(className);
			if (key != EnumBird.ignore) {
				return Mapping_Bird2WordString.getInstance().get(key);
			}
		}
		{
			EnumFood key = EnumFood.getName(className);
			if (key != EnumFood.ignore) {
				return Mapping_Food2WordString.getInstance().get(key);
			}
		}
		{
			EnumHumanCrafted key = EnumHumanCrafted.getName(className);
			if (key != EnumHumanCrafted.ignore) {
				return Mapping_HumanCrafted2WordString.getInstance().get(key);
			}
		}
		{
			EnumLiquid key = EnumLiquid.getName(className);
			if (key != EnumLiquid.ignore) {
				return Mapping_Liquid2WordString.getInstance().get(key);
			}
		}
		{
			EnumMammal key = EnumMammal.getName(className);
			if (key != EnumMammal.ignore) {
				return Mapping_Mammal2WordString.getInstance().get(key);
			}
		}
		{
			EnumSolid key = EnumSolid.getName(className);
			if (key != EnumSolid.ignore) {
				return Mapping_Solid2WordString.getInstance().get(key);
			}
		}
		{
			EnumAnimalPart key = EnumAnimalPart.getName(className);
			if (key != EnumAnimalPart.ignore) {
				return Mapping_AnimalPart2WordString.getInstance().get(key);
			}
		}
		{
			EnumPlantPart key = EnumPlantPart.getName(className);
			if (key != EnumPlantPart.ignore) {
				return Mapping_PlantPart2WordString.getInstance().get(key);
			}
		}
		return "";
	}	
}
