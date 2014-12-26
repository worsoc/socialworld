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

/**
 * @author Mathias Sikos
 *
 */
public class Event_Percipience {
	private float maxDistance;
	private float maxSee;
	private float maxHear;
	private float maxSmell;
	private float maxFeel;

	protected Event_Percipience(float maxDistance, float maxSee, float maxHear, float maxSmell, float maxFeel) {
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
	}
	
	protected Event_Percipience() {
		setDistancesOfNotice();
	}
	
	/**
	 * @return the maxDistance (the distance where the event is able to be
	 *         perceived)
	 */
	public float get_MaxDistance() {
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
		this. maxDistance = 100;
		this.maxSee = 100;
		this.maxHear = 100;
		this.maxSmell = 2;
		this.maxFeel = 1;
		
	}


}
