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
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.conversation.PunctuationMark;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.core.EventType;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ActionHear extends AbstractAction {

	private Hear hear;

	private String sentence;

	private Vector direction;

	public ActionHear(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		
		setBaseProperties(type,  mode,
			target, 
			intensity,  minTime, maxTime,
			 priority,  duration);
		
		this.setDirection(direction);

	}
	
	public ActionHear(ActionHear original) {
		setBaseProperties(original);
		this.direction = original.direction;
	}

	public  Object getConcreteProperty(ActionProperty prop) {
		switch (prop) {
		case direction:
				return getDirection();
		default:
			return null;
		}
	}

	public  void perform() {
		Human partner;
		Event event;
		int eventTypeAsInt;
		
		partner = (Human) getTarget();

		switch (mode) {
		case listenTo:
			
			sentence = partner.getLastSaidSentence();
			eventTypeAsInt = getEventType( mode, sentence);
			
			break;
			
		case understand:

			sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersSentence);
			eventTypeAsInt = getEventType( mode, sentence);

			break;
			
		default:
			return;
		}
		
		if (eventTypeAsInt == -1) return;
		
  		this.hear = new Hear(this);
  		
		event = new Event(eventTypeAsInt,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(),  hear /* as optional parameter */);

		addEvent(event);
	
	}

	private int getEventType(ActionMode mode, String sentence) {
		
		switch (mode) {
		case listenTo:
			switch (PunctuationMark.getPunctuationMark(sentence)) {
			case dot: 
				return EventType.listenToStatement.getIndex();
			case question:
				return EventType.listenToQuestion.getIndex();
			case exclamation:
				return EventType.listenToInstruction.getIndex();
			default:
				return -1;
			}
		case understand:
			return EventType.understand.getIndex();
		default:
			return -1;
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

	
	public String getSentence() {
		return sentence;
	}
}
