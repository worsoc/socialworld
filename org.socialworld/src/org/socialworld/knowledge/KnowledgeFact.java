package org.socialworld.knowledge;

public class KnowledgeFact {
	private KnowledgeFactCriterion criterion;
	private KnowledgeFactValue value;
	
	protected KnowledgeFactValue getValue() {
		return value;
	}
	
	protected KnowledgeFactCriterion getCriterion() {
		return criterion;
	}
}
