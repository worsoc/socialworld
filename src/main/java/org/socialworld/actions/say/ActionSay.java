/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.actions.say;



import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.SentenceType;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.core.EventToCandidates;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventToTarget;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse ActionSay ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Sprechen beschreiben, gehören zu dieser Klasse.
 * 
 * Zur Beschreibung des Sprechens führt die Klasse die zusätzlichen Eigenschaften
 *   die Frage, die im Falle eines Gesprächs (Frage-Antwort-Wechsel) gestellt bzw. beantwortet wird,
 *   der Satz, der im Falle von Sagen/Reden gesprochen wird,
 *   den Gesprächspartner,
 *   die Richtung.
 * Die Ausführung der Aktion wird in der Klasse Say geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionSay abgelegt ist.
 * 
 * Die Klasse ActionSay dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Say dient der Wirksamwerdung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() werden Frage bzw. Satz in den entsprechenden Instanzvariablen abgelegt.
 *   
 *  Danach wird das Ausführungsobjekt der Klasse Say erzeugt.
 *
 *  Schließlich wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionSay ist 
 *  a) das Sagen eines Satzes (Antwort oder Frage) in einem Gespräch 
 *  oder
 *  b) das Sagen eines Satzes ohne direkten Bezug eines Gesprächs (also ohne Erwartung einer Erwiderung),
 *   aber ggf. durchaus zu einem Gesprächsparnter, mit dem man gleichzeitig ein Gespräch führt
 *  
 * 
 * @author Mathias Sikos
 *
 */
public class ActionSay extends AbstractAction {

	private ActionPerformer say;
	
	private String sentence;
	private SentenceType sentenceType = SentenceType.nothing;
	
	private SimulationObject target;
	private Vector direction;
	
	private static AccessTokenActionSay token = AccessTokenActionSay.getValid();
	
	public ActionSay(ValueArrayList actionProperties) {
		super(actionProperties);
	}
	
	public ActionSay(ActionSay original) {
		super(original);
	}

	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject target;
		Vector direction;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			target = objectRequester.requestSimulationObject(token, value, this);
			this.setTarget(target);
		}

		value =  actionProperties.getValue(furtherPropertyNames[1]);
		if (value.isValid()) {
			direction = objectRequester.requestVector(token, value, this);
			this.setDirection(direction);
		}

	}

	protected void setFurtherProperties(AbstractAction original) {
		setTarget(((ActionSay) original).getTarget());
		setDirection(((ActionSay) original).getDirection());
	}

	public  void perform() {
		
		Event event = null;
		EventType eventType;
		
		final Human partner = (Human) target;
		
		switch (type) {
			case talk:
				
				
				switch (mode) {
					case answerNormal:
					case answerScream:
					case answerWhisper:
					
						sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersQuestion);
						if (sentence == null) {
							sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.myPlannedAnswer);
							if (sentence == null) {
								return;
							}
							else {
								sentenceType = SentenceType.answer;
							}
							
						}
						else {
							sentenceType = SentenceType.question;
						}
	
				 		this.say = new Answer(this);

						break;
						
					case askNormal:
					case askScream:
					case askWhisper:
					
						sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.myPlannedQuestion);
						if (sentence == null) return;
						
						sentenceType = SentenceType.question;
						
				 		this.say = new Ask(this);

						break;
						
					default:
						
						return;
				}
				
				
		 		eventType = getEventType(type, mode);
				if (eventType != EventType.nothing) {
					event = new EventToTarget(eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(token ),  say /* as performer */);
				}
				break;
				
			case say:
				// TODO implement action type say
				
				switch (mode) {
					case sayNormal:
						sentence = "i love it.";
						break;
					case sayScream:
						sentence = "Help me.";
						break;
					case sayWhisper:
						sentence = "That sucks.";
						break;
					default:
						return;
				}

				sentenceType = SentenceType.say;
				
		 		this.say = new Say(this);
		 		eventType = getEventType(type, mode);
				if (eventType != EventType.nothing) {
					event = new EventToCandidates(eventType,    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(token),  say /* as performer */);
				}
		 		break;
				
			default:
				return;
		}

		if (eventType != EventType.nothing) {
			addEvent(event);
		}
		
 		eventType = getEventToCauserType(type, mode);
		if (eventType != EventType.nothing) {
			event = new EventToCauser(eventType,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(token),  say /* as performer */);
			addEvent(event);
		}

	}



	private EventType getEventType(ActionType type, ActionMode mode) {
		switch (type) {
		case talk:
			
			switch (mode) {
				case answerNormal:
					return EventType.targetAnswerNormal;
				case answerScream:
					return EventType.targetAnswerScream;
				case answerWhisper:
					return EventType.targetAnswerWhisper;
				case askNormal:
					return EventType.targetAskNormal;
				case askScream:
					return EventType.targetAskScream;
				case askWhisper:
					return EventType.targetAskWhisper;
					
				default:
					return EventType.nothing;
			}
			
		case say:
			
			switch (mode) {
				case sayNormal:
					return EventType.candidatesSayNormal;
				case sayScream:
					return EventType.candidatesSayScream;
				case sayWhisper:
					return EventType.candidatesSayWhisper;
				default:
					return EventType.nothing;
			}
			
		default:
			return EventType.nothing;
		}
		
	}

	private EventType getEventToCauserType(ActionType type, ActionMode mode) {
		switch (type) {
		case talk:
			
			switch (mode) {
				case answerNormal:
					return EventType.selfAnswerNormal;
				case answerScream:
					return EventType.selfAnswerScream;
				case answerWhisper:
					return EventType.selfAnswerWhisper;
				case askNormal:
					return EventType.selfAskNormal;
				case askScream:
					return EventType.selfAskScream;
				case askWhisper:
					return EventType.selfAskWhisper;
					
				default:
					return EventType.nothing;
			}
			
		case say:
			
			switch (mode) {
				case sayNormal:
					return EventType.selfSayNormal;
				case sayScream:
					return EventType.selfSayScream;
				case sayWhisper:
					return EventType.selfSayWhisper;
				default:
					return EventType.nothing;
			}
			
		default:
			return EventType.nothing;
		}
		
	}
	

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}


	SentenceType getSentenceType() {
		return this.sentenceType;
	}
	
	public Value getSentenceTypeAsValue(String valueName) {
		return new Value(Type.integer, valueName, this.sentenceType.getIndex());
	}

	String getSentence() {
		return this.sentence;
	}
	
	public Value getSentenceAsValue(String valueName) {
		return new Value(Type.string, valueName, this.sentence);
	}
	
	public void setTarget(SimulationObject target) {
		this.target = target;
	}
	
	SimulationObject getTarget() {
		return this.target;
	}

	public Value getTargetAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.target);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getTargetAsValue(Value.VALUE_BY_NAME_ACTION_TARGET));
		propertiesAsValueList.add(getSentenceTypeAsValue(Value.VALUE_BY_NAME_ACTION_SENTENCETYPE));
		propertiesAsValueList.add(getSentenceAsValue(Value.VALUE_BY_NAME_ACTION_SENTENCE));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
}
