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
 * Die Klasse Hear ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Hear dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden 
 *   - der (Gesprächs)partner (ein Objekt der Klasse Human)
 *   - der Satz
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  Bei der Bereitstellung der beiden Eigenschaften werden die Fälle
 *   - Zuhören/Hinhören
 *   - Verstehen
 *  gleich behandelt (beide leiten Partner und Satz auf die gleiche Weise her).
 * 
 * @author Mathias Sikos
 *
 */
public class Hear extends ActionPerformer {

    public Hear (ActionHear action) {
    	super(action);
    }

	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
		switch (mode) {
		case listenToStatement:
		case listenToQuestion:
		case listenToInstruction:
		case understand:
	 		result.add(Value.VALUE_BY_NAME_EVENT_SENTENCE);
			result.add(Value.VALUE_BY_NAME_EVENT_TARGET);
			result.add(PropertyName.stateSeer_directionView.toString());
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

       	property = properties.getValue(PropertyName.stateSeer_directionView.toString());
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
			setEvaluated();
		}
	}
	
   public List<SimulationObject> getTargets() {

    	List<SimulationObject> targets = new ArrayList<SimulationObject>();
    	
    	return targets;
    	
    }
	
}
