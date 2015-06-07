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
import org.socialworld.conversation.Word;

public class Knowledge {
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private Word subject;
	
	private KnowledgeFact facts[];
	private KnowledgeSource source[];
	
	private int itemAccessCount[];
	
	private boolean itemIsValid[];
	private int validItemCount = 0;
	
	private int itemCount = 0;
	
	public Knowledge() {
		init();
	}
	
	public Knowledge(Word subject, KnowledgeFact facts[], KnowledgeSource sources[]) {
		
		int index;
		int size;
		
		init();
		
		this.subject = subject;
		
		size = facts.length;

		if (size > MAXIMUM_KNOWLEDGE_CAPACITY) size = MAXIMUM_KNOWLEDGE_CAPACITY;
		for (index = 0; index < size; index++) {
			this.facts[index] = facts[index];
			this.source[index] = sources[index];
			this.itemAccessCount[index] = 2;
			this.itemIsValid[index] = true;
		}
		validItemCount = size;
		itemCount = size;

	}
	
	private void init() {
		this.facts = new KnowledgeFact[MAXIMUM_KNOWLEDGE_CAPACITY];
		this.source = new KnowledgeSource[MAXIMUM_KNOWLEDGE_CAPACITY];
		
		this.itemAccessCount = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		this.itemIsValid = new boolean[MAXIMUM_KNOWLEDGE_CAPACITY];
	}

	protected int count() {
		return itemCount;
	}
	
	protected int compareTo(Knowledge knowledgeB) {
		int countEqual = 0;
	
		if (subject == knowledgeB.getSubject()) {
			for (int i = 0; i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
				if (itemIsValid[i]) {
					for (int j = 0; j < MAXIMUM_KNOWLEDGE_CAPACITY; j++) {
						if (knowledgeB.isItemValid(j)) {
							if (facts[i].equals(knowledgeB.getFact(j)))  {
								countEqual ++;
								j = MAXIMUM_KNOWLEDGE_CAPACITY;
							}
						}
					}
				}
			}
		}
		return countEqual;
	}
	
	protected void combineWith(Knowledge knowledgeB) {
		
		for (int j = 0; j < MAXIMUM_KNOWLEDGE_CAPACITY; j++) {
			if (knowledgeB.isItemValid(j)) {
				for (int i = 0; i < MAXIMUM_KNOWLEDGE_CAPACITY; i++) {
					if (itemIsValid[i])
						if (facts[i].equals(knowledgeB.getFact(j)))
							// break
							i = MAXIMUM_KNOWLEDGE_CAPACITY;
						else 
							// combine fact from knowledge B to Knowledge A
							addItem(knowledgeB.getFactAsCopy(j), knowledgeB.getSourceAsCopy(j));
				}
			}
		}
	}
	
	protected boolean isValid() {
		return (subject != null);
	}
	
	protected Word getSubject() {
		return subject;
	}

	protected int countValidItems() {
		return validItemCount;
	}
	
	protected boolean isItemValid(int index) {
		return itemIsValid[index];
	}
	
	protected void setSubject(Word subject) {
		if (validItemCount == 0)		this.subject = subject;
	}
	
	protected KnowledgeFact getFact(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return facts[index];
		else
			return null;
	}

	protected KnowledgeFact getFactAsCopy(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return new KnowledgeFact(facts[index]);
		else
			return null;
	}

	protected KnowledgeSource getSource(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return source[index];
		else
			return null;
	}

	protected KnowledgeSource getSourceAsCopy(int index) {
		if ((index >= 0) & (index < MAXIMUM_KNOWLEDGE_CAPACITY) )
			return new KnowledgeSource(source[index]);
		else
			return null;
	}

	protected int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_CAPACITY; index++) {
			if (itemIsValid[index]) 
				if (   facts[index].getCriterion() == criterion) 	{
						
					result_tmp[count] = index;
					count++;
				}
		}
		
		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		return result;
	}

	
	protected int[] findFactsForValue(Word value) {
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		for (index=0;index < MAXIMUM_KNOWLEDGE_CAPACITY; index++) {
			if (itemIsValid[index]) 
				if (   facts[index].getValue().getWord() == value) 	{
						
					result_tmp[count] = index;
					count++;
				}
		}
		
		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		return result;
	}

	
	protected void removeItem(int index) {
		
		if (index == itemCount-1) itemCount--;
		
		if (this.itemIsValid[index]) {
			this.itemIsValid[index] = false;
			this.itemAccessCount[index] = 0;
			validItemCount--;
		}
	}
	
	protected void addItem(KnowledgeFact fact, KnowledgeSource source) {
		int 	replacableIndex;
		
		replacableIndex = getReplacableIndex();
		
		if (replacableIndex  == itemCount) itemCount++;
		
		this.facts[replacableIndex] = fact;
		this.source[replacableIndex] = source;
		
		this.itemAccessCount[replacableIndex] = 2;
		
		if (this.itemIsValid[replacableIndex] == false) {
			this.validItemCount++;
			this.itemIsValid[replacableIndex] = true;
		}
		
		
	}
	
	protected int getIndexForValidWithMaxAccessCount() {
		int index;
		int result;
		
		result = getIndexForFirstValid();
		if (result == -1) return -1;
	
		for (index = result + 1; index < itemCount; index++) {
			if (itemIsValid[index] == true) {
			 if (itemAccessCount[result] < itemAccessCount[index]) 	result = index;
			}
		}
		return result;
	}
	
	protected int getIndexForValidWithMinAccessCount() {
		int index;
		int result;
	
		result = getIndexForFirstValid();
		if (result == -1) return -1;

		for (index = result + 1; index < itemCount; index++) {
			if (itemIsValid[index] == true) {
			 if (itemAccessCount[result] > itemAccessCount[index]) 	result = index;
			}
		}
		return result;
	}

	protected int getIndexForFirstValid() {
		int index;
	
		for (index = 0; index < itemCount; index++) {
			if (itemIsValid[index] == true)			  	return index;
		}
		return -1;
		
	}
	
	private int getReplacableIndex() {
		int count;
		int index;
		int i;
		
		if (itemCount == MAXIMUM_KNOWLEDGE_CAPACITY )
			count = itemCount;		
		else
			count = itemCount + 1;
		
		index = 0;
		for (i=0; i < count; i++) {
			if (itemIsValid[i] == false) {
				index = i;
				return index;
			}
			else if (itemAccessCount[i] < itemAccessCount[index]) 	index = i;
			
		}
		
		return index ;
	}
	
	public boolean equals(Knowledge b) {
		// we only check all facts , sources and itemIsValid
		
		int length;
		int index;
		
		length = facts.length;
		
		if (length != b.facts.length)
			return false;
		
		for (index = 0; index < length; index++) {
			if (!facts[index].equals(b.facts[index])) 
				return false;
			if (!source[index].equals(b.source[index])) 
				return false;
			if (itemIsValid[index] != b.itemIsValid[index]) 
				return false;

		}
		
		return true;
	}
}
