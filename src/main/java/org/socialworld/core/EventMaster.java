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


import org.socialworld.SocialWorld;
import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.properties.IPerceptible;

/**
 * The event master is a thread that decides what event's influence
 * should be calculated. this decision is made by fetching the first element
 * with highest priority. For that event the simulation objects, who may be
 * effected by the event, will be found. For these simulation objects the
 * effects (attribute changes) and reactions are calculated.
 * 
 * @author Mathias Sikos (MatWorsoc)
 */
public class EventMaster extends SocialWorldThread {

	private boolean doYield = false;
	
	private static EventMaster instance;
	
	private static AccessTokenCore tokenCore = AccessTokenCore.getValid();
	
	/**
	 * a queue of events ordered by event's priority
	 */
	private PriorityBlockingQueue<Event> eventQueue;
	private int lastPriority = Event.LOWEST_EVENT_PRIORITY;
	
	private static final int CANDIDATES_POOL_SIZE = 8192;
	private static final int PERCIPIENTS_POOL_SIZE = 8192;

	/**
	 * a list of simulation objects which may be affected by the event
	 */
	private final List<SimulationObject> candidates;

	/**
	 * a list of simulation objects which may perceive the event
	 */
	private final List<SimulationObject> percipients;

	/**
	 * the actually treated event
	 */
	private Event event;

	private boolean isRelevantForEffectiveCheck;
	private boolean isRelevantForPercipienceCheck;
	
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
	private Position eventPosition = Position.getObjectNothing();
	
	/**
	 * the direction how the event has effects.
	 */
	private Value eventDirection;
	

