package org.socialworld.knowledge;

import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;

public class KnowledgeValue extends KnowledgeAtom {

	private Value value;
	
	public KnowledgeValue(KnowledgeValue original) {
		if (original != null) {
			this.setValid(original.isItemValid());
			this.resetAcccessCount();
			this.value = original.value;

		}
	}

	KnowledgeAtom copy() {	
		return new KnowledgeValue(this);
	}
	
	Value getValue() {
		return this.value;
	}
	
	Type getType() {
		return this.value.getType();
	}
	
	String getName() {
		return this.value.getName();
	}


}
