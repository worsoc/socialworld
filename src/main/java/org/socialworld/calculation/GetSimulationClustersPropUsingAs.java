/*
* Social World
* Copyright (C) 2025  Mathias Sikos
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

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

class GetSimulationClustersPropUsingAs {

	
	private static PropertyUsingAs[] emptyPossibleUsingAs = {};
	private static List<PropertyUsingAs> listEmptyPossibleUsingAs = Arrays.asList(emptyPossibleUsingAs);


	private static PropertyUsingAs[] possibleUsingAs4ClusterAttributeArray = 
		{PropertyUsingAs.pathToAttributeArray, PropertyUsingAs.attributeArray//, PropertyUsingAs.attributeArrayCalculator
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterAttributeArray = Arrays.asList(possibleUsingAs4ClusterAttributeArray);
	
	private static PropertyUsingAs[] possibleUsingAs4ClusterPosition = 
		{PropertyUsingAs.pathToPosition, PropertyUsingAs.position,// PropertyUsingAs.positionCalculator
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterPosition = Arrays.asList(possibleUsingAs4ClusterPosition);
	
	private static PropertyUsingAs[] possibleUsingAs4ClusterPercipience = 
		{PropertyUsingAs.pathToPercipience, PropertyUsingAs.percipience
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterPercipience = Arrays.asList(possibleUsingAs4ClusterPercipience);
	
	private static PropertyUsingAs[] possibleUsingAs4ClusterAction = 
		{PropertyUsingAs.pathToAction, PropertyUsingAs.action, // PropertyUsingAs.actionCreator,
		PropertyUsingAs.pathToActionAttack, PropertyUsingAs.actionAttack,
		PropertyUsingAs.pathToActionBodilyFunction, PropertyUsingAs.actionBodilyFunction,
		PropertyUsingAs.pathToActionHandle, PropertyUsingAs.actionHandle,
		PropertyUsingAs.pathToActionHear, PropertyUsingAs.actionHear,
		PropertyUsingAs.pathToActionMove, PropertyUsingAs.actionMove,
		PropertyUsingAs.pathToActionSay, PropertyUsingAs.actionSay
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterAction = Arrays.asList(possibleUsingAs4ClusterAction);
	
	private static PropertyUsingAs[] possibleUsingAs4ClusterKnowledge = 
		{//PropertyUsingAs.knowledgeCalculator,
		PropertyUsingAs.pathToKnowledgeSubject, PropertyUsingAs.knowledgeSubject,
		PropertyUsingAs.pathToKnowledgeSource, PropertyUsingAs.knowledgeSource,
		PropertyUsingAs.pathToKnowledgeValue, PropertyUsingAs.knowledgeValue,
		PropertyUsingAs.pathToKnowledgeProperty, PropertyUsingAs.knowledgeProperty,
		PropertyUsingAs.pathToKnowledgeRelationSubject, PropertyUsingAs.knowledgeRelationSubject,
		PropertyUsingAs.pathToKnowledgeRelationVerb, PropertyUsingAs.knowledgeRelationVerb,
		PropertyUsingAs.pathToKnowledgeRelationAdverb, PropertyUsingAs.knowledgeRelationAdverb,
		PropertyUsingAs.pathToKnowledgeRelationObject, PropertyUsingAs.knowledgeRelationObject
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterKnowledge = Arrays.asList(possibleUsingAs4ClusterKnowledge);

	private static PropertyUsingAs[] possibleUsingAs4ClusterSimulationObject = 
		{PropertyUsingAs.object
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterSimulationObject = Arrays.asList(possibleUsingAs4ClusterSimulationObject);

	private static PropertyUsingAs[] possibleUsingAs4ClusterObjectMaster = 
		{PropertyUsingAs.object
		};
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterObjectMaster = Arrays.asList(possibleUsingAs4ClusterObjectMaster);

	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterExpressionOrFunction;
	
	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterCore;

	private static List<PropertyUsingAs> listPossibleUsingAs4ClusterVisualize;

	static List<PropertyUsingAs> getPossibleUsingAs(SimulationCluster cluster) {
		
		List<PropertyUsingAs> result;
		List<List<PropertyUsingAs>> listsForUnion; 
		
		switch(cluster) {
		case total:
		case test:
		case toBeSet:
			result = listEmptyPossibleUsingAs;
			break;
		case simulationObject:
			result = listPossibleUsingAs4ClusterSimulationObject;
			break;
		case knowledge:
			result = listPossibleUsingAs4ClusterKnowledge;
			break;
		case percipience:
			result = listPossibleUsingAs4ClusterPercipience;
			break;
		case position:
			result = listPossibleUsingAs4ClusterPosition;
			break;
		case attributeArray:
			result = listPossibleUsingAs4ClusterAttributeArray;
			break;
		case action:
			result = listPossibleUsingAs4ClusterAction;
			break;
		case objectMaster:
			result = listPossibleUsingAs4ClusterObjectMaster;
			break;
		case expressionEvaluate:
			if (listPossibleUsingAs4ClusterExpressionOrFunction == null) {
				listsForUnion = new ArrayList<List<PropertyUsingAs>>();
				listsForUnion.add(listPossibleUsingAs4ClusterAttributeArray);
				// TODO filling listsForUnion for listPossibleUsingAs4ClusterExpressionOrFunction
				listPossibleUsingAs4ClusterExpressionOrFunction = getUnion(listsForUnion);
			}
			result = listPossibleUsingAs4ClusterExpressionOrFunction;
			break;
		case expressionCalculate:
			if (listPossibleUsingAs4ClusterExpressionOrFunction == null) {
				listsForUnion = new ArrayList<List<PropertyUsingAs>>();
				listsForUnion.add(listPossibleUsingAs4ClusterAttributeArray);
				// TODO filling listsForUnion for listPossibleUsingAs4ClusterExpressionOrFunction
				listPossibleUsingAs4ClusterExpressionOrFunction = getUnion(listsForUnion);
			}
			result = listPossibleUsingAs4ClusterExpressionOrFunction;
			break;
		case function:
			if (listPossibleUsingAs4ClusterExpressionOrFunction == null) {
				listsForUnion = new ArrayList<List<PropertyUsingAs>>();
				listsForUnion.add(listPossibleUsingAs4ClusterAttributeArray);
				// TODO filling listsForUnion for listPossibleUsingAs4ClusterExpressionOrFunction
				listPossibleUsingAs4ClusterExpressionOrFunction = getUnion(listsForUnion);
			}
			result = listPossibleUsingAs4ClusterExpressionOrFunction;
			break;
		case core:
			if (listPossibleUsingAs4ClusterCore == null) {
				listsForUnion = new ArrayList<List<PropertyUsingAs>>();
				listsForUnion.add(listPossibleUsingAs4ClusterAttributeArray);
				// TODO filling listsForUnion for listPossibleUsingAs4ClusterCore
				listPossibleUsingAs4ClusterCore = getUnion(listsForUnion);
			}
			result = listPossibleUsingAs4ClusterCore;
			break;
		case visualize:
			if (listPossibleUsingAs4ClusterVisualize == null) {
				listsForUnion = new ArrayList<List<PropertyUsingAs>>();
				listsForUnion.add(listPossibleUsingAs4ClusterAttributeArray);
				// TODO filling listsForUnion for listPossibleUsingAs4ClusterVisualize
				listPossibleUsingAs4ClusterVisualize = getUnion(listsForUnion);
			}
			result = listPossibleUsingAs4ClusterVisualize;
			break;
			
		default:
			result = listEmptyPossibleUsingAs;
			
		}
		
		return result;
	}

	private static List<PropertyUsingAs> getUnion(List<List<PropertyUsingAs>> lists) {
		List<PropertyUsingAs> result = new ArrayList<PropertyUsingAs>();
		
		for (List<PropertyUsingAs> list : lists) {
			result.addAll(list);
		}
		return result;
	}
	
}
