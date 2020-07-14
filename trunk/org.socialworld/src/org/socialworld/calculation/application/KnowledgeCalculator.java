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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Calculation;
import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionAssignment;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.knowledge.KnowledgeAtom;
import org.socialworld.knowledge.KnowledgeAtomType;
import org.socialworld.knowledge.KnowledgeElement;
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

	
	private static KnowledgeCalculator instance;

	private List<Event> events4Perception;
	private List<StateAnimal> states4Perception;
	private List<HiddenAnimal> hiddenAnimals4Perception;

	
	/**
	 * private Constructor. 
	 */
	private KnowledgeCalculator() {

		this.events4Perception = new ArrayList<Event>();
		this.states4Perception = new ArrayList<StateAnimal>();
		this.hiddenAnimals4Perception = new ArrayList<HiddenAnimal>();

		
	}

	public static KnowledgeCalculator getInstance() {
		if (instance == null) {
			instance = new KnowledgeCalculator();
		}
		return instance;
	}

	public void run() {

		while (isRunning()) {
			
			calculatePerception();
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	final void calculatePerception(Event event, StateAnimal stateAnimal, HiddenAnimal hiddenWriteAccess) {
		if (event != null && stateAnimal != null && hiddenWriteAccess != null) {
			this.events4Perception.add(event);
			this.states4Perception.add(stateAnimal);
			this.hiddenAnimals4Perception.add( hiddenWriteAccess);
		}
	}

	private final int calculatePerception() {
		
		if ((this.events4Perception.size() == 0) || 
				(this.states4Perception.size() == 0) ||
				(this.hiddenAnimals4Perception.size() == 0)) 
		{
				return KNOWLEDGE_CALCULATOR_RETURNS_EMPTY_LISTS;
		}

		Event event = this.events4Perception.remove(0);
		StateAnimal stateAnimal  = this.states4Perception.remove(0);
		HiddenAnimal hiddenWriteAccess = this.hiddenAnimals4Perception.remove(0);

		return setFacts( event,  stateAnimal,  hiddenWriteAccess);
	
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
			f_CreatePerception = descGetKE.getFunctionCreatePerception(index);
			valueKE = f_CreatePerception.calculate(arguments);
			knowledgeElement = (KnowledgeElement) valueKE.getValue();	
			
			if (knowledgeElement != null) {
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
		
		SimulationObject subject;
		Lexem lexemSubject;
		
		int size;
		
		size = knowledgeElementProperties.size();
		
		if (size > 0) {
			
			subject = (SimulationObject) knowledgeElementProperties.get(0).getValue();
			lexemSubject = subject.getLexem();
			
			KnowledgeElement knowledgeElement = new KnowledgeElement(lexemSubject);
			KnowledgeSource source = null;
			KnowledgeAtom atom = null;
			
			for (int index = 1; index < size; index++) {
			
				if ((index % 2) == 1) {
					source = (KnowledgeSource) knowledgeElementProperties.get(index).getValue();
				}
				else {
					atom = (KnowledgeAtom) knowledgeElementProperties.get(index).getValue();
					knowledgeElement.add(atom, source);
				}
				
			}
			return knowledgeElement;
			
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
			type = KnowledgeSource_Type.getName((int) knowledgeSourceProperties.get(find).getValue());
			find = knowledgeSourceProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_SOURCE);
			if (find >= 0) {
				origin = (SimulationObject) knowledgeSourceProperties.get(find).getValue();
				result = new KnowledgeSource(type, origin);
			}
		}
		
		return result;
		
	}

	public static KnowledgeAtom createKnowledgeAtom(KnowledgeAtomType type, ValueArrayList knowledgeAtomProperties) {
		
		KnowledgeAtom result = new KnowledgeValue(Calculation.getNothing());
		
		int index;
		int size;
		int find;
		
		Value subject = new Value() ;
		Value verb = new Value();
		Value adverb = new Value();
		Value object1 = new Value();
		Value object2 = new Value();
		
		Value value = Calculation.getNothing();
		
		size = knowledgeAtomProperties.size();
		
		switch (type) {
		case relationTrinaer:
			
			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT2);
			if (find >= 0) {
				object2 = knowledgeAtomProperties.get(find);
			
			}

		case relationBinaer:
			
			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_OBJECT1);
			if (find >= 0) {
				object1 = knowledgeAtomProperties.get(find);
			
			}
			
		case relationUnaer:
			
			find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_SUBJECT);
			if (find >= 0) {
				
				subject = knowledgeAtomProperties.get(find);
				
				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_VERB);
				if (find >= 0) {
					verb = knowledgeAtomProperties.get(find);
				
				}

				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_RELATION_ADVERB);
				if (find >= 0) {
					adverb = knowledgeAtomProperties.get(find);
				
				}

			}
			
			break;
			
		case property:
			
			break;
			
		case value:

			if (size == 1) {
				
				value = knowledgeAtomProperties.get(0);
			
			}
			else {
				
				find = knowledgeAtomProperties.findValue(Value.VALUE_NAME_KNOWLEDGE_PROPERTY_VALUE + "0");
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
