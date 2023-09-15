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
package org.socialworld.objects.statics;

import org.socialworld.Constants;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;

public class GetLexemIDLowerPartFromMapping {

	
	public static int getForClassName(String className) {
		
		{
			EnumBird key = EnumBird.getName(className);
			if (key != EnumBird.ignore) {
				return Mapping_Bird2LexemIDLowerPart.getInstance().get(key);
			}
		}
		{
			EnumFood key = EnumFood.getName(className);
			if (key != EnumFood.ignore) {
				return Mapping_Food2LexemIDLowerPart.getInstance().get(key);
			}
		}
		{
			EnumHumanCrafted key = EnumHumanCrafted.getName(className);
			if (key != EnumHumanCrafted.ignore) {
				return Mapping_HumanCrafted2LexemIDLowerPart.getInstance().get(key);
			}
		}
		{
			EnumLiquid key = EnumLiquid.getName(className);
			if (key != EnumLiquid.ignore) {
				return Mapping_Liquid2LexemIDLowerPart.getInstance().get(key);
			}
		}
		{
			EnumMammal key = EnumMammal.getName(className);
			if (key != EnumMammal.ignore) {
				return Mapping_Mammal2LexemIDLowerPart.getInstance().get(key);
			}
		}
		return Constants.MAPPING_NO_ENTRY_FOR_KEY;
	}
}
