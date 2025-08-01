/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.actions.handle;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.EventTypeGeneral;
import org.socialworld.objects.SimulationObject;

public class Equip extends ActionPerformer {

	public Equip(ActionEquip action) {
		super(action);
	}
	
	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
 		List<String> result = new ArrayList<String>();
		switch (mode) {
		case takeItem:
	 		result.add(Value.VALUE_BY_NAME_EVENT_EQUIP_ITEM);
	 		break;
		case dropItem:
			break;
		case switchItemToOtherHand:
			break;
		case setItemToInventory:
	 		result.add(Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISDRINKABLE);
	 		result.add(Value.VALUE_BY_NAME_EVENT_EQUIP_ITEMISEATABLE);
	 		break;
		case getItemFromInventory:
			break;
		}
 		return result;
 	}

	@Override
	protected void choosePropertiesFromPropertyList(ValueArrayList properties) {


		Value property;
    	
    	property = properties.getValue(PropertyName.simobj_attributeArray.toString());
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_EQUIP_ITEM);
    	if (property.isValid()) {
    		addProperty(property);
    	}

	}

	@Override
	protected void perform() {

		if (!isEvaluated()) {
			setEvaluated();
		}

	}
	
   public List<SimulationObject> getTargets() {

    	List<SimulationObject> targets = new ArrayList<SimulationObject>();
    	SimulationObject target;
    	
    	target =  ((ActionEquip) this.getOriginalActionObject()).getItem();
    	if (target.isSimulationObject()) {
    		targets.add(target);
    	}
    	
    	return targets;
    	
    }
	
}
