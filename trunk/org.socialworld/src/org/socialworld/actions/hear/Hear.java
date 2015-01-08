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
package org.socialworld.actions.hear;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.conversation.PunctuationMark;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.knowledge.KnowledgeSource;
import org.socialworld.knowledge.KnowledgeSource_Type;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos
 *
 */
public class Hear extends ActionPerformer {

    public Hear (ActionHear action) {
    	super(action);
    }
		
	/* (non-Javadoc)
	 * @see org.socialworld.actions.ActionPerformer#perform()
	 */
	@Override
	public void perform() {
 		ActionHear originalAction;
		Human actor;
		Human partner;
		String sentence;
		
		originalAction = (ActionHear) getOriginalActionObject();
		actor = (Human) originalAction.getActor();
		ActionMode mode = originalAction.getMode();
		partner = (Human) originalAction.getTarget();
		
		switch (mode) {
		case  listenTo:
	
			sentence = originalAction.getSentence();
			addPartnersSentence(actor, sentence, partner);
			
			break;
				
		case understand:
			KnowledgeSource source;

			sentence = originalAction.getSentence();
			
			source = new KnowledgeSource();
			source.setSourceType( KnowledgeSource_Type.heardOf);
			// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
			source.setOrigin( actor.getAcquaintance(partner));
			
			actor.addFactsFromSentence(sentence, source);
			
			break;
			
		default:
		}
	}
	
	

	private PunctuationMark addPartnersSentence(Human actor, String sentence, Human partner) {
		PunctuationMark returnValue = null;
		Talk_SentenceType type;
		
		returnValue = PunctuationMark.getPunctuationMark(sentence);
		
		switch (returnValue) {
		case dot: 
			type = Talk_SentenceType.partnersSentence;
		case question: 
			type = Talk_SentenceType.partnersQuestion;
		default:
			type = Talk_SentenceType.partnersUnknownType;
		}
		((Human) actor).addSentence(sentence, type, partner);
		
		return returnValue;
	}

}
