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

public enum Taste {


	nothing(0),
	sweet(1),
	salty(2),
	sour(3),
	bitter(4),
	umami(5);

	private int arrayIndex;

	private Taste(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the Taste.
	 * 
	 * @return Taste's index
	 */
	public int getIndex() {
		return arrayIndex;
	}

	/**
	 * The method returns the taste name which belongs to the parameter
	 * taste index.
	 * 
	 * @param arrayIndex
	 *            taste index
	 * @return taste name
	 */
	public static Taste getName(int arrayIndex) {
		for (Taste element : Taste.values())
			if (element.arrayIndex == arrayIndex)
				return element;
		return null;
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
