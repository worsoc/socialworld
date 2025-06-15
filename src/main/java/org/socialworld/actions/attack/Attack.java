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
package org.socialworld.actions.attack;


import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventTypeGeneral;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse Attack ist von der abstrakten Klasse ActionPerformer abgeleitet.
  * 
 * Die Klasse Attack dient der Wirksamwerdung der Aktion,
 *  n�mlich als Argument f�r das zur Aktion geh�rende Ereignis.
 *
 *  In der Ausf�hrungsmethode perform() werden f�r den Fall eines Waffenangriffs/Faustschlags
 *   - die Richtung des Angriffs
 *   - die Intensit�t des Akteurs
 *   - die Intensit�t des Akteurs auf globaler Skala
 *   - die Waffe (das als Waffe verwendete Objekt)
 *   - die Richtung (Ausrichtung) der Brust
 *   - die Blickrichtung
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
*  In der Ausf�hrungsmethode perform() werden f�r den Fall eines tierischen Angriffs
 *   - die Richtung des Angriffs
 *   - die Intensit�t des Akteurs
 *   - die Intensit�t des Akteurs auf globaler Skala
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 * 
 * @author Mathias Sikos
 *
 */
public class Attack extends ActionPerformer {

	
 	public  Attack (ActionAttack action) {
 		super(action);
 	}
 	
 	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
 		List<String> result = new ArrayList<String>();
 		result.add(Value.VALUE_BY_NAME_EVENT_PUNCH_DIRECTION);
		result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
		result.add(Value.VALUE_BY_NAME_EVENT_PUNCH_INTENSITY);
		result.add(PropertyName.simobj_directionChest.toString());
		result.add(PropertyName.stateSeer_directionView.toString());
		result.add(PropertyName.stateSeer_directionView.toString());
		return result;
 	}
 	
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(PropertyName.simobj_attributeArray.toString());
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
       	property = properties.getValue(PropertyName.simobj_directionChest.toString());
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(PropertyName.stateSeer_directionView.toString());
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_WEAPON);
    	if (property.isValid()) {
    		addProperty(property);
    	}
  	
    }
 
    
    protected void perform() {
		
		if (!isEvaluated()) {
	
			if (isHumanFightAttack())
				performFightAttack();
			else
				performAnimalAttack();
			
		}
		
	}



   	private void performFightAttack() {
   		
   		
		setEvaluated();
  		
   	}
   	
   	private void performAnimalAttack() {
   		
		setEvaluated();
  		
   	}
   	
   	private boolean isHumanFightAttack() {
		ActionAttack originalAction;
		originalAction = (ActionAttack) getOriginalActionObject();
		
		switch (originalAction.getType()) {
		case useWeapon:
		case punch:
			return true;
		default:
			return false;
			
		}
   	}
   	
   	
    public List<SimulationObject> getTargets() {

    	List<SimulationObject> targets = new ArrayList<SimulationObject>();
    	SimulationObject target;
    	
    	target = (SimulationObject) ((ActionAttack) this.getOriginalActionObject()).getWeapon();
    	if (target.isSimulationObject()) {
    		targets.add(target);
    	}
    	
    	return targets;
    	
    }
    
}
