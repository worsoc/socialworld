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

import java.util.ArrayList;

import org.socialworld.conversation.Lexem;

public class KnowledgeFactList  {
	
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private ArrayList<KnowledgeFact> factSearchList;
	private ArrayList<KnowledgeSource> sourceSearchList;
	
	private int validItemCount = 0;

	
	
	public KnowledgeFactList() {
		factSearchList = new ArrayList<KnowledgeFact>();
	}
	
	protected int size() {
		return this.factSearchList.size();
	}

	protected int countValidItems() {
		return validItemCount;
	}

	protected boolean isItemValid(int index) {
		if (index < size()) {
			return factSearchList.get(index).isItemValid();
		}
		else {
			return false;
		}
			 
	}

	protected KnowledgeFact getFact(int index) {
		if ((index >= 0) & (index < size()) )
			return factSearchList.get(index);
		else
			return null;
	}

	protected KnowledgeFact getFactAsCopy(int index) {
		if ((index >= 0) & (index < size()) )
			return factSearchList.get(index).copy();
		else
			return null;
	}

	protected KnowledgeSource getSource(int index) {
		if ((index >= 0) & (index < size()) )
			return sourceSearchList.get(index);
		else
			return null;
	}

	protected KnowledgeSource getSourceAsCopy(int index) {
		if ((index >= 0) & (index < size()) )
			return new KnowledgeSource(sourceSearchList.get(index));
		else
			return null;
	}
	

	public KnowledgeFact find(Lexem  value) {
		KnowledgeFact fact;
		KnowledgeFact temp;
		
		Lexem lexems[];
		int indexLexem;
		int countLexems;
		
		int index = 0;
		int count;
		
		boolean found;
		
		count = factSearchList.size();
		
		// finding the element with the specified value wordID
		do
		{
			fact = factSearchList.get(index);
			index++;
			
			lexems = fact.getValues();
			countLexems = lexems.length;
			
			found = false;
			for (indexLexem = 0; indexLexem < countLexems; indexLexem++) {
				if (lexems[indexLexem] == value) found = true;
			}
			
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		if ( found && index > 0)  {
			
			// the previous list element
			temp = factSearchList.get(index - 1);
			
			factSearchList.set(index - 1, fact);
			factSearchList.set(index , temp);
			
		}
		
		if (!found) fact = null;
		
		return fact;
	}
	
	public void add (KnowledgeFact fact) {
		factSearchList.add(fact);
	}
	
	protected void add(KnowledgeFact fact, KnowledgeSource source) {
		int 	replacableIndex;
		
		if (fact.isItemValid()) {
			
			replacableIndex = getReplacableIndex();
			
			fact.resetAcccessCount();
			fact.incrementAccessCount();
			fact.incrementAccessCount();
			
	
			if (replacableIndex  == size()) {
				this.factSearchList.add( fact);
				this.sourceSearchList.add( source);
			}
			else {
				
				if (this.factSearchList.get(replacableIndex).isItemValid() == true) {
					this.validItemCount--;
				}
	
				this.factSearchList.set(replacableIndex, fact);
				this.sourceSearchList.set(replacableIndex, source);
			}
			
			this.validItemCount++;
			
		}
		
	}
	
	protected void remove(int index) {
		
		KnowledgeFact kfTmp;
		
		kfTmp = this.factSearchList.get(index);
		
		if (kfTmp.isItemValid()) {
			kfTmp.setValid( false);
			kfTmp.resetAcccessCount();
			this.validItemCount--;
		}
	}


	protected int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {

		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			fact = factSearchList.get(index);

			if (fact.isItemValid()) {
				if (   fact.getCriterion() == criterion) 	{
						
					result_tmp[count] = index;
					count++;
				}
			}
			
		}
		
		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		
		return result;
		
	}

	
	protected int[] findFactsForValue(Lexem value) {
		
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		Lexem lexems[];
		int indexLexem;
		int countLexems;

		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			fact = factSearchList.get(index);
			
			if (fact.isItemValid()) {
				
				lexems = fact.getValues();
				countLexems = lexems.length;
				
				for (indexLexem = 0; indexLexem < countLexems; indexLexem++) {
					
					if (lexems[indexLexem] == value)  	{
						
						result_tmp[count] = index;
						count++;
						break;
					}
					
				}

			}
			
		}
		
