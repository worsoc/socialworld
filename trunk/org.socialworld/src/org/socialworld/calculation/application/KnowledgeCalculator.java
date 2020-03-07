package org.socialworld.calculation.application;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.FunctionByExpression;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.EventPerceptionAssignment;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.Event;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.knowledge.KnowledgeElement;
import org.socialworld.knowledge.KnowledgeProperties;
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
		Value valueKF;
		
		ValueArrayList facts;   
		facts = event.getProperties();
		
		int result = KNOWLEDGE_CALCULATOR_RETURNS_NO_CHANGES;
		int resultTmp;
		
		int eventType = event.getEventTypeAsInt();
		int perceptionType = stateAnimal.getPerceptionType(eventType);
		EventPerceptionDescription descGetFacts = EventPerceptionAssignment.getInstance().getEventPerceptionDescription(
				eventType, perceptionType	);
		int count = descGetFacts.countFunctions();

		FunctionByExpression f_CreatePerception;
		
		for (int index = 0; index < count; index++) 
		{
			f_CreatePerception = descGetFacts.getFunctionCreatePerception(index);
			valueKF = f_CreatePerception.calculate(facts);
			knowledgeElement = (KnowledgeElement) valueKF.getValue();	
			
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
	
}
