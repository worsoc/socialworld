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

	public static final int KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS = 2;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_NO_CHANGES = 1;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_INVALID_RESULT = 3;
	public static final int KNOWLEDGE_CALCULATOR_RETURNS_CONTAINS_INVALIDS = 4;

	public static final String PRAEFIX_VALUE_NAME = "VALNAM_";
	
	private static KnowledgeCalculator instance;

	private CapacityQueue<CollectionElementSimObjInfluenced> perceptions;

	private static AccessTokenKnowledgeCalculator token = AccessTokenKnowledgeCalculator.getValid();
	
/*	private List<Event> events4Perception;
	private List<StateAnimal> states4Perception;
	private List<HiddenAnimal> hiddenAnimals4Perception;
*/
	
	/**
	 * private Constructor. 
	 */
	private KnowledgeCalculator() {

		this.sleepTime = SocialWorldThread.SLEEPTIME_KNOWLEDGE_CALCULATOR;
		
		this.perceptions = new CapacityQueue<CollectionElementSimObjInfluenced>("perceptions", 1000);

/*		this.events4Perception = new ArrayList<Event>();
		this.states4Perception = new ArrayList<StateAnimal>();
		this.hiddenAnimals4Perception = new ArrayList<HiddenAnimal>();
*/
		
	}

	public static KnowledgeCalculator getInstance() {
		if (instance == null) {
			instance = new KnowledgeCalculator();
		}
		return instance;
	}

	public void run() {

		while (isRunning()) {
			
			if (this.perceptions.size() > 0) calculatePerception();
			
			try {
				sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	final void calculatePerception(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		if (event != null && stateAnimal != null && hiddenWriteAccess != null) {
			if (!this.perceptions.add(new CollectionElementSimObjInfluenced(event, stateAnimal, hiddenWriteAccess))) {
//				System.out.println("KnowledgeCalculator.calculatePerception/3: CollectionElementSimObjInfluenced not added");
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
/*		if (event != null && stateAnimal != null && hiddenWriteAccess != null) {
			this.events4Perception.add(event);
			this.states4Perception.add(stateAnimal);
			this.hiddenAnimals4Perception.add( hiddenWriteAccess);
		}
*/
	}

	private final int calculatePerception() {
		
/*		if ((this.events4Perception.size() == 0) || 
				(this.states4Perception.size() == 0) ||
				(this.hiddenAnimals4Perception.size() == 0)) 
		{
				return KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}

		Event event = this.events4Perception.remove(0);
		StateAnimal stateAnimal  = this.states4Perception.remove(0);
		HiddenAnimal hiddenWriteAccess = this.hiddenAnimals4Perception.remove(0);
*/
		CollectionElementSimObjInfluenced perception = this.perceptions.remove();
		if (perception != null) {

			Event event = perception.getEvent();
			StateAnimal stateAnimal  = (StateAnimal) perception.getState();
			HiddenAnimal hiddenWriteAccess =  (HiddenAnimal) perception.getHidden();

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
		
		ValueArrayList arguments;
		arguments = new ValueArrayList();

		ValueArrayList eventParams;   
		eventParams = event.getProperties();
		
		arguments.add(new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, eventParams));
		arguments.add(new Value(Type.simulationObject, Value.VALUE_NAME_KNOWLEDGE_SOURCE_MYSELF, stateAnimal.getObject()));
		
		int result = KNOWLEDGE_CALCULATOR_RETURNS_NO_CHANGES;
		int resultTmp;
		
		int eventType = event.getEventTypeAsInt();
		int perceptionType = stateAnimal.getPerceptionType(eventType);
		EventPerceptionDescription descGetKE = EventPerceptionAssignment.getInstance().getEventPerceptionDescription(
				eventType, perceptionType	);
		int count = descGetKE.countFunctions();

		FunctionByExpression f_CreatePerception;
		
		for (int index = 0; index < count; index++) 
		{
			f_CreatePerception = descGetKE.getFunction(index);
			valueKE = f_CreatePerception.calculate(arguments);
			knowledgeElement = objectRequester.requestKnowledgeElement(token, valueKE, this);	
			
			if (knowledgeElement != KnowledgeElement.getObjectNothing()) {
				if (knowledgeElement.isValid())	{
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
				
				subject = getInstance().objectRequester.requestSimulationObject(token, knowledgeElementProperties.get(0), getInstance());
				source = getInstance().objectRequester.requestKnowledgeSource(token, knowledgeElementProperties.get(1), getInstance());
				lexemSubject = subject.getLexem();
				
				KnowledgeElement knowledgeElement = new KnowledgeElement(source, lexemSubject);
				KnowledgeItem atom = null;
				
				for (int index = 2; index < size; index++) {
				
					atom = getInstance().objectRequester.requestKnowledgeItem(token, knowledgeElementProperties.get(index), getInstance());
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
				origin = getInstance().objectRequester.requestSimulationObject(token, knowledgeSourceProperties.get(find), getInstance());
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
