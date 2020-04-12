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

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public class AnswerProperty extends KnowledgeProperty implements IAnswer {
	

	private Lexem subject;
	private KnowledgeSource source;
	
	public AnswerProperty(KnowledgeProperty original) {
		super(original);
	}
	
	/*
	public AnswerProperty(Lexem subject) {
		super(subject);
	}
	*/
	public KnowledgeAtomType getType() { return KnowledgeAtomType.property; }
	
	public void setSubject(Lexem subject, Numerus numerus) {
		this.subject = subject;
	}
	
	public Word getSubject() { return this.subject.getWord(); }
	

	public void setSource(KnowledgeSource source) {
		this.source = source;
	}
	
	public KnowledgeSource getSource() { return this.source; }


	
	public KnowledgeFact_Criterion getAnswerCriterion() {
		return getCriterion();
	}
	
	public Word getAnswerValue() {
		return getValue().getLexem().getWord();
		
	}

	public KnowledgeSource getAnswerSource() {
		return this.source;
	}

	public void sortBySource() {
		
	}
	
	/*
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
	*/
}
