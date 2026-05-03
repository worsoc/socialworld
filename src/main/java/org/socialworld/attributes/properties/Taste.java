/*
* Social World
* Copyright (C) 2022  Mathias Sikos
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
package org.socialworld.attributes.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Word_Type;
import org.socialworld.core.AllWords;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringTupel;

public enum Taste implements IEnumProperty {


	nothing(0),
	sweet(1),
	salty(2),
	sour(3),
	bitter(4),
	umami(5);

	private static final Map<String, Taste> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, Taste> INDEX_CACHE = new HashMap<>();

	static {
		for (Taste t : values()) {
			NAME_CACHE.put(t.name(), t);
			INDEX_CACHE.put(t.index, t);
		}
	}

	private int index;

	public static int getMaxIndex() {
		return INDEX_CACHE.size() - 1;
	}

	private Taste(int index) {
		this.index = index;
	}


	/**
	 * The method returns the index of the Taste.
	 * 
	 * @return Taste's index
	 */
	public int getIndex() {
		return index;
	}

	public int getLexemID() {
		int lexemID;
		lexemID = this.index + Lexem.OFFSET_LEXEMID_TASTE;
		
		return lexemID;
	}

	public Lexem getLexem() {
		Lexem lexem;
		int lexemID = getLexemID();
		
		lexem = AllWords.getLexem(lexemID);
		if (lexem == null) {
			lexem = new Lexem( lexemID,  Word_Type.adjective , false);
		}
		return lexem;
	}

	/**
	 * The method returns the taste name which belongs to the parameter
	 * taste index.
	 * 
	 * @param index
	 *            taste index
	 * @return taste name
	 */
	public static Taste getName(int index) {
		Taste t = INDEX_CACHE.get(index);
		return (t != null) ? t : nothing;
	}
	
	/**
	 * The method returns the taste which belongs to the string representation
	 * 
	 * @param String 
	 *            taste 
	 * @return taste 
	 */
	public static Taste fromName(String name) {
		Taste t = NAME_CACHE.get(name);
		return (t != null) ? t : nothing;
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////meta information    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = new ArrayList<StringTupel>();
		listOfPropMethodMetaInfo.add(new StringTupel(SimulationMetaInformation.CLASSNAME_ENUM_INDEX, SimulationMetaInformation.METHODNAME_ENUM_GETINDEX));
		return listOfPropMethodMetaInfo;
	}

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = new ArrayList<KnowledgeFact_Criterion>();
		listOfResultingKFCs.add(KnowledgeFact_Criterion.taste);
		return listOfResultingKFCs;
	}

}
