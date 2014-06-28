package org.socialworld.conversation;

import java.io.IOException;
import java.io.LineNumberReader;
import org.socialworld.knowledge.KnowledgeFactCriterion;

public class WordSearchTree {
	private WordSearchTreeNode root;
	private WordSearchTreeNode currentNode;
	
	
	public WordType checkExistsWord(String word) {
		currentNode = root;
		if (checkWord(word) > 0) return currentNode.getType();
		else return null;
	}
	
	public int getWordID(String word) {
		currentNode = root;
		if (checkWord(word) > 0) return currentNode.getID();
		else return 0;
	}

	public int getWordID() {
		return currentNode.getID();
	}

	public KnowledgeFactCriterion getKnowledgeFactCriterion() {
		return currentNode.getKnowledgeFactCriterion();
	}

	public boolean isAllowedAsKnowledgeSubject() {
		return currentNode.isAllowedAsKnowledgeSubject();
	}
	
	private int checkWord(String word) {
		char firstLetter;
		String letters;
		int count;
		int i;
		
		WordSearchTreeNode nodeWithFirstLetter;
		
		if (currentNode.getIsLeaf() && word.length() == 0 )return currentNode.getID(); // node is leaf -> tree contains the word
		else {
			firstLetter  = word.charAt(0);
			nodeWithFirstLetter = currentNode.getChild(firstLetter);
			
			if (nodeWithFirstLetter != null) {
				letters = nodeWithFirstLetter.getLetters();
				count = letters.length();
				if (count <= word.length()) {
					for (i = 0; i < count; i++) {
						if (letters.charAt(i) == word.charAt(i)) {
							
						}
						else // the node's letters don't match the search word letters -> tree doesn't contain the word
							return 0; 
					}
					// the node's letters match the search word
					// -> continue search at child node with the rest of the search word
					currentNode = nodeWithFirstLetter;
					word = word.substring(count);
					return checkWord(word);
				}
				else // there are more letters for the node than in the search word -> tree doesn't contain the word
					return 0;	
			}
			else // edge for the first letter doesn't exist -> tree doesn't contain the word
				return 0;	
		}
	}
	

	
	private void createTree(LineNumberReader lnr, WordSearchTreeNode node, int indexChild) {
		String line;
		int countChilds;
		int i;
		
		node = node.getChild(indexChild);
		try
		{
			if ((line = lnr.readLine()) != null) {
				line = line.trim();
				countChilds = Integer.parseInt(line);
				
				if (countChilds == 0) { // node is leaf
					node.setToLeaf();
				}
				else {
					node.createChildren(countChilds) ;
					for ( i = 0 ; i < countChilds ; i++ ) // for every edge
						createTree(lnr, node, i);
				}
					
			}
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}

	}
	
	private void fillTree(LineNumberReader lnr, WordSearchTreeNode node, int indexChild) {
		String line;
		int count;
		int i;
		node = node.getChild(indexChild);
		try
		{
			if ((line = lnr.readLine()) != null) {
				line = line.trim();
				node.setLetters(line);
				
				if (line.length() > 0) {
					count = node.getcountEdges();
					for ( i = 0 ; i < count ; i++ )		// for every edge
						fillTree(lnr, node, i);	
				}
			}
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
		
		
	}
	
}
