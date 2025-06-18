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

/**
 * German:
 * Die Klasse Handle ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Handle dient der Wirksamwerdung der Aktion,
 *  n�mlich als Argument f�r das zur Aktion geh�rende Ereignis.
 *
 *  In der Ausf�hrungsmethode perform() werden 
 *   - die Richtung der Handhabe
 *   - die Intensit�t des Akteurs
 *   - die Intensit�t des Akteurs auf globaler Skala
 *   - der Gegenstand 1
 *   - der Gegenstand 2
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  Im Falle einer Bewegung eines Objektes (Ziehen oder Schieben) wird KEIN Gegenstand 2 angegeben.
 *  Im Falle einer Ber�hrung eines Objektes wird gar kein Gegenstand angegeben. 
 *   Die Richtung der Ber�hrung ist entweder explizit angegeben 
 *   oder sie wird aus der Richtung zum Zielobjekt ermittelt. 
 *  Im Falle einer Benutzung eines Objektes wird KEIN Gegenstand 2 angegeben.
 *  Im Falle einer gleichzeitigen Benutzung zweier Objekte werden alle 5 Eigenschaften angegeben.
 *  Im Falle einer Kombination zweier Gegenst�nde werden alle 5 Eigenschaften gesetzt.
 *  
 * @author Mathias Sikos
 *
 */
public class Handle extends ActionPerformer {


    public Handle (ActionHandle action) {
    	super(action);
    }
	
	public static List<String> getEventParamNameList(EventTypeGeneral etg) {
		ActionMode mode = ActionMode.fromEventTypeGeneral(etg);
		List<String> result = new ArrayList<String>();
		switch (mode) {
		case useItemLeftHand:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
			break;
		case useItemRightHand:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
			break;
		case useTwoItems:
		case combineItems_AddRightToLeft:
		case combineItems_AddLeftToRight:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM2);
			break;

		case pull:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
			break;
		case push:
	 		result.add(Value.VALUE_BY_NAME_EVENT_DIRECTION);
			result.add(Value.VALUE_BY_NAME_ACTION_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_INTENSITY);
			result.add(Value.VALUE_BY_NAME_EVENT_HANDLE_ITEM1);
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

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_INTENSITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_DIRECTION);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_ITEM1);
    	if (property.isValid()) {
    		addProperty(property);
    	}

       	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_ITEM2);
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
	    	SimulationObject target;
	    	
	    	target =  ((ActionHandle) this.getOriginalActionObject()).getItem1();
	    	if (target.isSimulationObject()) {
	    		targets.add(target);
	    	}

	    	target =  ((ActionHandle) this.getOriginalActionObject()).getItem2();
	    	if (target.isSimulationObject()) {
	    		targets.add(target);
	    	}

	    	target =  ((ActionHandle) this.getOriginalActionObject()).getTarget();
	    	if (target.isSimulationObject()) {
	    		targets.add(target);
	    	}
	    	
	    	return targets;
	    	
	}
	
	/*
   	private int getHandleKind() {
 		ActionAttack originalAction;
		originalAction = (ActionAttack) getOriginalActionObject();
		
		ActionType type = originalAction.getType();
		ActionMode mode = originalAction.getMode();
		
		switch (type) {
		case touch:
			if (originalAction.getTarget().isSimulationObject())
				return HANDLEKIND_TOUCH_WITHTARGET;
			else
				return HANDLEKIND_TOUCH_WITHDIRECTION;
		case handleItem:
			switch (mode) {
			case useItemLeftHand:
			case useItemRightHand:
				if (originalAction.getTarget().isSimulationObject())
					return HANDLEKIND_USE_WITHTARGET;
				else
					return HANDLEKIND_USE_WITHDIRECTION;
			case useTwoItems:
				return HANDLEKIND_USE;
			case combineItems_AddRightToLeft:
				return HANDLEKIND_USE;
			case combineItems_AddLeftToRight:
				return HANDLEKIND_USE;
			case pull:
				return HANDLEKIND_MOVEOBJECT;
			case push:
				return HANDLEKIND_MOVEOBJECT;
			default:
				return HANDLEKIND_USE;
			}
		default:
			return OTHER_HANDLEKIND;
			
		}
   	}
   	
   	*/
}
