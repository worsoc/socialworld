/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.GlobalSwitches;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.SentenceType;
import org.socialworld.core.EventTypeGeneral;
import org.socialworld.knowledge.IAnswer;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse Answer ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Answer dient der Wirksamwerdung der Aktion,
 *  n�mlich als Argument f�r das zur Aktion geh�rende Ereignis.
 *
 *  In der Ausf�hrungsmethode perform() werden 
 *   - der (Gespr�chs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Antwort
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *   F�r die Bereitstellung der Parameter ist es unerheblich, ob die Antwort 
 *    normal gesprochen, gefl�stert oder geschrien wird.
 *     Diese Unterscheidung steckt bereits im EventType des Ereignisses.
 *    
 * Die Antwort auf eine Frage wird in Abh�ngigkeit der Beziehung zum Gespr�chspartner
 *  (also die Qualit�t der Bekanntschaft) manipuliert.
 *  Dadurch wird erreicht, dass der Antworter auf eine Frage nicht grunds�tzlich gleich antwortet.
 *  
 * @author Mathias Sikos
 *
 */
public class Answer extends ActionPerformer {

	
    public Answer (ActionSay action) {
    	super(action);
    	
    }

	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
		switch (mode) {
		case answerNormal:
		case answerScream:
		case answerWhisper:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
	 		result.add(Value.VALUE_BY_NAME_EVENT_SENTENCE);
	 		result.add(Value.VALUE_BY_NAME_EVENT_SENTENCETYPE);
			break;
		}
 		return result;
 	}
 
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(PropertyName.simobj_attributeArray.toString());
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
			
			SentenceType sentenceType;
			String sentence;
			
			originalAction = (ActionSay) getOriginalActionObject();
			actor = (Human) originalAction.getActor();
			mode = originalAction.getMode();
			
			
			switch (mode) {
				case answerNormal:
				case answerScream:
				case answerWhisper:
					
					Value tmp;
					
					tmp = getParam(Value.VALUE_BY_NAME_ACTION_TARGET);
					if (tmp.isValid()) {
						// TODO partner is not used until now
						//SimulationObject simObject;
						//simObject = objectRequester.requestSimulationObject(token, tmp, this, requestValueID);
						//if (simObject instanceof Human) partner = (Human) simObject;
					}
					else {
						partner = (Human) originalAction.getTarget();
						setParam( new Value(Type.simulationObject, Value.VALUE_BY_NAME_ACTION_TARGET, partner ));
					}

					List<IAnswer> answers;
					
					
					tmp = getParam(Value.VALUE_BY_NAME_ACTION_SENTENCETYPE);
					if (tmp.isValid()) {
						Object o = tmp.getObject(Type.integer);
						if (o instanceof NoObject) {
							if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
								System.out.println("Answer.perform > sentenceType: o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() + " instead of float");
							}
							sentenceType = SentenceType.getSentenceType(0);
						}
						else {
							sentenceType = SentenceType.getSentenceType((int) o);
						}
					}
					else {
						sentenceType = originalAction.getSentenceType();
						setParam( new Value(Type.integer, Value.VALUE_BY_NAME_ACTION_SENTENCETYPE, sentenceType.getIndex()));
					}
					
					tmp = getParam(Value.VALUE_BY_NAME_ACTION_SENTENCE);
					if (tmp.isValid()) {
						Object o = tmp.getObject(Type.string);
						if (o instanceof NoObject) {
							if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
								System.out.println("Answer.perform > sentence: o (getObject(Type.string)) is NoObject " + ((NoObject)o).getReason().toString() + " instead of float");
							}
							sentence = "";
						}
						else {
							sentence = (String) o;
						}
					}
					else {
						sentence = originalAction.getSentence();
						setParam( new Value(Type.string, Value.VALUE_BY_NAME_ACTION_SENTENCE, sentence));
					}
					
					if (sentence != null) {
					
						switch(sentenceType) {
							case say:
								break;
							case question:
								answers =  actor.getAnswersForQuestion(sentence);
								addParam( new Value(Type.valueList, Value.VALUE_BY_NAME_ACTION_ANSWERS, new ValueArrayList(answers, Type.answer)));
								break;
							case answer:
								break;
							default:
						}
	
					}
					
		
					
					break;
					
				default:
					
			}
			
			setEvaluated();
		}
	}


   public List<SimulationObject> getTargets() {

    	List<SimulationObject> targets = new ArrayList<SimulationObject>();
    	SimulationObject target;

       	target =  ((ActionSay) this.getOriginalActionObject()).getTarget();
    	if (target.isSimulationObject()) {
    		targets.add(target);
    	}

    	return targets;
    	
    }
	
}
