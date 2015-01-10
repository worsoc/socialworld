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
	
	public Human() {
		super(SimulationObject_Type.human);
		init();
		
	}

	public Human(SimulationObject_Type objectType) {
		super(objectType);
		init();
		
	}
	
	private void init() {
	}
	
	void init(WriteAccessToHuman guard) {
		super.init(guard);
		if (checkGuard(guard) )
			this.state = (StateHuman) getState(guard);
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
		return this.state.findSentence( partner,  type);
	}
	
	public String getLastSaidSentence() {
		return this.state.getLastSaidSentence();
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
		return this.state.knowledge.getAnswerForQuestion(question);
	}
	
	
	public Acquaintance getAcquaintance(Human partner) {
		return this.state.acquaintance.getAcquaintance(partner);
	}
	
	// TODO interface for more complex access to inventory
	public IWeapon getLeftHandWeapon() {
		return this.state.inventory.getLeftHandWeapon();
	}
	
	public IWeapon getRightHandWeapon() {
		return this.state.inventory.getRightHandWeapon();
	}

	public SimulationObject getLeftHand() {
		return this.state.inventory.getLeftHand();
	}
	
	public SimulationObject getRightHand() {
		return this.state.inventory.getRightHand();
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
	
	
}
