package org.socialworld.knowledge;

public class KnowledgeFact {
	private KnowledgeFact_Criterion criterion;
	private KnowledgeFact_Value value;
	
	protected KnowledgeFact_Value getValue() {
		return value;
	}
	
	protected KnowledgeFact_Criterion getCriterion() {
		return criterion;
	}
	
	protected int compare (KnowledgeFact factB) {
		if (this == factB) 
			return 1;
		else
			return 0;
	}
}
