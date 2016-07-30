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

import org.socialworld.conversation.Word;

public class AnswerProperties extends KnowledgeProperties {
	
	private int index;
	private int count;
	
	public boolean  resetIndex() {
		index = getIndexForFirstValid();
		if (index == -1) return false;
		
		count = count();
		return true;
	}
	
	public boolean next() {
		do	{
			index = index + 1;
			if (index == count) return false;
		}
		while (!isItemValid(index));
		return true;
	}
	
	public Word getAnswerSubject() {
		return getSubject();
	}
	
	public KnowledgeFact_Criterion getAnswerCriterion() {
		if (index < count)		return getFact(index).getCriterion();
		else return null;
	}
	
	public Word getAnswerValue() {
		if (index < count)	return getFact(index).getValue().getWord();
		else return null;
	}

	public KnowledgeSource getAnswerSource() {
		if (index < count)	return getSource(index);
		else return null;
	}

	public void sortBySource() {
		
	}
	
	public void reduceToFactWithMaxAccessCount() {
		int count;
		int index;
		int indexWithMaxAccessCount;
		
		count = count();
		indexWithMaxAccessCount = getIndexForValidWithMaxAccessCount();
		for (index = count - 1; index >= 0; index--) {
			if (index != indexWithMaxAccessCount) removeItem(index);
		}
		
	}
	
	public void reduceToFactWithMinAccessCount() {
		int count;
		int index;
		int indexWithMinAccessCount;
		
		count = count();
		indexWithMinAccessCount = getIndexForValidWithMinAccessCount();
		for (index = count - 1; index >= 0; index--) {
			if (index != indexWithMinAccessCount) removeItem(index);
		}
		
	}

}
