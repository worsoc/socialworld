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

import org.socialworld.attributes.GroundMaterial;

/**
 * @author Mathias Sikos
 *
 */
public class Ground_Material implements IMapProp {
	private GroundMaterial firstMaterial;
	private GroundMaterial secondMaterial;
	
	private float percentageFirst;
	private float percentageSecond;
	
	protected Ground_Material(GroundMaterial first, float percentageFirst, GroundMaterial second, float percentageSecond) {
		this.firstMaterial = first;
		this.percentageFirst = percentageFirst;
		this.secondMaterial = second;
		this.percentageSecond = percentageSecond;
	}
	
	protected Ground_Material(GroundMaterial first, float percentageFirst) {
		this.firstMaterial = first;
		this.percentageFirst = percentageFirst;
	}
	
	protected Ground_Material(GroundMaterial first) {
		this.firstMaterial = first;
		this.percentageFirst = 1F;
	}
	
	
	/**
	 * @return the firstMaterial
	 */
	public GroundMaterial getFirstMaterial() {
		return firstMaterial;
	}
	
	/**
	 * @param 
	 * firstMaterial the firstMaterial to set
	 * percentageFirst the percentageFirst to set
	 */
	protected void setFirstMaterial(GroundMaterial firstMaterial, float percentageFirst) {
		this.firstMaterial = firstMaterial;
		this.percentageFirst = percentageFirst;
	}

	/**
	 * @param 
	 * secondMaterial the secondMaterial to set
	 * percentageSecond the percentageSecond to set
	 */
	protected void setSecondMaterial(GroundMaterial secondMaterial, float percentageSecond) {
		this.secondMaterial = secondMaterial;
		this.percentageSecond = percentageSecond;
	}

	/**
	 * @param firstMaterial the firstMaterial to set
	 */
	protected void setFirstMaterial(GroundMaterial firstMaterial) {
		this.firstMaterial = firstMaterial;
		this.percentageFirst = 1F;
	}
	
	/**
	 * @return the secondMaterial
	 */
	public GroundMaterial getSecondMaterial() {
		return secondMaterial;
	}
	
	/**
	 * @param secondMaterial the secondMaterial to set
	 */
	public void setSecondMaterial(GroundMaterial secondMaterial) {
		this.secondMaterial = secondMaterial;
	}
	
	/**
	 * @return the percentageFirst
	 */
	public float getPercentageFirst() {
		return percentageFirst;
	}
	
	/**
	 * @param percentageFirst the percentageFirst to set
	 */
	public void setPercentageFirst(float percentageFirst) {
		this.percentageFirst = percentageFirst;
	}
	
	/**
	 * @return the percentageSecond
	 */
	public float getPercentageSecond() {
		return percentageSecond;
	}
	
	/**
	 * @param percentageSecond the percentageSecond to set
	 */
	public void setPercentageSecond(float percentageSecond) {
		this.percentageSecond = percentageSecond;
	}
}
