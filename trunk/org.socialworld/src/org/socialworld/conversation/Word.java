package org.socialworld.conversation;

import org.socialworld.knowledge.KnowledgeFact_Criterion;

public class Word {
	private String word;
	private int wordID;
	
	private Word_Type wordType;
	private KnowledgeFact_Criterion kfc;
	private boolean allowedAsKnowledgeSubject = false;
	
	private Word pronoun;
	
	public Word() {
		kfc = null;

	}
	
	public String getWord() {
		return word;
	}
	
	public int getWordID() {
		return wordID;
	}
	
	public Word_Type getType() {
		return wordType;
	}

	protected void setPronoun(Word pronoun) {
		this.pronoun = pronoun;
	}
	
	protected Word getPronoun() {
		return pronoun;
	}
	
	protected void setKnowledgeFact_Criterion(KnowledgeFact_Criterion kfc ) {
		this.kfc = kfc;
	}

	protected KnowledgeFact_Criterion getKnowledgeFact_Criterion() {
		return kfc;
	}

	
	protected void allowAsKnowledgeSubject() {
		this.allowedAsKnowledgeSubject = true;
	}
	
	public boolean isAllowedAsKnowledgeSubject() {
		return this.allowedAsKnowledgeSubject;
	}
	
}
