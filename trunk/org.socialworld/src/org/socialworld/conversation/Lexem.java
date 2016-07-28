package org.socialworld.conversation;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.collections.ReadOnlyIterator;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class Lexem {

	private int lexemID;
	private boolean allowedAsKnowledgeSubject = false;
	private Word_Type wordType;

	List<KnowledgeFact_Criterion> criterions;

	
	public Lexem(int lexemID,  Word_Type type , boolean allowedAsKnowledgeSubject) {
		this.lexemID = lexemID;
		this.wordType = type;
		this.allowedAsKnowledgeSubject = allowedAsKnowledgeSubject;
		
		if (allowedAsKnowledgeSubject)		criterions = new ArrayList<KnowledgeFact_Criterion>() ;
		
	}
	
	public Word_Type getType() {
		return wordType;
	}

	public boolean isAllowedAsKnowledgeSubject() {
		return this.allowedAsKnowledgeSubject;
	}
	
	protected ReadOnlyIterator<KnowledgeFact_Criterion> getKnowledgeFact_Criterions() {
		return new ReadOnlyIterator<KnowledgeFact_Criterion>(criterions.iterator());
	}
	
}
