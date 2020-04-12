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
	
	private KnowledgeAtomList knowledgeAtomList;

	public KnowledgeElement(Lexem subject) {
		
		init();
		this.subject = subject;
		
	}

	private void init() {
		this.knowledgeAtomList = new KnowledgeAtomList();
	}
	
	protected void setSubject(Lexem subject) {
		if (knowledgeAtomList.countValidItems() == 0)		this.subject = subject;
	}
	
	Lexem getLexemSubject() {
		return this.subject;
	}
	
	public Word getSubject() {
		return this.subject.getWord();
	}

	public void add(KnowledgeFact fact, KnowledgeSource source) {
		this.knowledgeAtomList.add(fact, source);
	}
	
	KnowledgeAtom getAtomAsCopy(int index) {
		return this.knowledgeAtomList.getAtomAsCopy(index);
	}

	KnowledgeSource getSourceAsCopy(int index) {
		return this.knowledgeAtomList.getSourceAsCopy(index);
	}

	int[] findFactsForCriterion(KnowledgeFact_Criterion criterion) {
		return this.knowledgeAtomList.findFactsForCriterion(criterion);
	}

	private KnowledgeAtomList getAtomList() {
		return this.knowledgeAtomList;		
	}
	
	int countValidFacts() {
		return this.knowledgeAtomList.countValidItems();
	}
	
	public boolean isValid() {
		return (this.subject != null);
	}
	
	protected int compareTo(KnowledgeElement keB) {
		
		int countEqual = 0;
		
		if (this.subject == keB.getLexemSubject()) {
			countEqual = knowledgeAtomList.compareTo(keB.getAtomList());
		}
		
		return countEqual;
		
	}
	
	protected void combineWith(KnowledgeElement keB) {
		this.getAtomList().combineWith(keB.getAtomList());
	}


}
