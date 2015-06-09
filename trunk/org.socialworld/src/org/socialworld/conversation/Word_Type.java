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
package org.socialworld.conversation;


public enum Word_Type {
	noun(4), 
	infinitive(5), finitive(6), simple_past(7), past_participle(8),
	preposition(9),
	adverb(10),
	adjective(11),
	personal_pronoun(1), possessive_pronoun(2), demonstrative_pronoun(3),
	name(12), title(13),
	question(14);

	private int arrayIndex;

	private Word_Type(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the  name which belongs to the parameter index.
	 * 
	 * @param arrayIndex
	 *            word type index
	 * @return word type name
	 */
	public static Word_Type getName(int arrayIndex) {
		for (Word_Type type : Word_Type.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}
	
}
