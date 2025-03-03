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


public enum PropertyUsingAs {
	
	dontCheck(0),

	pathToKnowledge(11001), knowledge(11002),
	pathToKnowledgeSubject(11021), knowledgeSubject(11022),
	pathToKnowledgeSource(11011), knowledgeSource(11012),
	pathToKnowledgeProperty(11031), knowledgeProperty(11032),
	pathToKnowledgeRelationSubject(11041), knowledgeRelationSubject(11042),
	pathToKnowledgeRelationVerb(11051), knowledgeRelationVerb(11052),
	pathToKnowledgeRelationAdverb(11061), knowledgeRelationAdverb(11062),
	pathToKnowledgeRelationObject(11071), knowledgeRelationObject(11072),
	pathToKnowledgeValue(11081), knowledgeValue(11082),

	pathToPosition(31001), position(31002),

	pathToAttributeArray(41001), attributeArray(41002),

	pathToAction(51001), action(51002),
	pathToActionAttack(51011), actionAttack(41012),
	pathToActionBodilyFunction(51021), actionBodilyFunction(51022),
	pathToActionHandle(51031), actionHandle(51032),
	pathToActionHear(51041), actionHear(51042),
	pathToActionMove(51051), actionMove(51052),
	pathToActionSay(51061), actionSay(51062),

	todo(9999999);


	private int index;

	private PropertyUsingAs(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public static PropertyUsingAs getName(int index) {
		for (PropertyUsingAs permission : PropertyUsingAs.values())
			if (permission.index == index)
				return permission;
		return dontCheck;
	}
	
	public PropertyUsingAs getPathPermission(PropertyUsingAs finalValuePermission) {
		return getName(finalValuePermission.index - 1);
	}
	
	public SimulationCluster getSimulationCluster() {
		
		if (this.index == 0) return SimulationCluster.unknown;
		else if (this.index < 100) return SimulationCluster.knowledge;
		else return SimulationCluster.unknown;
		
	}

}