		result = new int[ count];
		for (index = 0; index < count; index++) {
			result[index] = result_tmp[index];
		}
		return result;
		
	}

	
	protected int getIndexForValidWithMaxAccessCount() {
		int index;
		int result;
		
		result = getIndexForFirstValid();
		if (result == -1) return -1;
	
		for (index = result + 1; index < size(); index++) {
			if (factSearchList.get(index).isItemValid() == true) {
				 if (factSearchList.get(result).getItemAccessCount() < factSearchList.get(index).getItemAccessCount()) 	result = index;
			}
		}
		return result;
	}

	
	
	protected int getIndexForValidWithMinAccessCount() {
		int index;
		int result;
	
		result = getIndexForFirstValid();
		if (result == -1) return -1;

		for (index = result + 1; index < size(); index++) {
			if (factSearchList.get(index).isItemValid() == true) {
			 if (factSearchList.get(result).getItemAccessCount() > factSearchList.get(index).getItemAccessCount()) 	result = index;
			}
		}
		return result;
	}

	
	protected int getIndexForFirstValid() {
		int index;
	
		for (index = 0; index < size(); index++) {
			if (factSearchList.get(index).isItemValid() == true)			  	return index;
		}
		return -1;
		
	}

	private int getReplacableIndex() {
		int count;
		int index;
		int i;
		
		if (size() == MAXIMUM_KNOWLEDGE_CAPACITY )
			count = size();		
		else
			count = size() + 1;
		
		index = 0;
		for (i = 0; i < count; i++) {
			if (factSearchList.get(i).isItemValid() == false) {
				index = i;
				return index;
			}
			else if (factSearchList.get(i).getItemAccessCount() < factSearchList.get(index).getItemAccessCount()) 	index = i;
			
		}
		
		return index ;
	}

	protected void combineWith(KnowledgeFactList kflB) {
		
		for (int j = 0; j < kflB.size(); j++) {
			if (kflB.isItemValid(j)) {
				for (int i = 0; i < size(); i++) {
					if (isItemValid(i))
						if (factSearchList.get(i).equals(kflB.getFact(j))) {
							break;
						}
						else {
							// combine fact from knowledge B to Knowledge A
							add(kflB.getFactAsCopy(j), kflB.getSourceAsCopy(j));
						}
				}
			}
		}
	}

	protected int compareTo(KnowledgeFactList kflB) {
		
		int countEqual = 0;
		
		for (int i = 0; i < size(); i++) {
			if (isItemValid(i)) {
				for (int j = 0; j < kflB.size(); j++) {
					if (kflB.isItemValid(j)) {
						if (factSearchList.get(i).equals(kflB.getFact(j)))  {
							countEqual++;
							break;
						}
					}
				}
			}
		}
		
		return countEqual;
		
	}

	public boolean equals(KnowledgeFactList b) {
		// we only check all facts , sources and itemIsValid
		
		int length;
		int index;
		
		KnowledgeFact tmpCopyKF;
		
		length = size();
		
		if (length != b.size())
			return false;
		
		for (index = 0; index < length; index++) {
			
			tmpCopyKF = b.getFactAsCopy(index);
			
			if (!factSearchList.get(index).equals(tmpCopyKF)) 
				return false;
			if (!sourceSearchList.get(index).equals(b.getSource(index))) 
				return false;
			if (factSearchList.get(index).isItemValid() != tmpCopyKF.isItemValid()) 
				return false;

		}
		
		return true;
	}

	
}
