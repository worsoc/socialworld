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
import org.socialworld.conversation.Tense;
import org.socialworld.conversation.Word;

public abstract class KnowledgeRelation extends KnowledgeFact {

	private boolean isSelfRelation;		// knowledge of its own relations (subject ist "I")

	private Lexem verb;
	private Tense tense;
	
	private Lexem adverb;
	
	private Lexem subject;
	private Numerus numerusSubject;
	
	abstract Word getSubject();
	abstract Word getVerb();
	abstract Word getAdverb();
	
	boolean isSelfRelation() {return isSelfRelation;}

	Lexem getLexemVerb() { return verb; }
	Tense getTense() { return tense; }

	Lexem getLexemAdverb() { return adverb; }
	
	Lexem getLexemSubject() { return subject; }
	Numerus getNumerusSubject() { return numerusSubject; }
	
	abstract Lexem[] getValues();
	
	KnowledgeFact_Criterion getCriterion() {
		return KnowledgeFact_Criterion.relation;
	}
	
	void setLexemSubject(Lexem subject) {
		this.subject = subject;
	}

	void setNumerusSubject(Numerus numerus) {
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
	
}
