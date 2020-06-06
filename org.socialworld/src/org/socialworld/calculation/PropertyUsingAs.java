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
	pathToKnowledgeSubject(1), knowledgeSubject(2),
	pathToKnowledgeSource(11), knowledgeSource(12),
	pathToKnowledgeValue(21), knowledgeValue(22),
	pathToKnowledgeProperty(31), knowledgeProperty(32),
	pathToKnowledgeRelationSubject(41), knowledgeRelationSubject(42),
	pathToKnowledgeRelationVerb(51), knowledgeRelationVerb(52),
	pathToKnowledgeRelationAdverb(61), knowledgeRelationAdverb(62),
	pathToKnowledgeRelationObject(71), knowledgeRelationObject(72);

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
