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
package org.socialworld.calculation.application;

import java.util.concurrent.TimeUnit;

import org.socialworld.GlobalSwitches;
import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionAssignment;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.calculation.functions.FunctionByExpression;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.knowledge.KnowledgeItem;
import org.socialworld.knowledge.KnowledgeFact_Type;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.knowledge.KnowledgeProperty;
import org.socialworld.knowledge.KnowledgeRelationBinaer;
import org.socialworld.knowledge.KnowledgeRelationTrinaer;
import org.socialworld.knowledge.KnowledgeRelationUnaer;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.knowledge.KnowledgeValue;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.access.HiddenAnimal;

public class KnowledgeCalculator extends SocialWorldThread {

	
	private static final int POOL_SIZE = 8192; // Zweierpotenz für schnelles Bitmasking

	public static final int KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS = 2;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_NO_CHANGES = 1;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_INVALID_RESULT = 3;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_CONTAINS_INVALIDS = 4;

	public static final String PRAEFIX_VALUE_NAME = "VALNAM_";
	
	private static KnowledgeCalculator instance;

	private final CollectionElementSimObjInfluenced[] perceptionPool = new CollectionElementSimObjInfluenced[POOL_SIZE];
	private int poolWriteIndex = 0;

	private CapacityQueue<CollectionElementSimObjInfluenced> perceptions;

	// Wiederverwendbare, allokationsfreie Argumentenliste für die Wissens-Berechnung
	private final ValueArrayList workingKnowledgeArguments = new ValueArrayList();

	// Allokationsfreier Sandbox-Puffer für die Thread-Isolierung im Rechenkern
	private final ThreadLocal<ValueArrayList> localEvalArgs = 
	    ThreadLocal.withInitial(() -> new ValueArrayList());
	
	private static AccessTokenKnowledgeCalculator token = AccessTokenKnowledgeCalculator.getValid();
	
	
	/**
	 * private Constructor. 
	 */
	private KnowledgeCalculator() {

		
		this.perceptions = new CapacityQueue<CollectionElementSimObjInfluenced>("perceptions", 5000);

	    // Vorallokation des gesamten Pools beim Engine-Start (0 Byte zur Laufzeit)
	    for (int i = 0; i < POOL_SIZE; i++) {
	        this.perceptionPool[i] = new CollectionElementSimObjInfluenced(null, null, null);
	    }
		
	}

	public static KnowledgeCalculator getInstance() {
		if (instance == null) {
			instance = new KnowledgeCalculator();
		}
		return instance;
	}

