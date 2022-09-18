package org.socialworld.conversation;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class Lexem {

	public static int newLexemID = 0;
	
	private int lexemID;
	private boolean allowedAsKnowledgeSubject = false;
	private Word_Type wordType;
	private Relation relation;
	List<KnowledgeFact_Criterion> criterions;

	
	public Lexem(int lexemID,  Word_Type type , boolean allowedAsKnowledgeSubject) {
		this.lexemID = lexemID;
		this.wordType = type;
		this.allowedAsKnowledgeSubject = allowedAsKnowledgeSubject;
		
		if (allowedAsKnowledgeSubject)		criterions = new ArrayList<KnowledgeFact_Criterion>() ;
		
	}

	public Lexem( Relation relation) {
		this.relation = relation;
		this.lexemID = relation.getLexemID();
		this.wordType = relation.getTense().getWordType();
		this.allowedAsKnowledgeSubject = false;
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
