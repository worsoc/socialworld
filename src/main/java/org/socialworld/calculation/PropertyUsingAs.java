/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.calculation;

import java.util.HashMap;
import java.util.Map;

public enum PropertyUsingAs {
	
	dontCheck(0),

	object(1001),
	
	pathToKnowledge(11001), knowledge(11002), // knowledgeCalculator(11003),
	pathToKnowledgeSubject(11021), knowledgeSubject(11022),
	pathToKnowledgeSource(11011), knowledgeSource(11012),
	pathToKnowledgeProperty(11031), knowledgeProperty(11032),
	pathToKnowledgeRelationSubject(11041), knowledgeRelationSubject(11042),
	pathToKnowledgeRelationVerb(11051), knowledgeRelationVerb(11052),
	pathToKnowledgeRelationAdverb(11061), knowledgeRelationAdverb(11062),
	pathToKnowledgeRelationObject(11071), knowledgeRelationObject(11072),
	pathToKnowledgeValue(11081), knowledgeValue(11082),

	pathToPercipience(21001), percipience(21002),

	pathToPosition(31001), position(31002), //positionCalculator(31003),

	pathToAttributeArray(41001), attributeArray(41002), //attributeArrayCalculator(41003),

	pathToAction(51001), action(51002), //actionCreator(51003),
	pathToActionAttack(51011), actionAttack(51012),
	pathToActionBodilyFunction(51021), actionBodilyFunction(51022),
	pathToActionHandle(51031), actionHandle(51032),
	pathToActionHear(51041), actionHear(51042),
	pathToActionMove(51051), actionMove(51052),
	pathToActionSay(51061), actionSay(51062),

	todo(9999999);

	private static final Map<String, PropertyUsingAs> NAME_CACHE = new HashMap<>();
	private static final Map<Integer, PropertyUsingAs> INDEX_CACHE = new HashMap<>();

	static {
		for (PropertyUsingAs p : values()) {
			NAME_CACHE.put(p.name(), p);
			INDEX_CACHE.put(p.index, p);
		}
	}

	private final int index;

	private PropertyUsingAs(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static PropertyUsingAs getName(int index) {
		PropertyUsingAs permission = INDEX_CACHE.get(index);
		return (permission != null) ? permission : dontCheck;
	}
	
	public static PropertyUsingAs fromName(String name) {
		PropertyUsingAs permission = NAME_CACHE.get(name);
		return (permission != null) ? permission : dontCheck;
	}

	public PropertyUsingAs getPathPermission(PropertyUsingAs finalValuePermission) {
		return getName(finalValuePermission.index - 1);
	}
	
	public SimulationCluster getSimulationCluster() {
		if (this.index == 0) return SimulationCluster.unknown;
		
		int clusterPrefix = this.index / 1000; 
		switch (clusterPrefix) {
			case 11: return SimulationCluster.knowledge;
			case 21: return SimulationCluster.percipience;
			case 31: return SimulationCluster.position;
			case 41: return SimulationCluster.attributeArray;
			case 51: return SimulationCluster.action;
			case 1:  return SimulationCluster.simulationObject; // für 'object' (1001)
			default: return SimulationCluster.unknown;
		}
	}

}
