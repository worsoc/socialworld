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
	
	private static final int sizeRelationLists =
			KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_UNAER + 1 +
			KnowledgeFact_Criterion.MAX_INDEX_RELATION_BINAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_BINAER + 1 +
		   	KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_TRINAER + 1;


	private static final int offsetIndexRelationBinaer = - KnowledgeFact_Criterion.MIN_INDEX_RELATION_BINAER +
		KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_UNAER + 1;

	private static final int offsetIndexRelationTrinaer = - KnowledgeFact_Criterion.MIN_INDEX_RELATION_TRINAER +
			KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_UNAER + 1 +
			KnowledgeFact_Criterion.MAX_INDEX_RELATION_BINAER - KnowledgeFact_Criterion.MIN_INDEX_RELATION_BINAER + 1;
	
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

	public KnowledgeItem getItemFromPool(KnowledgeItem item) {
		
		if (item instanceof KnowledgeProperty) {
			return getPropertyFromPool((KnowledgeProperty) item);
		}
		else if (item instanceof KnowledgeRelation) {
			return getRelationFromPool((KnowledgeRelation) item);
		}
		else {
			return item;
		}
			 
	}
	
	public KnowledgeProperty getPropertyFromPool(KnowledgeProperty property) {
		
		KnowledgeFact_Criterion criterion = property.getCriterion();
		int indexInPool = getPoolsIndex(criterion);

		List<KnowledgeProperty> propertyListForCriterion = propertyListsByCriterion.get(indexInPool);
		
		for (KnowledgeProperty propertyFromPool : propertyListForCriterion) {
			
			if (propertyFromPool.equals(property)) {
				return propertyFromPool;
			}
		}
		
		// property is not in pool yet. Add to pool
		propertyListsByCriterion.get(indexInPool).add(property);
		
		return property;
		
	}
	
	public KnowledgeRelation getRelationFromPool(KnowledgeRelation relation) {
		
		KnowledgeFact_Criterion criterion = relation.getCriterion();
		int indexInPool = getPoolsIndex(criterion);

		List<KnowledgeRelation> relationListForCriterion = relationListsByCriterion.get(indexInPool);
		
		for (KnowledgeRelation relationFromPool : relationListForCriterion) {
			
			if (relationFromPool.equals(relation)) {
				return relationFromPool;
			}
		}
		
		// relation is not in pool yet. Add to pool
		relationListsByCriterion.get(indexInPool).add(relation);
		
		return relation;
		
	}

	public KnowledgeFact getFactForLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		
		KnowledgeFact result;
		
		List<KnowledgeFact> facts = findLexems(criterion,  lexems, false);
		
		if (facts.size() >= 1 ) {
			result = facts.get(0);
		}
		else {
			result = newEntry(criterion, lexems);
		}
		
		return result;
	}
	
	
	public List<KnowledgeFact> _findLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		return findLexems(criterion, lexems, true);
	}

	private List<KnowledgeFact> findLexems(KnowledgeFact_Criterion criterion, List<Lexem> lexems, boolean lexemsBeingSubsetToo) {
		
		int criterionsIndex = criterion.getIndex();
		int indexInPool = 0;
		
		List<KnowledgeFact> result = new ArrayList<KnowledgeFact>();
		
		if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_PROPERTY) {
		
			List<KnowledgeProperty> propertyList;
			indexInPool = criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_PROPERTY;
			propertyList = propertyListsByCriterion.get(indexInPool);
			
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
		else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER) {
		
			List<KnowledgeRelation> relationList;
			
			if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER) {
				indexInPool = criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_RELATION_UNAER;
			}
			else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_BINAER) {
				indexInPool = criterionsIndex + offsetIndexRelationBinaer;
			}
			else {
				indexInPool = criterionsIndex + offsetIndexRelationTrinaer;
			}
			relationList = relationListsByCriterion.get(indexInPool);
			
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
		
		size = sizeRelationLists;
		relationListsByCriterion = new ArrayList<List<KnowledgeRelation>>(size) ;
		for (int i = 0; i < size; i++) {
			List<KnowledgeRelation> relationList = new ArrayList<KnowledgeRelation>();
			relationListsByCriterion.set(i, relationList);
		}

	}
	
	private KnowledgeFact newEntry(KnowledgeFact_Criterion criterion, List<Lexem> lexems) {
		
		KnowledgeFact newFact = null;
		KnowledgeFact_Atoms atoms;
		
		int criterionsIndex = criterion.getIndex();
		int indexInPool = getPoolsIndex(criterion) ;

		if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_PROPERTY) {

			atoms = new KnowledgeFact_Atoms(KnowledgeFact_Atoms.translateToAtoms(lexems));
			newFact = new KnowledgeProperty(criterion, atoms);
			propertyListsByCriterion.get(indexInPool).add((KnowledgeProperty)newFact);
		
		}
		else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER) {
			
			if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER) {
				newFact = new KnowledgeRelationUnaer(lexems);
			}
			else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_BINAER) {
				newFact = new KnowledgeRelationBinaer(lexems);
			}
			else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER) {
				newFact = new KnowledgeRelationTrinaer(lexems);
			}
			
			if (newFact != null)	relationListsByCriterion.get(indexInPool).add((KnowledgeRelation)newFact);			
		}

		return newFact;
		
	}
	
	private int getPoolsIndex(KnowledgeFact_Criterion criterion) {
		
		int indexInPool = -1;
		int criterionsIndex = criterion.getIndex();
		
		if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_PROPERTY) {
			indexInPool = criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_PROPERTY;
		}
		else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER) {
			
			if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_UNAER) {
				indexInPool = criterionsIndex - KnowledgeFact_Criterion.MIN_INDEX_RELATION_UNAER;
			}
			else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_BINAER) {
				indexInPool = criterionsIndex + offsetIndexRelationBinaer;
			}
			else if (criterionsIndex <= KnowledgeFact_Criterion.MAX_INDEX_RELATION_TRINAER) {
				indexInPool = criterionsIndex + offsetIndexRelationTrinaer;
			}
		}

		return indexInPool;
		
	}
	
}
