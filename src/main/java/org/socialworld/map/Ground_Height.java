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
public class Ground_Height implements IMapProp {
	
	private static Ground_Height objectNothing;
	
	public static Ground_Height getObjectNothing() {
		if (objectNothing == null) {
			objectNothing = new Ground_Height();
		}
		return objectNothing;
	}	
	
	private Ground_Height() {isNothing = true;}
	
	private boolean isNothing;
	
	private float minHeight;
	private float maxHeight;
	private float avgHeight;

	boolean calculateAvg;
	

	protected Ground_Height(float avg, float min, float max) {
		this.minHeight = min;
		this.maxHeight = max;
		this.avgHeight = avg;
		this.calculateAvg  = false;
		this.isNothing = false;
	}

	protected Ground_Height(float min, float max) {
		this.minHeight = min;
		this.maxHeight = max;
		this.avgHeight = (this.minHeight + this.maxHeight) / 2;
		this.calculateAvg  = true;
		this.isNothing = false;
	}

	public float min() { return minHeight; }
	public float max() { return maxHeight; }
	public float avg() { return avgHeight; }
	
	protected void setMinHeight(float minHeight ) {
		if (isNothing) return;
		this.minHeight = minHeight;
		if (calculateAvg) this.avgHeight = (this.minHeight + this.maxHeight) / 2;
	}
	
	protected void setMaxHeight(float maxHeight) {
		if (isNothing) return;
		this.maxHeight = maxHeight;
		if (calculateAvg) this.avgHeight = (this.minHeight + this.maxHeight) / 2;
	}
	
	protected void setAvgHeight(float avgHeight) {
		if (isNothing) return;
		this.avgHeight = avgHeight;
	}
	
	public boolean equals(IMapProp propLike) {
		if (propLike instanceof Ground_Height) {
			 
			Ground_Height gh = (Ground_Height) propLike;

			if ((this.minHeight == gh.minHeight) && 
				(this.maxHeight == gh.maxHeight) &&
				(this.avgHeight == gh.avgHeight)) {
				return true;
			}
		}
		return false;
	}
	
}
