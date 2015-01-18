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

import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos
 *
 */
public class Attack extends ActionPerformer {

 	public  Attack (ActionAttack action) {
 		super(action);
 	}
 	
  
   	public void perform() {
		
		if (!isValid()) {
	
			if (isHumanFightAttack())
				performFightAttack();
			else
				performAnimalAttack();
			
		}
		
	}

   	private void performFightAttack() {
		ActionAttack originalAction;
		final Human actor;
		
		IWeapon weapon;
		float actorsIntensity;
		float intensity;
		int actorsPower;
		
		Vector directionChest;
		Vector directionView;
		Vector directionHit;
	
		originalAction  = (ActionAttack) getOriginalActionObject();
		actor = (Human) originalAction.getActor();
		
		actorsPower = actor.getAttributes().get(Attribute.power);
			
		// TODO
	  	directionChest = actor.getDirectionChest();
		directionView = actor.getDirectionView();
		directionHit = actor.getDirectionView();

		// TODO
		actorsIntensity = originalAction.getIntensity();
		intensity = actorsIntensity * actorsPower;
	
		weapon = originalAction.getWeapon();
	
		setMaxParam(6);
		setParam(0, new Value(Type.vector, "direction", directionHit));
		setParam(1, new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
		setParam(2, new Value(Type.floatingpoint, "intensity", intensity));
		setParam(3, new Value(Type.simulationObject, "weapon", weapon));
		setParam(4, new Value(Type.vector, "directionChest", directionChest));
		setParam(5, new Value(Type.vector, "directionView", directionView));
		
		setValid();
  		
   	}
   	
   	private void performAnimalAttack() {
		ActionAttack originalAction;
		final Animal actor;
		
		float actorsIntensity;
		float intensity;
		int actorsPower;
		
		Vector directionHit;
	
		originalAction  = (ActionAttack) getOriginalActionObject();
		actor = (Animal) originalAction.getActor();
		
		actorsPower = actor.getAttributes().get(Attribute.power);
			
		// TODO
		directionHit = actor.getDirectionView();

		// TODO
		actorsIntensity = originalAction.getIntensity();
		intensity = actorsIntensity * actorsPower;
	
	
		setMaxParam(3);
		setParam(0, new Value(Type.vector, "directionHit", directionHit));
		setParam(1, new Value(Type.floatingpoint, "actorsIntensity", actorsIntensity));
		setParam(2, new Value(Type.floatingpoint, "intensity", intensity));
		
		setValid();
  		
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
}
