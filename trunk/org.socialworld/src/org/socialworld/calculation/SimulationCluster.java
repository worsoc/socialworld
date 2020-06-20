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

public enum SimulationCluster {
	
	unknown(0),
	toBeSet(1),
	knowledge(11),
	percipience(21),
	position(31), pathFinder(32),
	attributeArray(41),
	action(51),
	event(61),
	objectMaster(1001),
	total(8888),
	todo(9998),
	test(9999);
	
	private int index;

	private PropertyUsingAs[] possibleUsingAs;
	
	private static PropertyUsingAs[] emptyPossibleUsingAs = {};
	private static PropertyUsingAs[] possibleUsingAs4ClusterKnowledge = 
		{PropertyUsingAs.pathToKnowledgeSubject, PropertyUsingAs.knowledgeSubject,
		PropertyUsingAs.pathToKnowledgeSource, PropertyUsingAs.knowledgeSource,
		PropertyUsingAs.pathToKnowledgeValue, PropertyUsingAs.knowledgeValue,
		PropertyUsingAs.pathToKnowledgeProperty, PropertyUsingAs.knowledgeProperty,
		PropertyUsingAs.pathToKnowledgeRelationSubject, PropertyUsingAs.knowledgeRelationSubject,
		PropertyUsingAs.pathToKnowledgeRelationVerb, PropertyUsingAs.knowledgeRelationVerb,
		PropertyUsingAs.pathToKnowledgeRelationAdverb, PropertyUsingAs.knowledgeRelationAdverb,
		PropertyUsingAs.pathToKnowledgeRelationObject, PropertyUsingAs.knowledgeRelationObject};
	
	private SimulationCluster(int index) {
		this.index = index;
		initPossibleUsingAs();
	}
	
	public int getIndex() {
		return index;
	}

	static SimulationCluster getName(int index) {
		for (SimulationCluster cluster : SimulationCluster.values())
			if (cluster.index == index)
				return cluster;
		return unknown;
	}
	
	private void initPossibleUsingAs() {
		
		PropertyUsingAs[] result;
		
		switch (this) {
		case knowledge:
			result = possibleUsingAs4ClusterKnowledge;
			break;
		case toBeSet:
		case total:
		case todo:
		case test:
			result = possibleUsingAs4ClusterKnowledge;
			break;
		default:
			result = emptyPossibleUsingAs;
		}
		
		this.possibleUsingAs = result;
	}
	
	public PropertyUsingAs[] getPossibleUsingAs() {
		
		PropertyUsingAs[] copy = this.possibleUsingAs.clone();
		return copy;
		
	}

}
