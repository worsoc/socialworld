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

import org.socialworld.conversation.Tense;

public enum KnowledgeFact_Criterion {
	nothing(-1),
	colour(0), material(1), nutrient(2), taste(3),
	relationUnaer(1000),
	relationBinaer(2000),
	relationTrinaer(3000);

	/**
	 * The constants hold the informations how many knowledge fact criteria exist.
	 * The constants are used for iteration about all criteria 
	 */
	public static final int MIN_INDEX_PROPERTY = 0;
	public static final int MAX_INDEX_PROPERTY = 1;
	public static final int MIN_INDEX_RELATION_UNAER = 1000;
	public static final int MAX_INDEX_RELATION_UNAER = 1000;
	public static final int MIN_INDEX_RELATION_BINAER = 2000;
	public static final int MAX_INDEX_RELATION_BINAER = 2000;
	public static final int MIN_INDEX_RELATION_TRINAER = 3000;
	public static final int MAX_INDEX_RELATION_TRINAER = 3000;
	
	public static final int NUMBER_OF_KNOWLEDGE_FACT_CRITERION = 2;

	/**
	 * The constant holds the offset that must be added to criterion's index for getting the word ID (word search tree ID)
	 */
	private final int CRITERION_WORD_ID_OFFSET = 0;

	private int arrayIndex;

	private KnowledgeFact_Criterion(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the index of the knowledge fact criterion.
	 * 
	 * @return criterion's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the word ID of the knowledge fact criterion.
	 * 
	 * @return criterion's word ID
	 */
	public int getWordID() {
		return arrayIndex + CRITERION_WORD_ID_OFFSET;
	}
	
	public boolean isRelation() {
		if ((this.arrayIndex >= MIN_INDEX_RELATION_UNAER) && 
				(this.arrayIndex <= MAX_INDEX_RELATION_TRINAER))
			return true;
		else
			return false;
	}

	/**
	 * The method returns the tense of the knowledge fact criterion (if it is a relation).
	 * 
	 * @return relation's tense
	 */
	public Tense getTense() {
		
		if (this.isRelation()) {
			switch (lastDigit()) {
			case 0:
				return Tense.simple_present_active;
			case 1:
				return Tense.simple_past_active;
			case 2:
				return Tense.will_future_active;
			default:	
				return Tense.simple_present_active;
			}
		}
		else
			return Tense.ignore;
	}
	
	
	/**
	 * The method returns the knowledge fact criterion name which belongs to the parameter
	 * criterion index.
	 * 
	 * @param arrayIndex
	 *            criterion index
	 * @return criterion name
	 */
	public static KnowledgeFact_Criterion getName(int arrayIndex) {
		for (KnowledgeFact_Criterion criterion : KnowledgeFact_Criterion.values())
			if (criterion.arrayIndex == arrayIndex)
				return criterion;
		return null;
	}
	
	public boolean equals(KnowledgeFact_Criterion criterion) {
		return this.arrayIndex == criterion.arrayIndex;
	}

	private int lastDigit() {
		return this.arrayIndex % 10;
	}
}
