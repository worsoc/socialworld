package org.socialworld.conversation;

public class WordSearchTree_Node {
	private Word word;
	private String letters;
	private char  firstLetter;
	private int countEdges;
	private boolean isLeaf;
	private  WordSearchTree_Node nextNodes[];
	
	
	protected WordSearchTree_Node getChild(int index) {
		if (nextNodes.length > index) return nextNodes[index];
		else return null;
	}
	
	protected WordSearchTree_Node getChild(char letter) {
		int i;
		WordSearchTree_Node result = null;
		
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
		nextNodes = new WordSearchTree_Node[count];
		isLeaf = false;
		countEdges = count;
	}
	
	protected Word getWord() {
		return word;
	}
	

}
