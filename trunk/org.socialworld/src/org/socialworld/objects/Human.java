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


import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.AnswerProperties;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.StateSeer;
import org.socialworld.objects.properties.IWeapon;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.IEventParam;

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
	private boolean initialized;
	

	protected SimulationObject_Type getSimObjectType() {
		return SimulationObject_Type.human;
	}

	
	protected void init() {
		if (initialized == false) {
			initialized = true;
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    STATE       //////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	protected void assignState(StateSimulationObject state) {
		super.assignState(state);
		if (checkIsMyState(state) ) this.state = (StateHuman) state;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(2000);
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateSeer.class.getName())) {
			return new StateSeer();
		}
		
		return null;
		
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    TALK       ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
		
	final public String getSentence(Human partner, Talk_SentenceType type) {
		return this.state.findSentence( partner,  type);
	}
	
	final public String getLastSaidSentence() {
		return this.state.getLastSaidSentence();
	}

	final public AnswerProperties getAnswerForQuestion(String question) {
		// no copy
		// because a new answer is created in method KnowledgePool.getAnswerForQuestion()
		return this.state.getAnswerForQuestion(question);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    KNOWLEDGE  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	final public Acquaintance getAcquaintance(Human partner) {
		return this.state.getAcquaintance(partner);
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    INVENTORY  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO interface for more complex access to inventory
	final public IWeapon getLeftHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getLeftHandWeapon();
	}
	
	final public IWeapon getRightHandWeapon() {
		// no copy because it is a simulation object and that isn't allowed to be duplicated
		return this.state.getRightHandWeapon();
	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    PROPERTY LIST  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public void requestPropertyList(IEventParam paramObject) {
		
		super.requestPropertyList(paramObject);
		
		ValueArrayList propertiesAsValueList = new ValueArrayList();
		paramObject.answerPropertiesRequest(propertiesAsValueList);
		
	}
		
	
}
