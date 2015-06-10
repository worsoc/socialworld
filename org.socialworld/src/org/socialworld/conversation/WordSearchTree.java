/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.conversation;

import java.io.IOException;
import java.io.LineNumberReader;

import org.socialworld.datasource.loadFromDB.LoadWordSearchTree;

public class WordSearchTree {
	private WordSearchTree_Node root;
	private WordSearchTree_Node currentNode;
	
	public WordSearchTree() {
		root = LoadWordSearchTree.getInstance().root();
	}
	
	public Word findAndGetWord(String word) {
		currentNode = root;
		return checkWord(word);
	}
	
	public Word getWord() {
		return currentNode.getWord();
	}

	
	private Word checkWord(String word) {
		char firstLetter;
		String letters;
		int count;
		int i;
		
		WordSearchTree_Node nodeWithFirstLetter;
		
		if (currentNode.getIsLeaf() && word.length() == 0 )return currentNode.getWord(); // node is leaf -> tree contains the word
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
							return null; 
					}
					// the node's letters match the search word
					// -> continue search at child node with the rest of the search word
					currentNode = nodeWithFirstLetter;
					word = word.substring(count);
					return checkWord(word);
				}
				else // there are more letters for the node than in the search word -> tree doesn't contain the word
					return null;	
			}
			else // edge for the first letter doesn't exist -> tree doesn't contain the word
				return null;	
		}
	}
	

	
	private void createTree(LineNumberReader lnr, WordSearchTree_Node node, int indexChild) {
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
	
	private void fillTree(LineNumberReader lnr, WordSearchTree_Node node, int indexChild) {
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
