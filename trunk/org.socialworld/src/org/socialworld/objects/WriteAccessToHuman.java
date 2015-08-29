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

	public HiddenSimulationObject getMeHidden() {
		return new HiddenHuman(this, nextToken());
	}

	public void setInventory(Inventory inventory, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.setInventory(inventory, this);
	}

	
	public void setTalks(ArrayList<Talk> talks, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.setTalks(talks, this);
	}
	
	
	public void addSentence(Human partner, Talk_SentenceType type,  String sentence,  HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.addSentence(partner, type, sentence, this);
	}

	public void addAnswer(Answer answer,  Human partner, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.addAnswer(answer, partner, this);
	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.addFactsFromSentence(sentence, source, this);
	}

	public void addKnowledge(Knowledge knowledge, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.addKnowledge(knowledge, this);
	}

	public void addAcquaintance(Acquaintance acquaintance, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.addAcquaintance(acquaintance, this);
	}

	
	public void setLastSaidSentence(String sentence, HiddenSimulationObject caller) {
		if (checkCaller(caller)) humanState.setLastSaidSentence(sentence, this);
	}
	
	
	
	
	
}
