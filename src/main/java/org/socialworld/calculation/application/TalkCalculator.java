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


import org.socialworld.GlobalSwitches;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
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
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.access.HiddenHuman;

/**
 * @author Mathias Sikos
 *
 */
public class TalkCalculator  extends SocialWorldThread {

	private static TalkCalculator instance;

	private CapacityQueue<CollectionElementSimObjInfluenced> influencedTalks;
	
	private static AccessTokenTalkCalculator token = AccessTokenTalkCalculator.getValid();
	
	private static final int TALK_POOL_SIZE = 8192;
	private final CollectionElementSimObjInfluenced[] talkPool = new CollectionElementSimObjInfluenced[TALK_POOL_SIZE];
	private int talkWriteIndex = 0;

	/**
	 * private Constructor. 
	 */
	private TalkCalculator() {

		this.sleepTime = SocialWorldThread.SLEEPTIME_TALK_CALCULATOR;
		
		this.influencedTalks = new CapacityQueue<CollectionElementSimObjInfluenced>("influencedTalks", 5000);

		for (int i = 0; i < TALK_POOL_SIZE; i++) {
			this.talkPool[i] = new CollectionElementSimObjInfluenced(null, null, null);
		}
		
	}

	public static TalkCalculator getInstance() {
		if (instance == null) {
			instance = new TalkCalculator();
		}
		return instance;
	}
	
	@Override
	public void run() {
	    while (isRunning()) {
	        try {
	            // Warten auf das nächste Gesprächs-Event (
	            CollectionElementSimObjInfluenced talk =
	            		influencedTalks.poll(SocialWorldThread.SLEEPTIME_TALK_CALCULATOR, java.util.concurrent.TimeUnit.MILLISECONDS);
	            
	            if (talk != null) {
	                // Das erste Element direkt verarbeiten
	                calculateTalkInfluencedByEvent(talk);
	 
	                // Speicher-Referenzen kappen gegen Memory Loitering (Besitzerrecht liegt beim run-Loop)
	                talk.setEvent(null);
	                talk.setState(null);
	                talk.setHidden(null);

	                // Sobald wir wach sind: Alles wegarbeiten, was sich angestaut hat
	                CollectionElementSimObjInfluenced nextTalk;
	                while ((nextTalk = influencedTalks.poll()) != null) {
	                    calculateTalkInfluencedByEvent(nextTalk);
	                    
	                    // Auch hier Speicher-Referenzen sofort freigeben
	                    nextTalk.setEvent(null);
	                    nextTalk.setState(null);
	                    nextTalk.setHidden(null);
	                }
	            }

	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            break;
	        }
	    }
	}
	
	
	
	public final void calculateTalkInfluencedByEvent(Event event, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		if (event != null && stateHuman != null && hiddenWriteAccess != null) {
			// Bitmasken-Recycling statt 'new'
			int targetIdx = talkWriteIndex & (TALK_POOL_SIZE - 1);
			CollectionElementSimObjInfluenced pooledTalk = this.talkPool[targetIdx];
			
			pooledTalk.setEvent(event);
			pooledTalk.setState(stateHuman);
			pooledTalk.setHidden(hiddenWriteAccess);
			
			talkWriteIndex++;

			if (!this.influencedTalks.add(pooledTalk)) {
				talkWriteIndex--; // Rollback bei voller Queue
			}
		}
	}

	private final void calculateTalkInfluencedByEvent(CollectionElementSimObjInfluenced influencedTalk) {
		
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
			if (value.isValid()) {
				SimulationObject simObject;
				simObject = getInstance().objectRequester.requestSimulationObject(token, value, getInstance());
				if (simObject instanceof Human) partner = (Human) simObject;
				else return;
			}
			else
				return;
	
			value = params.getParam("sentence");
			if (value.isValid())
			{	
				Object o = value.getObject(Type.string);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("TalkCalculator.calculateListenTo > sentence: o (getObject(Type.string)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					return;
				}
				else {
					sentence = (String) o;
				}
			}
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
			if (value.isValid()) {
				SimulationObject simObject;
				simObject = getInstance().objectRequester.requestSimulationObject(token, value, getInstance());
				if (simObject instanceof Human) partner = (Human) simObject;
				else return;
			}
			else
				return;
	
			value = params.getParam("sentence");
			if (value.isValid())
			{	
				Object o = value.getObject(Type.string);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("TalkCalculator.calculateUnderstand > sentence: o (getObject(Type.string)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					return;
				}
				else {
					sentence = (String) o;
				}
			}
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
			if (value.isValid()) {
				SimulationObject simObject;
				simObject = getInstance().objectRequester.requestSimulationObject(token, value, getInstance());
				if (simObject instanceof Human) partner = (Human) simObject;
				else return;
			}
			else
				return;
	
			value = params.getParam(Value.VALUE_BY_NAME_ACTION_ANSWERS);
			if (value.isValid())
				answers = getInstance().objectRequester.requestValueArrayList(token, value, getInstance());
			else
				return;
			
			// There is a parameter direction, too   (Type.vector, "direction").
			// But it is ignored for talk calculation.
			
			Acquaintance acquaintance;
			acquaintance = stateHuman.getAcquaintance(partner);

			for (int i = 0; i < answers.size(); i++) {
				answer = getInstance().objectRequester.requestAnswer(token, answers.get(i), getInstance());
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
