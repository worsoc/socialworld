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
package org.socialworld.objects.access;

import java.util.ArrayList;

import org.socialworld.attributes.Inventory;
import org.socialworld.conversation.Talk;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.Knowledge;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.Human;
import org.socialworld.objects.WriteAccessToHuman;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenHuman extends HiddenAnimal {

	private WriteAccessToHuman wa;

	public HiddenHuman(WriteAccessToHuman wa, GrantedAccessToProperty properties[]) {
		super(wa, properties);
		this.wa = wa;
	}

	public void setInventory(Inventory inventory) {
		wa.setInventory(inventory, this);
	}

	public void addSentence(Human partner, Talk_SentenceType type,  String sentence) {
		wa.addSentence(partner, type, sentence, this);
	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		wa.addFactsFromSentence(sentence, source, this);
	}
	
	public void addAnswer(Answer answer,  Human partner) {
		wa.addAnswer(answer, partner, this);
	}
	
	public void setTalks(ArrayList<Talk> talks) {
		wa.setTalks(talks, this);
	}
	
	public void addKnowledge(Knowledge knowledge) {
		wa.addKnowledge(knowledge, this);
	}

	public void addAcquaintance(Acquaintance acquaintance) {
		wa.addAcquaintance(acquaintance, this);
	}
	
	public void setLastSaidSentence(String sentence) {
		wa.setLastSaidSentence(sentence, this);
	}
	

}
