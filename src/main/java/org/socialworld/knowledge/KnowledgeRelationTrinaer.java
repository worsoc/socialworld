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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Tense;
import org.socialworld.conversation.Word;

public class KnowledgeRelationTrinaer extends KnowledgeRelation {

	private Lexem object1;
	private Numerus numerusObject1;

	private Lexem object2;
	private Numerus numerusObject2;
	
	public KnowledgeRelationTrinaer( Value verb, Value adverb, Value object1, Value object2) {
		super(verb, adverb);
		this.object1 = translateToLexem(object1);
		this.object2 = translateToLexem(object2);
	}
	
	public KnowledgeRelationTrinaer(Tense tense, List<Lexem> lexems) {
		super(tense, lexems);
		if (lexems.size() > 2) {
			this.object1 = lexems.get(2);
		}
		if (lexems.size() > 3) {
			this.object2 = lexems.get(3);
		}
	}

	KnowledgeRelationTrinaer(KnowledgeRelationTrinaer original) {
		super(original);
		if (original != null) {
			// TODO copy
		}
	}
	
	KnowledgeFact_Criterion getCriterion() {
		return KnowledgeFact_Criterion.relationTrinaer;
	}

	KnowledgeFact copy() {
		return new KnowledgeRelationTrinaer(this);
	}

	List<KnowledgeFactAtom> getAtoms() {
		List<KnowledgeFactAtom> result = new ArrayList<KnowledgeFactAtom>();
		// TODO KNOWLEDGE result.add(getLexemSubject());
		
		return result;
	}
	
	List<String> getNotes(int index) {
		return new ArrayList<String>();  // TODO KNOWLEDGE this.lexems.getLexems();
	}


	public Word getObject1AsWord() {
		return object1.getWord(numerusObject1);
	}

	public Word getObject2AsWord() {
		return object2.getWord(numerusObject2);
	}
	
	List<Lexem> getLexems() {
		List<Lexem> result = new ArrayList<Lexem>();
		result.add(this.object1);
		result.add(this.object2);
		
		return result;
	}

	protected boolean isEqual(KnowledgeItem item) {
		if (item instanceof KnowledgeRelationTrinaer) {
			return relsAreEqual((KnowledgeRelationTrinaer)item);
		}
		return false;
	}

	protected boolean relsAreEqual(KnowledgeRelationTrinaer b) {
		boolean isEqual = false;
			
		isEqual = super.equals(b);
		if (isEqual) {
			isEqual = checkWhetherTwoLexemsAreEqual(this.object1,  b.object1);
			if (isEqual) {
				isEqual = checkWhetherTwoLexemsAreEqual(this.object2, b.object2);
				if (isEqual) {
					isEqual = checkWhetherTwoNumerusAreEqual(this.numerusObject1, b.numerusObject1);
					if (isEqual) {
						isEqual = checkWhetherTwoNumerusAreEqual(this.numerusObject2, b.numerusObject2);
					}
				}
			}
		}
			
		return isEqual;
	}

	public String toString() {
		String output;
		output = super.toString() + ", " +
				(object1 == null ? "" : object1.toString()) + ", " +
				(object2 == null ? "" : object2.toString()) ;
		return output;
	}

}
