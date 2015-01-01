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
import org.socialworld.actions.ActionType;
import org.socialworld.actions.useHands.IWeapon;
import org.socialworld.attributes.Inventory;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.Answer;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
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

		
	public String getSentence(Human partner, Talk_SentenceType type) {
		return findSentence( partner,  type);
	}
	
	public String getLastSaidSentence() {
		return lastSaidSentence;
	}

	public void addSentence(String sentence, Talk_SentenceType type, Human partner) {
		
		Talk talk = findTalk(partner);
		talk.addSentence(sentence, type);
	}

	public void addAnswer(Answer answer,  Human partner) {
		
		Talk talk = findTalk(partner);
		talk.addAnswer(answer);
	}
	
	public Answer getAnswerForQuestion(String question) {
		return knowledge.getAnswerForQuestion(question);
	}
	
	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		knowledge.addFactsFromSentence(sentence, source);
	}
	
	public Acquaintance getAcquaintance(Human partner) {
		return acquaintance.getAcquaintance(partner);
	}
	
	// TODO interface formore complex access to inventory
	public IWeapon getLeftHandWeapon() {
		return inventory.getLeftHandWeapon();
	}
	
	public IWeapon getRightHandWeapon() {
		return inventory.getRightHandWeapon();
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
			break;
		case useWeaponRight:
			break;
		case move:
			break;
		case say:
			break;
		case handleItem:
			 handleItem(action);
			break;
		case hear:
		default:
			 super.doAction(type,  action);
			break;
		}
	}

	protected void handleItem(final AbstractAction action) {


	}



	protected void spell(final AbstractAction action) {


	}

	protected void controlHandManually(final AbstractAction action) {


	}

	protected void touch(final AbstractAction action) {


	}


	protected void understand(final AbstractAction action) {

	}
	
	


	
	private String findSentence(Human partner, Talk_SentenceType type) {
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

	
	private Talk findTalk(Human partner) {
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
