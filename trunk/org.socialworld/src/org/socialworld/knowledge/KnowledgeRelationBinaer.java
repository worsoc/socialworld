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
import org.socialworld.conversation.Relation;
import org.socialworld.conversation.Word;

public class KnowledgeRelationBinaer extends KnowledgeRelation {

	private Lexem object;
	private Numerus numerusObject;

	public KnowledgeRelationBinaer(Value verb, Value adverb, Value object) {
		super( verb, adverb);
		this.object = translateToLexem(object);
	}

	public KnowledgeRelationBinaer(List<Lexem> lexems) {
		super(lexems);
		if (lexems.size() == 3) {
			this.object = lexems.get(2);
		}
	}

	KnowledgeRelationBinaer(Relation relation, Lexem object) {
		super(relation);
		this.object = object;
	}

	KnowledgeRelationBinaer(KnowledgeRelationBinaer original) {
		super(original);
		if (original != null) {
			// TODO copy
		}
	}
	
	KnowledgeFact_Criterion getCriterion() {
		return KnowledgeFact_Criterion.relationBinaer;
	}

	KnowledgeFact copy() {
		return new KnowledgeRelationBinaer(this);
	}

	List<KnowledgeFactAtom> getAtoms() {
		List<KnowledgeFactAtom> result = new ArrayList<KnowledgeFactAtom>();
		// TODO KNOWLEDGE result.add(getLexemSubject());
		
		return result;
	}
	

	public Word getObjectAsWord() {
		return object.getWord(numerusObject);
	}
	
	List<Lexem> getLexems() {
		List<Lexem> result = new ArrayList<Lexem>();
		result.add(this.object);
		
		return result;
	}

	protected boolean equals(KnowledgeRelation b) {
		boolean isEqual = false;
		
		if (b instanceof KnowledgeRelationBinaer) {
			
			isEqual = super.equals(b);
			if (isEqual) {
				isEqual = checkWhetherTwoLexemsAreEqual(this.object, ((KnowledgeRelationBinaer) b).object);
				if (isEqual) {
					isEqual = checkWhetherTwoNumerusAreEqual(this.numerusObject, ((KnowledgeRelationBinaer) b).numerusObject);
				}
			}
		}
		return isEqual;
	}


}
