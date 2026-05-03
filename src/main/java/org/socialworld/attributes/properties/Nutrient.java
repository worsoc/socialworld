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

public enum Nutrient implements IEnumProperty {

	nothing(0),
	fat(1),
	protein(2),
	carbohydrates(3),
	water(4),
	roughage(5),
	vitamins(6),
	minerals(7);

	private static final Map<String, Nutrient> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, Nutrient> INDEX_CACHE = new HashMap<>();

	static {
		for (Nutrient n : values()) {
			NAME_CACHE.put(n.name(), n);
			INDEX_CACHE.put(n.index, n);
		}
	}
	private int index;

	private Nutrient(int index) {
		this.index = index;
	}

	public static int getMaxIndex() {
		return INDEX_CACHE.size() - 1;
	}

	/**
	 * The method returns the index of the Nutrient.
	 * 
	 * @return Nutrient's index
	 */
	public int getIndex() {
		return index;
	}

	public int getLexemID() {
		int lexemID;
		lexemID = this.index + Lexem.OFFSET_LEXEMID_NUTRIENT;
		
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
	 * The method returns the nutrient which belongs to the parameter
	 * nutrient index.
	 * 
	 * @param index
	 *            nutrient index
	 * @return nutrient 
	 */
	public static Nutrient getName(int index) {
		Nutrient n = INDEX_CACHE.get(index);
		return (n != null) ? n : nothing;
	}
	
	/**
	 * The method returns the nutrient which belongs to the string representation
	 * 
	 * @param String 
	 *            nutrient 
	 * @return nutrient 
	 */
	public static Nutrient fromName(String name) {
		Nutrient n = NAME_CACHE.get(name);
		return (n != null) ? n : nothing;
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
		listOfResultingKFCs.add(KnowledgeFact_Criterion.nutrient);
		return listOfResultingKFCs;
	}

}
