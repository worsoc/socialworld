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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.application.KnowledgeCalculator;
import org.socialworld.calculation.descriptions.EventPerceptionDescriptionEntry;
import org.socialworld.calculation.descriptions.ExtractionSlot;
import org.socialworld.datasource.parsing.JsonExtractionStep;
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
	
	private static AccessTokenExpressions4Knowledge token = AccessTokenExpressions4Knowledge.getValid();
	
	
    /**
     * Nativer, hochperformanter Konstruktor für die GSON-Pipeline.
     * Erzeugt das ausführbare Expressions-Netzwerk direkt aus den strukturierten JSON-Daten.
     * Eliminiert das fehleranfällige und CPU-intensive String-Splitting vollständig.
     */
    public CreateKnowledgeElementExpression(EventPerceptionDescriptionEntry entry) {
        
        // Ruft den Typen-Konstruktor der Vaterklasse (Expression) auf
        super(Type.knowledgeElement);
        
        // Defensiver Schutz vor Null-Pointern bei leeren JSON-Einträgen
        if (entry == null) {
			return;
		}
        
        List<Expression> listExpressions = new ArrayList<Expression>();
        
        // =========================================================================
        // 1. SUBJEKT (KSbj) AUFLÖSEN
        // =========================================================================
        // String.join verbindet die Liste ["GETVal(event_params)", "GETVal(event_causer)"]
        // allokationsarm wieder zu deiner gewohnten Dot-Notation.
        String descriptionSubject = String.join(".", entry.subjectPathSteps);
        Expression subject = new GetValue(token, PropertyUsingAs.knowledgeSubject, descriptionSubject, Value.VALUE_NAME_KNOWLEDGE_SUBJECT);
        listExpressions.add(subject);
        
        // =========================================================================
        // 2. QUELLE (KSrcT & KSrc) AUFLÖSEN
        // =========================================================================
        // Quelle-Typ (Konstante aus der sourceCategory des JSON)
        Expression knowledgeSourcetype = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE, entry.sourceCategory));
        
        // Quelle-Pfad (Dot-Notation)
        String descriptionKnowledgeSourceOrigin = String.join(".", entry.sourcePathSteps);
        Expression knowledgeSource = new GetValue(token, PropertyUsingAs.knowledgeSource, descriptionKnowledgeSourceOrigin, Value.VALUE_NAME_KNOWLEDGE_SOURCE);
        
        // Erstellung des kombinierten Quellen-Ausdrucks
        Expression creationKnowledgeSource = new CreateKnowledgeSourceExpression(knowledgeSourcetype, knowledgeSource);
        listExpressions.add(creationKnowledgeSource);
        
        // =========================================================================
        // 3. EXTRAKTIONEN (Exakte Trennung von Farben und Materialien über orderNr)
        // =========================================================================
        if (entry.extractions != null && !entry.extractions.isEmpty()) {
            
            int lastMainIndex = -1;
            int pseudoIndexSub = 0; 
            
            // Sammellisten für das aktuelle, kombinierte Wissens-Atom
            KnowledgeFact_Type currentFactType = null;
            List<Expression> currentExpressions = new ArrayList<Expression>();
            List<String> currentValueNames = new ArrayList<String>();
            
            for (JsonExtractionStep step : entry.extractions) {
                
                // Nutzt das im JSON definierte orderNr als unbestechlichen indexMain
                int pseudoIndexMain = step.orderNr; 
                
                // Erkennung eines Block-Wechsels (Wechsel des Semikolons im Altsystem)
                if (lastMainIndex != -1 && pseudoIndexMain != lastMainIndex) {
                    
                    // Der alte Sammel-Block (z.B. Farben) ist fertig -> Als EIN Atom sichern!
                    Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(currentFactType, currentExpressions, currentValueNames);
                    if (creationKnowledgeAtom.isValid()) {
                        listExpressions.add(creationKnowledgeAtom);
                    }
                    
                    // Unter-Index (indexSub) für das neue Material-Atom bei 0 starten lassen
                    pseudoIndexSub = 0;
                    
                    // Listen für das nächste Atom (Materialien) vollständig leeren
                    currentFactType = null;
                    currentExpressions = new ArrayList<Expression>();
                    currentValueNames = new ArrayList<String>();
                }
                lastMainIndex = pseudoIndexMain;
                
                // Schweißt die pathSteps-Liste allokationsarm zur Dot-Notation zusammen
                String extractionPath = String.join(".", step.pathSteps);
                
                // Dynamische Namensgenerierung (Inkrementiert subIndex innerhalb desselben Blocks)
                String fallbackValueName = KnowledgeCalculator.PRAEFIX_VALUE_NAME + Value.VALUE_NAME_KNOWLEDGE_PROPERTY_VALUE + pseudoIndexMain + "_" + pseudoIndexSub;
                String actualValueName = (step.customLabel != null && !step.customLabel.isEmpty()) 
                        ? KnowledgeCalculator.PRAEFIX_VALUE_NAME + step.customLabel 
                        : fallbackValueName;
                
                // Performante Weiche über die strukturelle Kategorie des JSON
                switch (step.category) {
                    
                    case "property":
                        // Das Kriterium (z.B. Konstante 0 für Farbe, 1 für Material) 
                        // wird nur EINMALIG ganz am Anfang des jeweiligen Atoms benötigt!
                        if (currentExpressions.isEmpty()) {
                            String criterionValueName = Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION + "_" + pseudoIndexMain;
                            currentExpressions.add(new Constant(new Value(Type.integer, criterionValueName, step.criterion)));
                            currentValueNames.add(criterionValueName);
                        }
                        
                        currentExpressions.add(new GetValue(token, PropertyUsingAs.knowledgeProperty, extractionPath, actualValueName));
                        currentValueNames.add(actualValueName);
                        
                        if (currentFactType == null) {
                            currentFactType = KnowledgeFact_Type.property;
                        }
                        break;
                        
                    case "value":
                        String valueValueName = KnowledgeCalculator.PRAEFIX_VALUE_NAME + Value.VALUE_NAME_KNOWLEDGE_VALUE_VALUE + pseudoIndexMain + "_" + pseudoIndexSub;
                        String targetValueName = (step.customLabel != null && !step.customLabel.isEmpty()) 
                                ? KnowledgeCalculator.PRAEFIX_VALUE_NAME + step.customLabel 
                                : valueValueName;
                        currentValueNames.add(targetValueName);
                        
                        currentExpressions.add(new GetValue(token, PropertyUsingAs.knowledgeValue, extractionPath, targetValueName));
                        if (currentFactType == null) {
                            currentFactType = KnowledgeFact_Type.value;
                        }
                        break;
                        
                    case "element":
                        if (currentExpressions.isEmpty()) {
                            for (int i = 0; i < 5; i++) {
                                currentExpressions.add(Nothing.getInstance());
                            }
                        }
                        
                        if (step.slot == ExtractionSlot.KNOWLEDGE_SUBJECT) {
                            currentExpressions.set(0, new GetValue(token, PropertyUsingAs.knowledgeRelationSubject, extractionPath, Value.VALUE_NAME_KNOWLEDGE_RELATION_SUBJECT));
                            currentFactType = KnowledgeFact_Type.relationUnaer;
                        } 
                        else if (step.slot == ExtractionSlot.KNOWLEDGE_VERB) {
                            currentExpressions.set(1, new GetValue(token, PropertyUsingAs.knowledgeRelationVerb, extractionPath, Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB));
                            currentFactType = KnowledgeFact_Type.relationUnaer;
                        } 
                        else if (step.slot == ExtractionSlot.KNOWLEDGE_ADVERB) {
                            currentExpressions.set(2, new GetValue(token, PropertyUsingAs.knowledgeRelationAdverb, extractionPath, Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB));
                            currentFactType = KnowledgeFact_Type.relationUnaer;
                        } 
                        else if (step.slot == ExtractionSlot.KNOWLEDGE_OBJECT_1) {
                            currentExpressions.set(3, new GetValue(token, PropertyUsingAs.knowledgeRelationObject, extractionPath, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1));
                            if (currentFactType == null || currentFactType.equals(KnowledgeFact_Type.relationUnaer)) {
                                currentFactType = KnowledgeFact_Type.relationBinaer;
                            }
                        } 
                        else if (step.slot == ExtractionSlot.KNOWLEDGE_OBJECT_2) {
                            currentExpressions.set(4, new GetValue(token, PropertyUsingAs.knowledgeRelationObject, extractionPath, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2));
                            currentFactType = KnowledgeFact_Type.relationTrinaer;
                        }
                        break;
                }
                
                // Inkrementiert subIndex innerhalb der aktuellen orderNr-Gruppe (z.B. 2_0 -> 2_1)
                pseudoIndexSub++;
            }
            
            // Reißverschluss-Verfahren: Das allerletzte angesammelte Atom (die Materialien) sichern!
            if (!currentExpressions.isEmpty()) {
                Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(currentFactType, currentExpressions, currentValueNames);
                if (creationKnowledgeAtom.isValid()) {
                    listExpressions.add(creationKnowledgeAtom);
                }
            }
        }
        
        // =========================================================================
        // 4. FINALER ZUSAMMENBAU (Die lauffähige Sequenz aktivieren)
        // =========================================================================
        if (listExpressions.size() > 1) {
            Expression sequence = new AddOrSetValuesToArguments(Value.VALUE_NAME_KNOWLEDGE_ELEMENT_PROPS, listExpressions);
            setExpression2(sequence);
            setValid();
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
			Expression subject = new GetValue(token, PropertyUsingAs.knowledgeSubject, descriptionSubject, Value.VALUE_NAME_KNOWLEDGE_SUBJECT);
			listExpressions.add(subject);
			
			
			
			String[] descriptionKnowledgeSourceElements = main[0].split("&");
			
			String descriptionKnowledgeSourceType = descriptionKnowledgeSourceElements[0].substring(LABEL_KNOWLEDGESOURCETYPE.length() + ":".length());
			Expression knowledgeSourcetype = new Constant(new Value(Type.integer, Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE, Integer.parseInt(descriptionKnowledgeSourceType) ));
			
			String descriptionKnowledgeSourceOrigin = descriptionKnowledgeSourceElements[1].substring(LABEL_KNOWLEDGESOURCE.length() + ":".length());
			Expression knowledgeSource = new GetValue(token, PropertyUsingAs.knowledgeSource, descriptionKnowledgeSourceOrigin, Value.VALUE_NAME_KNOWLEDGE_SOURCE);
			
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
					List<String> listValueNames = new ArrayList<String>();

					String criterionValueName = "";

					for (int indexSub = 0; indexSub < descriptionKnowledgeAtomList.length; indexSub++) {
						
						String valueName = "";
						

						if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEPROPERTY) >= 0) {
							
							criterionValueName = Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION + "_" + indexMain;
							
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
//							System.out.println(descriptionKnowledgeAtomList[indexSub]);
							valueName = KnowledgeCalculator.PRAEFIX_VALUE_NAME + Value.VALUE_NAME_KNOWLEDGE_PROPERTY_VALUE + indexMain + "_" + indexSub;
							if ((descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(":")) 
								&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(")"))
									&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") > descriptionKnowledgeAtomList[indexSub].indexOf("("))) {
								valueName =		KnowledgeCalculator.PRAEFIX_VALUE_NAME + descriptionKnowledgeAtomList[indexSub].
												substring(descriptionKnowledgeAtomList[indexSub].indexOf(",") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							
								kfc = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf("(") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(","));
							}	
							else {
								kfc = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf("(") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							}
							
							knowledgeFactCriterion = new Constant(new Value(Type.integer, criterionValueName , Integer.parseInt(kfc) ));
							expressions.add(knowledgeFactCriterion);
							listValueNames.add(criterionValueName);
												
							value = new GetValue(token, PropertyUsingAs.knowledgeProperty, descriptionKnowledgeAtomPart, valueName);
							expressions.add(value);
							listValueNames.add(valueName);
												
							if (knowledgeFact_Type == null ) {
								knowledgeFact_Type = KnowledgeFact_Type.property;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGEVALUE) >= 0) {
							
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
		
							valueName = KnowledgeCalculator.PRAEFIX_VALUE_NAME + Value.VALUE_NAME_KNOWLEDGE_VALUE_VALUE + indexMain + "_" + indexSub;
							if ((descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(":")) 
									&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") < descriptionKnowledgeAtomList[indexSub].indexOf(")"))
										&& (descriptionKnowledgeAtomList[indexSub].indexOf(",") > descriptionKnowledgeAtomList[indexSub].indexOf("("))) {
									valueName =	KnowledgeCalculator.PRAEFIX_VALUE_NAME +	descriptionKnowledgeAtomList[indexSub].
													substring(descriptionKnowledgeAtomList[indexSub].indexOf(",") + 1, descriptionKnowledgeAtomList[indexSub].indexOf(")"));
							}	
							listValueNames.add(valueName);
							
							value = new GetValue(token, PropertyUsingAs.knowledgeValue, descriptionKnowledgeAtomPart, valueName);
							expressions.add(value);
							
							if (knowledgeFact_Type == null ) {
								knowledgeFact_Type = KnowledgeFact_Type.value;
							}
						}
						else if ( descriptionKnowledgeAtomList[indexSub].indexOf(LABEL_KNOWLEDGERELATIONSUBJECT) >= 0) {
							descriptionKnowledgeAtomPart = descriptionKnowledgeAtomList[indexSub].substring(descriptionKnowledgeAtomList[indexSub].indexOf(":") + 1);
							value = new GetValue(token, PropertyUsingAs.knowledgeRelationSubject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_SUBJECT);
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
							value = new GetValue(token, PropertyUsingAs.knowledgeRelationVerb, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
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
							value = new GetValue(token, PropertyUsingAs.knowledgeRelationAdverb, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
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
							value = new GetValue(token, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1);
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
							value = new GetValue(token, PropertyUsingAs.knowledgeRelationObject, descriptionKnowledgeAtomPart, Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2);
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
					
					// listValueNames just uses for property and value , for other types the list is empty (is not be needed for other types)
					// TODO different constructors CreateKnowledgeAtomExpression
					Expression creationKnowledgeAtom = new CreateKnowledgeAtomExpression(knowledgeFact_Type, expressions, listValueNames );
					
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
