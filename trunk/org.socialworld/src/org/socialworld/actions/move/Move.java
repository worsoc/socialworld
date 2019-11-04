/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.actions.move;

import org.socialworld.actions.ActionPerformer;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;


/**
 * German:
 * Die Klasse Move ist von der abstrakten Klasse ActionPerformer abgeleitet.
  * 
 * Die Klasse Move dient der Wirksamwerdung der Aktion,
 *  nämlich als Argument für das zur Aktion gehörende Ereignis.
 *
 *  In der Ausführungsmethode perform() werden 
 *   - die Richtung der Bewegung
 *   - die Geschwindigkeit der Bewegung
 *   für den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 * Die Geschwindigkeit wird unter anderem aus der Intensität der Bewegung hergeleitet.
 * 
 * Aus der Länge des zurückzulegenden Weges und der Geschwindigkeit wird die Zahl der Fortsetzungen ermittelt.
 * 
 *
 * @author Mathias Sikos (tyloesand) 
 */
public class Move extends ActionPerformer {
	
	private float remainedDistance;
	
	private float velocity;
	private float acceleration;
	
	
	public Move (   ActionMove action ) {
		super(action);
		
		this.acceleration = action.getIntensity();
		this.velocity = 0;
		this.remainedDistance = action.getSectionLength();

	}
	
    protected final void choosePropertiesFromPropertyList(ValueArrayList properties) {
    	
    	Value property;
    	
    	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_ATTRIBUTES);
    	if (property.isValid()) {
    		addProperty(property);
    	}
 
       	property = properties.getValue(Value.VALUE_BY_NAME_SIMOBJ_DIRECTION_CHEST);
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

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION);
    	if (property.isValid()) {
    		addProperty(property);
    	}

      	property = properties.getValue(Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY);
    	if (property.isValid()) {
    		addProperty(property);
    	}
  	
    }

	public void perform() {
		
		Value tmp;
		
		tmp = getParam(Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION);
		if (tmp.isValid()) {
			this.acceleration = (Float) tmp.getValueCopy();
		}
		else {
			setParam( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_MOVE_ACCELERATION, this.acceleration));
		}
		
		tmp = getParam(Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY);
		if (tmp.isValid()) {
			this.velocity = (Float) tmp.getValueCopy();
		}
		else {
			this.velocity = this.velocity + this.acceleration;
			setParam( new Value(Type.floatingpoint, Value.VALUE_BY_NAME_ACTION_MOVE_VELOCITY, this.velocity));
		}
				
		this.remainedDistance = this.remainedDistance - this.velocity;
		
	} 
	
	
	public boolean checkContinueMove() {
		return (this.remainedDistance > 0);
	}
	
	public boolean checkIsWithAcceleration() {
		
		return (this.acceleration != 0);
	}
	
	public float getVelocity() {
		return this.velocity;
	}

	public float getAcceleration() {
		return this.acceleration;
	}

}