	/**
	 * private Constructor. 
	 */
	private EventMaster() {

		// Puffer-Kapazitäten großzügig wählen, um internes Array-Resize zur Laufzeit zu verhindern
		this.candidates = new ArrayList<SimulationObject>(CANDIDATES_POOL_SIZE);
		this.percipients = new ArrayList<SimulationObject>(PERCIPIENTS_POOL_SIZE);
		this.eventQueue = new PriorityBlockingQueue<Event>();
		
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
			// Absolut thread-sicher und ressourcenschonend: 
			// Schläft ohne CPU-Last, bis ein Event in die Queue gepusht wird.
			calculateNextEvent();
			
			// Freiwilliges Nachgeben für Prioritäts-Gleichlauf auswerten
			if (doYield) {
				Thread.yield(); // Braucht KEIN try-catch, da es keine Exception wirft!
				doYield = false; // WICHTIG: Das Flag wieder zurücksetzen für den nächsten Durchlauf
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
		if (event != null) {
			this.eventQueue.add(event);
		}
	}
	
	/**
	 * Calculates the influences of the event to other simulation objects.
	 */
	private void calculateNextEvent() {
		Event currentEvent = null;
		try {
			// Der hocheffiziente Wecker: Blockiert synchron, bis Daten da sind
			currentEvent = this.eventQueue.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}

		if (loadEvent(currentEvent)) {
			if (currentEvent.isEventToTarget()) {
				determineInfluenceToTargets();
			}
			if (currentEvent.isEventToCauserItself()) {
				determineInfluenceToCauser();
			}
			if (isRelevantForEffectiveCheck) {
				determineCandidates();
				determineInfluenceToCandidates();
			}
			if (isRelevantForPercipienceCheck) {
				determinePossiblePercipients();
				determineInfluenceToPercipients();
			}
			
			// WICHTIG: Speicher-Referenzen kappen gegen Memory Loitering
			this.event = null;
			this.candidates.clear();
			this.percipients.clear();
		}
	}


	private boolean loadEvent(Event event) {
		if (event == null) return false;
		
		this.event = event;
		if (isLowerPriorityThanEventBefore()) 
			doYield = true;
		else 
			doYield = false;
		
		if (event.hasOptionalParam()) {
			event.evaluateOptionalParam();
		}
		
		this.isRelevantForEffectiveCheck = 
				event.getEventType().isRelevantForEffectiveCheck() && !event.isEventToCauserItself();

		this.isRelevantForPercipienceCheck =
				event.getEventType().isEventToPercipient();
		
		if (this.isRelevantForEffectiveCheck) {
			this.effectDistance = event.getEffectDistance();
			this.effectAngle = event.getEffectAngle();
			this.eventDirection = event.getDirection();
		}

		if (this.isRelevantForPercipienceCheck) {
			this.effectDistance = event.getEffectDistance();
		}
		
		this.eventPosition = event.getPosition();
		
		return true;
	}
	
	private boolean isLowerPriorityThanEventBefore() {
		
		int lastPriority = this.lastPriority;
		this.lastPriority = event.getPriority();
		return (this.lastPriority < lastPriority);
	}
	
	private void determineCandidates() {
		int ignoreCandidate;
		int maxSize = 10000;
		int index = 0;

		// 1. ALLOKATIONSFREIES LEEREN STATT NEW
		this.candidates.clear();

		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		
		// Typsichere Zuweisung ohne Warnungen
		Object searchResult = simulation.getObjectSearch().getObjects(this.event);
		if (!(searchResult instanceof List)) return;

		@SuppressWarnings("unchecked")
		List<List<SimulationObject>> lists = (List<List<SimulationObject>>) searchResult;

		// Äußere Schleife über die ObjektTypen
		int listsSize = lists.size();
		for (int i = 0; i < listsSize; i++) {
			
			List<SimulationObject> list = lists.get(i);
			if (list == null) continue;
				
			// Innere Schleife über die konkreten Kandidaten im Cluster
			int listSize = list.size();
			for (int j = 0; j < listSize; j++) {
				
				SimulationObject candidate = list.get(j);
				if (candidate != null && candidate.isSimulationObject()) {
					
					ignoreCandidate = checkIgnoreCandidate(candidate);
					if ( ignoreCandidate == 0) {
						// assumption: list doesn't (better lists don't) contain duplicate objects
						candidates.add(candidate);
						index++;
						if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_CANDIDATES) {
							System.out.println("EventMaster.determineCandidates: Event " + this.event.toString() + ": Kandidat " + candidate.getObjectID() + " " + candidate.getPosition(tokenCore).toString() + " added to candidates list");
						}
						
						if (index == maxSize) break;
					}
					else {
						if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_CANDIDATES) {
							System.out.println("EventMaster.determineCandidates: Event " + this.event.toString() + ": Kandidat " + candidate.getObjectID() + " " + candidate.getPosition(tokenCore).toString() + " ignored with ignoreCandidate = " + ignoreCandidate);
						}
					}
					
				}					
			}
			if (index == maxSize) break;

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
	 * 			3 ... IGNORE: if the event direction is the 0-vector (0,0,0) (and the effect angle is not 360�)
	 * 			4 ... IGNORE: if there is no event direction ( ERROR!!!)
	 *          -1 ... IGNORE: if the event has no effect to the candidate because of distance
	 *                (and in consequence there are no further candidates with effect 
	 *                	because they must have a greater distance than the actually treated candidate)
	 *          	
	 */
	private int checkIgnoreCandidate(SimulationObject candidate) {
		
		Position position = Position.getObjectNothing();
		Vector direction;
		double distance;
		
		double cosineBetweenDirections;
		double angleBetweenDirectionsToRadians;
		double effectAngleToRadians;
		
		position = candidate.getPosition(tokenCore);

		direction = position.getDirectionFrom(this.eventPosition);
		if (direction.is000()) return 2;
		
		distance = direction.length();
		
		if (distance <= this.effectDistance) {
			
			if (this.effectAngle > 180.0F) {
				return 0;
			}
			else {
				Vector vectorDirectionEvent;
				Direction directionEvent;
				if (this.eventDirection != null) {
					directionEvent = objectRequester.requestDirection(tokenCore, this.eventDirection, this);
					if (directionEvent.isObjectNothing()) {
						// TODO directionEvent is not object: that shouldn't be possible
						System.out.println("EventMaster.checkIgnoreCandidate(): this.eventDirection.getValue() is no direction object ");
						return 4;
					}
					vectorDirectionEvent = directionEvent.getVector(tokenCore);
					if (vectorDirectionEvent.is000()) {
						return 3;
					}
					else {
						effectAngleToRadians = Math.toRadians(this.effectAngle);
						
						cosineBetweenDirections = direction.getCosPhi(vectorDirectionEvent);
						angleBetweenDirectionsToRadians = Math.acos(cosineBetweenDirections);
						
						if (angleBetweenDirectionsToRadians <= effectAngleToRadians)
							return 0;
						else
							return 1;
					}
				}
				else {
					System.out.println("EventMaster.checkIgnoreCandidate(): this.eventDirection is null ");
					return 4;
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
		int size = this.candidates.size();
	
		if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_CANDIDATES)
			System.out.println("Start determine infuence to candidates (" + this.candidates.size() + "): "+ ActualTime.asTime().toString());
		
		// two iterations because of asynchronous calculations
		// there is a better chance to react on the event (second iteration) using the newer state (just calculated in first iteration)
		
		// first iteration for calculating the effect to the simulation object's state
		for (int i = 0; i < size; i++) {
			candidate = this.candidates.get(i);
			if (candidate != null) {
				candidate.changeByEvent(this.event);
			}
		}
		
		// second iteration for creating the reaction
		for (int i = 0; i < size; i++) {
			candidate = this.candidates.get(i);			
			if (candidate != null) {
				candidate.reactToEvent(this.event);
			}
			// don't remove because the list is created new for the next event -> Ist durch .clear() gelöst!
		}

		if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_CANDIDATES)
			System.out.println("End determine infuence to candidates "+ ActualTime.asTime().toString());
		
	}
	
	

	/**
	 * The method lets all explicitly assigned  target objects calculate the event's effect.
	 */
	private void determineInfluenceToTargets() {
		
		List<SimulationObject> targets = this.event.getTargetObjects();
		
		if (targets == null) return;
		
		int size = targets.size();
		if (size > 0){
			
			SimulationObject target;
			
			if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_TARGETS)
				System.out.println("Start determine infuence to targets (" + targets.size() + "): "+ ActualTime.asTime().toString());
			
			// two iterations because of asynchronous calculations
			// there is a better chance to react on the event (second iteration) using the newer state (just calculated in first iteration)
			
			// first iteration for calculating the effect to the simulation object's state
			for (int i = 0; i < size; i++) {
				target = targets.get(i);
				if (target != null) {
					target.changeByEvent(this.event);
				}
			}
			
			// second iteration for creating the reaction
			for (int i = 0; i < size; i++) {
				target = targets.get(i);			
				if (target != null) {
					target.reactToEvent(this.event);
				}
			}
		
			if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_TARGETS)
				System.out.println("End determine infuence to targets " + ActualTime.asTime().toString());

		}
		
	}
	
