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

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.actions.IActionPerformer;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.core.Event;
import org.socialworld.objects.Human;
import org.socialworld.objects.IHumanWrite;
import org.socialworld.objects.WriteAccessToHuman;
import org.socialworld.objects.WriteAccessToSimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class Attack implements IHumanWrite, IActionPerformer {
	private ActionMode mode;
    private IWeapon weapon;
    private boolean useLeftHand;
    private boolean useRightHand;

	private Vector directionChest;
	private Vector directionView;
	private Vector directionHit;
	boolean directionsSet;
	
	private float actorsIntensity;
  
    private Human actor;
    private WriteAccessToHuman writeAccessToActor;

    
    public Attack (ActionType type, ActionMode mode, IWeapon weapon ) {
    	if (mode == ActionMode.club) {
     		useLeftHand = true;
    		useRightHand = true;
    	}
    	else    	
    		switch (type) {
    		case useWeaponLeft:
    			useLeftHand = true;
    			break;
    		case useWeaponRight:
    			useRightHand = true;
    			break;
    		default:
    			
    		}
    	
    	this.mode = mode;
    	this.weapon = weapon;
    	
  
    }
    
    public void setHitParams(Vector directionChest, Vector directionView, Vector directionHit, float actorsIntensity) {
    	this.actorsIntensity = actorsIntensity;
	  	this.directionChest = directionChest;
    	this.directionView = directionView;
    	this.directionHit = directionHit;
    }
    
     
	protected void setActor(Human actor, WriteAccessToSimulationObject writeAccess) {
		this.actor = actor;
		this.writeAccessToActor = (WriteAccessToHuman) writeAccess;
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.IActionPerformer#perform()
	 */
	@Override
	public Event perform() {
		Event event;
		
		FunctionBase function;
		Value[] arguments;
		
		float intensity;
		
		AttributeArray attributes;
		
		if (actor == null | writeAccessToActor == null) return null;

		switch (mode) {
		case stab:
			function = weapon.getStabFunction();
			break;
		case stroke:
			function = weapon.getStrokeFunction();
			break;
		case club:
			function = weapon.getClubFunction();
			break;
		case backhand:
			function = weapon.getBackhandFunction();
			break;
		default:
			return null;
		}
		

		if (!directionsSet) setDefaultDirections();

		
		// TODO
		intensity = actorsIntensity * actor.getAttributes().get(Attribute.power);
		
		event = new Event( 2,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(),  intensity /* as strength */);
		
		
		arguments = new Value[1];
		arguments[0] = new Value(Type.attributeArray, actor.getAttributes());

		function.calculate(arguments);
		attributes = (AttributeArray) function.calculate(arguments).getValue();
		writeAccessToActor.setAttributes(attributes, this);
	
		return event;
	}

	private void setDefaultDirections() {
	  	this.directionChest = actor.getDirectionChest();
    	this.directionView = actor.getDirectionView();
    	switch (mode) {
    	case stab:
        	this.directionHit = this.directionView;
    	default:
    		// TODO
        	this.directionHit = this.directionView;
    		
    	}
	
	}
}
