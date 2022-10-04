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

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Relation;
import org.socialworld.conversation.Tense;
import org.socialworld.conversation.Word;
import org.socialworld.core.AllWords;

public abstract class KnowledgeRelation extends KnowledgeFact {

	private boolean isSelfRelation;		// knowledge of its own relations (subject ist "I")

	private Relation relation = Relation.ignore;
	
	private Lexem verb;
	private Tense tense = Tense.ignore;
	
	private Lexem adverb;
	
	private Numerus numerusSubject;
	
	protected KnowledgeRelation( Value verb, Value adverb) {
		this.relation = translateToRelation(verb);
		setLexemAndTenseFromRelation();
		this.adverb = translateToLexem(adverb);
		// TODO set numerus
	}
	
	protected KnowledgeRelation(Tense tense, List<Lexem> lexems) {
		if (lexems.size() >= 2) {
			this.tense = tense;
			setVerb(lexems.get(0));
			setAdverb(lexems.get(1));
			setRelationFromLexemAndTense();
		}
	}

	protected KnowledgeRelation(Relation relation) {
		this.relation = relation;
		setLexemAndTenseFromRelation();
	}
	
	protected KnowledgeRelation(KnowledgeRelation original) {
		if (original != null) {
			
			this.isSelfRelation = original.isSelfRelation;	
			this.relation = original.relation;
			
			this.verb = original.verb;
			this.tense = original.tense;
			
			this.adverb = original.adverb;
			
			this.numerusSubject = original.numerusSubject;
			
		}
	}

	protected void setRelation(Relation relation) {
		this.relation = relation;
	}
	
	protected void setVerb(Lexem verb) {
		this.verb = verb;
	}

	protected void setAdverb(Lexem adverb) {
		this.adverb = adverb;
	}

	public Word getVerbAsWord() {
		return verb.getWord(tense);
	}

	public Word getAdverbAsWord() {
		return adverb.getWord();
	}
	
	
	boolean isSelfRelation() {return isSelfRelation;}

	
	Lexem getVerbsLexem() { return verb; }
	Tense getVerbsTense() { return tense; }

	Lexem getAdverbsLexem() { return adverb; }
	
	Numerus getSubjectsNumerus() { return numerusSubject; }
	
	
	
	

	void setSubjectsNumerus(Numerus numerus) {
		this.numerusSubject = numerus;
	}
	
	boolean isSubjectPlural() {
		if (numerusSubject == Numerus.plural) return true;
		if (numerusSubject == Numerus.singular) return false;
		return true;
	}
	
	boolean isSubjectSingular() {
		if (numerusSubject == Numerus.plural) return false;
		if (numerusSubject == Numerus.singular) return true;
		return true;
	}
	
	private Relation translateToRelation(Value value) {
		Relation result = Relation.ignore;
		if (value.getType() == Type.sentenceElement) {
			Object o = value.getValue();
			if (o instanceof Relation)	{
				result = (Relation) o;
			}
		}
		return result;
	}

	protected Lexem translateToLexem(Value value) {
		Lexem result = null;
		if (value.getType() == Type.sentenceElement) {
			Object o = value.getValue();
			if (o instanceof Lexem)	{
				result = (Lexem) o;
			}
			else if (o instanceof Integer) {
				int lexemID = ((Integer) o).intValue();
				result = AllWords.getLexem(lexemID);
			}
			else if (Integer.class.isInstance(o)) {
				int lexemID  = (int) o;
				result = AllWords.getLexem(lexemID);
			}
			
		}
		return result;
	}

	private void setLexemAndTenseFromRelation() {
		if (this.relation != null) {
			this.verb = this.relation.getLexem();
			this.tense = this.relation.getTense();
		}
	}
	
	private void setRelationFromLexemAndTense() {
		if (this.verb != null && this.tense != Tense.ignore) {
			this.relation = Relation.getName(this.verb, this.tense);
		}
	}

	KnowledgeItemNotes removeNotes() {
		return new KnowledgeItemNotes(0);  // TODO KNOWLEDGE removeNotes();
	}

	protected boolean equals(KnowledgeRelation b) {
		boolean isEqual = false;
		
		isEqual = checkWhetherTwoLexemsAreEqual(this.adverb, b.adverb);
		if (isEqual) {
			isEqual = checkWhetherTwoLexemsAreEqual(this.verb, b.verb);
			if (isEqual) {
				isEqual = checkWhetherTwoTensesAreEqual(this.tense, b.tense);
				if (isEqual) {
					isEqual = checkWhetherTwoRelationsAreEqual(this.relation, b.relation);
					if (isEqual) {
						isEqual = checkWhetherTwoNumerusAreEqual(this.numerusSubject, b.numerusSubject);
						if (isEqual) {
							isEqual = this.isSelfRelation == b.isSelfRelation;
						}
					}
				}
			}
		}
		return isEqual;
	}
	
	protected boolean checkWhetherTwoTensesAreEqual(Tense a, Tense b) {
		
		if (a == null && b == null) return true;
		if (a == null && b != null) return false;
		if (a != null && b == null) return false;
		
		return a.equals(b);
		
	}

	protected boolean checkWhetherTwoRelationsAreEqual(Relation a, Relation b) {
		
		if (a == null && b == null) return true;
		if (a == null && b != null) return false;
		if (a != null && b == null) return false;
		
		return a.equals(b);
		
	}
	
	protected boolean checkWhetherTwoNumerusAreEqual(Numerus a, Numerus b) {
		
		if (a == null && b == null) return true;
		if (a == null && b != null) return false;
		if (a != null && b == null) return false;
		
		return a.equals(b);
		
	}

}
