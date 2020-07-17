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

public class TasteProperty extends SharesPropertyBase {

	public static float[] nothing = {1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
	
	public static int TASTEPROP_COUNT = 6;
	
	public static int TASTEPROP_INDEX_NOTHING = 0;
	public static int TASTEPROP_INDEX_SWEET  	= 1;
	public static int TASTEPROP_INDEX_SALTY  	= 2;
	public static int TASTEPROP_INDEX_SOUR  	= 3;
	public static int TASTEPROP_INDEX_BITTER  = 4;
	public static int TASTEPROP_INDEX_UMAMI  	= 5;


	public TasteProperty(float[] sharesTaste) {
		super(sharesTaste);
	}

	public TasteProperty(TasteProperty original) {
		super(original);
	}

	protected int getMainSharesComponentCount() { return TASTEPROP_COUNT;}
	protected float[] getMainNothing() {return TasteProperty.nothing; }
	
	public float getShareSweet() { return getSharesMain()[TASTEPROP_INDEX_SWEET]; }
	public float getShareSalty() { return getSharesMain()[TASTEPROP_INDEX_SALTY]; }
	public float getShareSour() { return getSharesMain()[TASTEPROP_INDEX_SOUR]; }
	public float getShareBitter() { return getSharesMain()[TASTEPROP_INDEX_BITTER]; }
	public float getShareUmami() { return getSharesMain()[TASTEPROP_INDEX_UMAMI]; }
	
}
