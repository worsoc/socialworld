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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class Lexem {

	private static Lexem testLexem;

	public static final int LEXEMID_RANGE_PROPERTIES_1 = 1000;
	public static final int LEXEMID_RANGE_RELATION = 2000;

	public static final int OFFSET_LEXEMID_RELATION = 100000;

	public static final int OFFSET_LEXEMID_COLOUR = 10000;
	public static final int OFFSET_LEXEMID_MATERIAL = 11000;
	public static final int OFFSET_LEXEMID_NUTRIENT = 12000;
	public static final int OFFSET_LEXEMID_TASTE = 13000;
	
	public static int newLexemID = 0;
	
	private int lexemID;
	private boolean allowedAsKnowledgeSubject = false;
	private Word_Type wordType;

	List<KnowledgeFact_Criterion> criterions;

	public  static Lexem getTestLexem() {
		if (testLexem == null) {
			testLexem = new Lexem();
		}
		return testLexem;
	}
	
	private Lexem() {
		
	}
	
	public Lexem(int lexemID,  Word_Type type , boolean allowedAsKnowledgeSubject) {
		this.lexemID = lexemID;
		this.wordType = type;
		this.allowedAsKnowledgeSubject = allowedAsKnowledgeSubject;
		
		if (allowedAsKnowledgeSubject)		criterions = new ArrayList<KnowledgeFact_Criterion>() ;
		
	}

	
	public int getLexemID() {
		return lexemID;
	}
	
	public Word getWord() {
		return null;
	}

	public Word getWord(Tense tense) {
		return null;
	}

	public Word getWord(Numerus numerus) {
		return null;
	}

	public Word_Type getType() {
		return wordType;
	}

	public boolean isAllowedAsKnowledgeSubject() {
		return this.allowedAsKnowledgeSubject;
	}
	
	public ReadOnlyIterator<KnowledgeFact_Criterion> getKnowledgeFact_Criterions() {
		return new ReadOnlyIterator<KnowledgeFact_Criterion>(criterions.iterator());
	}

}
