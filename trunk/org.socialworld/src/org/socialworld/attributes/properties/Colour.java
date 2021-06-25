package org.socialworld.attributes.properties;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringPair;
import org.socialworld.visualize.SimVisual;

public enum Colour {

	
	black(000000000),
	dimgray(105105105),
	gray(128128128),
	darkgray(169169169),
	silver(192192192);
	
	public Color getColour(Colour colour) {
		switch(colour) {
			case black: return SimVisual.COLOR_BLACK;
			case dimgray: return SimVisual.COLOR_DIMGRAY;
			case gray: return SimVisual.COLOR_GRAY;
			case darkgray: return SimVisual.COLOR_DARKGRAY;
			case silver: return SimVisual.COLOR_SILVER;
			
			default:return SimVisual.COLOR_BLACK;
		}
	}
	
	
	
	
	private int arrayIndex;

	private Colour(int index) {
		this.arrayIndex = index;
	}
	
	/**
	 * The method returns the index of the material.
	 * 
	 * @return material's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the material name which belongs to the parameter
	 * material index.
	 * 
	 * @param arrayIndex
	 *            material index
	 * @return material name
	 */
	public static Colour getName(int arrayIndex) {
		for (Colour element : Colour.values())
			if (element.arrayIndex == arrayIndex)
				return element;
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////meta information    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<StringPair> getPropMethodsMetaInfos() {
		List<StringPair> listOfPropMethodMetaInfo = new ArrayList<StringPair>();
		listOfPropMethodMetaInfo.add(new StringPair(SimulationMetaInformation.CLASSNAME_ENUM_INDEX, SimulationMetaInformation.METHODNAME_ENUM_GETINDEX));
		return listOfPropMethodMetaInfo;
	}

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = new ArrayList<KnowledgeFact_Criterion>();
		listOfResultingKFCs.add(KnowledgeFact_Criterion.colour);
		return listOfResultingKFCs;
	}
	
	
}
