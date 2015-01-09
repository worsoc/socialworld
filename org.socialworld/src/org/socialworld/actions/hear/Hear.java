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
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
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
		
		if (!isValid()) {
	 		ActionHear originalAction;
			Human partner;
			String sentence;
			
			originalAction = (ActionHear) getOriginalActionObject();
			ActionMode mode = originalAction.getMode();
			partner = (Human) originalAction.getTarget();
			
			switch (mode) {
			case  listenTo:
		
				sentence = originalAction.getSentence();
		
				// actor.addSentence(sentence, type (Talk_SentenceType!), partner);
				// TODO --> event processing
				
				setMaxParam(2);
				setParam(0, new Value(Type.simulationObject, "partner", partner));
				setParam(1, new Value(Type.string, "sentence", sentence));
				
				setValid();
				
				break;
					
			case understand:
	
				sentence = originalAction.getSentence();
				
				//KnowledgeSource source;
				//source = new KnowledgeSource();
				//source.setSourceType( KnowledgeSource_Type.heardOf);
				// get the acquaintance of target human (null if the there isn't an acquaintance of target human)
				//source.setOrigin( actor.getAcquaintance(partner));
				// actor.addFactsFromSentence(sentence, source);
				// TODO --> event processing
				
				setMaxParam(2);
				setParam(0, new Value(Type.simulationObject, "partner", partner));
				setParam(1, new Value(Type.string, "sentence", sentence));
				
				setValid();
				
				break;
				
			default:
			}
		}
	}
	
	


}