	@Override
	public void run() {
		while (isRunning()) {
			try {

		           // 1. PHASE: REAKTIVER RUHEMODUS
	            // Der Thread schläft energiesparend, bis ein Wahrnehmungs-Event eintrifft.
	            CollectionElementSimObjInfluenced perception = perceptions.poll(
	                    SocialWorldThread.SLEEPTIME_KNOWLEDGE_CALCULATOR, TimeUnit.MILLISECONDS);
	            
	            if (perception != null) {
	                // Das erste Element direkt verarbeiten
	                calculatePerception(perception);
	                // Optisch sauber, gekapselt und zukunftssicher:
	                perception.clearReferences();

	                // 2. PHASE: MASSEN-WAHRNEHMUNGS-EXPEDITION (Kaskade)
	                // Wir fegen alle in der Zwischenzeit aufgelaufenen Wahrnehmungen 
	                // ohne Wartezeit in einem Rutsch leer, solange Arbeit da ist!
	                CollectionElementSimObjInfluenced nextPerception;
	                while ((nextPerception = this.perceptions.poll()) != null) {
	                    calculatePerception(nextPerception);
	                    nextPerception.clearReferences();
	                }
	            }
	            
			} catch (InterruptedException e) {
				// Sauberes Beenden bei Simulations-Stopp
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	final void calculatePerception(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
	    if (event != null && stateAnimal != null && hiddenWriteAccess != null) {
	        
	        // Ringpuffer-Index ohne Division berechnen (Bitmaske für POOL_SIZE = 8192)
	        int targetIdx = poolWriteIndex & (POOL_SIZE - 1);
	        CollectionElementSimObjInfluenced pooledElement = this.perceptionPool[targetIdx];
	        
	        // Bestehendes Objekt neu beschreiben, statt ein neues zu erzeugen!
	        pooledElement.setEvent(event);
	        pooledElement.setState(stateAnimal);
	        pooledElement.setHidden(hiddenWriteAccess);
	        
	        poolWriteIndex++;

	        // Das vorallokierte Objekt in die Queue schieben
	        if (!this.perceptions.add(pooledElement)) {
	            pooledElement.clearReferences(); 
	            // Falls die Queue voll ist, greift die Fallback-Logik
	            poolWriteIndex--; // Cursor zurücksetzen
	        }
	    }
	}
	

	private final int calculatePerception(CollectionElementSimObjInfluenced perception) {
		
		if (perception != null) {

			Event event = perception.getEvent();
			StateAnimal stateAnimal  = (StateAnimal) perception.getState();
			HiddenAnimal hiddenWriteAccess =  (HiddenAnimal) perception.getHidden();

			if (event == null || stateAnimal == null || hiddenWriteAccess == null) {
				if (GlobalSwitches.OUTPUT_DEBUG_KNOWLEDGECALCULATOR_VARIABLE_IS_NULL) {
					System.out.println("KnowledgeCalculator.calculatePerception(): Inner elements are null (Already processed)");
				}
				return KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS; // Allokations- und crashfreier Abbruch
			}
			
			return setFacts( event,  stateAnimal,  hiddenWriteAccess);
			
		}
		else {
			System.out.println("KnowledgeCalculator.calculatePerception(): perception is null");
			return KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}
		
	}
	
	private final int setFacts(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		
	    KnowledgeElement knowledgeElement;
	    Value valueKE;
	    
	    // 1. ALLOKATIONSFREIES LEEREN DER KLASSEN-LISTE
	    this.workingKnowledgeArguments.clear(); 

	    ValueArrayList eventParams = event.getProperties();
	    
	    // Befüllen der primären Arbeitsliste
	    this.workingKnowledgeArguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, eventParams));
	    this.workingKnowledgeArguments.add(new Value(Type.simulationObject, Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF, stateAnimal.getObject()));
	    
	    // ====================================================================
	    // THREAD-ISOLIERUNG: Kopieren in die localArgs 
	    // ====================================================================
	    ValueArrayList localArgs = this.localEvalArgs.get();
	    localArgs.clear(); // Alten Inhalt der Sandbox allokationsfrei löschen
	    localArgs.addAll(this.workingKnowledgeArguments); // Daten sicher überspielen
	    // ====================================================================

	    int result = KNOWLEDGE_CALCULATOR_RETURNS_NO_CHANGES;
	    int resultTmp;
	    
	    int eventType = event.getEventTypeAsInt();
	    int perceptionType = stateAnimal.getPerceptionType(eventType);
	    EventPerceptionDescription descGetKE = EventPerceptionAssignment.getInstance().getEventPerceptionDescription(
	            eventType, perceptionType );
	    int count = descGetKE.countFunctions();

	    FunctionByExpression f_CreatePerception;
	    
	    for (int index = 0; index < count; index++) 
	    {
	        f_CreatePerception = descGetKE.getFunction(index);
	        
	        // ÜBERGABE DER ISOLIERTEN LOCAL-ARGS AN DEN INTERPRETER:
	        valueKE = f_CreatePerception.calculate(localArgs);
	        
	        knowledgeElement = getObjectRequester().requestKnowledgeElement(token, valueKE, this); 
	        
	        if (knowledgeElement != KnowledgeElement.getObjectNothing()) {
	            if (knowledgeElement.isValid()) {
	                resultTmp = hiddenWriteAccess.addKnowledgeElement(knowledgeElement);
	                if (resultTmp > 0) {
	                    result = KNOWLEDGE_CALCULATOR_RETURNS_CONTAINS_INVALIDS;
	                }
	                else {
	                    result = resultTmp;
	                }
	            }
	        }
	    }
	    
	    // ====================================================================
	    // DAS SPEICHER-FALLBEIL FÜR BEIDE ENDEN:
	    // ====================================================================
	    this.workingKnowledgeArguments.clear();
	    localArgs.clear(); // Auch die Sandbox wird sofort wieder ausgekehrt!
	    // ====================================================================
	    
	    return result;
	}
	
	
	
	public static KnowledgeElement createKnowledgeElement(ValueArrayList knowledgeElementProperties) {
		
		KnowledgeSource source;
		SimulationObject subject;
		
		Lexem lexemSubject;
		
		Value getFromVAL;
		
		int size;
		
		size = knowledgeElementProperties.size();
		
		if (size > 0) {
			
			getFromVAL = knowledgeElementProperties.get(0);
			
			if (getFromVAL.isValid() && getFromVAL.getName().equals(Value.VALUE_NAME_KNOWLEDGE_SUBJECT)) {
				
				subject = getInstance().getObjectRequester().requestSimulationObject(token, knowledgeElementProperties.get(0), getInstance());
				source = getInstance().getObjectRequester().requestKnowledgeSource(token, knowledgeElementProperties.get(1), getInstance());
				lexemSubject = subject.getLexem();
				
				KnowledgeElement knowledgeElement = new KnowledgeElement(source, lexemSubject);
				KnowledgeItem atom = null;
				
				for (int index = 2; index < size; index++) {
				
					atom = getInstance().getObjectRequester().requestKnowledgeItem(token, knowledgeElementProperties.get(index), getInstance());
					knowledgeElement.add(atom);
					
				}
				return knowledgeElement;
			
			}
// TODO NULL WEG			
			return null;
			
		}
		else {
			
			return null;
			
		}
		
	}

	public static KnowledgeSource createKnowledgeSource(ValueArrayList knowledgeSourceProperties) {
		
		KnowledgeSource_Type type;
		SimulationObject  origin;
		KnowledgeSource result = null;
		int find;
		
		find = knowledgeSourceProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE_TYPE);
		
		if (find >= 0) {
			
			Value v = knowledgeSourceProperties.get(find);
			Object o = v.getObject(Type.integer);
			if (o instanceof NoObject) {
				if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
					System.out.println("KnowledgeCalculator.createKnowledgeSource > type: o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
				}
				return KnowledgeSource.getObjectNothing();
			}
			else {
				type = KnowledgeSource_Type.getName((int) o);
			}
			
			find = knowledgeSourceProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE);
			if (find >= 0) {
				origin = getInstance().getObjectRequester().requestSimulationObject(token, knowledgeSourceProperties.get(find), getInstance());
				result = new KnowledgeSource(type, origin);
			}
		}
		
