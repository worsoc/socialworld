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
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.conversation.PunctuationMark;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ActionHear extends AbstractAction {

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
		
		switch (mode) {
			case  listenTo:
				ActionHear followingAction;
				
				if (target instanceof Human) {

					final Human human = (Human) target;
					String sentence;
					PunctuationMark punctuationMark;
					
					sentence = human.getLastSaidSentence();
					punctuationMark = addPartnersSentence(sentence, human);
					
					followingAction = new ActionHear(this);
					switch (punctuationMark) {
						case dot:
							followingAction.setMode(ActionMode.understand);
						case question:
							followingAction.setType(ActionType.say);
							followingAction.setMode(ActionMode.answer);
						default:
							followingAction.setType(ActionType.ignore);

					}
					((Human) actor).addAction(followingAction);
				}
				
			case understand:
				final Human human = (Human) target;
				KnowledgeSource source;
				String sentence;

				sentence = ((Human) actor).getSentence(human, Talk_SentenceType.partnersSentence);
				if (sentence != null) {
					source = new KnowledgeSource();
					source.setSourceType( KnowledgeSource_Type.heardOf);
					// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
					source.setOrigin(((Human) actor).getAcquaintance(human));
					((Human) actor).addFactsFromSentence(sentence, source);
				}
			default:
		}
		
		

		
	}


	private PunctuationMark addPartnersSentence(String sentence, Human partner) {
		PunctuationMark returnValue = null;
		Talk_SentenceType type;
		
		returnValue = PunctuationMark.getPunctuationMark(sentence);
		
		switch (returnValue) {
		case dot: 
			type = Talk_SentenceType.partnersSentence;
		case question: 
			type = Talk_SentenceType.partnersQuestion;
		default:
			type = Talk_SentenceType.partnersUnknownType;
		}
		((Human) actor).addSentence(sentence, type, partner);
		
		return returnValue;
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

	
}
