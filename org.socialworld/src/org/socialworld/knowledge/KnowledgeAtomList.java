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

public class KnowledgeAtomList  {
	
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private ArrayList<KnowledgeAtom> atomSearchList;
	private ArrayList<KnowledgeSource> sourceSearchList;
	
	private int validItemCount = 0;

	
	
	public KnowledgeAtomList() {
		atomSearchList = new ArrayList<KnowledgeAtom>();
	}
	
	protected int size() {
		return this.atomSearchList.size();
	}

	protected int countValidItems() {
		return validItemCount;
	}

	protected boolean isItemValid(int index) {
		if (index < size()) {
			return atomSearchList.get(index).isItemValid();
		}
		else {
			return false;
		}
			 
	}

	protected KnowledgeAtom getAtom(int index) {
		if ((index >= 0) & (index < size()) )
			return atomSearchList.get(index);
		else
			return null;
	}

	protected KnowledgeAtom getAtomAsCopy(int index) {
		if ((index >= 0) & (index < size()) )
			return atomSearchList.get(index).copy();
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
		KnowledgeAtom atom;
		KnowledgeFact fact = null;
		
		Lexem lexems[];
		int indexLexem;
		int countLexems;
		
		int index = 0;
		int count;
		
		boolean found = false;
		
		count = atomSearchList.size();
		
		// finding the element with the specified value wordID
		do
		{
			atom = atomSearchList.get(index);
			index++;
			
			if (atom.isItemValid()) {
				
				if (atom instanceof KnowledgeFact ) {
	
					fact = (KnowledgeFact) atom;
					
					lexems = fact.getValues();
					countLexems = lexems.length;
					
					found = false;
					for (indexLexem = 0; indexLexem < countLexems; indexLexem++) {
						if (lexems[indexLexem] == value) found = true;
					}
				
				}
			
			}

			
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		// the knowledge fact is changed to any knowledge atom
		if ( found && index > 0)  {
			
			// the previous list element
			atom = atomSearchList.get(index - 1);
					
			atomSearchList.set(index - 1, fact);
			atomSearchList.set(index , atom);
			
		}
		
		if (!found) fact = null;
		
		return fact;
	}
	
	public void add (KnowledgeAtom atom) {
		atomSearchList.add(atom);
	}
	
	protected void add(KnowledgeAtom atom, KnowledgeSource source) {
		int 	replacableIndex;
		
		if (atom.isItemValid()) {
			
			replacableIndex = getReplacableIndex();
			
			atom.resetAccessCount();
			atom.incrementAccessCount();
			atom.incrementAccessCount();
			
	
			if (replacableIndex  == size()) {
				this.atomSearchList.add( atom);
				this.sourceSearchList.add( source);
			}
			else {
				
				if (this.atomSearchList.get(replacableIndex).isItemValid() == true) {
					this.validItemCount--;
				}
	
				this.atomSearchList.set(replacableIndex, atom);
				this.sourceSearchList.set(replacableIndex, source);
			}
			
			this.validItemCount++;
			
		}
		
	}
	
	protected void remove(int index) {
		
		KnowledgeAtom kaTmp;
		
		kaTmp = this.atomSearchList.get(index);
		
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
		
		KnowledgeAtom atom;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			atom = atomSearchList.get(index);
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
		
		Lexem lexems[];
		int indexLexem;
		int countLexems;

		KnowledgeAtom atom;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			atom = atomSearchList.get(index);
			if (atom.isItemValid()) { 
				
				if (atom instanceof KnowledgeFact ) {
			
					fact = (KnowledgeFact) atom;
					
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
			if (atomSearchList.get(index).isItemValid() == true) {
				 if (atomSearchList.get(result).getItemAccessCount() < atomSearchList.get(index).getItemAccessCount()) 	result = index;
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
			if (atomSearchList.get(index).isItemValid() == true) {
			 if (atomSearchList.get(result).getItemAccessCount() > atomSearchList.get(index).getItemAccessCount()) 	result = index;
			}
		}
		return result;
	}

	
	protected int getIndexForFirstValid() {
		int index;
	
		for (index = 0; index < size(); index++) {
			if (atomSearchList.get(index).isItemValid() == true)			  	return index;
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
			if (atomSearchList.get(i).isItemValid() == false) {
				index = i;
				return index;
			}
			else if (atomSearchList.get(i).getItemAccessCount() < atomSearchList.get(index).getItemAccessCount()) 	index = i;
			
		}
		
		return index ;
	}

	protected void combineWith(KnowledgeAtomList kalB) {
		
		for (int j = 0; j < kalB.size(); j++) {
			if (kalB.isItemValid(j)) {
				for (int i = 0; i < size(); i++) {
					if (isItemValid(i))
						if (atomSearchList.get(i).equals(kalB.getAtom(j))) {
							break;
						}
						else {
							// combine atom from knowledge B to Knowledge A
							add(kalB.getAtomAsCopy(j), kalB.getSourceAsCopy(j));
						}
				}
			}
		}
	}

	protected int compareTo(KnowledgeAtomList kalB) {
		
		int countEqual = 0;
		
		for (int i = 0; i < size(); i++) {
			if (isItemValid(i)) {
				for (int j = 0; j < kalB.size(); j++) {
					if (kalB.isItemValid(j)) {
						if (atomSearchList.get(i).equals(kalB.getAtom(j)))  {
							countEqual++;
							break;
						}
					}
				}
			}
		}
		
		return countEqual;
		
	}

	public boolean equals(KnowledgeAtomList b) {
		// we only check all atoms , sources and itemIsValid
		
		int length;
		int index;
		
		KnowledgeAtom tmpCopyKF;
		
		length = size();
		
		if (length != b.size())
			return false;
		
		for (index = 0; index < length; index++) {
			
			tmpCopyKF = b.getAtomAsCopy(index);
			
			if (!atomSearchList.get(index).equals(tmpCopyKF)) 
				return false;
			if (!sourceSearchList.get(index).equals(b.getSource(index))) 
				return false;
			if (atomSearchList.get(index).isItemValid() != tmpCopyKF.isItemValid()) 
				return false;

		}
		
		return true;
	}

	
}