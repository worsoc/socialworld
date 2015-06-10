/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.loadFromDB;

import org.socialworld.conversation.Word;
import org.socialworld.conversation.WordSearchTree_Node;
import org.socialworld.core.AllWords;
import org.socialworld.datasource.tablesSimulation.TableWordSearchTree;


/**
 * @author Mathias Sikos
 *
 */
public class LoadWordSearchTree {

	private static LoadWordSearchTree instance;
	
	private TableWordSearchTree tableWST;
	
	private WordSearchTree_Node root;
	
	private LoadWordSearchTree() {
		
		tableWST = new TableWordSearchTree();
		
		
		int countAll;
		int index;
		
		int wstn_id; // > 0 in table WordSearchTree
		int parent;  // > 0 in table WordSearchTree
		int word_id; // > 0 in table Word
		String letters;
		int childrenCount;
		
		Word word;
		
		WordSearchTree_Node wstn;
		WordSearchTree_Node allWSTN[];
		

		tableWST.select(tableWST.SELECT_ALL_COLUMNS, "", "ORDER BY level");
		countAll = tableWST.rowCount();
		
		allWSTN = new WordSearchTree_Node[countAll];
		
		for (index = 0; index < countAll; index++) {
			wstn_id = tableWST.getWSTNID(index);
			word_id = tableWST.getWord(index);
			letters = tableWST.getLetters(index);
			parent = tableWST.getParent(index);
			childrenCount = tableWST.getChildren(index);
			
			word = AllWords.getWord(word_id);
			
			wstn = new WordSearchTree_Node(word, letters, childrenCount);
			
			if (parent > 0) {
				allWSTN[parent-1].addChild(wstn);
			}
			
			allWSTN[wstn_id-1] = wstn;
		}
		
		root = allWSTN[0];
	}
	
	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadWordSearchTree getInstance() {
		if (instance == null) {
			instance = new LoadWordSearchTree();
		}
		return instance;
	}

	public WordSearchTree_Node root() {
		return root;
	}
}
