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


import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.CapacityQueue;
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

	private CapacityQueue<CollectionElementSimObjInfluenced> moved;

	private static AccessTokenPositionCalculator token = AccessTokenPositionCalculator.getValid();

/*	private List<Event> events;
	private List<StateSimulationObject> states;
	private List<HiddenSimulationObject> hiddenSimObjects;
*/
	/**
	 * private Constructor. 
	 */
	private PositionCalculator() {

		this.sleepTime = SocialWorldThread.SLEEPTIME_POSITION_CALCULATOR;

		this.moved = new CapacityQueue<CollectionElementSimObjInfluenced>("moved", 1000);
/*		this.events = new ArrayList<Event>();
		this.states = new ArrayList<StateSimulationObject>();
		this.hiddenSimObjects = new ArrayList<HiddenSimulationObject>();
*/		
	}

	public static PositionCalculator getInstance() {
		if (instance == null) {
			instance = new PositionCalculator();
		}
		return instance;
	}
	
	public void run() {

		while (isRunning()) {
			
			if (this.moved.size() > 0) calculatePositionChangedByEvent();
			
			try {
				sleep(this.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	final void calculatePositionChangedByEvent(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {

		if (event != null && state != null && hiddenWriteAccess != null) {
			if (!this.moved.add(new CollectionElementSimObjInfluenced(event, state, hiddenWriteAccess))) {
				// SUB_THREAD_IMPLEMENTATION what shall happen if the queue is filled
			};
		}
/*		this.events.add(event);
		this.states.add(state);
		this.hiddenSimObjects.add( hiddenWriteAccess);
*/
	}
	
	private final int calculatePositionChangedByEvent() {

/*		if (this.hiddenSimObjects.size() == 0) return POSITION_CALCULATOR_RETURNS_EMPTY_LISTS;
		
		Event event = this.events.remove(0);
		StateSimulationObject state  = this.states.remove(0);
		HiddenSimulationObject hiddenWriteAccess = this.hiddenSimObjects.remove(0);
*/		
		CollectionElementSimObjInfluenced moved = this.moved.remove();
		if (moved != null) {

			Event event = moved.getEvent();
			StateSimulationObject state  =  moved.getState();
			HiddenSimulationObject hiddenWriteAccess =   moved.getHidden();

		
			int returnSetPosition;
			int returnSetMove;
			
			EventType eventType;
			
			
			Position positionOriginal = Position.getObjectNothing();
			Vector position;
			Position newPosition = Position.getObjectNothing();
			
			Direction directionMoveObject;
			Vector vectorMoveObject;
			float powerMoveObject;
	
			Direction directionEvent;
			Vector vectorEvent;
			float powerEvent;
			
			float resultingPowerMoveObject;
				
			
			eventType = event.getEventType();
			positionOriginal = objectRequester.requestPosition(token, state.getProperty(token, PropertyName.simobj_position), this);
			position = positionOriginal.getVector(token);
			
			directionMoveObject = objectRequester.requestDirection(token, state.getProperty(token, PropertyName.simobj_directionMove), this);
			vectorMoveObject = directionMoveObject.getVector(token);
			powerMoveObject = directionMoveObject.getPower();
			
			directionEvent = objectRequester.requestDirection(token, event.getDirection(), this);
			if (directionEvent != null) {
				vectorEvent = directionEvent.getVector(token);
			}
			else {
				// TODO directionEvent is null
				vectorEvent = new Vector(0,0,0);
			}
			powerEvent = event.getStrength();
			
			if (!vectorEvent.isNormalized()) vectorEvent.normalize();
			if (!vectorMoveObject.isNormalized()) vectorMoveObject.normalize();
			
			switch (eventType) {
			// TODO cases and implementations
			case selfSleep:
				vectorEvent = new Vector(0,0,0);
				powerMoveObject = 0;
				break;
			default:
				return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
			}
			
			// TODO calculate resulting direction and power
			
			
			vectorMoveObject.mul(powerMoveObject);
			vectorEvent.mul(powerEvent);
			vectorMoveObject.add(vectorEvent);
			
			resultingPowerMoveObject = vectorMoveObject.length();
			position.add(vectorMoveObject);
			vectorMoveObject.normalize();
			
			newPosition = new Position(PropertyName.simobj_position, position);
			
			
			returnSetPosition = hiddenWriteAccess.setPosition(newPosition);
			if (returnSetPosition != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetPosition;
			
			returnSetMove = hiddenWriteAccess.setMove(new Direction(PropertyName.simobj_directionMove, vectorMoveObject, resultingPowerMoveObject));
			if (returnSetMove != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetMove;
	
			if (vectorMoveObject.equals(new Vector(0,0,0)))
				return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
			else
				return POSITION_CALCULATOR_RETURNS_CHANGE;
		}	
		else {
			System.out.println("PositionCalculator.calculatePositionChangedByEvent(): moved is null");
			return POSITION_CALCULATOR_RETURNS_EMPTY_LISTS;
		}
	}
}
