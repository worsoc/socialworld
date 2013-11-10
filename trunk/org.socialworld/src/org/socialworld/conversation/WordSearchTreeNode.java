package org.socialworld.conversation;


public class WordSearchTreeNode {
	private String letters;
	private char  firstLetter;
	private int countEdges;
	private boolean isLeaf;
	private  WordSearchTreeNode nextNodes[];
	private WordType wordType;
	
	protected WordSearchTreeNode getChild(int index) {
		if (nextNodes.length > index) return nextNodes[index];
		else return null;
	}
	
	protected WordSearchTreeNode getChild(char letter) {
		int i;
		WordSearchTreeNode result = null;
		
		if (!isLeaf) {
			for (i = 0; i < countEdges; i++) {
				result = getChild(i);
				if (result.getFirstLetter() == letter) {
					break;
				}
				else result = null;
			}
		}
		return result;
	}
	
	protected WordType getType() {
		return wordType;
	}
	
	protected void setType(WordType type) {
		this.wordType = type;
	}
	
	protected char getFirstLetter() {
		return firstLetter;
	}

	protected String getLetters() {
		return letters;
	}

	protected void setLetters(String letters) {
		this.letters = letters;
		firstLetter = letters.charAt(0);
	}
	
	protected boolean getIsLeaf() {
		return isLeaf;
	}
	
	protected int getcountEdges() {
		return countEdges;
	}
	
	protected void setToLeaf() {
	 	isLeaf = true;
		countEdges = 0;
	 }
	
	
	protected void createChildren(int count) {
		nextNodes = new WordSearchTreeNode[count];
		isLeaf = false;
		countEdges = count;
	}
}
