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
package org.socialworld.actions.say;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Acquaintance_Attribute;
import org.socialworld.knowledge.AnswerProperties;
import org.socialworld.objects.Human;

/**
 * German:
 * Die Klasse Say ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Say dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden im Falle einer Antwort
 *   - der (Gesprächs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Antwort
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  In der Ausführungsmethode perform() werden im Falle einer Frage
 *   - der (Gesprächs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Frage (als Satz (also String))
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *   Für die Bereitstellung der Parameter ist es unerheblich, ob die Antwort bzw. die Frage
 *    normal gesprochen, geflüstert oder geschrien wird.
 *     Diese Unterscheidung steckt bereits im EventType des Ereignisses.
 *    
 * Die Antwort auf eine Frage wird in Abhängigkeit der Beziehung zum Gesprächspartner
 *  (also die Qualität der Bekanntschaft) manipuliert.
 *  Dadurch wird erreicht, dass der Antworter auf eine Frage nicht grundsätzlich gleich antwortet.
 *  
 * @author Mathias Sikos
 *
 */
public class Say extends ActionPerformer {

	
    public Say (ActionSay action) {
    	super(action);
    	
    }

    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_DIRECTION);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}

    	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_TARGET);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_QUESTION);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_SENTENCE);
    	if (property.isValid()) {
    		addProperty(property);
    	}
  	
    }

	/* (non-Javadoc)
	 * @see org.socialworld.actions.ActionPerformer#perform()
	 */
	@Override
	protected void perform() {
		
		
		if (!isEvaluated()) {
			
	 		ActionSay originalAction;
			Human actor;
			Human partner;
			ActionMode mode;
			
			String question;
			
			originalAction = (ActionSay) getOriginalActionObject();
			actor = (Human) originalAction.getActor();
			mode = originalAction.getMode();
			
			switch (mode) {
				case answerNormal:
				case answerScream:
				case answerWhisper:
					
					AnswerProperties answer;
					
					Value tmp;
					tmp = getParam(Value.VALUE_BY_NAME_ACTION_QUESTION);
					if (tmp.isValid()) {
						question = (String) tmp.getValueCopy();
					}
					else {
						question = originalAction.getQuestion();
						setParam( new Value(Type.string, Value.VALUE_BY_NAME_ACTION_QUESTION, question));
					}
	
					tmp = getParam(Value.VALUE_BY_NAME_ACTION_TARGET);
					if (tmp.isValid()) {
						partner = (Human) tmp.getValueCopy();
					}
					else {
						partner = (Human) originalAction.getTarget();
						setParam( new Value(Type.simulationObject, Value.VALUE_BY_NAME_ACTION_TARGET, partner ));
					}

					answer =  actor.getAnswerForQuestion(question);
					manipulateAnswer(actor, answer, partner);
		
					addParam( new Value(Type.answer, Value.VALUE_BY_NAME_ACTION_ANSWER, answer));
					
					
					break;
					
				case askNormal:
				case askScream:
				case askWhisper:
										

					break;
					
				case normal:
				case scream:
				case whisper:
										
					
					break;
					
				default:
					
			}
			
			setEvaluated();
		}
	}

	private void manipulateAnswer(final Human actor, AnswerProperties answer, final Human partner) {
		
		Acquaintance acquaintance;
		acquaintance = actor.getAcquaintance(partner);
		
		// TODO implement manipulateAnswer()
		// more complex, please
		// here only an example for an easy decision
		if (acquaintance.isAttributeValueLessThan(Acquaintance_Attribute.sympathy, AttributeArray.ATTRIBUTE_VALUE_MIDDLE) ) 
			answer.reduceToFactWithMinAccessCount();
		else if (acquaintance.isAttributeValueGreaterThan(Acquaintance_Attribute.sympathy, AttributeArray.ATTRIBUTE_VALUE_MIDDLE) ) 
			answer.sortBySource();
		else answer.reduceToFactWithMaxAccessCount();
	}

}
