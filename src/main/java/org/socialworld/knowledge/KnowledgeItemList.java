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

import org.socialworld.attributes.PropertyName;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Relation;
import org.socialworld.objects.GroupingOfSimulationObjects;

final class KnowledgeItemList  {
	
	private class KnowledgeItemWithMetaInfo {
		
		private KnowledgeItem item;
		private int accessCount = 0;
		private boolean isValid = false; 
		
		private KnowledgeItemWithMetaInfo(KnowledgeItem item) {
			this.item = item;
		}
		
		private void setValid() {
			resetAccessCount();
			this.isValid = true;
		}

		private void setInValid() {
			resetAccessCount();
			this.isValid = false;
		}

		private void resetAccessCount() {
			this.accessCount = 0;
		}
		
		private void incrementAccessCount() {
			this.accessCount++;
		}
		
		private int getAccessCount() {
			return this.accessCount;
		}

	}
	
	final int MAXIMUM_KNOWLEDGE_CAPACITY = 100;
	
	private ArrayList<KnowledgeItemWithMetaInfo> itemSearchList;
	
	private ArrayList<KnowledgeItemNotes> itemNotes;
	
	private int validItemCount = 0;
	
	private KnowledgeElement myParent;

	private boolean notesHasBeenMovedFromItems = false;
	
	KnowledgeFactPool poolInstance;
	
	KnowledgeItemList(KnowledgeElement myParent) {
		
		itemSearchList = new ArrayList<KnowledgeItemWithMetaInfo>();
		
		poolInstance = KnowledgeFactPool.getInstance();
		
	}
	
	
	private void replaceFactsWithFactsFromPool() {
		

		moveNotesFromItems();
		
		int size = size();
		
		for (int index = 0; index < size; index++) {
			
			KnowledgeItemWithMetaInfo itemWMI = itemSearchList.get(index);
			
			if (itemWMI != null) {
				
				KnowledgeItem item = itemWMI.item;
				
				KnowledgeItemWithMetaInfo newItemWMI = null;
				
				if (item instanceof KnowledgeProperty) {
					KnowledgeProperty property = (KnowledgeProperty) item;
					KnowledgeProperty propertyFromPool = poolInstance.getPropertyFromPool(property);
					newItemWMI = new KnowledgeItemWithMetaInfo(propertyFromPool);
				}
				else if (item instanceof KnowledgeRelation) {
					KnowledgeRelation relation = (KnowledgeRelation) item;
					KnowledgeRelation relationFromPool = poolInstance.getRelationFromPool(relation);
					newItemWMI = new KnowledgeItemWithMetaInfo(relationFromPool);
				}

				if (newItemWMI != null) {
					
					newItemWMI.setValid();
					itemSearchList.set(index, newItemWMI);

				}

			}
			
			index++;
			
		}
	}
	
	private void moveNotesFromItems() {
	
		if (!notesHasBeenMovedFromItems) {
			

			int size = size();
			
			itemNotes = new ArrayList<KnowledgeItemNotes>(size);
			
			for (int index = 0; index < size; index++) {
			
				KnowledgeItemWithMetaInfo itemWMI = itemSearchList.get(index);
				KnowledgeItemNotes itemsNotes = itemWMI.item.removeNotes();
				
				itemNotes.set(index, itemsNotes);
			}
			
			notesHasBeenMovedFromItems = true;
			
		}
		
	}
	
	int size() {
		return this.itemSearchList.size();
	}

	int countValidItems() {
		return validItemCount;
	}

	boolean isItemValid(int index) {
		if (index < size()) {
			return itemSearchList.get(index).isValid;
		}
		else {
			return false;
		}
			 
	}

	KnowledgeItem getAtom(int index) {
		if ((index >= 0) & (index < size()) )
			return itemSearchList.get(index).item;
		else
			return null;
	}

	KnowledgeItem getAtomAsCopy(int index) {
		if ((index >= 0) & (index < size()) )
			return itemSearchList.get(index).item.copy();
		else
			return null;
	}


	

	KnowledgeFact find(Lexem  value) {
		KnowledgeItemWithMetaInfo item;
		KnowledgeItemWithMetaInfo itemPrev;;
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
			if (item.isValid) {
				
				if (item.item instanceof KnowledgeFact ) {
	
					fact = (KnowledgeFact) item.item;
					
					atoms = fact.getAtoms();
					
					found = false;
					for (KnowledgeFactAtom atom : atoms) {
						if ((atom.getType() == KnowledgeFactAtom_Type.lexem) 
								&& (atom.getLexem() == value)) 
							found = true;
					}
				
				}
			
			}
			index++;

			
		}
		while( found  | (index == count) );
		
		index--;
		
