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
package org.socialworld.attributes.percipience;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.objects.Animal;
import org.socialworld.objects.concrete.animals.ISeer;

/**
 * @author Mathias Sikos
 *
 */
public class Percipience {
	
	public static int PERCIPIENCE_TOTAL = 0;
	public static int PERCIPIENCE_SEE = 1;
	public static int PERCIPIENCE_HEAR = 2;
	public static int PERCIPIENCE_SMELL = 3;
	public static int PERCIPIENCE_FEEL = 4;
	
	
	private PercipienceType type;
	
	private Position position = Position.getObjectNothing();
	private Vector cuboid;
	
	private Visibility visibility;
	
	private float maxDistance;
	private float maxSee;
	private float maxHear;
	private float maxSmell;
	private float maxFeel;

	public Percipience(PercipienceType type, float maxDistance, float maxSee, float maxHear, float maxSmell, float maxFeel) {
		this.type = type;
		this.maxSee = maxSee;
		this.maxHear = maxHear;
		this.maxSmell = maxSmell;
		this.maxFeel = maxFeel;
		
		if (maxDistance > 0 ) this.maxDistance = maxDistance;
		else {
			maxDistance = maxSee;
			if (maxHear > maxDistance) maxDistance = maxHear;
			if (maxSmell > maxDistance) maxDistance = maxSmell;
			if (maxFeel > maxDistance) maxDistance = maxFeel;
		}
		
		this.cuboid = new Vector();
	}
	
	public Percipience(PercipienceType type) {
		this.type = type;
		this.cuboid = new Vector(0, 0, 0);
		setDistancesOfNotice();
	}

	public Percipience(PercipienceType type, float cubeDiagonal) {
		double tmp;
		float a;
		tmp = cubeDiagonal * cubeDiagonal;
		tmp = tmp / 3;
		tmp = Math.sqrt(tmp);
		a = (float) tmp;
		
		this.type = type;
		this.cuboid = new Vector(a, a, a);
		setDistancesOfNotice();
	}
	
	public Percipience(PercipienceType type, Position position, Vector cuboid) {
		this.type = type;
		this.position = position;
		this.cuboid = cuboid;
		this.visibility = new Visibility(position, cuboid);
		setDistancesOfNotice();
	}
	
	
	public boolean checkIsPossiblePercipient(Animal possiblePercipient) {
		
		if (checkChanceToBeSeen(possiblePercipient)) {
			return checkIsPossibleSeer(possiblePercipient);
		}
		else {
			return false;
		}
	}


	
	public boolean checkChanceToBeSeen(Animal possibleSeer) {
		return true;
	}

	public boolean checkIsPossibleSeer(Animal possibleSeer) {
		switch (type) {
		case simobject:
			return checkMaySeeingObject(possibleSeer);
		case event:
			return checkMaySeeingEvent(possibleSeer);
		case dynamic:
			return checkMaySeeingEvent(possibleSeer);
		default:
			return false;
		}
	}
	
	private boolean checkMaySeeingObject(Animal possibleSeer) {
		
		if (possibleSeer instanceof ISeer) {

		
			Position positionSeer = possibleSeer.getPosition(SimulationCluster.percipience);
			Vector direction = this.position.getDirectionFrom(positionSeer);
	
			if (direction.is000()) return false;
	
			double distance = direction.length();
	
			if (distance <= this.maxSee) {
	
				ValueProperty vpDirectionView;
				vpDirectionView = possibleSeer.getStateProperty(SimulationCluster.todo, PropertyName.stateSeer, PropertyName.stateSeer_directionView, PropertyName.stateSeer_directionView.toString());
				Vector directionView = ((Direction)vpDirectionView.getValue()).getVector(SimulationCluster.todo);
	
				ValueProperty vpPropsSeer;
				ValueProperty vpAngleView;
				vpPropsSeer = possibleSeer.getStateProperty(SimulationCluster.todo, PropertyName.stateSeer, PropertyName.stateSeer_propsSeer, PropertyName.stateSeer_propsSeer.toString());
				vpAngleView = vpPropsSeer.getSubProperty(PropertyName.propsSeer_angleViewPerceivingObjectsInRadians, PropertyName.propsSeer_angleViewPerceivingObjectsInRadians.toString());
				double angleViewInRadians = (double) vpAngleView.getValue();
				
			/*	
				Vector directionView =  possibleSeer.getDirectionView().getVector();
				double angleViewInRadians =  possibleSeer.getAngleViewPerceivingObjectsInRadians();
			*/	
				
				double cosineBetweenDirections = direction.getCosPhi(directionView);
				double angleBetweenDirectionsInRadians = Math.acos(cosineBetweenDirections);
				
				if (angleBetweenDirectionsInRadians <= angleViewInRadians)
					// TEMP_SOLUTION return true raus
					return true;
					// --> return this.visibility.checkIsPossibleSeer(possibleSeer, distance);
				else
					// TEMP_SOLUTION return true raus
					return true;
					//--> return false;
	
	
			}
		}
		
		return false;
	}

