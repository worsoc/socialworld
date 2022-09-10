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

import org.socialworld.calculation.Value;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Tense;
import org.socialworld.conversation.Word;

public abstract class KnowledgeRelation extends KnowledgeFact {

	private boolean isSelfRelation;		// knowledge of its own relations (subject ist "I")

	private Lexem verb;
	private Tense tense;
	
	private Lexem adverb;
	
	private Numerus numerusSubject;
	
	protected KnowledgeRelation( Value verb, Value adverb) {
		this.verb = translateToLexem(verb);
		this.adverb = translateToLexem(adverb);
		// TODO set tense and numerus
	}
	
	protected KnowledgeRelation(List<Lexem> lexems) {
		if (lexems.size() >= 2) {
			setVerb(lexems.get(0));
			setAdverb(lexems.get(1));
		}
	}

	protected KnowledgeRelation(KnowledgeRelation original) {
		if (original != null) {
			// TODO copy
		}
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
	
	protected Lexem translateToLexem(Value value) {
		Lexem result = null;
		// TODO KNOWLEDGE translate from value to Lexem
		return result;
	}
	
	KnowledgeItemNotes removeNotes() {
		return new KnowledgeItemNotes(0);  // TODO KNOWLEDGE removeNotes();
	}


}