		// exchange the element for the previous one
		// the knowledge fact is changed to any knowledge item
		if ( found && index > 0)  {
			
			// the previous list element
			itemPrev = itemSearchList.get(index - 1);
					
			itemSearchList.set(index - 1, item);
			itemSearchList.set(index , itemPrev);
			
		}
		
		if (!found)
			return null;
		else
			return fact;
	}
		
	void add(KnowledgeItem item) {
		
		boolean isAlreadyInList = false;
	
		isAlreadyInList = checkWhetherItemIsAlreadyInList(item);
		
		if (!isAlreadyInList) {
			
			int 	replacableIndex;
			
			KnowledgeItem itemFromPool = poolInstance.getItemFromPool(item);
			
			KnowledgeItemWithMetaInfo newItem = new KnowledgeItemWithMetaInfo(itemFromPool);
			newItem.setValid();
			newItem.incrementAccessCount();
			newItem.incrementAccessCount();
			
			replacableIndex = getReplacableIndex();
				
				
				
			if (replacableIndex  == size()) {
				this.itemSearchList.add( newItem);
			}
			else {
				
				if (this.itemSearchList.get(replacableIndex).isValid == true) {
					this.validItemCount--;
				}

				this.itemSearchList.set(replacableIndex, newItem);
			}
			
			this.validItemCount++;

		}
		
	}
	
	private boolean checkWhetherItemIsAlreadyInList(KnowledgeItem item) {
		
		boolean isAlreadyInList = false;
		for (KnowledgeItemWithMetaInfo itemWMI : itemSearchList) {
			if (itemWMI.item.equals(item)) {
				isAlreadyInList = true;
				break;
			}
		}
		return isAlreadyInList;
		
	}
	
	void remove(int index) {
		
		KnowledgeItemWithMetaInfo itemTmp;
		
		itemTmp = this.itemSearchList.get(index);
		
		if (itemTmp.isValid) {
			itemTmp.isValid = false;
			itemTmp.resetAccessCount();
			this.validItemCount--;
		}
	}


	int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {

		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		KnowledgeItemWithMetaInfo item;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			item = itemSearchList.get(index);
			if (item.isValid) {
				
				if (item.item instanceof KnowledgeFact ) {
				
				fact = (KnowledgeFact) item.item;
	
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

	
	int[] findFactsForValue(Lexem value) {
		
		int result_tmp[] = new int[MAXIMUM_KNOWLEDGE_CAPACITY];
		int result[];
		int count = 0;
		int index;
		
		List<KnowledgeFactAtom> atoms;
		int indexLexem;
		int countLexems;

		KnowledgeItemWithMetaInfo item;
		KnowledgeFact fact;

		for (index = 0; index < size(); index++) {
			
			item = itemSearchList.get(index);
			if (item.isValid) { 
				
				if (item.item instanceof KnowledgeFact ) {
			
					fact = (KnowledgeFact) item.item;
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

	
	int getIndexForValidWithMaxAccessCount() {
		int index;
		int result;
		
		result = getIndexForFirstValid();
		if (result == -1) return -1;
	
		for (index = result + 1; index < size(); index++) {
			if (itemSearchList.get(index).isValid == true) {
				 if (itemSearchList.get(result).accessCount < itemSearchList.get(index).accessCount) 	result = index;
			}
		}
		return result;
	}

	
	
	int getIndexForValidWithMinAccessCount() {
		int index;
		int result;
	
		result = getIndexForFirstValid();
		if (result == -1) return -1;

		for (index = result + 1; index < size(); index++) {
			if (itemSearchList.get(index).isValid == true) {
			 if (itemSearchList.get(result).accessCount > itemSearchList.get(index).accessCount) 	result = index;
			}
		}
		return result;
	}

	
	int getIndexForFirstValid() {
		int index;
	
		for (index = 0; index < size(); index++) {
			if (itemSearchList.get(index).isValid == true)			  	return index;
		}
		return -1;
		
	}

	private int getReplacableIndex() {
		int count;
		int index;
		int i;
		
		count = size();		
		
		index = 0;
		for (i = 0; i < count; i++) {
			if (itemSearchList.get(i).isValid == false) {
				index = i;
				return index;
			}
			else if (itemSearchList.get(i).accessCount < itemSearchList.get(index).accessCount) 	index = i;
			
		}

		if (size() == MAXIMUM_KNOWLEDGE_CAPACITY ) {
			return index ;
		}
		else {
			return count;
		}
		
	}

	void combineWith(KnowledgeItemList kalB) {
		
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

	int compareTo(KnowledgeItemList kalB) {
		
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

	boolean equals(KnowledgeItemList b) {
		// we only check all atoms  and itemIsValid
		
		int length;
		int index;
		
		KnowledgeItem tmpCopyKF;
		
		length = size();
		
		if (length != b.size())
			return false;
		
		for (index = 0; index < length; index++) {
			
			tmpCopyKF = b.getAtomAsCopy(index);
			
			if (!itemSearchList.get(index).item.equals(tmpCopyKF)) 
				return false;
			if (itemSearchList.get(index).isValid != b.isItemValid(index)) 
				return false;

			// TODO KNOWLEDGE compare sources, too?
		}
		
		return true;
	}

	// derives knowledge items for the same knowledge item list
	// that means or the same source and subject
	void deriveKnowledgeItemsFromNotes() {
		
		replaceFactsWithFactsFromPool();
		
		List<KnowledgeItem> items = new ArrayList<KnowledgeItem>();
		
		for (KnowledgeItemNotes kiNotes : itemNotes) {
			
			List<List<String>> listOfNotes = kiNotes.getNotes();
			
			for (int index = 0; index < listOfNotes.size(); index++) {
				
				List<String> notes = listOfNotes.get(index);
				for (String note : notes) {
					
					List<KnowledgeItem> oneNoteItems;
					oneNoteItems = deriveKnowledgeItemsFromNote(note);
					
					addIfNotContainsYet(oneNoteItems, items);
				}
			}
			
		}
		
		for (KnowledgeItem item : items) {
				add(item);
		}
		
	}
	
	private List<KnowledgeItem> deriveKnowledgeItemsFromNote(String note) {
		
		String[] noteParts = note.split("\\.");
		String part;
		PropertyName propName;
		List<Relation> possibleRelations = new ArrayList<Relation>();
		
		List<KnowledgeItem> oneNoteItems = new ArrayList<KnowledgeItem>();
		
		for (int index = 0; index < noteParts.length; index++) {
			
			part = noteParts[index];
			
			if (possibleRelations.size() > 0) {
				
				Relation choosenRelation = chooseRelationForObject(possibleRelations, part);
				
				if (!choosenRelation.equals(Relation.nothing)) {
					Lexem object = getObjectLexem(part);
					if (object != null) {
						KnowledgeItem ki = new KnowledgeRelationBinaer(choosenRelation, object);
						oneNoteItems.add(ki);
					}
				}
				
				possibleRelations = new ArrayList<Relation>();
				continue;
				
			}
			
			propName = PropertyName.forString(part);
			if ( propName != PropertyName.unknown) {
				
				if (propName.checkIsUsableAsRelationObject(Relation.carry)) {
					possibleRelations.add(Relation.carry);
				}
			}
			
		}
		
		return oneNoteItems;
		
	}
	
	private Relation chooseRelationForObject(List<Relation> possibleRelations, String object) {
		Relation relation = Relation.nothing;
		
		// TODO KNOWLEDGE chooseRelationForObject
		if (possibleRelations.size() > 0) {
			relation = possibleRelations.get(0);
		}
		
		return relation;
	}
	
	private Lexem getObjectLexem(String part) {
		
		GroupingOfSimulationObjects goso = GroupingOfSimulationObjects.getInstance();
		
		Lexem lexem = null;
		Lexem noObject = null;
		
		int sogn  = GroupingOfSimulationObjects.getGroupNumber(part);
		
		if (sogn >= 0) {
			
			lexem = goso.getLexemForGroupingNumber(sogn);

			if  (goso.checkIsLexemSomething(lexem)) {
				lexem = noObject;
			}
			else if  (goso.checkIsLexemNothing(lexem)) {
				lexem = noObject;
			}
			
		}
		
		return lexem;
	}
	
	private void addIfNotContainsYet(List<KnowledgeItem> someItems, List<KnowledgeItem> totalItems ) {
		
		boolean addToDistinctList = false;
		
		List<KnowledgeItem> someDistinctItems = new ArrayList<KnowledgeItem>();
		
		for (KnowledgeItem item : someItems) {
			
			addToDistinctList = true;
			
			for (KnowledgeItem itemInDistinctList : someDistinctItems) {
				
				
				if (item.equals(itemInDistinctList)) {
					addToDistinctList = false;
					continue;
				}
				
			}
			
			if (addToDistinctList) {
				someDistinctItems.add(item);
			}
		}
		
		boolean addToTotalList = false;
		
		for (KnowledgeItem itemInDistinctList : someDistinctItems) {
			
			addToTotalList = true;
			
			for (KnowledgeItem itemInTotalList : totalItems) {
				
				
				if (itemInDistinctList.equals(itemInTotalList)) {
					addToTotalList = false;
					continue;
				}
				
			}
			
			if (addToTotalList) {
				totalItems.add(itemInDistinctList);
			}
		}

	}

	public String toString() {
		String output = "";
		String item;
		for (KnowledgeItemWithMetaInfo ki : itemSearchList) {
			item = ki.item.toString();
			if (item.length() == 0 || item.contains("<>")) continue;
			output = output + "\n" + item;
		}
		if (output.length() > 0) {
			output = "[" + output + "\n" + "]";
		}
		return output;
	}	
}
