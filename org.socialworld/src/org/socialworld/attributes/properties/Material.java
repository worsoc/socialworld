package org.socialworld.attributes.properties;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringPair;

public enum Material {

	leather(1);
	
	private int arrayIndex;

	private Material(int index) {
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
	public static Material getName(int arrayIndex) {
		for (Material element : Material.values())
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
		listOfResultingKFCs.add(KnowledgeFact_Criterion.material);
		return listOfResultingKFCs;
	}

}
