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

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventTypeGeneral;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse Ask ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Ask dient der Wirksamwerdung der Aktion,
 *  n�mlich als Argument f�r das zur Aktion geh�rende Ereignis.
 *
 *  In der Ausf�hrungsmethode perform() werden
 *   - der (Gespr�chs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Frage (als Satz (also String))
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *   F�r die Bereitstellung der Parameter ist es unerheblich, ob die die Frage
 *    normal gesprochen, gefl�stert oder geschrien wird.
 *     Diese Unterscheidung steckt bereits im EventType des Ereignisses.
 *    
 *  
 * @author Mathias Sikos
 *
 */
public class Ask extends ActionPerformer {

	
    public Ask (ActionSay action) {
    	super(action);
    	
    }

	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
 		result.add("TODO");
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
			
			String question;
			
			originalAction = (ActionSay) getOriginalActionObject();
			actor = (Human) originalAction.getActor();
			mode = originalAction.getMode();
			
			switch (mode) {
					
				case askNormal:
				case askScream:
				case askWhisper:
										

					break;
					
				case sayNormal:
				case sayScream:
				case sayWhisper:
										
					
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
