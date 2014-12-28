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
	private float minHeight;
	private float maxHeight;
	private float avgHeight;
	
	protected float min() { return minHeight; }
	protected float max() { return maxHeight; }
	protected float avg() { return avgHeight; }
	
	protected void setMinHeight(float minHeight) {
		this.minHeight = minHeight;
		this.avgHeight = (this.minHeight + this.maxHeight) / 2;
	}
	
	protected void setMaxHeight(float maxHeight) {
		this.maxHeight = maxHeight;
		this.avgHeight = (this.minHeight + this.maxHeight) / 2;
	}
	
	protected void setAvgHeight(float avgHeight) {
		this.avgHeight = avgHeight;
	}
}
