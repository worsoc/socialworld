package org.socialworld.conversation;

import org.socialworld.knowledge.KnowledgeFactCriterion;

public class Word {
	private String word;
	private int wordID;
	
	private WordType wordType;
	private KnowledgeFactCriterion kfc;
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
	
	public WordType getType() {
		return wordType;
	}

	protected void setPronoun(Word pronoun) {
		this.pronoun = pronoun;
	}
	
	protected Word getPronoun() {
		return pronoun;
	}
	
	protected void setKnowledgeFactCriterion(KnowledgeFactCriterion kfc ) {
		this.kfc = kfc;
	}

	protected KnowledgeFactCriterion getKnowledgeFactCriterion() {
		return kfc;
	}

	
	protected void allowAsKnowledgeSubject() {
		this.allowedAsKnowledgeSubject = true;
	}
	
	public boolean isAllowedAsKnowledgeSubject() {
		return this.allowedAsKnowledgeSubject;
	}
	
}
