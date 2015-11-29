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
import org.socialworld.calculation.Vector;
import org.socialworld.objects.Animal;


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
	
	Vector sectionDirection;
	float remainedDistance;
	
	private float velocity;
	private float acceleration;
	private float duration;
	
	
	public Move (   ActionMove action ) {
		super(action);
		
		this.duration = 0;
	}
	

	public void perform() {
		

		if (this.duration == 0 | this.acceleration > 0) {
			ActionMove originalAction;
			final Animal actor;
				
			originalAction  = (ActionMove) getOriginalActionObject();
			actor = (Animal) originalAction.getActor();
	
			if (this.duration == 0)		{
				this.sectionDirection = originalAction.getDirectionForSection();
				
				setMaxParam(3);
				setParam(0, new Value(Type.vector, "direction", this.sectionDirection));
				
				this.remainedDistance = this.sectionDirection.length();
			}

			calculateVelocity(originalAction, actor);
	
			
			setParam(1, new Value(Type.floatingpoint, "velocity", velocity));
			setParam(2, new Value(Type.floatingpoint, "acceleration", acceleration));
		}	

		this.remainedDistance = this.remainedDistance - this.velocity;
		
	} 
	
	private void calculateVelocity(ActionMove action, Animal actor) {
		
		if (this.duration == 0) {
			this.velocity = action.getIntensity();
		}
		else {
			this.acceleration = action.getIntensity();
			this.velocity = this.velocity + this.acceleration * this.duration;
			
		}
		duration = duration + 1;
	}
	
	public boolean checkContinueMove() {
		return (this.remainedDistance > 0);
	}
	
	public boolean checkIsWithAcceleration() {
		
		return (this.acceleration != 0);
	}
}
