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
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class EventMaster extends Thread {

	private static final Logger logger = Logger.getLogger(EventMaster.class);

	private static EventMaster eventMaster;
	private PriorityQueue<Event> eventQueue;

	private List<SimulationObject> candidates;
	private Event event;

	private double tangentOfEffectAngle;
	private boolean isRunning;

	/**
	 * Private Constructor. (Singleton)
	 */
	private EventMaster() {

		candidates = new ArrayList<SimulationObject>();
		eventQueue = new PriorityQueue<Event>();
	}

	public static EventMaster getEventMaster() {
		if (eventMaster == null) {
			eventMaster = new EventMaster();
		}
		return eventMaster;
	}
	
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

		tangentOfEffectAngle = Math.tan(Math.toRadians(event.getEffectAngle() ));

		// TODO (tyloesand) Optimierung Finden Kandidaten
		// und dann nicht nur �ber Humans
		candidate = ObjectManager.getCurrent().getHumans().iterator()
				.next();
		while (true) {
			if (decideEffective(candidate)) {
				candidates.add(candidate);
			}
		}
	}

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

	private void determineInfluence() {
		SimulationObject candidate;
		candidate = this.candidates.iterator().next();
		while (candidate != null) {
			candidate.changeByEvent(this.event);
			candidate = this.candidates.iterator().next();
		}
	}
}

// class PSBWS_EventMaster
// {
// public:
// PSBWS_EventMaster(PSBWS_Object_Manager*, TNatural);
// ~PSBWS_EventMaster();
//
// int fill_eventQueue();
// int insert_event(PSBWS_Event*);
// int handle_events();
// PSBWS_Event* create_event(TEVC, long int, int, int, int, int,
// TONR, TUChar au_intensity = 0, int ai_relX = 0, int ai_relY = 0, int ai_relZ
// = 0,
// float af_dirX = 0, float af_dirY = 0, float af_dirZ = 0,
// TNatural au_distance_effect = 0, TUChar au_angle_effect = 0);
//
// private:
//
// int determine_candidates();
// int determine_influence();
//
// bool decide_effective(TONR uONr);
// bool decide_time_remains();
//
// PSBWS_Event* mP_event; // aktuell betrachtetes Ereignis
// TNatural mu_posX_event;
// TNatural mu_posY_event;
// TNatural mu_posZ_event;
// float mf_dirX_event;
// float mf_dirY_event;
// float mf_dirZ_event;
// TNatural mu_distance_effect;
// double md_angleTangens_effect;
//
// TNatural mu_number_events;
// TNatural mu_minNumber_events;
//
// SM_PrioritaetsSchlange<PSBWS_Event>* mSM_events;
// SM_Schlange<STR_Candidate>* mSM_candidates;
//
// PSBWS_Object_Manager* mP_objectManager;
// SM_VektorMathematik* mSM_vector;
//
// };
