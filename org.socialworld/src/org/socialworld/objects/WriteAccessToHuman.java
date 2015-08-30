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

import org.socialworld.attributes.Inventory;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.Knowledge;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenAnimal;
import org.socialworld.objects.access.HiddenHuman;
import org.socialworld.objects.access.HiddenSimulationObject;

public class WriteAccessToHuman extends WriteAccessToAnimal {

	private  Human human;
	private  StateHuman	humanState;
	
	public WriteAccessToHuman(Human human, StateHuman state, HiddenAnimal returnHidden) {
		super(human, state, returnHidden);
		this.human = human;
		this.humanState = state;
		this.human.init();
	}

	public HiddenSimulationObject getMeHidden(GrantedAccessToProperty properties[]) {
		return new HiddenHuman(this, properties);
	}

	public boolean setInventory(Inventory inventory, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.setInventory(inventory, this);
			return true;
		}
		else
			return false;
	}

	
	public boolean setTalks(ArrayList<Talk> talks, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.setTalks(talks, this);
			return true;
		}
		else
			return false;
	}
	
	
	public boolean addSentence(Human partner, Talk_SentenceType type,  String sentence,  HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)){
			humanState.addSentence(partner, type, sentence, this);
			return true;
		}
		else
			return false;
	}

	public boolean addAnswer(Answer answer,  Human partner, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.addAnswer(answer, partner, this);
			return true;
		}
		else
			return false;
	}

	public boolean addFactsFromSentence(String sentence, KnowledgeSource source, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.addFactsFromSentence(sentence, source, this);
			return true;
		}
		else
			return false;
	}

	public boolean addKnowledge(Knowledge knowledge, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.addKnowledge(knowledge, this);
			return true;
		}
		else
			return false;
	}

	public boolean addAcquaintance(Acquaintance acquaintance, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.addAcquaintance(acquaintance, this);
			return true;
		}
		else
			return false;
	}

	
	public boolean setLastSaidSentence(String sentence, HiddenSimulationObject caller) {
		if (checkCaller(caller) & checkAccessToPropertyGranted(caller, GrantedAccessToProperty.directionView)) {
			humanState.setLastSaidSentence(sentence, this);
			return true;
		}
		else
			return false;
	}
	
	
}
