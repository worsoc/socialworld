/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.attributes.properties;

public class NutrientProperty extends SharesPropertyBase {

	public static float[] nothing = {1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	
	public static int NUTPROP_COUNT = 8;

	public static int NUTPROP_INDEX_NOTHING  		= 0;
	public static int NUTPROP_INDEX_FAT  			= 1;
	public static int NUTPROP_INDEX_PROTEIN  		= 2;
	public static int NUTPROP_INDEX_CARBOHYDRATES  	= 3;
	public static int NUTPROP_INDEX_WATER  			= 4;
	public static int NUTPROP_INDEX_ROUGHAGE  		= 5;
	public static int NUTPROP_INDEX_VITAMINS  		= 6;
	public static int NUTPROP_INDEX_MINERALS  		= 7;
	
	public NutrientProperty(float[] sharesNutrientMain) {
		super(sharesNutrientMain);
	}
	
	public NutrientProperty(NutrientProperty original) {
		super(original);
	}
	
	protected int getMainSharesComponentCount() { return NUTPROP_COUNT;}
	protected float[] getMainNothing() {return NutrientProperty.nothing; }

	public float getShareCarbohydrates() { return getSharesMain()[NUTPROP_INDEX_CARBOHYDRATES];}
	public float getShareFat() { return getSharesMain()[NUTPROP_INDEX_FAT];}
	public float getShareProtein() { return getSharesMain()[NUTPROP_INDEX_PROTEIN];}
	public float getShareMinerals() { return getSharesMain()[NUTPROP_INDEX_MINERALS];}
	public float getShareWater() { return getSharesMain()[NUTPROP_INDEX_WATER];}
	public float getShareVitamins() { return getSharesMain()[NUTPROP_INDEX_VITAMINS];}
	public float getShareRoughage() { return getSharesMain()[NUTPROP_INDEX_ROUGHAGE];}
	
	
}
