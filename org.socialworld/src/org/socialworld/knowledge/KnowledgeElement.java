/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;

public class KnowledgeElement {


	private Lexem subject;
	
	private KnowledgeItemList knowledgeItemList;

	public KnowledgeElement(Lexem subject) {
		
		init();
		this.subject = subject;
		
	}

	private void init() {
		this.knowledgeItemList = new KnowledgeItemList();
	}
	
	protected void setSubject(Lexem subject) {
		if (knowledgeItemList.countValidItems() == 0)		this.subject = subject;
	}
	
	Lexem getLexemSubject() {
		return this.subject;
	}
	
	public Word getSubject() {
		return this.subject.getWord();
	}

	public void add(KnowledgeItem atom, KnowledgeSource source) {
		this.knowledgeItemList.add(atom, source);
	}
	
	KnowledgeItem getAtomAsCopy(int index) {
		return this.knowledgeItemList.getAtomAsCopy(index);
	}

	KnowledgeSource getSourceAsCopy(int index) {
		KnowledgeItem ka;
		ka = this.knowledgeItemList.getAtom(index);
		if (ka != null) {
			return ka.getSourceAsCopy();
		}
		else {
			return null;
		}
	}

	int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {
		return this.knowledgeItemList.findFactsForCriterion(criterion);
	}

	private KnowledgeItemList getAtomList() {
		return this.knowledgeItemList;		
	}
	
	int countValidFacts() {
		return this.knowledgeItemList.countValidItems();
	}
	
	public boolean isValid() {
		return (this.subject != null);
	}
	
	protected int compareTo(KnowledgeElement keB) {
		
		int countEqual = 0;
		
		if (this.subject == keB.getLexemSubject()) {
			countEqual = knowledgeItemList.compareTo(keB.getAtomList());
		}
		
		return countEqual;
		
	}
	
	protected void combineWith(KnowledgeElement keB) {
		this.getAtomList().combineWith(keB.getAtomList());
	}


}
