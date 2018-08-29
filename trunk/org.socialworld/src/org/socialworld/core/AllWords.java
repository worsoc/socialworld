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
package org.socialworld.core;

import org.socialworld.collections.Array;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word;
import org.socialworld.datasource.loadFromDB.LoadWords;

/**
 * @author Mathias Sikos
 *
 */
public class AllWords {

	private static AllWords instance;

	private static Array<Lexem> allLexemsbyLexemID;
	private static Array<Word> allWordsbyWordID;
	
	private static boolean isLoading = false;
	
	private  AllWords() {
		allLexemsbyLexemID = LoadWords.getInstance().getAllLexems();
		isLoading = true;
		allWordsbyWordID = LoadWords.getInstance().getAllWords();
		isLoading = false;
	}
	
	public static Lexem getLexem(int lexemID) {
		if ( (instance == null) && (isLoading == false) ) {
			instance = new AllWords();
		}
		return allLexemsbyLexemID.get(lexemID);
	}
	
	public static Word getWord(int wordID) {
		if  (instance == null) {
			instance = new AllWords();
		}
		return allWordsbyWordID.get(wordID);
	}
	
}