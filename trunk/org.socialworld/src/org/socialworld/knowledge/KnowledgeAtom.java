package org.socialworld.knowledge;

public abstract class KnowledgeAtom {

	private int itemAccessCount;
	private boolean itemIsValid;

	abstract KnowledgeAtom copy();

	void setValid(boolean isValid) {
		this.itemIsValid = isValid;
	}
	
	boolean isItemValid() {
		return this.itemIsValid;
	}
	
	void resetAcccessCount() {
		this.itemAccessCount = 0;
	}
	
	void incrementAccessCount() {
		this.itemAccessCount++;
	}
	
	int getItemAccessCount() {
		return this.itemAccessCount;
	}

}
