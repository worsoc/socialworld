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
package org.socialworld.objects;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.hear.ActionHear;
import org.socialworld.actions.say.ActionSay;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.attributes.Inventory;
import org.socialworld.knowledge.Acquaintance_Attribute;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.conversation.PunctuationMark;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A human is described in most details. It is the most important simulation
 * object. The simulation of a human is the main target of the game. A human has
 * an attribute array that describes his inner state. There are values for
 * courage, spirituality and morals for example. There is a detailed
 * differentiation of action handling an event influence. A human is the only
 * simulation object that has an inventory. So it can carry items and use them.
 * 
 * @author Mathias Sikos (tyloesand)
 * 
 */
 public class Human extends Mammal {
	 
	protected Inventory inventory;
	protected KnowledgePool knowledge;
	protected AcquaintancePool acquaintance;
	
	private ArrayList<Talk> talks;
	private String lastSaidSentence;
	
	public Human() {
		super();
		talks = new ArrayList<Talk>();
		
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	void setInventory(final Inventory inventory, WriteAccessToHuman guard) {
		if (this.guard == guard ) this.inventory = inventory;
	}

	/**
	 * Depending on the action type the method calls the according procedure
	 * with special implementation of the action.
	 */
	@Override
	protected void doAction(final ActionType type, final AbstractAction action) {


		switch (type) {
		case touch:
			 touch(action);
			break;
		case sleep:
			 sleep(action);
			break;
		case changeMove:
			 changeMove(action);
			break;
		case kick:
			 kick(action);
			break;
		case controlHandManually:
			 controlHandManually(action);
			break;
		case spell:
			 spell(action);
			break;
		case useWeaponLeft:
			 useWeaponLeft(action);
			break;
		case useWeaponRight:
			 useWeaponRight(action);
			break;
		case move:
			 move(action);
			break;
		case say:
			 say(action);
			break;
		case handleItem:
			 handleItem(action);
			break;
		case hear:
			 hear(action);
		default:
			 super.doAction(type,  action);
			break;
		}
	}

	protected void handleItem(final AbstractAction action) {


	}


	protected void useWeaponRight(final AbstractAction action) {
		final SimulationObject rightHand = this.inventory.getRightHand();
		if (rightHand != null ) 
			if (rightHand instanceof Weapon){
				final Weapon weapon = (Weapon) rightHand;
				weapon.handle(action, this);
			}
			else if (rightHand instanceof Item) {
				final Item item = (Item) rightHand;
				item.handle(action, this);
			}

		
	}

	protected void useWeaponLeft(final AbstractAction action) {
		final SimulationObject leftHand = this.inventory.getLeftHand();
		if (leftHand != null ) 
			if (leftHand instanceof Weapon){
				final Weapon weapon = (Weapon) leftHand;
				weapon.handle(action, this);
			}
			else if (leftHand instanceof Item) {
				final Item item = (Item) leftHand;
				item.handle(action, this);
			}

	}

	protected void spell(final AbstractAction action) {


	}

	protected void controlHandManually(final AbstractAction action) {


	}

	protected void touch(final AbstractAction action) {


	}

	protected void say(final AbstractAction action) {
		ActionMode mode = action.getMode();
		ActionSay followingAction = null;
		final Human human = (Human) action.getTarget();
		String question;
		
		switch (mode) {
			case answer:
				Answer answer;
			
				question = getSentence(human, Talk_SentenceType.partnersQuestion);
				if (question != null) {
					followingAction = new ActionSay((ActionSay) action);
					answer = knowledge.getAnswerForQuestion(question);
					manipulateAnswer(answer, human);
					addAnswer(answer,  human);
					// TODO the mode depends on intensity
					followingAction.setMode(ActionMode.say);
				}
			case ask:
			
				question = getSentence(human, Talk_SentenceType.myPlannedQuestion);
				if (question != null) {
				}
			default:
		}
		actionHandler.insertAction(followingAction);

	}
	
	protected void hear(final AbstractAction action) {
		ActionMode mode = action.getMode();
		
		switch (mode) {
			case  listenTo:
				final SimulationObject target = action.getTarget();
				ActionHear followingAction;
				
				if (target instanceof Human) {

					final Human human = (Human) target;
					String sentence;
					PunctuationMark punctuationMark;
					
					sentence = human.getLastSaidSentence();
					punctuationMark = addPartnersSentence(sentence, human);
					
					followingAction = new ActionHear((ActionHear)action);
					switch (punctuationMark) {
						case dot:
							followingAction.setMode(ActionMode.understand);
						case question:
							followingAction.setType(ActionType.say);
							followingAction.setMode(ActionMode.answer);
						default:
							followingAction.setType(ActionType.ignore);

					}
					actionHandler.insertAction(followingAction);
				}
				
			case understand:
				final Human human = (Human) action.getTarget();
				KnowledgeSource source;
				String sentence;

				sentence = getSentence(human, Talk_SentenceType.partnersSentence);
				if (sentence != null) {
					source = new KnowledgeSource();
					source.setSourceType( KnowledgeSource_Type.heardOf);
					// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
					source.setOrigin(acquaintance.getAcquaintance(human));
					knowledge.addFactsFromSentence(sentence, source);
				}
			default:
		}
		
		

	}

	protected void understand(final AbstractAction action) {

	}
	
	protected String getLastSaidSentence() {
		return lastSaidSentence;
	}
	
	protected PunctuationMark addPartnersSentence(String sentence, Human partner) {
		PunctuationMark returnValue = null;
		Talk_SentenceType type;
		
		returnValue = talks.get(0).getPunctuationMark(sentence);
		
		switch (returnValue) {
		case dot: 
			type = Talk_SentenceType.partnersSentence;
		case question: 
			type = Talk_SentenceType.partnersQuestion;
		default:
			type = Talk_SentenceType.partnersUnknownType;
		}
		addSentence(sentence, type, partner);
		
		return returnValue;
	}

	protected void addAnswer(Answer answer,  Human partner) {
		
		Talk talk;
		String sentence;
		
		talk = getTalk(partner);
		
		sentence = talk.makeAnswerSentence(answer);
		talk.addSentence(sentence, Talk_SentenceType.myPlannedSentence);
	}

	protected void addSentence(String sentence, Talk_SentenceType type, Human partner) {
		Talk talk;
		
		talk = getTalk(partner);
		
		talk.addSentence(sentence, type);
	}
	
	protected String getSentence(Human partner, Talk_SentenceType type) {
		ListIterator<Talk> iterator ;
		Talk talk;
		String sentence = null;
		
		iterator = talks.listIterator();
		
		if (partner == null) 
			
			sentence = talks.get(0).getSentence(type);		
		
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				sentence = talk.getSentence(type);		
				if (sentence == null) iterator.remove();
				break;
			}
		}
		return sentence;
	}

	private void manipulateAnswer(Answer answer, Human partner) {
		
		Acquaintance acquaintance;
		acquaintance = this.acquaintance.getAcquaintance(partner);
		
		// TODO
		// more complex, please
		// here only an example for an easy decision
		if (acquaintance.isAttributeValueLessThan(Acquaintance_Attribute.sympathy, AttributeArray.VALUE_MIDDLE) ) 
			answer.reduceToFactWithMinAccessCount();
		else if (acquaintance.isAttributeValueGreaterThan(Acquaintance_Attribute.sympathy, AttributeArray.VALUE_MIDDLE) ) 
			answer.sortBySource();
		else answer.reduceToFactWithMaxAccessCount();
	}
	
	private Talk getTalk(Human partner) {
		ListIterator<Talk> iterator ;
		Talk talk;
		
		iterator = talks.listIterator();
		
		
		if (partner == null) {
			
				return talks.get(0);
		}
		else while (iterator.hasNext()) {
			talk = iterator.next();
			if (talk.getPartner() == partner) {
				return talk;
			}
		}
		return  talks.get(0);
	}
}
