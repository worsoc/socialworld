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

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.attack.ActionAttack;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.Human;
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

	public final int HANDLEKIND_MOVEOBJECT = 1;
	public final int HANDLEKIND_TOUCH_WITHTARGET = 2;
	public final int HANDLEKIND_TOUCH_WITHDIRECTION = 3;
	public final int HANDLEKIND_USE = 4;
	public final int HANDLEKIND_USE_WITHTARGET = 5;
	public final int HANDLEKIND_USE_WITHDIRECTION = 6;
	
	public final int OTHER_HANDLEKIND = 0;


    public Handle (ActionHandle action) {
    	super(action);
    }
	
	/* (non-Javadoc)
	 * @see org.socialworld.actions.ActionPerformer#perform()
	 */
	@Override
	public void perform() {

		if (!isValid()) {

			ActionHandle originalAction;
			final Human actor;
			
			SimulationObject item1 = null;
			SimulationObject item2 = null;
			Vector direction;
			float actorsIntensity;
			float intensity;
			int actorsPower;
	
			originalAction  = (ActionHandle) getOriginalActionObject();
			actor = (Human) originalAction.getActor();
	
			switch (getHandleKind()) {
			case HANDLEKIND_MOVEOBJECT:
				item1 = originalAction.getItem1();
				direction = originalAction.getDirectionCopy();
				break;
			case HANDLEKIND_TOUCH_WITHTARGET:
				direction = actor.getPosition().getDirectionTo(originalAction.getTarget().getPosition());
				break;
			case HANDLEKIND_TOUCH_WITHDIRECTION:
				direction = originalAction.getDirectionCopy();
				break;
			case HANDLEKIND_USE:
				item1 = originalAction.getItem1();
				item2 = originalAction.getItem2();
				direction = originalAction.getDirectionCopy();
				break;
			case HANDLEKIND_USE_WITHTARGET:
				item1 = originalAction.getItem1();
				direction = actor.getPosition().getDirectionTo(originalAction.getTarget().getPosition());
				break;
			case HANDLEKIND_USE_WITHDIRECTION:
				item1 = originalAction.getItem1();
				direction = originalAction.getDirectionCopy();
				break;
			default:
				direction = originalAction.getDirectionCopy();
			}
			
			actorsIntensity = originalAction.getIntensity();
			// TODO
			actorsPower = actor.getAttributes().get(Attribute.power);
			intensity = actorsIntensity * actorsPower;
	
			setMaxParam(5);
			setParam(0, new Value(Type.vector, "direction", direction));
			setParam(1, new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
			setParam(2, new Value(Type.floatingpoint, "intensity", intensity));
			setParam(3, new Value(Type.simulationObject, "item1", item1));
			setParam(4, new Value(Type.simulationObject, "item2", item2));
			
			setValid();
			
		}
		
	}

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
}
