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
package org.socialworld.actions.useHands;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.Time;
import org.socialworld.core.Event;
import org.socialworld.objects.Animal;
import org.socialworld.objects.Item;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos
 *
 */
public class ActionAttack extends AbstractAction {
	private Attack attack;
	
	public ActionAttack(final ActionType type, final ActionMode mode,
			final SimulationObject target, 
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				target, 
				intensity,  minTime, maxTime,
				 priority,  duration);
			
	}
	
	public ActionAttack(ActionAttack original) {
		setBaseProperties(original);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		// TODO Auto-generated method stub
		Event event;
		final IWeapon weapon;

   		switch (type) {
		case useWeaponLeft:
			weapon = ((Human) actor).getRightHandWeapon();
			break;
		case useWeaponRight:
			weapon = ((Human)actor).getRightHandWeapon();
			break;
		default:
			weapon = null;
		}
		
   		if (weapon == null) return;
   		
   		this.attack = new Attack(type, mode,  weapon);
   		this.attack.setActor((Human) actor, getWriteAccess(this));
   		
   		// TODO
   		//this.attack.setHitParams(directionChest, directionView, directionHit, actorsIntensity);
   		
   		event = attack.perform();
   		
		if (event != null) addEvent(event);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#getConcreteProperty(org.socialworld.actions.ActionProperty)
	 */
	@Override
	public Object getConcreteProperty(ActionProperty prop) {
		// TODO Auto-generated method stub
		return null;
	}

}
