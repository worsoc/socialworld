/**
 * 
 */
package org.socialworld.core;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.objects.SimulationObject;

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

	private static EventMaster eventMaster;

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
	 * says whether the thread is running or not
	 */
	private boolean isRunning;

	/**
	 * Private Constructor. (Singleton)
	 */
	private EventMaster() {

		candidates = new ArrayList<SimulationObject>();
		eventQueue = new PriorityQueue<Event>();
	}

	/**
	 * returns the only one event master instance
	 */
	public static EventMaster getEventMaster() {
		if (eventMaster == null) {
			eventMaster = new EventMaster();
		}
		return eventMaster;
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
			this.event = this.eventQueue.poll();
			if (this.event != null) {
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

	private void determineCandidates() {
		SimulationObject candidate;

		tangentOfEffectAngle = Math.tan(Math.toRadians(event.getEffectAngle()));

		// TODO (tyloesand) Optimierung Finden Kandidaten
		// und dann nicht nur �ber Humans
		candidate = ObjectManager.getCurrent().getHumans().iterator().next();
		while (true) {
			if (decideEffective(candidate)) {
				candidates.add(candidate);
			}
		}
	}

	/**
	 * The method calculates whether a candidate {@link SumulationObject} is
	 * near enough to the event so that the event could effect to the candidate
	 * object.
	 * 
	 * @param candidate
	 *            a simulation object that may be affected by the event
	 * @return true if the event has effects to the candidate and false if there
	 *         are no effects
	 */
	private boolean decideEffective(SimulationObject candidate) {
		Position position;
		Direction direction;
		double distance;
		double tangent;

		position = candidate.getPosition();

		distance = position.getDistance(event.getPosition());

		if (distance <= event.getEffectDistance()) {
			direction = position.getDirection(event.getPosition());
			tangent = direction.getAngleTangent(event.getDirection());
			return (tangent <= tangentOfEffectAngle);
		}
		return false;
	}

	/**
	 * The method lets all candidates calculate the event's effect.
	 */
	private void determineInfluence() {
		SimulationObject candidate;
		candidate = this.candidates.iterator().next();
		while (candidate != null) {
			candidate.changeByEvent(this.event);
			candidate = this.candidates.iterator().next();
		}
	}
}