	private boolean checkMaySeeingEvent(Animal possibleSeer) {
		
		if (possibleSeer instanceof ISeer) {

			Position positionSeer = possibleSeer.getPosition(SimulationCluster.percipience);
			Vector direction = this.position.getDirectionFrom(positionSeer);
	
			if (direction.is000()) return false;
	
			double distance = direction.length();
	
			if (distance <= this.maxSee) {
	
				ValueProperty vpDirectionView;
				vpDirectionView = possibleSeer.getStateProperty(SimulationCluster.todo, PropertyName.stateSeer, PropertyName.stateSeer_directionView, PropertyName.stateSeer_directionView.toString());
				Vector directionView = (Vector) (vpDirectionView.getSubProperty(PropertyName.direction_vector)).getValue();
		//		Vector directionView = ((Direction)vpDirectionView.getValue()).getVector();
	
				ValueProperty vpPropsSeer;
				ValueProperty vpAngleView;
				vpPropsSeer = possibleSeer.getStateProperty(SimulationCluster.todo, PropertyName.stateSeer, PropertyName.stateSeer_propsSeer, PropertyName.stateSeer_propsSeer.toString());
				vpAngleView = vpPropsSeer.getSubProperty(PropertyName.propsSeer_angleViewPerceivingEventsInRadians, PropertyName.propsSeer_angleViewPerceivingEventsInRadians.toString());
				double angleViewInRadians = (double) vpAngleView.getValue();
	
				
				double cosineBetweenDirections = direction.getCosPhi(directionView);
				double angleBetweenDirectionsInRadians = Math.acos(cosineBetweenDirections);
				
				if (angleBetweenDirectionsInRadians <= angleViewInRadians)
					return this.visibility.checkIsPossibleSeer(possibleSeer, distance);
				else
					return false;
	
	
			}
		}
		
		return false;
	}

	public void setCuboid(Vector cuboid) {
		this.cuboid = cuboid;
		if (!this.position.checkIsObjectNothing()) {
			setVisibility();
		}
	}

	public void setPosition(Position position) {
		this.position = position;
		if (this.cuboid != null) {
			setVisibility();
		}
	}

	private void setVisibility() {
		Vector cuboidCopy = new Vector(this.cuboid);
		Position positionCopy = new Position(this.position.getPropertyName().getType(),   this.position);
		
		setVisibility(positionCopy, cuboidCopy);
		
	}

	private void setVisibility(Position  position, Vector cuboid) {
		
		if (this.cuboid.equals(cuboid) && this.visibility != null) {
			this.visibility.changeMiddlePoint(position);
		}
		else {
			this.visibility = new Visibility(position, cuboid);
		}
		
	}

	/**
	 * @return the maxDistance (the distance where the event is able to be
	 *         perceived)
	 */
	public float getMaxDistance() {
		return maxDistance;
	}

	/**
	 * @param maxDistance
	 *            the distance where the event is able to be perceived
	 */
	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	/**
	 * @return the maxSee (the distance where the event is able to be seen)
	 */
	public float getMaxSee() {
		return maxSee;
	}

	/**
	 * @param maxSee
	 *            the distance where the event is able to be seen
	 */
	public void setMaxSee(float maxSee) {
		this.maxSee = maxSee;
	}

	/**
	 * @return the maxHear (the distance where the event is able to be heard)
	 */
	public float getMaxHear() {
		return maxHear;
	}

	/**
	 * @param maxHear
	 *            the distance where the event is able to be heard
	 */
	public void setMaxHear(float maxHear) {
		this.maxHear = maxHear;
	}

	/**
	 * @return the maxSmell (the distance where the event is able to be smelled)
	 */
	public float getMaxSmell() {
		return maxSmell;
	}

	/**
	 * @param maxSmell
	 *            the distance where the event is able to be smelled
	 */
	public void setMaxSmell(float maxSmell) {
		this.maxSmell = maxSmell;
	}

	/**
	 * @return the maxFeel (the distance where the event is able to be felt)
	 */
	public float getMaxFeel() {
		return maxFeel;
	}

	/**
	 * @param maxFeel
	 *            the distance where the event is able to be felt
	 */
	public void setMaxFeel(float maxFeel) {
		this.maxFeel = maxFeel;
	}

	private void setDistancesOfNotice( ) {
		this.maxDistance = 100000;
		this.maxSee = 100000;
		this.maxHear = 100000;
		this.maxSmell = 2000;
		this.maxFeel = 1000;
		
	}


}
