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
package org.socialworld.conversation;


import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class Word {
	private String word;
	private int wordID;
	private Lexem lexem;
	private Numerus numerus;
	
	//private Word_Type wordType;
	//private KnowledgeFact_Criterion kfc;
	//private boolean allowedAsKnowledgeSubject = false;
	
	private Word pronoun;
	

	public Word(int wordID, String word, Lexem lexem, Word pronoun) {
		this.wordID = wordID;
		this.lexem = lexem;
		this.word = word;
		this.pronoun = pronoun;
	}

	public Word(int wordID, String word, Lexem lexem, Word pronoun, Numerus numerus) {
		this.wordID = wordID;
		this.lexem = lexem;
		this.word = word;
		this.pronoun = pronoun;
		this.numerus = numerus;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getWordID() {
		return wordID;
	}
	
	public Lexem getLexem() {
		return lexem;
	}
	
	public Numerus getNumerus() {
		return numerus;
	}
	
	public Word_Type getType() {
		return this.lexem.getType();
	}

	
	public Word getPronoun() {
		return pronoun;
	}
	
	/*
	protected void setKnowledgeFact_Criterion(KnowledgeFact_Criterion kfc ) {
		this.kfc = kfc;
	}
*/
	protected ReadOnlyIterator<KnowledgeFact_Criterion> getKnowledgeFact_Criterions() {
		return this.lexem.getKnowledgeFact_Criterions();
	}

/*	
	protected void allowAsKnowledgeSubject() {
		this.allowedAsKnowledgeSubject = true;
	}
	*/
	
	public boolean isAllowedAsKnowledgeSubject() {
		return this.lexem.isAllowedAsKnowledgeSubject();
	}
	
	public boolean equals(Word b) {
		return this.wordID == b.wordID;
	}
	
}