	private void determineInfluenceToCauser() {
		this.event.getCauser().changeByEvent(this.event);
		this.event.getCauser().reactToEvent(this.event);
	}
	
	
	private void determinePossiblePercipients() {
		
		IPerceptible causer;
		boolean isPossiblePercipient;
		EventToPercipient event;

		// 1. ALLOKATIONSFREIES LEEREN STATT NEW
				this.percipients.clear();

		if (this.event instanceof EventToPercipient) {
			event = (EventToPercipient) this.event;
			if (event.checkIsEvent_LetMeBePerceived())
				causer = event.getCauser();
			else
				causer = event;
			
	
			Simulation simulation = SocialWorld.getCurrent().getSimulation();
			// Typsichere Zuweisung ohne Warnungen
			Object searchResult = simulation.getObjectSearch().getObjects(this.event);
			if (!(searchResult instanceof List)) return;

			@SuppressWarnings("unchecked")
			List<List<SimulationObject>> lists = (List<List<SimulationObject>>) searchResult;			
			
			// Äußere Schleife über die Suchcluster via Index (Verhindert Iterator-Allokation)
			int listsSize = lists.size();
			for (int i = 0; i < listsSize; i++) {
				List<SimulationObject> list = lists.get(i);
				if (list == null) continue;
				
				// Innere Schleife über die konkreten Wahrnehmenden (Percipients) im Cluster
				int listSize = list.size();
				for (int j = 0; j < listSize; j++) {
					SimulationObject percipient = list.get(j);
					
					if (percipient != null && percipient.isSimulationObject() && (percipient instanceof Animal)) {
			
	//				System.out.println("Position Human: " + percipient.getPosition().toString());
	
						isPossiblePercipient = causer.checkIsPossiblePercipient((Animal) percipient);
						if ( isPossiblePercipient ) {
							percipients.add(percipient);
						}

					}
					else {
						// assumption: in the list are no animals (incl. humans)
						break;
					}
				}
			}
		}
	}

	/**
	 * The method lets all percipients calculate the event's effect.
	 */
	private void determineInfluenceToPercipients() {
		
		SimulationObject percipient;
		int size = this.percipients.size();
				
		if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_PERCIPIENTS)
			System.out.println("Start determine infuence to percipients (" + this.percipients.size() + "): "+ ActualTime.asTime().toString());
		
		// two iterations because of asynchronous calculations
		// there is a better chance to react on the event (second iteration) using the newer state (just calculated in first iteration)
		
		// first iteration for calculating the effect to the simulation object's state
		for (int i = 0; i < size; i++) {
			percipient = this.percipients.get(i);
			if (percipient != null) {
				percipient.changeByEvent(this.event);
			}
		}
		
		// second iteration for creating the reaction
		for (int i = 0; i < size; i++) {
			percipient = this.percipients.get(i);			
			if (percipient != null) {
				percipient.reactToEvent(this.event);
			}
			// don't remove because the list is created new for the next event -> Durch .clear() gelöst!
		}

		if (GlobalSwitches.OUTPUT_EVENTMASTER_DETERMINE_INFLUENCE_TO_PERCIPIENTS)
			System.out.println("End determine infuence to percipients " + ActualTime.asTime().toString());

	}
	

	
}
