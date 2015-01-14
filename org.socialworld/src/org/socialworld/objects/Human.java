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

import org.socialworld.actions.attack.IWeapon;
import org.socialworld.attributes.Inventory;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Answer;
import org.socialworld.conversation.Talk_SentenceType;

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
	 
	private StateHuman state;
	
	public Human(StateHuman state) {
		super(state);
		this.state = state;
		init();
	}

	
	private void init() {
	}
	

	
	/**
	 * @param inventory
	 *            the inventory to set
	 */
	void setInventory(final Inventory inventory, WriteAccessToHuman guard) {
		if (checkGuard(guard) ) 
			this.state.setInventory(inventory);
	}

		
	public String getSentence(Human partner, Talk_SentenceType type) {
		return new String(this.state.findSentence( partner,  type));
	}
	
	public String getLastSaidSentence() {
		return new String(this.state.getLastSaidSentence());
	}

	void addSentence(Human partner, Talk_SentenceType type,  String sentence, WriteAccessToHuman guard) {
		if (checkGuard(guard) ) 
			this.state.addSentence( partner,  type,   sentence);
	}

	void addAnswer(Answer answer,  Human partner, WriteAccessToHuman guard) {
		if (checkGuard(guard) ) 
			this.state.addAnswer( answer,   partner);
	}
	
	public Answer getAnswerForQuestion(String question) {
		// no copy
		// because a new answer is created in method KnowledgePool.getAnswerForQuestion()
		return this.state.getAnswerForQuestion(question);
	}
	
	
	public Acquaintance getAcquaintance(Human partner) {
		return new Acquaintance(this.state.getAcquaintance(partner));
	}
	
	// TODO interface for more complex access to inventory
	public IWeapon getLeftHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getLeftHandWeapon();
	}
	
	public IWeapon getRightHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getRightHandWeapon();
	}

	public SimulationObject getLeftHand() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getLeftHand();
	}
	
	public SimulationObject getRightHand() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getRightHand();
	}


		
	
}
