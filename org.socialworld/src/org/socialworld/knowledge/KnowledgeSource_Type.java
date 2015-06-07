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
package org.socialworld.knowledge;

/**
 * The enumeration collects all knowledge source types.
 * @author Mathias Sikos (tyloesand) 
*/
public enum KnowledgeSource_Type {
	ownExperience(1), heardOf(2), readAbout(3);

	private int arrayIndex;

	private KnowledgeSource_Type(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the knowledge source type name which belongs to the parameter
	 *  index.
	 * 
	 * @param arrayIndex
	 *            type index
	 * @return source type name
	 */
	public static KnowledgeSource_Type getName(int arrayIndex) {
		for (KnowledgeSource_Type type : KnowledgeSource_Type.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}	

}
