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

import org.socialworld.collections.Array;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;
import org.socialworld.conversation.Word_Type;
import org.socialworld.core.AllWords;
import org.socialworld.datasource.tablesSimulation.TableLexem;
import org.socialworld.datasource.tablesSimulation.ViewWordJoinLexem;

/**
 * @author Mathias Sikos
 *
 */
public class LoadWords {

	private static LoadWords instance;
	
	ViewWordJoinLexem viewWordJoinLexem;
	TableLexem tableLexem;
	
	private LoadWords() {
		tableLexem = new TableLexem();
		viewWordJoinLexem = new ViewWordJoinLexem();
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

	public Array<Lexem> getAllLexems() {

		int rowCount;
		int index;

		Array<Lexem> allLexems;
		
		int lexem_id;
		Lexem element;
		
		int type;
		Word_Type word_type;
		
		boolean subjectable;
	
		tableLexem.select(tableLexem.SELECT_ALL_COLUMNS, "", " ORDER BY type");
		rowCount = tableLexem.rowCount();
		
		allLexems = new Array<Lexem>(rowCount);
	
		for (index = 0; index < rowCount; index++) {
			lexem_id = tableLexem.getLexemID(index);
			if (tableLexem.getSubjectable(index) > 0)	subjectable = true;
			else subjectable = false;
			type = tableLexem.getType(index);
			word_type = Word_Type.getName(type);
			
			element = new Lexem(
					lexem_id, 
					word_type,
					 subjectable);					
			allLexems.set(lexem_id, element);
		}
		
		return allLexems;
	}

	public Array<Word> getAllWords() {
		
		int rowCount;
		int index;
		
		Array<Word> allWords;
		
		int word_id;
		Word element;
		String word;
		
		int lexem_id;
		Lexem lexem;
		
		int pronoun_word_id;
		Word pronoun;
		
		// pronouns first (--> ORDER BY type (pronouns have got the smallest type numbers))
		viewWordJoinLexem.select(viewWordJoinLexem.SELECT_ALL_COLUMNS, "", " ORDER BY type");
		rowCount = viewWordJoinLexem.rowCount();
		
		allWords = new Array<Word>(rowCount);
		
		for (index = 0; index < rowCount; index++) {
			word_id = viewWordJoinLexem.getWordID(index);
			word = viewWordJoinLexem.getWord(index);
			lexem_id = viewWordJoinLexem.getLexemID(index);
			pronoun_word_id = viewWordJoinLexem.getPronounWordID(index);

			lexem = AllWords.getLexem(lexem_id);
			pronoun = allWords.get(pronoun_word_id);
			
			element = new Word(
					 word_id, 
					 word,
					 lexem,
					 pronoun);					
			allWords.set(word_id, element);
		}
		return allWords;
		
	}
}