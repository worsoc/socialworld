/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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


import org.socialworld.calculation.Value;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.Human;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.access.HiddenHuman;

/**
 * @author Mathias Sikos
 *
 */
public class TalkCalculator  extends SocialWorldThread {

	private static TalkCalculator instance;

	private CapacityQueue<CollectionElementSimObjInfluenced> influencedTalks;
	
/*	private List<Event> events;
	private List<StateHuman> states;
	private List<HiddenHuman> hiddenHumans;
*/
	/**
	 * private Constructor. 
	 */
	private TalkCalculator() {

		this.influencedTalks = new CapacityQueue<CollectionElementSimObjInfluenced>("influencedTalks", 1000);

/*		this.events = new ArrayList<Event>();
		this.states = new ArrayList<StateHuman>();
		this.hiddenHumans = new ArrayList<HiddenHuman>();
*/		
	}

	public static TalkCalculator getInstance() {
		if (instance == null) {
			instance = new TalkCalculator();
		}
		return instance;
	}
	
	public void run() {

		while (isRunning()) {
			
			if (this.influencedTalks.size() > 0) calculateTalkInfluencedByEvent();
			
			try {
				sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	public final void calculateTalkInfluencedByEvent(Event event, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		if (event != null && stateHuman != null && hiddenWriteAccess != null) {
			if (!this.influencedTalks.add(new CollectionElementSimObjInfluenced(event, stateHuman, hiddenWriteAccess))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
/*		this.events.add(event);
		this.states.add(stateHuman);
		this.hiddenHumans.add( hiddenWriteAccess);
*/
	}

	private final void calculateTalkInfluencedByEvent() {
		
/*		if (this.hiddenHumans.size() == 0) return;
		
		Event event = this.events.remove(0);
		StateHuman stateHuman  = this.states.remove(0);
		HiddenHuman hiddenWriteAccess = this.hiddenHumans.remove(0);
*/		
		CollectionElementSimObjInfluenced influencedTalk = this.influencedTalks.remove();
		if (influencedTalk != null) {

			Event event = influencedTalk.getEvent();
			StateHuman stateHuman  = (StateHuman) influencedTalk.getState();
			HiddenHuman hiddenWriteAccess =  (HiddenHuman) influencedTalk.getHidden();
		
			EventType eventType;
			eventType = event.getEventType();
		
			switch (eventType) {
			case selfListenToStatement:
			case selfListenToQuestion:
			case selfListenToInstruction:
				calculateListenTo(event, eventType, stateHuman, hiddenWriteAccess );
				break;
				
			case selfUnderstand:
				calculateUnderstand(event, eventType, stateHuman, hiddenWriteAccess);
				break;
				
			case selfAnswerNormal:
			case selfAnswerScream:
			case selfAnswerWhisper:
				calculateAnswers(event, eventType, stateHuman, hiddenWriteAccess);
				break;
				
				
			default:
				return;
			}
		}	
		else {
			System.out.println("TalkCalculator.calculateTalkInfluencedByEvent(): influencedTalk is null");
			return;
		}
			
	}
	
	private final static void calculateListenTo(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		
		IEventParam params;
		Value value;

		String sentence;
		Human partner;
		
		if (event.hasOptionalParam()) {
		
			params = event.getOptionalParam();
			
			value = params.getParam("partner");
			if (value.isValid())
				partner = (Human) value.getValue();
			else
				return;
	
			value = params.getParam("sentence");
			if (value.isValid())
				sentence = (String) value.getValue();
			else
				return;
	
			switch (eventType) {
			case selfListenToStatement:
				hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersSentence, sentence);
				break;
			case selfListenToQuestion:	
				hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersQuestion, sentence);
				break;
			case selfListenToInstruction:
				hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersInstruction, sentence);
				break;
			default:
				return;
			}
		
		}	
		
	}

	private final static void calculateUnderstand(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		IEventParam params;
		Value value;

		String sentence;
		Human partner;

		KnowledgeSource source;

		if (event.hasOptionalParam()) {

			params = event.getOptionalParam();
			
			value = params.getParam("partner");
			if (value.isValid())
				partner = (Human) value.getValue();
			else
				return;
	
			value = params.getParam("sentence");
			if (value.isValid())
				sentence = (String) value.getValue();
			else
				return;
			
			source = new KnowledgeSource(KnowledgeSource_Type.heardOf, partner);
			hiddenWriteAccess.addFactsFromSentence(sentence, source);
		
		}

	}

	private final static void calculateAnswers(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		IEventParam params;
		Value value;

		ValueArrayList answers;
		
		IAnswer answer;
		Human partner;

		if (event.hasOptionalParam()) {

			params = event.getOptionalParam();
			
			value = params.getParam(Value.VALUE_BY_NAME_ACTION_TARGET);
			if (value.isValid())
				partner = (Human) value.getValue();
			else
				return;
	
			value = params.getParam(Value.VALUE_BY_NAME_ACTION_ANSWERS);
			if (value.isValid())
				answers = (ValueArrayList) value.getValue();
			else
				return;
			
			// There is a parameter direction, too   (Type.vector, "direction").
			// But it is ignored for talk calculation.
			
			Acquaintance acquaintance;
			acquaintance = stateHuman.getAcquaintance(partner);

			for (int i = 0; i < answers.size(); i++) {
				answer = (IAnswer) answers.get(i).getValue();
				manipulateAnswer(answer, acquaintance, stateHuman,  partner);
				hiddenWriteAccess.addAnswer(answer,  partner); 
			}

		}
		
	}
	
	
	private static void manipulateAnswer(IAnswer answer, Acquaintance acquaintance, final StateHuman stateHuman,  final Human partner) {
		
		
		// TODO implement manipulateAnswer()
		// more complex, please
		// here only an example for an easy decision
		
		/*
		if (acquaintance.isAttributeValueLessThan(Acquaintance_Attribute.sympathy, AttributeArray.ATTRIBUTE_VALUE_MIDDLE) ) 
			answer.reduceToFactWithMinAccessCount();
		else if (acquaintance.isAttributeValueGreaterThan(Acquaintance_Attribute.sympathy, AttributeArray.ATTRIBUTE_VALUE_MIDDLE) ) 
			answer.sortBySource();
		else answer.reduceToFactWithMaxAccessCount();
		
		*/
	}

}
