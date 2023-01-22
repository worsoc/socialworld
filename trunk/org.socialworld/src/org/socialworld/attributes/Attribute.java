/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.attributes;

/**
 * The enumeration Attribute holds all attribute names and collects an index for every
 * attribute. So an attribute is addressable in arrays.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public enum Attribute {
	ignore(-1),
	mood(0), courage(1), morals(2), materialism(3), tiredness(4),
	curiosity(5), spirituality(6), hunger(7), power(8);

	/**
	 * The constant holds the informations how many attributes are simulated.
	 * The constant is used for iteration about all attributes and for creation
	 * of attribute arrays.
	 */
	public static final int NUMBER_OF_ATTRIBUTES = 9;
	
	private int arrayIndex;

	private Attribute(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the attribute.
	 * 
	 * @return attribute's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the attribute name which belongs to the parameter
	 * attribute index.
	 * 
	 * @param arrayIndex
	 *            attribute index
	 * @return attribute name
	 */
	public static Attribute getAttributeName(int arrayIndex) {
		for (Attribute attribute : Attribute.values())
			if (attribute.arrayIndex == arrayIndex)
				return attribute;
		return ignore;
	}
	
	public String toString() {
		
		switch (this.arrayIndex) {
		case 0: return "mood"; 
		case 1: return "courage";
		case 2: return "morals"; 
		case 3: return "materialism";
		case 4: return "tiredness";
		case 5: return "curiosity"; 
		case 6: return "spirituality";
		case 7: return "hunger"; 
		case 8: return "power";
		default: return "";
		}
		
	}
	
}
