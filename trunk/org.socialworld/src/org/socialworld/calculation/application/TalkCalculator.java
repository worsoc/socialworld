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
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.Human;
import org.socialworld.objects.StateHuman;
import org.socialworld.objects.access.HiddenHuman;

/**
 * @author Mathias Sikos
 *
 */
public class TalkCalculator {

	public final static void calculateTalkChangedByEvent(Event event, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		
		EventType eventType;
	
		eventType = event.getEventType();
	
		switch (eventType) {
		case listenToStatement:
		case listenToQuestion:
		case listenToInstruction:
			calculateListenTo(event, eventType, stateHuman, hiddenWriteAccess );
			break;
			
		case understand:
			calculateUnderstand(event, eventType, stateHuman, hiddenWriteAccess);
			break;
			
		case answerNormal:
		case answerScream:
		case answerWhisper:
			calculateAnswer(event, eventType, stateHuman, hiddenWriteAccess);
			break;
			
			
		default:
			return;
		}
	}
	
	private final static void calculateListenTo(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		
		IEventParam params;
		Value value;

		String sentence;
		Human partner;
		
		params = event.getOptionalParam();
		
		value = params.getParam(params.find("partner"));
		if (value.isValid())
			partner = (Human) value.getValue();
		else
			return;

		value = params.getParam(params.find("sentence"));
		if (value.isValid())
			sentence = (String) value.getValue();
		else
			return;

		switch (eventType) {
		case listenToStatement:
			hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersSentence, sentence);
			break;
		case listenToQuestion:	
			hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersQuestion, sentence);
			break;
		case listenToInstruction:
			hiddenWriteAccess.addSentence(partner, Talk_SentenceType.partnersInstruction, sentence);
			break;
		default:
			return;
		}
		
	}

	private final static void calculateUnderstand(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		IEventParam params;
		Value value;

		String sentence;
		Human partner;

		KnowledgeSource source;

		params = event.getOptionalParam();
		
		value = params.getParam(params.find("partner"));
		if (value.isValid())
			partner = (Human) value.getValue();
		else
			return;

		value = params.getParam(params.find("sentence"));
		if (value.isValid())
			sentence = (String) value.getValue();
		else
			return;
		
		source = new KnowledgeSource(KnowledgeSource_Type.heardOf, partner);
		hiddenWriteAccess.addFactsFromSentence(sentence, source);

	}

	private final static void calculateAnswer(Event event, EventType eventType, StateHuman stateHuman, HiddenHuman hiddenWriteAccess) {
		IEventParam params;
		Value value;

		Answer answer;
		Human partner;


		params = event.getOptionalParam();
		
		value = params.getParam(params.find("partner"));
		if (value.isValid())
			partner = (Human) value.getValue();
		else
			return;

		value = params.getParam(params.find("answer"));
		if (value.isValid())
			answer = (Answer) value.getValue();
		else
			return;
		
		// There is a parameter direction, too   (Type.vector, "direction").
		// But it is ignored for talk calculation.
		
		
		hiddenWriteAccess.addAnswer(answer,  partner); 

	}
}
