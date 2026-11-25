/*
 * Social World
 * Copyright (C) 2022  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
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

public enum Material implements IEnumProperty {

	nothing(0),

	// sorted by groups since 2026-11-24 (composition concept Z1); the old numbers (leather 1 .. paper 5) had no
	// meaning yet. The words are in sw_word under lexem 11000 + index and have to be made again after a change.

	// wood and metals
	wood(1),
	iron(2),
	steel(3),
	bronze(4),
	copper(5),
	tin(6),
	silver(7),
	gold(8),

	// textiles, leather and wax
	linen(9),
	cotton(10),
	wool(11),
	silk(12),
	hemp(13),
	felt(14),
	straw(15),
	fur(16),
	leather(17),
	wax(18),

	// earth, stone and glass
	clay(19),
	stone(20),
	glas(21),

	// from animals, and for writing
	bone(22),
	horn(23),
	feather(24),
	gut(25),
	parchment(26),
	paper(27),
	ink(28),

	// raw materials and plants
	coal(29),
	ore(30),
	plant(31),

	// food and drink: what it is made of (the nutrients are a set of their own)
	grain(32),
	meat(33),
	fish(34),
	milk(35),
	fruit(36),
	vegetable(37),
	water(38),
	salt(39),
	honey(40),
	malt(41),
	fat(42);
	
	private static final Map<String, Material> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, Material> INDEX_CACHE = new HashMap<>();

	static {
		for (Material m : values()) {
			NAME_CACHE.put(m.name(), m);
			INDEX_CACHE.put(m.index, m);
		}
	}

	private int index;

	private Material(int index) {
		this.index = index;
	}

	public static int getMaxIndex() {
		return INDEX_CACHE.size() - 1;
	}
	
	/**
	 * The method returns the index of the material.
	 * 
	 * @return material's index
	 */
	public int getIndex() {
		return index;
	}

	public int getLexemID() {
		int lexemID;
		lexemID = this.index + Lexem.OFFSET_LEXEMID_MATERIAL;
		
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
	 * The method returns the material which belongs to the parameter
	 * material index.
	 * 
	 * @param index
	 *            material index
	 * @return material name
	 */
	public static Material getName(int index) {
		Material m = INDEX_CACHE.get(index);
		return (m != null) ? m : nothing;
	}
	
	/**
	 * The method returns the material which belongs to the string representation
	 * 
	 * @param String 
	 *            material 
	 * @return material 
	 */
	public static Material fromName(String name) {
		Material m = NAME_CACHE.get(name);
		return (m != null) ? m : nothing;
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
		listOfResultingKFCs.add(KnowledgeFact_Criterion.material);
		return listOfResultingKFCs;
	}

}
