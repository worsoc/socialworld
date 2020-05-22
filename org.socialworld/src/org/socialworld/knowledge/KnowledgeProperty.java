package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;

public class KnowledgeProperty extends KnowledgeFact {

	private KnowledgeFact_Criterion criterion;
	private KnowledgeFact_Value value;
	
	public KnowledgeProperty(KnowledgeFact_Criterion criterion, KnowledgeFact_Value value ) {
		this.criterion = criterion;
		this.value = value;
	}
	
	public KnowledgeProperty(KnowledgeProperty original) {
		if (original != null) {
			this.criterion  = original.getCriterion();
			this.value = new KnowledgeFact_Value(original.getValue());
			this.setValid(original.isItemValid());
			this.resetAccessCount();
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeProperty(this);
	}
	
	Lexem[] getValues() {
		return this.value.getLexems();
	}
	
	protected KnowledgeFact_Value getValue() {
		return value;
	}
	
	protected KnowledgeFact_Criterion getCriterion() {
		return criterion;
	}
	
	protected boolean equals(KnowledgeProperty b) {
		return ( this.criterion == b.criterion & this.value.equals(b.value) );
	}

}
