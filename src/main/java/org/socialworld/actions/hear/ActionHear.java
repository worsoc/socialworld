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
package org.socialworld.actions.hear;


import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.attributes.ActualTime;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.PunctuationMark;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.EventToCauser;
import org.socialworld.core.EventType;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionHear ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Zuhören und Verstegen beschreiben, gehören zu dieser Klasse.
 * Zur Beschreibung des Hörens führt die Klasse die zusätzlichen Eigenschaften
 * für den gehörten Satz und das Zielobjekt.
 * Die Ausführung der Aktion wird in der Klasse Hear geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionHear abgelegt ist.
 * 
 * Die Klasse ActionHear dient der Verwaltung der Aktion.
 * Die zugehörige Klasse Hear dient der Ausführung der Aktion, 
 *  nämlich als Argument für das zur Aktion gehörende Event.
 *
 *  In der Ausführungsmethode perform() wird der vom Zielobjekt gesprochene Satz ermittelt
 *   und in der Instanzvariablen sentence abgelegt. 
 *  Dabei wird im Falle des Zuhörens direkt beim Zielobjekt ausgelesen, 
 *   im Falle des Verstehens in der eigenen Stuktur (Talk) des Akteurs.
 *  Danach wird das Ausführungsobjekt der Klasse Hear erzeugt.
 *  Schließlich wird das Ereignis zur Aktion erzeugt, mit dem Ausführungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abhängigkeit von Aktionsmodus (ActionMode) und Satz ermittelt.
 *   
 *  Eine Aktion der Klasse ActionHear ist 
 *  a) das Zuhören (das Aufnehmen eines Satzes)
 *  oder
 *  b) das Verstehen eines Satzes (Information extrahieren und als Wissen ablegen)
 *
 */
public class ActionHear extends AbstractAction {

	private Hear hear;

	private String sentence;

	private SimulationObject target;

	public ActionHear(ValueArrayList actionProperties) {
		super(actionProperties);
	}

	
	public ActionHear(ActionHear original) {
		super(original);
	}

	protected void setFurtherProperties(ValueArrayList actionProperties) {

		Value value;
		
		SimulationObject target;

		value =  actionProperties.getValue(furtherPropertyNames[0]);
		if (value.isValid()) {
			target = objectRequester.requestSimulationObject(SimulationCluster.total, value, this);
			this.setTarget(target);
		}

	}

	protected void setFurtherProperties(AbstractAction original) {
		setTarget(((ActionHear) original).getTarget());
	}
	
	public  void perform() {
		
		Human partner;
		EventToCauser event;
		EventType eventType;
		
		partner = (Human) this.target;

		switch (mode) {
		case listenTo:
			
			sentence = partner.getLastSaidSentence();
			eventType = getEventType( mode, sentence);
			
			break;
			
		case understand:

			sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersSentence);
			eventType = getEventType( mode, sentence);

			break;
			
		default:
			return;
		}
		
		if (eventType == EventType.nothing) return;
		
  		this.hear = new Hear(this);
  		
		event = new EventToCauser(eventType,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(SimulationCluster.action),  hear /* as performer */);

		addEvent(event);
	
	}

	private EventType getEventType(ActionMode mode, String sentence) {
		
		switch (mode) {
		case listenTo:
			switch (PunctuationMark.getPunctuationMark(sentence)) {
			case dot: 
				return EventType.selfListenToStatement;
			case question:
				return EventType.selfListenToQuestion;
			case exclamation:
				return EventType.selfListenToInstruction;
			default:
				return EventType.nothing;
			}
		case understand:
			return EventType.selfUnderstand;
		default:
			return EventType.nothing;
		}
	}
	

	public void setTarget(SimulationObject target) {
		this.target = target;
	}

	private SimulationObject getTarget() {
		return this.target;
	}
	
	public Value getTargetAsValue(String valueName) {
		return new Value(Type.simulationObject, valueName, this.target);
	}
	
	public Value getSentenceAsValue(String valueName) {
		return new Value(Type.string, valueName, this.sentence);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
	
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		
		propertiesAsValueList.add(getTargetAsValue(Value.VALUE_BY_NAME_ACTION_TARGET));
		propertiesAsValueList.add(getSentenceAsValue(Value.VALUE_BY_NAME_ACTION_SENTENCE));
		paramObject.answerPropertiesRequest(propertiesAsValueList);
	
	}
	
}
