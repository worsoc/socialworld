package org.socialworld.knowledge;

public class KnowledgeValue extends KnowledgeAtom {

	public KnowledgeValue(KnowledgeValue original) {
		if (original != null) {
			this.setValid(original.isItemValid());
			this.resetAcccessCount();
			// TODO  implement copy constructor

		}
	}

	KnowledgeAtom copy() {	
		return new KnowledgeValue(this);
	}

}
