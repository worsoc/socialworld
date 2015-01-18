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
package org.socialworld.objects;

import java.util.ArrayList;
import java.util.ListIterator;

import org.socialworld.actions.attack.IWeapon;
import org.socialworld.attributes.Inventory;
import org.socialworld.calculation.application.TalkCalculator;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.Event;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.AcquaintancePool;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;

/**
 * @author Mathias Sikos
 *
 */
public class StateHuman extends StateAnimal {

	private Inventory inventory;
	private KnowledgePool knowledge;
	private AcquaintancePool acquaintance;
	
	private ArrayList<Talk> talks;
	private String lastSaidSentence;

	public StateHuman() {
		super();
		
		talks = new ArrayList<Talk>();
		
	}

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		
		TalkCalculator.calculateTalkChangedByEvent(event, this);
		
	}

	public void setTalks(ArrayList<Talk> talks) {
		this.talks = talks;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void addSentence(Human partner, Talk_SentenceType type,  String sentence) {
			Talk talk = findTalk(partner);
			talk.addSentence(sentence, type);
	}

	public void addAnswer(Answer answer,  Human partner) {
		
		Talk talk = findTalk(partner);
		talk.addAnswer(answer);
	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		this.knowledge.addFactsFromSentence(sentence, source);
	}

	public Acquaintance getAcquaintance(Human partner) {
		return this.acquaintance.getAcquaintance(partner);
	}
	
	public String getLastSaidSentence() {
		return lastSaidSentence;
	}
	
	public Answer getAnswerForQuestion(String question) {
		// no copy
		// because a new answer is created in method KnowledgePool.getAnswerForQuestion()
		return this.knowledge.getAnswerForQuestion(question);
	}

	protected String findSentence(Human partner, Talk_SentenceType type) {
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

	protected Talk findTalk(Human partner) {
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

	public IWeapon getLeftHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getLeftHandWeapon();
	}
	
	public IWeapon getRightHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getRightHandWeapon();
	}

	public SimulationObject getLeftHand() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getLeftHand();
	}
	
	public SimulationObject getRightHand() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.inventory.getRightHand();
	}

}