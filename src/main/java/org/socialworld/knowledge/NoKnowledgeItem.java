package org.socialworld.knowledge;


public class NoKnowledgeItem extends KnowledgeItem {

	private static NoKnowledgeItem itemNothing;
	
	public final static NoKnowledgeItem getObjectNothing() {
		if (itemNothing == null) {
			itemNothing = new NoKnowledgeItem();
		}
		return itemNothing;
	}
	
	@Override
	KnowledgeItem copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	KnowledgeItemNotes removeNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isEqual(KnowledgeItem item) {
		// TODO Auto-generated method stub
		return false;
	}

}
