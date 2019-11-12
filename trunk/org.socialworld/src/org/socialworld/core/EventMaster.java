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
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ListIterator;


import org.socialworld.SocialWorld;
import org.socialworld.calculation.Vector;
import org.socialworld.attributes.Position;
import org.socialworld.objects.SimulationObject;

/**
 * The event master is a thread that decides what event's influence
 * should be calculated. this decision is made by fetching the first element
 * with highest priority. For that event the simulation objects, who may be
 * effected by the event, will be found. For these simulation objects the
 * effects (attribute changes) and reactions are calculated.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class EventMaster extends SocialWorldThread {

	private static final int MAX_SLEEP_TIME_FOR_DECREASING_PRIORITY = 10;
	private static final int MAX_SLEEP_TIME_FOR_EMPTY_QUEUE = 10;
	
	private static EventMaster instance;
	
	private int sleepTime = 50;
	
	private boolean blockedByAdd = false;
	private boolean blockedByCalculate = false;
	
	/**
	 * a queue of events ordered by event's priority
	 */
	private PriorityBlockingQueue<Event> eventQueue;
	private int lastPriority = Event.LOWEST_EVENT_PRIORITY;
	
	/**
	 * a list of simulation objects which may be affected by the event
	 */
	private List<SimulationObject> candidates;

	/**
	 * the actually treated event
	 */
	private Event event;

	private boolean isRelevantForEffectiveCheck;
	
	/**
	 * the angle that describes the range where the event has
	 * effects.
	 */
	private double effectAngle;

	
	/**
	 * the distance that describes the range where the event has
	 * effects.
	 */
	private float effectDistance;
	
	/**
	 * the event's position
	 */
	private Position eventPosition;
	
	/**
	 * the direction how the event has effects.
	 */
	private Vector eventDirection;
	

	/**
	 * private Constructor. 
	 */
	private EventMaster() {

		candidates = new ArrayList<SimulationObject>();
		eventQueue = new PriorityBlockingQueue<Event>();
		
	}

	public static EventMaster getInstance() {
		if (instance == null) {
			instance = new EventMaster();
		}
		return instance;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		while (isRunning()) {
			
			// calculating the next event from event queue
			// !!! the sleepTime may change
			
			if (!blockedByAdd && !blockedByCalculate) {
//				System.out.println("EventMaster.run(): Aufruf calculateNextEvent()");
				calculateNextEvent();
			}
			else {
//				System.out.println("EventMaster.run(): blockiert Aufruf calculateNextEvent()");
				
			}
			
			sleepTime = 50;
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	/**
	 * Adds a new {@link Event} to the event queue.
	 * 
	 * @param event -
	 *            The event, which should add to the event queue.
	 */
	public void addEvent(Event event) {
		blockedByAdd = true;
		eventQueue.add(event);
		blockedByAdd = false;
	}
	
	/**
	 * Calculates the influences of the event to other simulation objects.
	 */
	private void calculateNextEvent() {

		if (!eventQueue.isEmpty()) {
			
			Event event = null;
			try {
				event = this.eventQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			
			blockedByCalculate = true;
			if ( loadEvent( event ) == true ) {
				if (isRelevantForEffectiveCheck) {
					determineCandidates();
					determineInfluenceToCandidates();
				}
			}
			blockedByCalculate = false;
		}
		else
			sleepTime = MAX_SLEEP_TIME_FOR_EMPTY_QUEUE;

	}


	private boolean loadEvent(Event event) {
		if (event == null) return false;
		
		this.event = event;
		if (isLowerPriorityThanEventBefore()) 
			sleepTime = MAX_SLEEP_TIME_FOR_DECREASING_PRIORITY;
		else if (sleepTime > 0)
			sleepTime--;
		
		if (event.hasOptionalParam()) {
			event.evaluateOptionalParam();
		}
		
		isRelevantForEffectiveCheck = event.getEventType().isRelevantForEffectiveCheck();
		effectAngle = event.getEffectAngle();
		effectDistance = event.getEffectDistance();
		eventPosition = event.getPosition();
		eventDirection = event.getDirection();
		
		return true;
	}
	
	private boolean isLowerPriorityThanEventBefore() {
		
		int lastPriority = this.lastPriority;
		this.lastPriority = event.getPriority();
		return (this.lastPriority < lastPriority);
	}
	
	private void determineCandidates() {
		SimulationObject candidate;
		int ignoreCandidate;
		

		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		candidate = simulation.getFirstByPosition(this.eventPosition );
		while (candidate != null) {
			
			ignoreCandidate = checkIgnoreCandidate(candidate);
			if ( ignoreCandidate == 0) 	candidates.add(candidate);
			if ( ignoreCandidate < 0)
				// exit loop
				candidate = null;
			else
				// continue
				candidate = simulation.getNextByPosition();
		}

	}

	/**
	 * The method calculates whether a candidate {@link SimulationObject} is
	 * near enough to the event so that the event could effect to the candidate
	 * object.
	 * 
	 * @param candidate
	 *            a simulation object that may be affected by the event
	 * @return 0 ... DON'T IGNORE : if the event may have effects to the candidate,
	 * 			1 ... IGNORE: if the event has no effect to the candidate because of angle
	 * 			2 ... IGNORE: if the event position is the same to candidate position
	 * 			3 ... IGNORE: if the event direction is the 0-vector (0,0,0) (and the effect angle is not 360°)
	 *          -1 ... IGNORE: if the event has no effect to the candidate because of distance
	 *                (and in consequence there are no further candidates with effect 
	 *                	because they must have a greater distance than the actually treated candidate)
	 *          	
	 */
	private int checkIgnoreCandidate(SimulationObject candidate) {
		Position position;
		Vector direction;
		double distance;
		double cosineBetweenDirections;
		double angleBetweenDirectionsToRadians;
		double effectAngleToRadians;
		
		position = candidate.getPosition();

		direction = position.getDirectionFrom(this.eventPosition);
		distance = direction.length();

		if (distance == 0) return 2;
		
		if (distance <= this.effectDistance) {
			
			if (this.effectAngle > 180.0F) {
				return 0;
			}
			else {
				
				if (this.eventDirection.is000()) {
					return 3;
				}
				else {
					effectAngleToRadians = Math.toRadians(this.effectAngle);
					
					cosineBetweenDirections = direction.getCosPhi(this.eventDirection);
					angleBetweenDirectionsToRadians = Math.acos(cosineBetweenDirections);
					
					if (angleBetweenDirectionsToRadians <= effectAngleToRadians)
						return 0;
					else
						return 1;
				}
				
			}
			
		}
		return -1;
	}

	/**
	 * The method lets all candidates calculate the event's effect.
	 */
	private void determineInfluenceToCandidates() {
		SimulationObject candidate;
		ListIterator<SimulationObject> iterator;
		
		// two iterations because of asynchronous calculations
		// there is a better chance to react on the event (second iteration) using the newer state (just calculated in first iteration)
		
		// first iteration for calculating the effect to the simulation object's state
		iterator = this.candidates.listIterator();
		while (iterator.hasNext()) {
			candidate = iterator.next();
			candidate.changeByEvent(this.event);
		}
		
		// second iteration for creating the reaction
		iterator = this.candidates.listIterator();
		while (iterator.hasNext()) {
			candidate = iterator.next();			
			candidate.reactToEvent(this.event);
			iterator.remove();
		}

	}
}
