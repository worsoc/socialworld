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


public enum Talk_SentenceType {
	myPlannedSentence(1), myPlannedQuestion(2), myPlannedAnswer(3), partnersSentence(4), partnersQuestion(5),
	partnersInstruction(6), partnersUnknownType(7);

	private int arrayIndex;

	private Talk_SentenceType(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the sentence type name which belongs to the parameter
	 * criterion index.
	 * 
	 * @param arrayIndex
	 *            type index
	 * @return sentence type name
	 */
	public static Talk_SentenceType getName(int arrayIndex) {
		for (Talk_SentenceType type : Talk_SentenceType.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}	

}
