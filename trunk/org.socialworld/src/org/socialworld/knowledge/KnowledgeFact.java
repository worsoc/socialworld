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
	
	protected int compare (KnowledgeFact factB) {
		if (this == factB) 
			return 1;
		else
			return 0;
	}
}
