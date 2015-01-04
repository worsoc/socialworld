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

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.core.Event;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class ActionHandle extends AbstractAction {

	private Handle handle;
	
	private Vector direction;

	private SimulationObject item1;
	private SimulationObject item2;

	public ActionHandle(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				target, 
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setDirection(direction);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#getConcreteProperty(org.socialworld.actions.ActionProperty)
	 */
	@Override
	public Object getConcreteProperty(ActionProperty prop) {
		switch (prop) {
		case direction:
			return getDirection();
		default:
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {

		// TODO continuation of a handle action that belongs more than one time union
		
   		switch (mode) {
		case useItemLeftHand:
			item1 = ((Human) actor).getLeftHand();
			if (item1 == null) return;
			break;
		case useItemRightHand:
			item1 = ((Human) actor).getRightHand();
			if (item1 == null) return;
			break;
		case useTwoItems:
			item1 = ((Human) actor).getRightHand();
			item2 = ((Human) actor).getLeftHand();
			if (item1 == null | item2 == null) return;
			break;
		case combineItems_AddLeftToRight:
			item1 = ((Human) actor).getRightHand();
			item2 = ((Human) actor).getLeftHand();
			if (item1 == null | item2 == null) return;
			break;
		case combineItems_AddRightToLeft:
			item1 = ((Human) actor).getLeftHand();
			item2 = ((Human) actor).getRightHand();
			if (item1 == null | item2 == null) return;
			break;

		default:
		}

   		this.handle = new Handle(this);
  
		Event event;
		event = new Event( getEventType(type, mode),    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(),  handle /* as optional parameter */);
		addEvent(event);

	}


	
	private int getEventType(ActionType type, ActionMode mode) {
		int eventType = 0;
		// TODO
		
	
	  	return eventType;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @return the direction
	 */
	public Vector getDirectionCopy() {
		return new Vector(this.direction);
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}

	public SimulationObject getItem1() {
		return this.item1;
	}
	
	public SimulationObject getItem2() {
		return this.item2;
	}

}
