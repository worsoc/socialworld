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
import org.socialworld.knowledge.Knowledge;
import org.socialworld.knowledge.KnowledgePool;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.access.HiddenHuman;

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
		knowledge = new KnowledgePool();
		acquaintance = new AcquaintancePool();
	}

	void calculateEventInfluence(Event event) {
		
		super.calculateEventInfluence(event);
		
		TalkCalculator.calculateTalkChangedByEvent(event, ((StateHuman)getMeReadableOnly()), ((HiddenHuman)getMeWritableButHidden() ));
		
		
	}

	public void setTalks(ArrayList<Talk> talks, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.talks = talks;
		}
	}
	
	public void setInventory(Inventory inventory, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.inventory = inventory;
		}
	}
	
	public void addSentence(Human partner, Talk_SentenceType type,  String sentence, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			Talk talk = findTalk(partner);
			talk.addSentence(sentence, type);
		}
	}

	public void addAnswer(Answer answer,  Human partner, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			Talk talk = findTalk(partner);
			talk.addAnswer(answer);
		}
	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.knowledge.addFactsFromSentence(sentence, source);
		}
	}

	public void addKnowledge(Knowledge knowledge, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.knowledge.addKnowledge(knowledge);
		}
	}

	public void addAcquaintance(Acquaintance acquaintance, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			this.acquaintance.addAcquaintance(acquaintance);
		}
	}

	public Acquaintance getAcquaintance(Human partner) {
		return new Acquaintance(this.acquaintance.getAcquaintance(partner));
	}
	
	public void setLastSaidSentence(String sentence, WriteAccessToHuman guard) {
		if (checkGuard(guard)) {
			lastSaidSentence = sentence;
		}
	}
	
	public String getLastSaidSentence() {
		return new String(lastSaidSentence);
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
		return new String(sentence);

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
