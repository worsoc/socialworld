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

import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.Answer;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.objects.Human;
import org.socialworld.objects.WriteAccessToHuman;

/**
 * @author Mathias Sikos
 *
 */
public class HiddenHuman extends HiddenAnimal {

	private WriteAccessToHuman wa;

	public HiddenHuman(WriteAccessToHuman wa, long token) {
		super(wa, token);
		this.wa = wa;
	}

	public void addSentence(Human partner, Talk_SentenceType type,  String sentence) {
		if (isValid()) wa.addSentence(partner, type, sentence, this);

	}

	public void addFactsFromSentence(String sentence, KnowledgeSource source) {
		if (isValid()) wa.addFactsFromSentence(sentence, source, this);
	}
	
	public void addAnswer(Answer answer,  Human partner) {
		if (isValid()) wa.addAnswer(answer, partner, this);
	}
	
}
