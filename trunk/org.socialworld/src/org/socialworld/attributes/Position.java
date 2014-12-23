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
package org.socialworld.attributes;

import org.socialworld.calculation.Vector;

/**
 * The class holds information about the position

 * @author Mathias Sikos (tyloesand)  
 */
public class Position {

	private Vector m_position;

	public Position(Vector position) {
		m_position =  position;
	}
	
	public Position(Position position) {
		m_position =  new Vector (position.getVector());
	}

	public Vector getVector() {return m_position;}
			
	
	public float getDistance(Position position) {
		Vector direction;
		float distance;

		direction = getDirectionFrom(position);
		distance = direction.length();

		return distance;
	}

	/**
	 * The method calculates the direction between two positions. The result
	 * vector has the direction from parameter position to this position.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDirectionFrom(Position position) {
		Vector direction = m_position.getDirectionFrom(position.getVector());
		return direction;
	}
	
	public int getX() { return (int) m_position.getX(); }
	public int getY() { return (int) m_position.getY(); }
	public int getZ() { return (int) m_position.getZ(); }
	
}
