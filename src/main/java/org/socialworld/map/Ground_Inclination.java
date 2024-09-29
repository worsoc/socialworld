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
package org.socialworld.map;

/**
 * @author Mathias Sikos
 *
 */
public class Ground_Inclination implements IMapProp {
	
	private static Ground_Inclination objectNothing;
	
	public static Ground_Inclination getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Ground_Inclination();
		}
		return objectNothing;
	}	
	
	private Ground_Inclination() {isNothing = true;}
	
	private boolean isNothing;
	
	private float inclination;

	protected Ground_Inclination(float inclination) {
		setInclination(inclination);
	}
	
	/**
	 * @return the inclination
	 */
	public float getInclination() {
		return inclination;
	}

	/**
	 * @param inclination the inclination to set
	 */
	protected void setInclination(float inclination) {
		if (isNothing) return;
		this.inclination = inclination;
	}
	
	public boolean equals(IMapProp propLike) {
		if (propLike instanceof Ground_Inclination) {
			 
			Ground_Inclination gi = (Ground_Inclination) propLike;

			if ((this.inclination == gi.inclination)) {
				return true;
			}
		}
		return false;
	}
	
}
