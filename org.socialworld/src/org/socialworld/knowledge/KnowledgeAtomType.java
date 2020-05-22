/*
* Social World
* Copyright (C) 2016  Mathias Sikos
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


public enum KnowledgeAtomType {

	relationUnaer(21), relationBinaer(22), relationTrinaer(23), property(11), value(1);
	
	private int index;

	private KnowledgeAtomType(int index) {
		this.index = index;
	}
	
	/**
	 * The method returns the index of the KnowledgeAtomType.
	 * 
	 * @return type's index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * The method returns the type name which belongs to the parameter
	 * type index.
	 * 
	 * @param index
	 *            type index
	 * @return type name
	 */
	public static KnowledgeAtomType getName(int index) {
		for (KnowledgeAtomType type : KnowledgeAtomType.values())
			if (type.index == index)
				return type;
		return null;
	}	

}
