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

	
	public KnowledgeFact getFactForLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		
		KnowledgeFact result;
		
		List<KnowledgeFact> facts = findLexems(criterion,  lexems, false);
		
		if (facts.size() >= 1 ) {
			result = facts.get(0);
		}
		else {
			// TODO new entry in pool
			result = newEntry(criterion, lexems);
		}
		
		return result;
	}
	
	
	public List<KnowledgeFact> findLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		return findLexems(criterion, lexems, true);
	}

	private List<KnowledgeFact> findLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems, boolean lexemsBeingSubsetToo) {
		
		int criterionsIndex = criterion.getIndex();
		
		List<KnowledgeFact> result = new ArrayList<KnowledgeFact>();
		
		if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_PROPERTY) {
		
			List<KnowledgeProperty> propertyList;
			propertyList = propertyListsByCriterion.get(criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_PROPERTY);
			
			for (KnowledgeProperty property : propertyList) {
				
				if (lexemsBeingSubsetToo) {
					if (property.checkForLexems(lexems)) {
						result.add(property);
					}
				}
				else {
					if (property.checkForLexems(lexems)) {
						result.add(property);
					}
				}
			}
		}
		else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION) {
		
			List<KnowledgeRelation> relationList;
			relationList = relationListsByCriterion.get(criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_RELATION);
			
			for (KnowledgeRelation relation : relationList) {
				
				if (lexemsBeingSubsetToo) {
					if (relation.checkForLexems(lexems)) {
						result.add(relation);
					}
				}
				else {
					if (relation.checkForLexems(lexems)) {
						result.add(relation);
					}
				}
				
			}

		}

		return result;
	}
	
	private void createPool() {
		
		int size;
		
		size = KnowledgeFact_Criterion.MAX_INDEX_PROPERTY + 1;
		propertyListsByCriterion = new ArrayList<List<KnowledgeProperty>>(size);
		for (int i = 0; i < size; i++) {
			List<KnowledgeProperty> propertyList = new ArrayList<KnowledgeProperty>();
			propertyListsByCriterion.set(i, propertyList);
		}

		size = KnowledgeFact_Criterion.MAX_INDEX_RELATION - KnowledgeFact_Criterion.MIN_INDEX_RELATION + 1;
		relationListsByCriterion = new ArrayList<List<KnowledgeRelation>>(size) ;
		for (int i = 0; i < size; i++) {
			List<KnowledgeRelation> relationList = new ArrayList<KnowledgeRelation>();
			relationListsByCriterion.set(i, relationList);
		}

	}
	
}