		return result;
		
	}

	public static KnowledgeItem createKnowledgeAtom(KnowledgeFact_Type type, ValueArrayList knowledgeAtomProperties) {
		
		KnowledgeItem result = new KnowledgeValue(Calculation.getNothing());
		
		int size;
		int find;
		
		Value verb = Calculation.getNothing();
		Value adverb = Calculation.getNothing();
		Value object1 = Calculation.getNothing();
		Value object2 = Calculation.getNothing();
		
		Value kfc = Calculation.getNothing();
		
		Value value = Calculation.getNothing();
		
		ValueArrayList values = new ValueArrayList();
		
		size = knowledgeAtomProperties.size();
		
		switch (type) {
		case relationTrinaer:
	
				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
				if (find >= 0) {
					verb = knowledgeAtomProperties.get(find);
				}

				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
				if (find >= 0) {
					adverb = knowledgeAtomProperties.get(find);
				}

				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1);
				if (find >= 0) {
					object1 = knowledgeAtomProperties.get(find);
				}

				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2);
				if (find >= 0) {
					object2 = knowledgeAtomProperties.get(find);
				}

				result = new KnowledgeRelationTrinaer(verb, adverb, object1, object2);
			
			break;
			
		case relationBinaer:
			
				
			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
			if (find >= 0) {
				verb = knowledgeAtomProperties.get(find);
			}

			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
			if (find >= 0) {
				adverb = knowledgeAtomProperties.get(find);
			}

			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1);
			if (find >= 0) {
				object1 = knowledgeAtomProperties.get(find);
			}
			
			result = new KnowledgeRelationBinaer( verb, adverb, object1);


			
			break;
			
		case relationUnaer:
				
			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
			if (find >= 0) {
				verb = knowledgeAtomProperties.get(find);
			
			}

			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
			if (find >= 0) {
				adverb = knowledgeAtomProperties.get(find);
			
			}

			result = new KnowledgeRelationUnaer(verb, adverb);

			
			break;
			
		case property:
			
			find = knowledgeAtomProperties.findValueNameStartingWith(Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION);
			
			if (find >= 0) {
				
				kfc = knowledgeAtomProperties.get(find);
				
				if (size >= 2) {
					// okay
					
					for (find = 0; find < size; find++) {
						value = knowledgeAtomProperties.get(find);
						if (value.getName().indexOf(Value.VALUE_NAME_KNOWLEDGE_PROPERTY_CRITERION) == 0 ) continue;
						if (value.getName().contains(PRAEFIX_VALUE_NAME)) {
							values.add(value);
						}
						
					}
					
					result = new KnowledgeProperty(kfc, values);
				
				}

			}
			
			break;
			
		case value:

			if (size == 1) {
				
				value = knowledgeAtomProperties.get(0);
			
			}
			else {
				
				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_VALUE_VALUE + "0");
				if (find >= 0) {
					value = knowledgeAtomProperties.get(find);
				
				}
				
			}
			
			result = new KnowledgeValue(value);

			break;
			
		}
		
		return result;
		
	}

}
