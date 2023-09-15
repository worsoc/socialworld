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

import org.socialworld.objects.enums.EnumBaseSimObj;
import org.socialworld.objects.enums.EnumBird;
import org.socialworld.objects.enums.EnumFood;
import org.socialworld.objects.enums.EnumHumanCrafted;
import org.socialworld.objects.enums.EnumLiquid;
import org.socialworld.objects.enums.EnumMammal;
import org.socialworld.objects.enums.EnumTestSimObj;

public class GetEnumSimObjForClassName {

	public static Object getForClassName(String className) {
		
		{
			EnumBaseSimObj key = EnumBaseSimObj.getName(className);
			if (key != EnumBaseSimObj.ignore) {
				return key;
			}
		}
		{
			EnumBird key = EnumBird.getName(className);
			if (key != EnumBird.ignore) {
				return key;
			}
		}
		{
			EnumFood key = EnumFood.getName(className);
			if (key != EnumFood.ignore) {
				return key;
			}
		}
		{
			EnumHumanCrafted key = EnumHumanCrafted.getName(className);
			if (key != EnumHumanCrafted.ignore) {
				return key;
			}
		}
		{
			EnumLiquid key = EnumLiquid.getName(className);
			if (key != EnumLiquid.ignore) {
				return key;
			}
		}
		{
			EnumMammal key = EnumMammal.getName(className);
			if (key != EnumMammal.ignore) {
				return key;
			}
		}
		{
			EnumTestSimObj key = EnumTestSimObj.getName(className);
			if (key != EnumTestSimObj.ignore) {
				return key;
			}
		}
		return null;

	}
}
