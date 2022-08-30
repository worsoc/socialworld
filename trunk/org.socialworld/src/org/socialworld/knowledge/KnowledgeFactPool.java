/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.knowledge;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.conversation.Lexem;


public class KnowledgeFactPool {
	
	private static KnowledgeFactPool instance;
	List<List<KnowledgeProperty>> propertyListsByCriterion;
	List<List<KnowledgeRelation>> relationListsByCriterion;
	
	private KnowledgeFactPool() {

		createPool();
		
	}

	/**
	 * The method gets back the only instance of the KnowledgeFactPool.
	 * 
	 * @return singleton object of KnowledgeFactPool
	 */
	public static KnowledgeFactPool getInstance() {
		if (instance == null) instance = new KnowledgeFactPool();
		return instance;
	}
	
	public List<KnowledgeFact> findLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		
		List<KnowledgeFact> result = new ArrayList<KnowledgeFact>();
		KnowledgeFact fact;
		
		List<KnowledgeProperty> propertyList;
		propertyList = propertyListsByCriterion.get(criterion.getIndex());
		
		for (KnowledgeProperty property : propertyList) {
			
			if (property.checkForLexems(lexems)) {
				result.add(property);
			}
			
		}
		
		List<KnowledgeRelation> relationList;
		relationList = relationListsByCriterion.get(criterion.getIndex());
		
		for (KnowledgeRelation relation : relationList) {
			
			if (relation.checkForLexems(lexems)) {
				result.add(relation);
			}
			
		}

		return result;
	}
	
	private void createPool() {
		
		propertyListsByCriterion = new ArrayList<List<KnowledgeProperty>>(KnowledgeFact_Criterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION);
		for (int i = 0; i < KnowledgeFact_Criterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION; i++) {
			List<KnowledgeProperty> propertyList = new ArrayList<KnowledgeProperty>();
			propertyListsByCriterion.set(i, propertyList);
		}

	}
	
}
