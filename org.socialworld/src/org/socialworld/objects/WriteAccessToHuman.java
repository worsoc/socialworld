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

import java.util.ArrayList;

import org.socialworld.actions.handle.Inventory;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.AnswerProperties;
import org.socialworld.knowledge.KnowledgeProperties;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToHuman extends WriteAccessToAnimal {

	private  Human human;
	private  StateHuman	humanState;
	
	public WriteAccessToHuman(Human human, StateHuman state) {
		super(human, state);
		this.human = human;
		this.humanState = state;
		this.human.init();
	}

	public HiddenHuman getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenHuman(this, properties);
	}

	public int setInventory(Inventory inventory, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.inventory)) {
				humanState.setInventory(inventory, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setTalks(ArrayList<Talk> talks, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.talks)) {
				humanState.setTalks(talks, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int addSentence(Human partner, Talk_SentenceType type,  String sentence, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.sentence)) {
				humanState.addSentence(partner, type, sentence, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}
	
	public int addAnswer(AnswerProperties answer,  Human partner, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.answer)) {
				humanState.addAnswer(answer, partner, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int addFactsFromSentence(String sentence, KnowledgeSource source, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.factsFromSentence)) {
				humanState.addFactsFromSentence(sentence, source, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}
	

	public int addKnowledgeProperties(KnowledgeProperties knowledge, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.knowledge)) {
				humanState.addKnowledgeProperties(knowledge, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int addAcquaintance(Acquaintance acquaintance, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.acquaintance)) {
				humanState.addAcquaintance(acquaintance, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}

	public int setLastSaidSentence(String sentence, HiddenSimulationObject caller) {
		if (checkCaller(caller)) 
			if	(checkAccessToPropertyGranted(caller, GrantedAccessToProperty.lastSaidSentence)) {
				humanState.setLastSaidSentence(sentence, this);
				return WRITE_ACCESS_RETURNS_SUCCESS;
			}
			else
				return WRITE_ACCESS_RETURNS_NO_GRANT_FOR_PROPERTY;
		else
			return WRITE_ACCESS_RETURNS_INVALID_CALLER;
	}
	

}
