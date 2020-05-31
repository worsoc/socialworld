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
package org.socialworld.calculation.expressions;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Expression;
import org.socialworld.calculation.PropertyUsingAs;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.knowledge.KnowledgeAtomType;

public class CreateKnowledgeElementExpression extends CreateValue {

	
	public static String LABEL_SUBJECT = "KSbj:";
	public static String LABEL_KNOWLEDGESOURCETYPE = "KSrcT:";
	public static String LABEL_KNOWLEDGESOURCE = "KSrc:";
	public static String LABEL_KNOWLEDGEVALUE = "KVal:";
	public static String LABEL_KNOWLEDGEFACTCRITERION = "KFC:";
	public static String LABEL_KNOWLEDGEPROPERTY = "KProp:";
	public static String LABEL_KNOWLEDGERELATIONSUBJECT = "KRelSub:";
	public static String LABEL_KNOWLEDGERELATIONVERB = "KRelVrb:";
	public static String LABEL_KNOWLEDGERELATIONADVERB = "KRelAdv:";
	public static String LABEL_KNOWLEDGERELATIONOBJECT1 = "KRelObj1:";
	public static String LABEL_KNOWLEDGERELATIONOBJECT2 = "KRelObj2:";
	
	public CreateKnowledgeElementExpression(String description) {
		
		super(Type.knowledgeElement);
			
		String main[];
		main = description.split(";");
		
		// at main index = 0: expression for subject lexem  (GetLexem)
		// at main index > 0: expressions for some KnowledgeSource/KnowledgeAtomcombinations  --> KnowledgeAtomList

		// for any sub entry (element of the KnowledgeAtomList):
		// at sub index 0: expression for KnowledgeSourceType
		// at sub index 1: expression for KnowledgeSource simulation object
		// at sub index > 1: one or more expressions, describing the KnowledgeAtom

		if (main.length > 1) {
			
			List<Expression> listExpressions = new ArrayList<Expression>();;
			
			String descriptionSubject = main[0].substring(LABEL_SUBJECT.length());
			Expression subject = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSubject, descriptionSubject, "subject");
			
			listExpressions.add(subject);
			
			for (int indexMain = 1; indexMain < main.length; indexMain++) {
			
				String descriptionKnowledgeAtomList[] = main[indexMain].split(",");
			
				if (descriptionKnowledgeAtomList.length > 2) {
					
					String descriptionKnowledgeSourceType = descriptionKnowledgeAtomList[0].substring(LABEL_KNOWLEDGESOURCETYPE.length());
					Expression knowledgeSourcetype = new Constant(new Value(Type.integer, "sourceType", Integer.parseInt(descriptionKnowledgeSourceType) ));
					
					
					String descriptionKnowledgeSource = descriptionKnowledgeAtomList[1].substring(LABEL_KNOWLEDGESOURCE.length());
					Expression knowledgeSource = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSource, descriptionKnowledgeSource, "source");
					
					Expression creationKnowledgeSource = new CreateKnowledgeSourceExpression(knowledgeSourcetype, knowledgeSource);
					
					//////////////////////////////////////////////////////////
					
					
					String descriptionKnowledgeAtomPart;
					Expression knowledgeFactCriterion;
					Expression value;
					
					KnowledgeAtomType knowledgeAtomType = null;
					List<Expression> expressions = new ArrayList<Expression>();
					
					for (int indexSub = 2; indexSub < descriptionKnowledgeAtomList.length; indexSub++) {
						
						if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEFACTCRITERION.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGEFACTCRITERION.length());
							knowledgeFactCriterion = new Constant(new Value(Type.integer, "criterion", Integer.parseInt(descriptionKnowledgeAtomPart) ));
							expressions.add(knowledgeFactCriterion);
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEPROPERTY.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGEPROPERTY.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeProperty, descriptionKnowledgeAtomPart, "value_" + (indexSub - 2));
							expressions.add(value);
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEVALUE.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGEVALUE.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeValue, descriptionKnowledgeAtomPart, "value_" + (indexSub - 2));
							expressions.add(value);
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONSUBJECT.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGERELATIONSUBJECT.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationSubject, descriptionKnowledgeAtomPart, "subject");
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(0, value);
							if (knowledgeAtomType == null) {
								knowledgeAtomType = KnowledgeAtomType.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONVERB.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGERELATIONVERB.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationVerb, descriptionKnowledgeAtomPart, "verb");
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(1, value);
							if (knowledgeAtomType == null) {
								knowledgeAtomType = KnowledgeAtomType.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONADVERB.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGERELATIONADVERB.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationAdverb, descriptionKnowledgeAtomPart, "adverb");
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(2, value);
							if (knowledgeAtomType == null) {
								knowledgeAtomType = KnowledgeAtomType.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONOBJECT1.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGERELATIONOBJECT1.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, "object1");
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(3, value);
							if (knowledgeAtomType == null || knowledgeAtomType.equals(KnowledgeAtomType.relationUnaer)) {
								knowledgeAtomType = KnowledgeAtomType.relationBinaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONOBJECT2.length()) > 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGERELATIONOBJECT2.length());
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, "object2");
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(4, value);
							knowledgeAtomType = KnowledgeAtomType.relationTrinaer;
						}
						
					}
					
					Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(knowledgeAtomType, expressions);
					
					if (creationKnowledgeSource.isValid() && creationKnowledgeAtom.isValid() ) {
						
						listExpressions.add(creationKnowledgeSource);
						listExpressions.add(creationKnowledgeAtom);
						
					}
					
				}
				
			
			}
			
			if (listExpressions.size() > 1) {
				
				Expression sequence = new	AddOrSetValuesToArguments(listExpressions);
				setExpression2(sequence);
	
				setValid();
			
			}
					
		}
	}

}
