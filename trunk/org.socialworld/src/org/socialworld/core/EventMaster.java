/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;
import org.socialworld.SocialWorld;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.SimulationObjectType;

/**
 * The event master is a singleton thread that decides what event's influence
 * should be calculated. this decision is made by fetching the first element
 * with highest priority. For that event the simulation objects, who may be
 * affected by the event, will be found. For these simulation objects the
 * effects (attribute changes) and reactions are calculated.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class EventMaster extends Thread {

	private static final Logger logger = Logger.getLogger(EventMaster.class);

	/**
	 * a queue of events ordered by event's priority
	 */
	private PriorityQueue<Event> eventQueue;

	/**
	 * a list of simulation objects which may be affected by the event
	 */
	private List<SimulationObject> candidates;

	/**
	 * the actually analysed event
	 */
	private Event event;

	/**
	 * the tangent of the angle that describes the range where the event has
	 * effects.
	 */
	private double tangentOfEffectAngle;

	
	/**
	 * the event's position
	 */
	private Position eventPosition;
	
	/**
	 * the direction how the event has effects.
	 */
	private Direction eventDirection;
	
	/**
	 * says whether the thread is running or not
	 */
	private boolean isRunning;

	/**
	 * Private Constructor. (Singleton)
	 */
	public EventMaster() {

		candidates = new ArrayList<SimulationObject>();
		eventQueue = new PriorityQueue<Event>();
	}

	/**
	 * the method stops the event processing
	 */
	public void stopEventMaster() {
		isRunning = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		int sleepTime = 10;
		while (isRunning) {
			calculateNextEvent();
			if (eventQueue.isEmpty())
				sleepTime = 100;
			else
				sleepTime = 10;
			try {
				sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				logger.error(e.getStackTrace());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Calculates the influences of the event to other simulation objects.
	 */
	private void calculateNextEvent() {
		// Liefert Kopf und entfernt Element aus Liste
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
		
		tangentOfEffectAngle = Math.tan(Math.toRadians(event.getEffectAngle()));
		eventPosition = event.getPosition();
		eventDirection = event.getDirection();
		
		return true;
	}
	private void determineCandidates() {
		SimulationObject candidate;
		

		// TODO (tyloesand) Optimierung Finden Kandidaten
		// und dann nicht nur ueber Humans
		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		while (true) {
			if (simulation.hasNext(SimulationObjectType.human) ) {
				candidate = simulation.next(SimulationObjectType.human);
				if (decideEffective(candidate)) {
					candidates.add(candidate);
				}
			}
			else return;
		}
	}

	/**
	 * The method calculates whether a candidate {@link SimulationObject} is
	 * near enough to the event so that the event could effect to the candidate
	 * object.
	 * 
	 * @param candidate
	 *            a simulation object that may be affected by the event
	 * @return true if the event has effects to the candidate and
	 * 		 false if there are no effects
	 */
	private boolean decideEffective(SimulationObject candidate) {
		Position position;
		Direction direction;
		double distance;
		double tangent;

		position = candidate.getPosition();

		distance = position.getDistance(this.eventPosition);

		if (distance <= this.event.getEffectDistance()) {
			direction = position.getDirection(this.eventPosition);
			tangent = direction.getAngleTangent(this.eventDirection);
			return (tangent <= this.tangentOfEffectAngle);
		}
		return false;
	}

	/**
	 * The method lets all candidates calculate the event's effect.
	 */
	private void determineInfluence() {
		SimulationObject candidate;

		while (this.candidates.iterator().hasNext()) {
			candidate = this.candidates.iterator().next();
			candidate.changeByEvent(this.event);
		}
	}
}
