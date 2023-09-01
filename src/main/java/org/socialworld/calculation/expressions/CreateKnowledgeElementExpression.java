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
import org.socialworld.datasource.tablesPool.TablePoolDotElemLine;
import org.socialworld.datasource.tablesPool.ViewDotElementJoinDotElem;
import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.knowledge.KnowledgeFact_Type;

public class CreateKnowledgeElementExpression extends CreateValue {

	
	public static String LABEL_SUBJECT = "KSbj";
	public static String LABEL_KNOWLEDGESOURCETYPE = "KSrcT";
	public static String LABEL_KNOWLEDGESOURCE = "KSrc";
	public static String LABEL_KNOWLEDGEVALUE = "KVal";
	public static String LABEL_KNOWLEDGEFACTCRITERION = "KFC";
	public static String LABEL_KNOWLEDGEPROPERTY = "KProp";
	public static String LABEL_KNOWLEDGERELATIONSUBJECT = "KRelSub";
	public static String LABEL_KNOWLEDGERELATIONVERB = "KRelVrb";
	public static String LABEL_KNOWLEDGERELATIONADVERB = "KRelAdv";
	public static String LABEL_KNOWLEDGERELATIONOBJECT1 = "KRelObj1";
	public static String LABEL_KNOWLEDGERELATIONOBJECT2 = "KRelObj2";
	
	public CreateKnowledgeElementExpression(int[] dotElemLineIDs) {
		super(Type.knowledgeElement);
		
		if (dotElemLineIDs.length > 0) {
			
			List<Expression> listExpressions = new ArrayList<Expression>();
			
			String descriptionSubject = "GETVal(" + Value.VALUE_BY_NAME_EVENT_PARAMS + ").GETVal(" + Value.VALUE_BY_NAME_EVENT_CAUSER + ")";
			Expression subject = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSubject, descriptionSubject, Value.VALUE_NAME_KNOWLEDGE_SUBJECT);
			listExpressions.add(subject);
		
			Expression knowledgeSourcetype = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE, 1 ));
			Expression knowledgeSource = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSource, "GETVal(" + Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF + ")", Value.VALUE_NAME_KNOWLEDGE_SOURCE);
			Expression creationKnowledgeSource = new CreateKnowledgeSourceExpression(knowledgeSourcetype, knowledgeSource);
			listExpressions.add(creationKnowledgeSource);
			
			ViewDotElementJoinDotElem dotElements = new ViewDotElementJoinDotElem();
			int dot_element_id;
			int dotelem_function; 		
			String dotelem_value_name; 
