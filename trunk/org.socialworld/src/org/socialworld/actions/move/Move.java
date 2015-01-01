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

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.IActionPerformer;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;
import org.socialworld.core.Event;
import org.socialworld.objects.Animal;
import org.socialworld.objects.IAnimalWrite;
import org.socialworld.objects.WriteAccessToAnimal;
import org.socialworld.objects.WriteAccessToSimulationObject;


/**
 * The class collects information about a
 *         simulation object's movements. A move has an action mode (here it is
 *         a move mode).
 * @author Mathias Sikos (tyloesand) 
 */
public class Move implements IAnimalWrite, IActionPerformer {
	private ActionMode mode;
	private Vector direction;
	private float velocity;
    private Animal actor;
    private WriteAccessToAnimal writeAccessToActor;
    
	public Move() {
		this.mode = ActionMode.walk;
	}

	public Move( ActionMode mode, Vector direction, float velocity) {
		this.mode = mode;
		this.direction = direction;
		this.velocity = velocity;
	}
	
	protected void setActor(Animal actor, WriteAccessToSimulationObject writeAccess) {
		this.actor = actor;
		this.writeAccessToActor = (WriteAccessToAnimal) writeAccess;
	}
	
	public Event perform() {
		Event event;
		
		if (actor == null | writeAccessToActor == null) return null;
		changePosition();
		changeState();
		
		// TODO
		event = new Event( 1,    actor /* as causer*/,  ActualTime.asTime(),
					actor.getPosition(),  velocity /* as strength */);
		
		return event;
	} 
	
	private void changePosition() {
		Vector newPositionVector;
		Position newPosition;
		
		newPositionVector = actor.getPosition().getVector();
		newPositionVector.add(direction);
		
		newPosition = new Position(newPositionVector );
		writeAccessToActor.setPosition(newPosition, this);
	}
	
	private void changeState() {
		
	}
	
	/**
	 * @return the mode
	 */
	public ActionMode getMode() {
		return this.mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(final ActionMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}
	
}
