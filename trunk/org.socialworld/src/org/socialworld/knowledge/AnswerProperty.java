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

import java.util.List;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public class AnswerProperty extends KnowledgeProperty implements IAnswer {
	

	private KnowledgeProperty originalProperty;
	
	public AnswerProperty(KnowledgeProperty original) {
		super(original);
		this.originalProperty = original;
	}
	
	/*
	public AnswerProperty(Lexem subject) {
		super(subject);
	}
	*/
	public KnowledgeFact_Type getType() { return KnowledgeFact_Type.property; }
	
	public void changeSubject(Lexem subject) {
		setSubject(subject);
	}
	
	public Word getSubjectAsWord() { return getSubject().getWord(); }

	public void setSpeechRecognitionsSubjectWord(Word subject) {
		changeSubject(subject.getLexem());
	}

	public void changeSource(KnowledgeSource newSource) {
		this.setSource(newSource);
	}
	
	public KnowledgeFact_Criterion getAnswerCriterion() {
		return getCriterion();
	}
	
	public Word getAnswerValue() {
		// TODO choose from  list
		
		List<KnowledgeFactAtom> atoms = getAtoms();
		if (atoms.size() > 0) {
			for (KnowledgeFactAtom atom : atoms ) {
				if (atom.getType() == KnowledgeFactAtom_Type.lexem) {
					return atom.getLexem().getWord();
				}
			}
		}
		
		return Word.getNoWord();
		
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
