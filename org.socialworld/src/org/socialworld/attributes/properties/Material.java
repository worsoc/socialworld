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
import java.util.List;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringTupel;

public enum Material {

	nothing(0),  
	leather(1),
	glas(2),
	cotton(3),
	iron(4);
	
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
		return nothing;  // instead of null 
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
