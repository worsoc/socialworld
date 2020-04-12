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
import org.socialworld.conversation.Lexem;


public class KnowledgeFactPool {
	
	private static KnowledgeFactPool instance;
	KnowledgeAtomList[] factListsByCriterion;
	
	private KnowledgeFactPool() {
		factListsByCriterion = new KnowledgeAtomList[KnowledgeFact_Criterion.NUMBER_OF_KNOWLEDGE_FACT_CRITERION];
		
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
	
	public KnowledgeFact find(KnowledgeFact_Criterion criterion, Lexem value) {
		KnowledgeFact fact;
		
		fact = factListsByCriterion[criterion.getIndex()].find(value);
		
		return fact;
	}
	
	private void createPool() {
	}
	
}
