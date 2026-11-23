/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.application;


import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.collections.CapacityQueue;
import org.socialworld.core.Event;
import org.socialworld.core.EventType;
import org.socialworld.core.Simulation;
import org.socialworld.core.SocialWorldThread;
import org.socialworld.objects.SimulationObject;
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

	private static final int MOVED_POOL_SIZE = 8192; // Zweierpotenz (2^13)
	private final CollectionElementSimObjInfluenced[] movedPool = new CollectionElementSimObjInfluenced[MOVED_POOL_SIZE];
	private int poolWriteIndex = 0;
	
	private final Vector zeroVector = Vector.get0Vector();
	

	// movement concept (2026-11-19): events change only the velocities, the position changes only in the
	// time step positionStep, once per second for every object whose velocity sum is not zero
	static final float STEP_SECONDS = 1.0f;
	// numbers N1-N3 (2026-11-20): the passive velocity halves per second (the ground may change it later);
	// a bystander gets 0.2 × the velocity of the mover as impulse; below 50 mm/s the passive velocity
	// counts as rest, so that a push does not creep on for seconds with millimetre steps
	static final float FRICTION_PER_SECOND = 0.5f;
	static final float BYSTANDER_PUSH_SHARE = 0.2f;
	static final float MIN_SPEED = 50.0f;
	// blows P3 (2026-11-20): the impulse is a share of the event intensity (intensity × power / scale),
	// fists 0.1, weapons 0.15, at most 3000 mm/s; it pushes away from the hitter
	static final float BLOW_IMPULSE_SHARE_FIST = 0.1f;
	static final float BLOW_IMPULSE_SHARE_WEAPON = 0.15f;
	static final float MAX_BLOW_IMPULSE = 3000.0f;

	private final Set<SimulationObject> movingObjects = ConcurrentHashMap.newKeySet();


	/**
	 * private Constructor. 
	 */
	private PositionCalculator() {

		this.moved = new CapacityQueue<CollectionElementSimObjInfluenced>("moved", 5000);
		
		for (int i = 0; i < MOVED_POOL_SIZE; i++) {
			this.movedPool[i] = new CollectionElementSimObjInfluenced(null, null, null);
		}
		
	}

	public static PositionCalculator getInstance() {
		if (instance == null) {
			instance = new PositionCalculator();
		}
		return instance;
	}

	@Override
	public void run() {
	    while (isRunning()) {
	        try {
	            // Warten auf die nächste Positionsänderung 
	            // Sobald ein Objekt sich bewegt, wacht der Calculator SOFORT auf.
	            CollectionElementSimObjInfluenced moved = 
	            		this.moved.poll( SocialWorldThread.SLEEPTIME_POSITION_CALCULATOR, java.util.concurrent.TimeUnit.MILLISECONDS);
	            
	            if (moved != null) {
	                // Verarbeitet das entnommene Element direkt
	                calculatePositionChangedByEvent(moved);
	                moved.clearReferences();
	                
		            // Optional: Ein kleiner Loop, um bei Massenbewegungen die Queue 
		            // sofort komplett leerzufressen, bevor wir wieder schlafen gehen.
		            CollectionElementSimObjInfluenced nextMoved;
		            while ((nextMoved = this.moved.poll()) != null) {
		                calculatePositionChangedByEvent(nextMoved);
		                nextMoved.clearReferences();
		            }

	            }


	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            break;
	        }
	    }
	}
	
	/**
	 * Called at the end of every simulation second: every object that moves gets its time step.
	 * The object hands in its own write access (SimulationObject.stepPosition), the step does not
	 * go through the EventMaster (technical event, bypass).
	 */
	final void sendPositionSteps() {
		for (SimulationObject object : movingObjects) {
			object.stepPosition();
		}
	}

	final int countMovingObjects() {
		return movingObjects.size();
	}

	final synchronized void calculatePositionChangedByEvent(final Event event, final StateSimulationObject state, final HiddenSimulationObject hiddenWriteAccess) {

		if (event != null && state != null && hiddenWriteAccess != null) {
			// Bitmasken-Recycling statt 'new'
			int targetIdx = poolWriteIndex & (MOVED_POOL_SIZE - 1);
			CollectionElementSimObjInfluenced pooledElement = this.movedPool[targetIdx];
			
			pooledElement.setEvent(event);
			pooledElement.setState(state);
			pooledElement.setHidden(hiddenWriteAccess);
			
			poolWriteIndex++;

			if (!this.moved.add(pooledElement)) {
				pooledElement.clearReferences(); 
				poolWriteIndex--; // Rollback bei voller Queue
			}
		}
	}
	
	
	/**
	 * Lets the search by position follow a move. The sector trees hold an object only
	 * down to its search level, so nothing has to be done as long as it stays within
	 * the same sectors. An object without a position has never been added to the search.
	 */
	private void updateObjectSearch(SimulationObject object, Position oldPosition, Position newPosition) {
		if (oldPosition.checkIsObjectNothing()) return;
		if (isInSameSectors(oldPosition, newPosition, object.getLevelObjectSearchBase9(), object.getLevelObjectSearchBase25())) return;

		Simulation.getInstance().changePosition(object);
	}

	static boolean isInSameSectors(Position a, Position b, int levelBase9, int levelBase25) {
		int divisor = 1;
		for (int level = levelBase9; level < b.LOCATIONBYBASEMAXLENGTH; level++) divisor *= Position.LOCATIONBASE9 + 1;

		return a.getLocationByBase9() / divisor == b.getLocationByBase9() / divisor
				&& a.getLocationByBase25().regionMatches(0, b.getLocationByBase25(), 0, levelBase25);
	}

	/**
	 * A candidate is moved by an event only within the event type's position change distance.
	 * 
	 * @return true if the candidate is farther away from the event than that distance
	 *         or if one of the positions is missing
	 */
	private boolean isBeyondPositionChangeDistance(Event event, EventType eventType, Position candidatePosition) {
		float maxDistance = eventType.getPositionChangeDistance();
		if (maxDistance <= 0) return true;
		if (candidatePosition.checkIsObjectNothing()) return true;
		
		Object o = event.getPosition().getObject(Type.eventProp);
		if (!(o instanceof Position)) return true;
		
		return candidatePosition.getDirectionFrom((Position) o).length() > maxDistance;
	}

	/** @return the vector from the event position (the causer) to the candidate, the zero vector if one is missing */
	private Vector directionFromEvent(Event event, Position candidatePosition) {
		if (candidatePosition.checkIsObjectNothing()) return Vector.get0Vector();
		Object o = event.getPosition().getObject(Type.eventProp);
		if (!(o instanceof Position)) return Vector.get0Vector();
		return candidatePosition.getDirectionFrom((Position) o);
	}

	/**
	 * Direction of the push of a blow: away from the hitter. If the candidate stands where the hitter stood,
	 * the direction of the blow is taken.
	 */
	static Vector awayFromCauser(Vector causerToCandidate, Vector blowDirection) {
		if (causerToCandidate == null || causerToCandidate.is000()) return unit(blowDirection);
		return unit(causerToCandidate);
	}

	/** The impulse of a blow: share × event intensity (fist 0.1, weapon 0.15), between 0 and MAX_BLOW_IMPULSE (B3). */
	static float blowImpulse(EventType eventType, float eventIntensity) {
		float share = eventType.isCandidatesWeapon() ? BLOW_IMPULSE_SHARE_WEAPON : BLOW_IMPULSE_SHARE_FIST;
		if (Float.isNaN(eventIntensity)) return 0f;
		return Math.max(0f, Math.min(MAX_BLOW_IMPULSE, share * eventIntensity));
	}
	
	private final int calculatePositionChangedByEvent(CollectionElementSimObjInfluenced moved) {

		if (moved == null) {
			System.out.println("PositionCalculator.calculatePositionChangedByEvent(): moved is null");
			return POSITION_CALCULATOR_RETURNS_EMPTY_LISTS;
		}

		Event event = moved.getEvent();
		StateSimulationObject state = moved.getState();
		HiddenSimulationObject hiddenWriteAccess = moved.getHidden();

		if (event == null || state == null || hiddenWriteAccess == null) {
			if (GlobalSwitches.OUTPUT_DEBUG_POSITIONCALCULATOR_VARIABLE_IS_NULL) {
				System.out.println("PositionCalculator.calculatePositionChangedByEvent(): Inner elements are null (Already processed)");
			}
			return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
		}

		EventType eventType = event.getEventType();

		if (eventType == EventType.followCarrier) {
			return followCarrier(state, hiddenWriteAccess);
		}
		// a carried object does not move by itself: no step, no push, no blow (inventory concept D1)
		if (isIgnoredWhileCarried(eventType, state.getCarrier().isSimulationObject())) {
			return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
		}

		if (eventType == EventType.positionStep) {
			return stepPosition(state, hiddenWriteAccess);
		}

		Vector active = readVelocity(state, PropertyName.simobj_velocityActive);
		Vector passive = readVelocity(state, PropertyName.simobj_velocityPassive);

		if (eventType == EventType.activeMoveStop) {
			active = Vector.get0Vector();
		}
		else if (eventType == EventType.selfSleep) {
			// sleeping means rest
			active = Vector.get0Vector();
			passive = Vector.get0Vector();
		}
		else if (eventType.isSelfMove()) {
			// the own move SETS the active velocity: direction × the current velocity of the move action
			active = scaled(eventDirection(event), event.getMoveVelocity());
		}
		else {
			// the causer is a candidate of its own move or blow as well; for a mover only selfMove changes the
			// velocity (user, 2026-11-19), a hitter does not push itself (P3, B5)
			if (isOwnEventSeenAsCandidate(eventType, event.getCauser() == state.getObject())) return POSITION_CALCULATOR_RETURNS_NO_CHANGE;

			// a push or a blow ADDS an impulse to the passive velocity
			Vector causerToCandidate = Vector.get0Vector();
			if (eventType.isEventToCandidates()) {
				Position candidatePosition = getObjectRequester().requestPosition(token, state.getProperty(token, PropertyName.simobj_position), this);
				if (isBeyondPositionChangeDistance(event, eventType, candidatePosition)) return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
				causerToCandidate = directionFromEvent(event, candidatePosition);
			}

			Vector direction = eventDirection(event);
			float strength;
			if (eventType.isCandidatesMove()) {
				strength = bystanderPush(event.getMoveVelocity());
			}
			else if (eventType.isCandidatesBlow()) {
				// who is hit is pushed away from the hitter (B2), the view only chose the cone
				direction = awayFromCauser(causerToCandidate, direction);
				strength = blowImpulse(eventType, event.getStrength());
			}
			else {
				strength = event.getStrength();
			}
			passive = addImpulse(passive, direction, strength);
		}

		int result = writeVelocities(state, hiddenWriteAccess, active, passive);

		if (GlobalSwitches.OUTPUT_DEBUG_MOVE_DIRECTION) {
			System.out.println("MOVEDIR 3 velocity obj " + state.getObjectID() + " " + eventType
					+ " strength " + event.getStrength() + " velocity " + event.getMoveVelocity()
					+ " active " + active + " passive " + passive);
		}
		return result;
	}

	/**
	 * followCarrier: the carried object takes the position of its carrier plus its offset. The positions stay
	 * absolute (there are no relative positions yet); the offset keeps the place, e.g. z + 800 for a cap.
	 */
	private int followCarrier(StateSimulationObject state, HiddenSimulationObject hiddenWriteAccess) {
		SimulationObject carrier = state.getCarrier();
		if (!carrier.isSimulationObject()) return POSITION_CALCULATOR_RETURNS_NO_CHANGE;

		Position carrierPosition = carrier.getPosition(token);
		if (carrierPosition.checkIsObjectNothing()) return POSITION_CALCULATOR_RETURNS_NO_CHANGE;

		Position positionOriginal = getObjectRequester().requestPosition(token, state.getProperty(token, PropertyName.simobj_position), this);
		Position newPosition = new Position(PropertyName.simobj_position,
				followPosition(carrierPosition.getVector(token), state.getCarriedOffset()));

		int returnSetPosition = hiddenWriteAccess.setPosition(newPosition);
		if (returnSetPosition != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetPosition;
		if (positionOriginal.checkIsObjectNothing()) Simulation.getInstance().changePosition(state.getObject());
		else updateObjectSearch(state.getObject(), positionOriginal, newPosition);
		// and what it carries itself
		state.getObject().letCarriedFollow();
		return returnSetPosition;
	}

	/** The position of a carried object: carrier + offset. */
	static Vector followPosition(Vector carrierPosition, Vector offset) {
		Vector position = new Vector(carrierPosition);
		position.add(offset);
		return position;
	}

	/**
	 * @return true if the event is ignored because the object is carried: everything except followCarrier
	 *         (a carried object has no own step, is not pushed and not hit away)
	 */
	static boolean isIgnoredWhileCarried(EventType eventType, boolean carried) {
		return carried && eventType != EventType.followCarrier;
	}

	/** The time step (positionStep): position += (active + passive) × 1 s, then the passive velocity is damped. */
	private int stepPosition(StateSimulationObject state, HiddenSimulationObject hiddenWriteAccess) {
		Vector active = readVelocity(state, PropertyName.simobj_velocityActive);
		Vector passive = readVelocity(state, PropertyName.simobj_velocityPassive);
		Vector sum = sumOf(active, passive);

		if (sum.is000()) {
			movingObjects.remove(state.getObject());
			return POSITION_CALCULATOR_RETURNS_NO_CHANGE;
		}

		Position positionOriginal = getObjectRequester().requestPosition(token, state.getProperty(token, PropertyName.simobj_position), this);
		if (positionOriginal.checkIsObjectNothing()) return POSITION_CALCULATOR_RETURNS_NO_CHANGE;

		Vector position = new Vector(positionOriginal.getVector(token));
		position.add(scaled(sum, STEP_SECONDS));
		Position newPosition = new Position(PropertyName.simobj_position, position);

		int returnSetPosition = hiddenWriteAccess.setPosition(newPosition);
		if (returnSetPosition != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnSetPosition;
		updateObjectSearch(state.getObject(), positionOriginal, newPosition);
		// what it carries goes with it
		state.getObject().letCarriedFollow();

		int result = writeVelocities(state, hiddenWriteAccess, active, damped(passive));

		if (GlobalSwitches.OUTPUT_DEBUG_MOVE_DIRECTION) {
			System.out.println("MOVEDIR 4 step obj " + state.getObjectID() + " sum " + sum + " position " + position);
		}
		if (result != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return result;
		return POSITION_CALCULATOR_RETURNS_CHANGE;
	}

	/** Writes both velocities and keeps the list of moving objects up to date. */
	private int writeVelocities(StateSimulationObject state, HiddenSimulationObject hiddenWriteAccess, Vector active, Vector passive) {
		int returnActive = hiddenWriteAccess.setVelocityActive(asDirection(PropertyName.simobj_velocityActive, active));
		if (returnActive != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnActive;
		int returnPassive = hiddenWriteAccess.setVelocityPassive(asDirection(PropertyName.simobj_velocityPassive, passive));
		if (returnPassive != WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS) return returnPassive;

		if (sumOf(active, passive).is000()) movingObjects.remove(state.getObject());
		else movingObjects.add(state.getObject());

		return WriteAccessToSimulationObject.WRITE_ACCESS_RETURNS_SUCCESS;
	}

	/** @return the velocity in mm/s (unit direction × speed), the zero vector if there is none */
	private Vector readVelocity(StateSimulationObject state, PropertyName prop) {
		Direction direction = getObjectRequester().requestDirection(token, state.getProperty(token, prop), this);
		if (direction == null || direction.checkIsObjectNothing()) return Vector.get0Vector();
		Vector unit = direction.getVector(token);
		if (unit == null || unit.checkIsObjectNothing()) return Vector.get0Vector();
		return scaled(unit, direction.getPower());
	}

	/** @return the unit direction of the event, the zero vector if it has none */
	private Vector eventDirection(Event event) {
		Direction direction = getObjectRequester().requestDirection(token, event.getDirection(), this);
		if (direction == null || direction.checkIsObjectNothing()) return Vector.get0Vector();
		Vector vector = direction.getVector(token);
		if (vector == null || vector.checkIsObjectNothing()) return Vector.get0Vector();
		return unit(vector);
	}

	private static Direction asDirection(PropertyName prop, Vector velocity) {
		return new Direction(prop, unit(velocity), length(velocity));
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////  vector arithmetic of the movement (testable)  ///////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	/** @return a new vector of length 1 in the same direction, the zero vector for the zero vector */
	static Vector unit(Vector v) {
		Vector work = new Vector(v);
		work.normalize();
		return new Vector(work.getX(), work.getY(), work.getZ());
	}

	static float length(Vector v) {
		return new Vector(v.getX(), v.getY(), v.getZ()).length();
	}

	static Vector scaled(Vector v, float factor) {
		return new Vector(v.getX() * factor, v.getY() * factor, v.getZ() * factor);
	}

	static Vector sumOf(Vector a, Vector b) {
		return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}

	/**
	 * @return true for a candidatesMove or a candidates blow that reaches its own causer: it is ignored for
	 *         the velocity (the causer's move is already its selfMove, a hitter does not push itself);
	 *         bystanders and those hit are still pushed. Only here, the candidates search stays as it is.
	 */
	static boolean isOwnEventSeenAsCandidate(EventType eventType, boolean causerIsTheObject) {
		return causerIsTheObject && (eventType.isCandidatesMove() || eventType.isCandidatesBlow());
	}

	/** An impulse changes the passive velocity once: passive + unit direction × strength. */
	static Vector addImpulse(Vector passive, Vector direction, float strength) {
		return sumOf(passive, scaled(unit(direction), strength));
	}

	/** The impulse a bystander gets from a mover nearby: BYSTANDER_PUSH_SHARE × the velocity of the mover (N1). */
	static float bystanderPush(float moverVelocity) {
		return BYSTANDER_PUSH_SHARE * moverVelocity;
	}

	/** Friction per second: the passive velocity × FRICTION_PER_SECOND, below MIN_SPEED it is rest. */
	static Vector damped(Vector passive) {
		Vector result = scaled(passive, FRICTION_PER_SECOND);
		if (length(result) < MIN_SPEED) return Vector.get0Vector();
		return result;
	}

}
