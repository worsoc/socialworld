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
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ActionSay extends AbstractAction {

	private Say say;
	
	private String question;
	private String sentence;
	
	private SimulationObject target;
	private Vector direction;
	
	public ActionSay(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setDirection(direction);
			this.target = target;
	}
	
	public ActionSay(ActionSay original) {
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
		
		EventByAction event;
		final Human partner = (Human) target;
		int eventTypeAsInt;

		
		switch (type) {
			case talk:
				
				switch (mode) {
					case answerNormal:
					case answerScream:
					case answerWhisper:
					
						question = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersQuestion);
						if (question == null) return;
						
						break;
						
					case askNormal:
					case askScream:
					case askWhisper:
					
						question = ((Human) actor).getSentence(partner, Talk_SentenceType.myPlannedQuestion);
						if (question == null) return;
		
						break;
						
					default:
						
						return;
				}
				break;
				
			case say:
				
				switch (mode) {
					case normal:
						// TODO
						//sentence = 
						break;
					case scream:
						// TODO
						//sentence = 
						break;
					case whisper:
						// TODO
						//sentence = 
						break;
					default:
						return;
				}
				break;
				
			default:
				return;
		}

		
		switch (type) {
		case talk:
			
			switch (mode) {
				case answerNormal:
					eventTypeAsInt = EventType.answerNormal.getIndex();
					break;
				case answerScream:
					eventTypeAsInt = EventType.answerScream.getIndex();
					break;
				case answerWhisper:
					eventTypeAsInt = EventType.answerWhisper.getIndex();
					break;
				case askNormal:
					eventTypeAsInt = EventType.askNormal.getIndex();
					break;
				case askScream:
					eventTypeAsInt = EventType.askScream.getIndex();
					break;
				case askWhisper:
					eventTypeAsInt = EventType.askWhisper.getIndex();
					break;
					
				default:
					
					return;
			}
			break;
			
		case say:
			
			switch (mode) {
				case normal:
					eventTypeAsInt = EventType.sayNormal.getIndex();
					break;
				case scream:
					eventTypeAsInt = EventType.sayScream.getIndex();
					break;
				case whisper:
					eventTypeAsInt = EventType.sayWhisper.getIndex();
					break;
				default:
					return;
			}
			break;
			
		default:
			return;
	}

 		this.say = new Say(this);
  		
		event = new EventByAction(eventTypeAsInt,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(),  say /* as performer */);

		addEvent(event);

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

	public String getQuestion() {
		return question;
	}
	
	public SimulationObject getTarget() {
		return this.target;
	}
}
