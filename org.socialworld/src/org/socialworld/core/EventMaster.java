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
import java.util.PriorityQueue;
import java.util.ListIterator;


import org.apache.log4j.Logger;
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
public class EventMaster extends Thread {

	private static final Logger logger = Logger.getLogger(EventMaster.class);

	private static EventMaster instance;
	
	/**
	 * says whether the thread is running or not
	 */
	private boolean isRunning;

	/**
	 * a queue of events ordered by event's priority
	 */
	private PriorityQueue<Event> eventQueue;

	/**
	 * a list of simulation objects which may be affected by the event
	 */
	private List<SimulationObject> candidates;

	/**
	 * the actually treated event
	 */
	private Event event;

	/**
	 * the tangent of the angle that describes the range where the event has
	 * effects.
	 */
	private double tangentOfEffectAngle;

	
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
	 * public Constructor. 
	 */
	private EventMaster() {

		candidates = new ArrayList<SimulationObject>();
		eventQueue = new PriorityQueue<Event>();
	}

	public static EventMaster getInstance() {
		if (instance == null) {
			instance = new EventMaster();
		}
		return instance;
	}
	
	/**
	 * the method stops the event processing
	 */
	public void stopEventMaster() {
		if (Simulation.WITH_LOGGING == 1 )		logger.debug("EventMaster.stopEventMaster");
		isRunning = false;
	}

	/**
	 * the method starts the event processing
	 */
	public void startEventMaster() {
		if (Simulation.WITH_LOGGING == 1 )		logger.debug("EventMaster.startEventMaster");
		isRunning = true;
		this.run();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (Simulation.WITH_LOGGING == 1 )		logger.debug("EventMaster.run");
		int sleepTime = 10;
		int countIterations =  0;
		while (isRunning) {
			countIterations++;
			if (Simulation.WITH_LOGGING == 1 )		logger.debug("EventMaster.run: Iteration " + Integer.toString(countIterations));
			calculateNextEvent();
			if (eventQueue.isEmpty())
				sleepTime = 100;
			else
				sleepTime = 10;
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				if (Simulation.WITH_ERROR_LOGGING == 1 ) logger.error(e.getMessage());
				if (Simulation.WITH_ERROR_LOGGING == 1 ) logger.error(e.getStackTrace());
				e.printStackTrace();
			}
			if (countIterations == 10) stopEventMaster();
		}
		if (Simulation.WITH_LOGGING == 1 )		logger.debug("EventMaster stops");
	}

	/**
	 * Calculates the influences of the event to other simulation objects.
	 */
	private void calculateNextEvent() {

		if (!eventQueue.isEmpty()) {
			if ( loadEvent( this.eventQueue.poll() ) == true ) {
				determineCandidates();
				determineInfluence();
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
		eventQueue.add(event);
	}

	private boolean loadEvent(Event event) {
		if (event == null) return false;
		
		this.event = event;
		
		if (event.hasOptionalParam()) {
			event.evaluateOptionalParam();
		}
		
		tangentOfEffectAngle = Math.tan(Math.toRadians(event.getEffectAngle()));
		effectDistance = event.getEffectDistance();
		eventPosition = event.getPosition();
		eventDirection = event.getDirection();
		
		return true;
	}
	
	private void determineCandidates() {
		SimulationObject candidate;
		int resultDecideEffective;
		
		if (Simulation.WITH_LOGGING == 1 )	logger.debug("determineCandidates");

		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		candidate = simulation.getFirstByPosition(this.eventPosition );
		while (candidate != null) {
			resultDecideEffective = decideEffective(candidate);
			if ( resultDecideEffective == 1) 	candidates.add(candidate);
			if ( resultDecideEffective < 0)
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
	 * @return 1 ... if the event may have effects to the candidate,
	 * 			0 ... if the event has no effect to the candidate because of angle
	 *          -1 ... if the event has no effect to the candidate because of distance
	 *                (and in consequence there are no further candidates with effect 
	 *                	because they must have a greater distance than the actually treated candidate)
	 *          	
	 */
	private int decideEffective(SimulationObject candidate) {
		Position position;
		Vector direction;
		double distance;
		double tangent;

		position = candidate.getPosition();

		distance = position.getDistance(this.eventPosition);

		if (distance <= this.effectDistance) {
			direction = position.getDirectionFrom(this.eventPosition);
			tangent = direction.getTanPhi(this.eventDirection);
			if (tangent <= this.tangentOfEffectAngle)
				return 1;
			else
				return 0;
		}
		return -1;
	}

	/**
	 * The method lets all candidates calculate the event's effect.
	 */
	private void determineInfluence() {
		SimulationObject candidate;
		ListIterator<SimulationObject> iterator;
		
		iterator = this.candidates.listIterator();
		while (iterator.hasNext()) {
			candidate = iterator.next();
			candidate.changeByEvent(this.event);
			candidate.reactToEvent(this.event);
			iterator.remove();
		}
	}
}
