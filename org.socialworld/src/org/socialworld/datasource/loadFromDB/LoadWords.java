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

import org.socialworld.collections.WordArray;
import org.socialworld.conversation.Word;
import org.socialworld.conversation.Word_Type;
import org.socialworld.datasource.db.TableWord;
import org.socialworld.knowledge.KnowledgeFact_Criterion;

/**
 * @author Mathias Sikos
 *
 */
public class LoadWords {

	private static LoadWords instance;
	
	TableWord tableWord;
	
	private LoadWords() {
		tableWord = new TableWord();
	}
	
	/**
	 * The method creates the only instance of the LoadAnimal.
	 * 
	 */
	public static LoadWords getInstance() {
		if (instance == null) {
			instance = new LoadWords();
		}
		return instance;
	}

	public WordArray getAllWords() {
		
		int rowCount;
		int index;
		Word element;
		WordArray allWords;
		
		int word_id;
		int type;
		int kfc;
		boolean subjectable;
		String word;
		int pronoun;
		
		// pronouns first (--> ORDER BY type (pronouns have got the smallest type numbers))
		tableWord.select(tableWord.SELECT_ALL_COLUMNS, "", " ORDER BY type");
		rowCount = tableWord.rowCount();
		
		allWords = new WordArray(rowCount);
		
		for (index = 0; index < rowCount; index++) {
			word_id = tableWord.getWordID(index);
			type = tableWord.getType(index);
			kfc = tableWord.getKFC(index);
			if (tableWord.getSubjectable(index) > 0) 
				 subjectable = true;
			else
				 subjectable = false;
			word = tableWord.getWord(index);
			pronoun = tableWord.getPronoun(index);

			element = new Word(
					 word_id, 
					 word,
					 Word_Type.getName(type) ,
					 KnowledgeFact_Criterion.getName(kfc) , 
					 subjectable,
					 allWords.get(pronoun));					
			allWords.set(word_id, element);
		}
		return allWords;
		
	}
}
