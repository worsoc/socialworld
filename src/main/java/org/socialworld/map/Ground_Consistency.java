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
public class Ground_Consistency implements IMapProp {
	
	private static Ground_Consistency objectNothing;
	
	public static Ground_Consistency getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Ground_Consistency();
		}
		return objectNothing;
	}	
	
	private Ground_Consistency() {isNothing = true;}
	
	private boolean isNothing;
	
	
		float springiness;   // German: Federung, Elastizität
		float penetration;   // German: Durchdringung, Eindringen, Tiefenwirkung
		
	
		/**
		 * @param springiness
		 * @param penetration
		 */
		public Ground_Consistency(float springiness, float penetration) {
			this.springiness = springiness;
			this.penetration = penetration;
		}


		/**
		 * @return the springiness
		 */
		public float getSpringiness() {
			return springiness;
		}


		/**
		 * @param springiness the springiness to set
		 */
		protected void setSpringiness(float springiness) {
			if (isNothing) return;
			this.springiness = springiness;
		}


		/**
		 * @return the penetration
		 */
		public float getPenetration() {
			return penetration;
		}


		/**
		 * @param penetration the penetration to set
		 */
		protected void setPenetration(float penetration) {
			if (isNothing) return;
			this.penetration = penetration;
		}
		
		public boolean equals(IMapProp propLike) {
			if (propLike instanceof Ground_Consistency) {
				 
				Ground_Consistency gc = (Ground_Consistency) propLike;
				
				if ((this.springiness == gc.springiness) && 
					(this.penetration == gc.penetration)) {
					return true;
				}
			}
			return false;
		}
}
