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
import java.util.List;

import org.socialworld.conversation.Lexem;

public class KnowledgeItemList  {
	
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private ArrayList<KnowledgeItem> itemSearchList;
	
	private int validItemCount = 0;
	


	
	
	public KnowledgeItemList() {
		
		itemSearchList = new ArrayList<KnowledgeItem>();
		
		// add invalid KnowledgeValue as first dummy element (ensure there is always an element in the list)
		
		KnowledgeItem dummy = new KnowledgeValue();
		itemSearchList.add(dummy);
		
	}
	
	protected int size() {
		return this.itemSearchList.size();
	}

	protected int countValidItems() {
		return validItemCount;
	}

	protected boolean isItemValid(int index) {
		if (index < size()) {
			return itemSearchList.get(index).isItemValid();
		}
		else {
			return false;
		}
			 
	}

	protected KnowledgeItem getAtom(int index) {
		if ((index >= 0) & (index < size()) )
			return itemSearchList.get(index);
		else
			return null;
	}

	protected KnowledgeItem getAtomAsCopy(int index) {
		if ((index >= 0) & (index < size()) )
			return itemSearchList.get(index).copy();
		else
			return null;
	}


	

	public KnowledgeFact find(Lexem  value) {
		KnowledgeItem item;
		KnowledgeFact fact = null;
		
		List<KnowledgeFactAtom> atoms;
		
		int index = 0;
		int count;
		
		boolean found = false;
		
		count = itemSearchList.size();
		
		// finding the element with the specified value wordID
		do
		{
			item = itemSearchList.get(index);
			index++;
			
			if (item.isItemValid()) {
				
				if (item instanceof KnowledgeFact ) {
	
					fact = (KnowledgeFact) item;
					
					atoms = fact.getAtoms();
					
					found = false;
					for (KnowledgeFactAtom atom : atoms) {
						if ((atom.getType() == KnowledgeFactAtom_Type.lexem) 
								&& (atom.getLexem() == value)) 
							found = true;
					}
				
				}
			
			}

			
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		// the knowledge fact is changed to any knowledge item
		if ( found && index > 0)  {
			
			// the previous list element
			item = itemSearchList.get(index - 1);
					
			itemSearchList.set(index - 1, fact);
			itemSearchList.set(index , item);
			
		}
		
		if (!found) fact = null;
		
		return fact;
	}
	
	private void add (KnowledgeItem atom) {
		itemSearchList.add(atom);
	}
	
	protected void add(KnowledgeItem atom, KnowledgeSource source) {
		int 	replacableIndex;
		
		if (atom.isItemValid()) {
			
			replacableIndex = getReplacableIndex();
			
			atom.resetAccessCount();
			atom.incrementAccessCount();
			atom.incrementAccessCount();
			
			atom.setSource(source);
			
			if (replacableIndex  == size()) {
				this.itemSearchList.add( atom);
			}
			else {
				
				if (this.itemSearchList.get(replacableIndex).isItemValid() == true) {
					this.validItemCount--;
				}
	
				this.itemSearchList.set(replacableIndex, atom);
			}
			
			this.validItemCount++;
			
		}
		
	}
	
	protected void remove(int index) {
		
		KnowledgeItem kaTmp;
		
		kaTmp = this.itemSearchList.get(index);
		
		if (kaTmp.isItemValid()) {
			kaTmp.setValid( false);
			kaTmp.resetAccessCount();
			this.validItemCount--;
		}
	}


	protected int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {

		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		KnowledgeItem atom;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			atom = itemSearchList.get(index);
			if (atom.isItemValid()) {
				
				if (atom instanceof KnowledgeFact ) {
				
				fact = (KnowledgeFact) atom;
	
					if (   fact.getCriterion() == criterion) 	{
							
						result_tmp[count] = index;
						count++;
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

	
	protected int[] findFactsForValue(Lexem value) {
		
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		List<KnowledgeFactAtom> atoms;
		int indexLexem;
		int countLexems;

		KnowledgeItem item;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			item = itemSearchList.get(index);
			if (item.isItemValid()) { 
				
				if (item instanceof KnowledgeFact ) {
			
					fact = (KnowledgeFact) item;
					
					
					atoms = fact.getAtoms();
					
					for (KnowledgeFactAtom atom : atoms) {
						if ((atom.getType() == KnowledgeFactAtom_Type.lexem) 
								&& (atom.getLexem() == value)) 
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
			if (itemSearchList.get(index).isItemValid() == true) {
				 if (itemSearchList.get(result).getItemAccessCount() < itemSearchList.get(index).getItemAccessCount()) 	result = index;
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
			if (itemSearchList.get(index).isItemValid() == true) {
			 if (itemSearchList.get(result).getItemAccessCount() > itemSearchList.get(index).getItemAccessCount()) 	result = index;
			}
		}
		return result;
	}

	
	protected int getIndexForFirstValid() {
		int index;
	
		for (index = 0; index < size(); index++) {
			if (itemSearchList.get(index).isItemValid() == true)			  	return index;
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
			if (itemSearchList.get(i).isItemValid() == false) {
				index = i;
				return index;
			}
			else if (itemSearchList.get(i).getItemAccessCount() < itemSearchList.get(index).getItemAccessCount()) 	index = i;
			
		}
		
		return index ;
	}

	protected void combineWith(KnowledgeItemList kalB) {
		
		for (int j = 0; j < kalB.size(); j++) {
			if (kalB.isItemValid(j)) {
				for (int i = 0; i < size(); i++) {
					if (isItemValid(i))
						if (itemSearchList.get(i).equals(kalB.getAtom(j))) {
							break;
						}
						else {
							// combine atom from knowledge B to Knowledge A
							add(kalB.getAtomAsCopy(j));
						}
				}
			}
		}
	}

	protected int compareTo(KnowledgeItemList kalB) {
		
		int countEqual = 0;
		
		for (int i = 0; i < size(); i++) {
			if (isItemValid(i)) {
				for (int j = 0; j < kalB.size(); j++) {
					if (kalB.isItemValid(j)) {
						if (itemSearchList.get(i).equals(kalB.getAtom(j)))  {
							countEqual++;
							break;
						}
					}
				}
			}
		}
		
		return countEqual;
		
	}

	public boolean equals(KnowledgeItemList b) {
		// we only check all atoms , sources and itemIsValid
		
		int length;
		int index;
		
		KnowledgeItem tmpCopyKF;
		
		length = size();
		
		if (length != b.size())
			return false;
		
		for (index = 0; index < length; index++) {
			
			tmpCopyKF = b.getAtomAsCopy(index);
			
			if (!itemSearchList.get(index).equals(tmpCopyKF)) 
				return false;
			// TODO compare source too, in implementation KnowledgeItem.equals()
			if (!itemSearchList.get(index).getSource().equals(b.getAtom(index).getSource())) 
				return false;
			if (itemSearchList.get(index).isItemValid() != tmpCopyKF.isItemValid()) 
				return false;

		}
		
		return true;
	}

	
}
