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

	// index, density g/cm3 (of the stuff itself), value per kg (relative, iron = 10),
	// protection against stab, club, stroke, backhand (0 .. 100, a typical protective piece at form factor 1),
	// ignition temperature in degrees Celsius or NEVER_IGNITES (concept value and protection from the material, 2026-11-26)

	nothing(0, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Constants.NEVER_IGNITES),

	// sorted by groups since 2026-11-24 (composition concept Z1); the old numbers (leather 1 .. paper 5) had no
	// meaning yet. The words are in sw_word under lexem 11000 + index and have to be made again after a change.

	// wood and metals
	wood(1, 0.6f, 2.0f, 35.0f, 45.0f, 40.0f, 35.0f, 300.0f),
	iron(2, 7.87f, 10.0f, 70.0f, 55.0f, 80.0f, 60.0f, Constants.NEVER_IGNITES),
	steel(3, 7.85f, 25.0f, 80.0f, 60.0f, 90.0f, 70.0f, Constants.NEVER_IGNITES),
	bronze(4, 8.8f, 30.0f, 60.0f, 50.0f, 70.0f, 55.0f, Constants.NEVER_IGNITES),
	copper(5, 8.96f, 20.0f, 40.0f, 35.0f, 45.0f, 35.0f, Constants.NEVER_IGNITES),
	tin(6, 7.29f, 25.0f, 20.0f, 20.0f, 25.0f, 20.0f, Constants.NEVER_IGNITES),
	silver(7, 10.5f, 800.0f, 35.0f, 30.0f, 40.0f, 30.0f, Constants.NEVER_IGNITES),
	gold(8, 19.3f, 10000.0f, 25.0f, 25.0f, 30.0f, 25.0f, Constants.NEVER_IGNITES),

	// textiles, leather and wax
	linen(9, 1.5f, 40.0f, 10.0f, 10.0f, 12.0f, 10.0f, 400.0f),
	cotton(10, 1.54f, 30.0f, 8.0f, 10.0f, 10.0f, 8.0f, 400.0f),
	wool(11, 1.31f, 25.0f, 10.0f, 15.0f, 10.0f, 10.0f, 570.0f),
	silk(12, 1.34f, 600.0f, 15.0f, 5.0f, 15.0f, 10.0f, 600.0f),
	hemp(13, 1.48f, 10.0f, 12.0f, 12.0f, 15.0f, 12.0f, 400.0f),
	felt(14, 1.31f, 30.0f, 15.0f, 25.0f, 20.0f, 20.0f, 570.0f),
	straw(15, 1.2f, 0.5f, 3.0f, 8.0f, 3.0f, 3.0f, 230.0f),
	fur(16, 1.1f, 150.0f, 15.0f, 20.0f, 15.0f, 15.0f, 550.0f),
	leather(17, 0.86f, 40.0f, 25.0f, 20.0f, 30.0f, 20.0f, 400.0f),
	wax(18, 0.96f, 30.0f, 2.0f, 3.0f, 2.0f, 2.0f, 300.0f),

	// earth, stone and glass
	clay(19, 2.0f, 0.5f, 20.0f, 10.0f, 25.0f, 20.0f, Constants.NEVER_IGNITES),
	stone(20, 2.6f, 0.2f, 60.0f, 70.0f, 65.0f, 60.0f, Constants.NEVER_IGNITES),
	glas(21, 2.5f, 80.0f, 15.0f, 5.0f, 20.0f, 15.0f, Constants.NEVER_IGNITES),

	// from animals, and for writing
	bone(22, 1.9f, 2.0f, 40.0f, 30.0f, 45.0f, 35.0f, 700.0f),
	horn(23, 1.3f, 10.0f, 45.0f, 40.0f, 50.0f, 40.0f, 600.0f),
	feather(24, 1.3f, 20.0f, 1.0f, 3.0f, 1.0f, 1.0f, 550.0f),
	gut(25, 1.1f, 15.0f, 5.0f, 5.0f, 8.0f, 5.0f, 450.0f),
	parchment(26, 1.2f, 200.0f, 5.0f, 3.0f, 5.0f, 3.0f, 400.0f),
	paper(27, 0.8f, 100.0f, 2.0f, 1.0f, 2.0f, 1.0f, 230.0f),
	ink(28, 1.1f, 100.0f, 0.0f, 0.0f, 0.0f, 0.0f, Constants.NEVER_IGNITES),

	// raw materials and plants
	coal(29, 1.4f, 2.0f, 5.0f, 5.0f, 5.0f, 5.0f, 400.0f),
	ore(30, 4.0f, 3.0f, 30.0f, 30.0f, 30.0f, 30.0f, Constants.NEVER_IGNITES),
	plant(31, 0.8f, 1.0f, 3.0f, 5.0f, 3.0f, 3.0f, 300.0f),

	// food and drink: what it is made of (the nutrients are a set of their own)
	grain(32, 1.3f, 3.0f, 2.0f, 5.0f, 2.0f, 2.0f, 400.0f),
	meat(33, 1.06f, 15.0f, 2.0f, 5.0f, 2.0f, 2.0f, Constants.NEVER_IGNITES),
	fish(34, 1.05f, 10.0f, 1.0f, 3.0f, 1.0f, 1.0f, Constants.NEVER_IGNITES),
	milk(35, 1.03f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, Constants.NEVER_IGNITES),
	fruit(36, 0.85f, 4.0f, 1.0f, 2.0f, 1.0f, 1.0f, Constants.NEVER_IGNITES),
	vegetable(37, 0.95f, 3.0f, 1.0f, 2.0f, 1.0f, 1.0f, Constants.NEVER_IGNITES),
	water(38, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Constants.NEVER_IGNITES),
	salt(39, 2.17f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, Constants.NEVER_IGNITES),
	honey(40, 1.42f, 40.0f, 0.0f, 0.0f, 0.0f, 0.0f, 450.0f),
	malt(41, 1.3f, 5.0f, 2.0f, 4.0f, 2.0f, 2.0f, 400.0f),
	fat(42, 0.92f, 12.0f, 1.0f, 5.0f, 1.0f, 1.0f, 380.0f);
	
	/** The ignition temperature of a stuff that does not burn: never reached, so the formulas need no special case. */
	public static final float NEVER_IGNITES = Constants.NEVER_IGNITES;

	// an enum constant may not use a static field of its own enum, so the value lives here
	private static final class Constants {
		private static final float NEVER_IGNITES = Float.POSITIVE_INFINITY;
	}

	private static final Map<String, Material> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, Material> INDEX_CACHE = new HashMap<>();

	static {
		for (Material m : values()) {
			NAME_CACHE.put(m.name(), m);
			INDEX_CACHE.put(m.index, m);
		}
	}

	private int index;
	private float density;
	private float valuePerKg;
	private float protectionAgainstStab;
	private float protectionAgainstClub;
	private float protectionAgainstStroke;
	private float protectionAgainstBackhand;
	private float ignitionTemperature;

	private Material(int index, float density, float valuePerKg,
			float protectionAgainstStab, float protectionAgainstClub, float protectionAgainstStroke,
			float protectionAgainstBackhand, float ignitionTemperature) {
		this.index = index;
		this.density = density;
		this.valuePerKg = valuePerKg;
		this.protectionAgainstStab = protectionAgainstStab;
		this.protectionAgainstClub = protectionAgainstClub;
		this.protectionAgainstStroke = protectionAgainstStroke;
		this.protectionAgainstBackhand = protectionAgainstBackhand;
		this.ignitionTemperature = ignitionTemperature;
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

	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////    base values    ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	// Only base values of the stuff; factors and formulas belong to the item classes.

	/**
	 * @return the density of the stuff itself in g/cm3 (not of a fabric or a heap with air in it)
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * @return the value per kg, relative (iron = 10), open to the top
	 */
	public float getValuePerKg() {
		return valuePerKg;
	}

	/**
	 * @return the protection against stabs of a typical protective piece of this stuff, 0 .. 100
	 */
	public float getProtectionAgainstStab() {
		return protectionAgainstStab;
	}

	/**
	 * @return the protection against club blows of a typical protective piece of this stuff, 0 .. 100
	 */
	public float getProtectionAgainstClub() {
		return protectionAgainstClub;
	}

	/**
	 * @return the protection against strokes of a typical protective piece of this stuff, 0 .. 100
	 */
	public float getProtectionAgainstStroke() {
		return protectionAgainstStroke;
	}

	/**
	 * @return the protection against backhand blows of a typical protective piece of this stuff, 0 .. 100
	 */
	public float getProtectionAgainstBackhand() {
		return protectionAgainstBackhand;
	}

	/**
	 * @param blow the kind of blow
	 * @return the protection against this kind of blow of a typical protective piece of this stuff, 0 .. 100
	 */
	public float getProtectionAgainst(BlowKind blow) {
		switch (blow) {
		case stab:
			return protectionAgainstStab;
		case club:
			return protectionAgainstClub;
		case stroke:
			return protectionAgainstStroke;
		case backhand:
			return protectionAgainstBackhand;
		default:
			return 0;
		}
	}

	/**
	 * @return the ignition temperature in degrees Celsius, NEVER_IGNITES for a stuff that does not burn
	 */
	public float getIgnitionTemperature() {
		return ignitionTemperature;
	}

	/**
	 * Use this instead of comparing with NEVER_IGNITES.
	 *
	 * @return whether the stuff can burn at all
	 */
	public boolean canBurn() {
		return ignitionTemperature != NEVER_IGNITES;
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
