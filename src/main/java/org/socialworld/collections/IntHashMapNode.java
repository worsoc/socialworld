package org.socialworld.collections;

public class IntHashMapNode {

	private IntHashMapNode[] childNodes = new IntHashMapNode[16];
	private Object value;
	
	void addChildNode(byte nrChild, IntHashMapNode childNode) {
		if (nrChild < this.childNodes.length) {
			this.childNodes[nrChild] = childNode;
		}
	}
	
	IntHashMapNode getNextNode(byte nrChild) {
		if (nrChild < this.childNodes.length) {
			return this.childNodes[nrChild];
		}
		else
			return null;
	}
	
	void setValue(Object value) {this.value = value;}
	
	Object getValue() {return this.value;}
}