//			int dotelem_addon; 
//			int dotelem_addon_intarg; 
//			String dotelem_addon_charsarg; 

			
			int indexTablePoolDotElemLine;
			int function;
			String resultType;
			int kfc = -1;
			String resultValueName;
			TablePoolDotElemLine dotElemLine = new TablePoolDotElemLine();
			dotElemLine.select(dotElemLine.SELECT_ALL_COLUMNS , "", "");
			for (int id : dotElemLineIDs) {
				
				indexTablePoolDotElemLine = dotElemLine.getIndexFor1PK(id);
				function = dotElemLine.getFunction(indexTablePoolDotElemLine);
				resultType = dotElemLine.getResultType(indexTablePoolDotElemLine);
				resultValueName = dotElemLine.getResultValueName(indexTablePoolDotElemLine);

				Expression knowledgeFactCriterion;

				Expression value;
				List<Expression> expressions = new ArrayList<Expression>();

				KnowledgeFact_Type knowledgeFact_Type = null;
				switch (function) {
				case 1:
					knowledgeFact_Type = KnowledgeFact_Type.value;
					switch (resultType) {
					case "StateAppearance":
						kfc = KnowledgeFact_Criterion.colour.getIndex();
						break;
					case "StateComposition":
						kfc = KnowledgeFact_Criterion.material.getIndex();
						break;
					default:
						kfc = -1;
					}
					break;
				case 2:
					knowledgeFact_Type = KnowledgeFact_Type.property;
					kfc = Integer.parseInt(resultType);
					break;
				}
				
				if (resultType.length() > 0 ) {
					knowledgeFactCriterion = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION, kfc ));
					expressions.add(knowledgeFactCriterion);
				}

				dotElements.select(dotElemLine.SELECT_ALL_COLUMNS , " WHERE dot_elem_line_id = " + id, " ORDER BY lfd_nr");
				for (int i = 0; i < 1 /*dotElements.rowCount()*/; i++) {
					dot_element_id = dotElements.getDotElementID(i);
					dotelem_function = dotElements.getFunction(i); 		
					dotelem_value_name = dotElements.getValueName(i); 		
				/*	dotelem_addon = dotElements.getAddon(i); 		
					dotelem_addon_intarg = dotElements.getAddon_IntArg(i); 		
					dotelem_addon_charsarg = dotElements.getAddon_CharsArg(i); 		
				*/
									
					switch (dotelem_function) {
					case 1:  // GetValue
						
						value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeValue, dotElements, i, dotelem_value_name);
						expressions.add(value);
						break;
						
					
					case 2: // GetProperty
						

						
						value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeProperty, dotElements, i, dotelem_value_name);
						expressions.add(value);
						break;
						
						
					}
					
				}
				
				Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(knowledgeFact_Type, expressions);
				
				if ( creationKnowledgeAtom.isValid() ) {
					
					listExpressions.add(creationKnowledgeAtom);
					
				}

			}
			
			if (listExpressions.size() > 1) {
				
				Expression sequence = new	AddOrSetValuesToArguments(Value.VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS, listExpressions);
				setExpression2(sequence);
	
				setValid();
			
			}

		}
		
	}
	
	public CreateKnowledgeElementExpression(String description) {
		
		super(Type.knowledgeElement);
			
		String main[];
		main = description.split(";");
		
		// at main index = 0: expressions for KnowledgeSource
		// at main index = 1: expression for subject lexem  (GetLexem)
		// at main index > 1: expressions for KnowledgeAtomcombinations  --> KnowledgeItemList

		// for any sub entry (element of the KnowledgeItemList):
		// at sub index >= 0: one or more expressions, describing the KnowledgeItem

		if (main.length > 1) {
			
			List<Expression> listExpressions = new ArrayList<Expression>();
			
			String descriptionSubject = main[1].substring(LABEL_SUBJECT.length() + ":".length());
			Expression subject = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSubject, descriptionSubject, Value.VALUE_NAME_KNOWLEDGE_SUBJECT);
			listExpressions.add(subject);
			
			
			
			String[] descriptionKnowledgeSourceElements = main[0].split("&");
			
			String descriptionKnowledgeSourceType = descriptionKnowledgeSourceElements[0].substring(LABEL_KNOWLEDGESOURCETYPE.length() + ":".length());
			Expression knowledgeSourcetype = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE, Integer.parseInt(descriptionKnowledgeSourceType) ));
			
			String descriptionKnowledgeSourceOrigin = descriptionKnowledgeSourceElements[1].substring(LABEL_KNOWLEDGESOURCE.length() + ":".length());
			Expression knowledgeSource = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeSource, descriptionKnowledgeSourceOrigin, Value.VALUE_NAME_KNOWLEDGE_SOURCE);
			
			Expression creationKnowledgeSource = new CreateKnowledgeSourceExpression(knowledgeSourcetype, knowledgeSource);
			listExpressions.add(creationKnowledgeSource);
				
		
			
			for (int indexMain = 2; indexMain < main.length; indexMain++) {
			
				String descriptionKnowledgeAtomList[] = main[indexMain].split("&");
			
				if (descriptionKnowledgeAtomList.length > 0) {
					
					String kfc;
					Expression knowledgeFactCriterion;
						
					
					String descriptionKnowledgeAtomPart;
					Expression value;
					
					KnowledgeFact_Type knowledgeFact_Type = null;
					List<Expression> expressions = new ArrayList<Expression>();
					
					for (int indexSub = 0; indexSub < descriptionKnowledgeAtomList.length; indexSub++) {
						
						String valueName = "";
						
/*						if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEFACTCRITERION) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(LABEL_KNOWLEDGEFACTCRITERION.length());
							knowledgeFactCriterion = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION, Integer.parseInt(descriptionKnowledgeAtomPart) ));
							expressions.add(knowledgeFactCriterion);
							if (knowledgeAtomType == null ) {
								knowledgeAtomType = KnowledgeFact_Type.property;
							}
						}
*/
						if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEPROPERTY) >= 0) {
							
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
//							System.out.println(descriptionKnowledgeAtomList[indexSub]);
							valueName = Value.VALUE_NAME_KNOWLEDGE_PROPERTY_VALUE + indexSub;
							if ((descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(":")) 
								&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(")"))
									&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") > descriptionKnowledgeAtomList[indexSub].indexOf("("))) {
								valueName =		descriptionKnowledgeAtomList[indexSub].
												substring(descriptionKnowledgeAtomList[indexSub].indexOf(",") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							
								kfc = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf("(") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(","));
							}	
							else {
								kfc = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf("(") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							}

							knowledgeFactCriterion = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION, Integer.parseInt(kfc) ));
							expressions.add(knowledgeFactCriterion);
							
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeProperty, descriptionKnowledgeAtomPart, valueName);
							expressions.add(value);
							
							if (knowledgeFact_Type == null ) {
								knowledgeFact_Type = KnowledgeFact_Type.property;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEVALUE) >= 0) {
							
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
		
							valueName = Value.VALUE_NAME_KNOWLEDGE_VALUE_VALUE + indexSub;
							if ((descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(":")) 
									&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(")"))
										&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") > descriptionKnowledgeAtomList[indexSub].indexOf("("))) {
									valueName =		descriptionKnowledgeAtomList[indexSub].
													substring(descriptionKnowledgeAtomList[indexSub].indexOf(",") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							}	
								
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeValue, descriptionKnowledgeAtomPart, valueName);
							expressions.add(value);
							
							if (knowledgeFact_Type == null ) {
								knowledgeFact_Type = KnowledgeFact_Type.value;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONSUBJECT) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationSubject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_SUBJECT);
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(0, value);
							if (knowledgeFact_Type == null) {
								knowledgeFact_Type = KnowledgeFact_Type.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONVERB) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationVerb, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(1, value);
							if (knowledgeFact_Type == null) {
								knowledgeFact_Type = KnowledgeFact_Type.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONADVERB) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationAdverb, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(2, value);
							if (knowledgeFact_Type == null) {
								knowledgeFact_Type = KnowledgeFact_Type.relationUnaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONOBJECT1) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1);
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(3, value);
							if (knowledgeFact_Type == null || knowledgeFact_Type.equals(KnowledgeFact_Type.relationUnaer)) {
								knowledgeFact_Type = KnowledgeFact_Type.relationBinaer;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONOBJECT2) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(SimulationCluster.knowledge, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2);
							if (expressions.size() == 0) {
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
								expressions.add(Nothing.getInstance());
							}
							expressions.set(4, value);
							knowledgeFact_Type = KnowledgeFact_Type.relationTrinaer;
						}
						
					}
					
					Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(knowledgeFact_Type, expressions);
					
					if ( creationKnowledgeAtom.isValid() ) {
						
						listExpressions.add(creationKnowledgeAtom);
						
					}
					
				}
				
			
			}
			
			if (listExpressions.size() > 1) {
				
				Expression sequence = new	AddOrSetValuesToArguments(Value.VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS, listExpressions);
				setExpression2(sequence);
	
				setValid();
			
			}
					
		}
	}

	
	
}
