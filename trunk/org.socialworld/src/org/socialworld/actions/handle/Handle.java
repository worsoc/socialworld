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

import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse Handle ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Handle dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden 
 *   - die Richtung der Handhabe
 *   - die Intensität des Akteurs
 *   - die Intensität des Akteurs auf globaler Skala
 *   - der Gegenstand 1
 *   - der Gegenstand 2
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  Im Falle einer Bewegung eines Objektes (Ziehen oder Schieben) wird KEIN Gegenstand 2 angegeben.
 *  Im Falle einer Berührung eines Objektes wird gar kein Gegenstand angegeben. 
 *   Die Richtung der Berührung ist entweder explizit angegeben 
 *   oder sie wird aus der Richtung zum Zielobjekt ermittelt. 
 *  Im Falle einer Benutzung eines Objektes wird KEIN Gegenstand 2 angegeben.
 *  Im Falle einer gleichzeitigen Benutzung zweier Objekte werden alle 5 Eigenschaften angegeben.
 *  Im Falle einer Kombination zweier Gegenstände werden alle 5 Eigenschaften gesetzt.
 *  
 * @author Mathias Sikos
 *
 */
public class Handle extends ActionPerformer {

/*
	public final int HANDLEKIND_MOVEOBJECT = 1;
	public final int HANDLEKIND_TOUCH_WITHTARGET = 2;
	public final int HANDLEKIND_TOUCH_WITHDIRECTION = 3;
	public final int HANDLEKIND_USE = 4;
	public final int HANDLEKIND_USE_WITHTARGET = 5;
	public final int HANDLEKIND_USE_WITHDIRECTION = 6;
	
	public final int OTHER_HANDLEKIND = 0;
*/

    public Handle (ActionHandle action) {
    	super(action);
    }
	
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
       	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_DIRECTION_VIEW);
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
	    	if (target != null) {
	    		targets.add(target);
	    	}

	    	target =  ((ActionHandle) this.getOriginalActionObject()).getItem2();
	    	if (target != null) {
	    		targets.add(target);
	    	}

	    	target =  ((ActionHandle) this.getOriginalActionObject()).getTarget();
	    	if (target != null) {
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
			if (originalAction.getTarget() != null)
				return HANDLEKIND_TOUCH_WITHTARGET;
			else
				return HANDLEKIND_TOUCH_WITHDIRECTION;
		case handleItem:
			switch (mode) {
			case useItemLeftHand:
			case useItemRightHand:
				if (originalAction.getTarget() != null)
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
