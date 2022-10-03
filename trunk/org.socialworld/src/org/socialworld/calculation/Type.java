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
package org.socialworld.calculation;

public enum Type {
	nothing(0),
	integer(1), longinteger(2), floatingpoint(3),
	string(11),
	bool(21),
	vector(31), 
	time(41),
	actionType(51), actionMode(52),
	attributeArray(61),
	event(71),
	action(81),
	simulationObject(91),
	simPropName(101), simObjProp(102), eventProp(103), actionProp(104),
	sentenceElement(111), answer(112),
	knowledgeElement(121), knowledgeSource(122), knowledgeAtom(123),
	valueList(8888), 
	object(9999);

	private int index;

	private Type(int index) {
		this.index = index;
	}
	
	/**
	 * The method returns the index of the type.
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
	public static Type getName(int index) {
		for (Type type : Type.values())
			if (type.index == index)
				return type;
		return null;
	}	
	
	public boolean isAllowedAsKnowledgeValue() {
		
		switch (this){
			case integer: return true;
			case longinteger: return true;
			case floatingpoint: return true;
			case string: return true;
			case vector: return true;
			case bool: return true;
			case actionType: return true;
			case actionMode: return true;
			case time: return true;
			case simulationObject: return true;
			
			default:return false;
		}
	}
	
	public String getIndexWithSWTPraefix() {
			return "SWType_" + getIndex();
	
		
	}
	
	public static Type getTypeForSWTPraefixedName(String preafixdName) {
		
		if (preafixdName.startsWith("SWType_")) {
			int index = (int) Integer.parseInt(preafixdName.substring(7)); 
			return getName(index);
		}
		else {
			return Type.nothing;
		}
	}

}
