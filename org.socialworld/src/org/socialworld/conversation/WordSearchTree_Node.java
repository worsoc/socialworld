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
