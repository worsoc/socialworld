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
package org.socialworld.calculation.application;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Position;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.core.Event;
import org.socialworld.core.EventType;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.StateSimulationObject;
import org.socialworld.objects.WriteAccessToSimulationObject;
import org.socialworld.objects.access.HiddenSimulationObject;

/**
 * @author Mathias Sikos
 *
 */
public class PositionCalculator extends SocialWorldThread {

	public static final int POSITION_CALCULATOR_RETURNS_EMPTY_LISTS = 0;
	public static final int POSITION_CALCULATOR_RETURNS_NO_CHANGE = 2;
	public static final int POSITION_CALCULATOR_RETURNS_CHANGE = 3;

	private static PositionCalculator instance;

	private List<Event> events;
	private List<StateSimulationObject> states;
	private List<HiddenSimulationObject> hiddenSimObjects;

	/**
	 * private Constructor. 
	 */
	private PositionCalculator() {

		this.events = new ArrayList<Event>();
		this.states = new ArrayList<StateSimulationObject>();
		this.hiddenSimObjects = new ArrayList<HiddenSimulationObject>();
		
	}

	public static PositionCalculator getInstance() {
		if (instance == null) {
			instance = new PositionCalculator();
		}
		return instance;
	}
	
	public void run() {

		while (isRunning()) {
			
			calculatePositionChangedByEvent();
			
			try {
				sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	final void calculatePositionChangedByEvent(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {
		this.events.add(event);
		this.states.add(state);
		this.hiddenSimObjects.add( hiddenWriteAccess);
	}
	
	private final int calculatePositionChangedByEvent() {

		if (this.hiddenSimObjects.size() == 0) return POSITION_CALCULATOR_RETURNS_EMPTY_LISTS;
		
		Event event = this.events.remove(0);
		StateSimulationObject state  = this.states.remove(0);
		HiddenSimulationObject hiddenWriteAccess = this.hiddenSimObjects.remove(0);
		
		int returnSetPosition;
		int returnSetMove;
		
		EventType eventType;
		
		
		Vector position;
		Position newPosition;
		
		
		Vector directionEvent;
		Vector directionMoveObject;
		
		
		float powerEvent;
		float powerMoveObject;
		
		float resultingPowerMoveObject;
		
		
		// TODO get elements from List

		
		eventType = event.getEventType();
		position = state.getPosition().getVector();
		
		directionMoveObject = (Vector) state.getDirectionMoveAsValue(Value.VALUE_BY_NAME_SIMOBJ_MOVE_DIRECTION).getValue();
		powerMoveObject = (float)state.getPowerMoveAsValue(Value.VALUE_BY_NAME_SIMOBJ_MOVE_POWER).getValueCopy();
		
		directionEvent = event.getDirection();
		powerEvent = event.getStrength();
		
		if (!directionEvent.isNormalized()) directionEvent.normalize();
		if (!directionMoveObject.isNormalized()) directionMoveObject.normalize();
		
		switch (eventType) {
		// TODO cases and implementations
		case sleepCaused:
			directionEvent = new Vector(0,0,0);
			powerMoveObject = 0;
			break;
		default:
			return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
		}
		
		// TODO calculate resulting direction and power
		
		
		directionMoveObject.mul(powerMoveObject);
		directionEvent.mul(powerEvent);
		directionMoveObject.add(directionEvent);
		
		resultingPowerMoveObject = directionMoveObject.length();
		position.add(directionMoveObject);
		directionMoveObject.normalize();
		
		newPosition = new Position(position);
		
		
		returnSetPosition = hiddenWriteAccess.setPosition(newPosition);
		if (returnSetPosition != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetPosition;
		
		returnSetMove = hiddenWriteAccess.setMove(directionMoveObject, resultingPowerMoveObject);
		if (returnSetMove != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetMove;

		if (directionMoveObject.equals(new Vector(0,0,0)))
			return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
		else
			return POSITION_CALCULATOR_RETURNS_CHANGE;
			
	}
}
