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

	public final int LOCATIONBYBASEMAXLENGTH = 9;
	
	private Vector m_position;
	
	private int locationByBase9;
	private String locationByBase25;

	public Position(Vector position) {
		m_position =  position;
		
		setLocationByBases(position);
	}
	
	public Position(Position position) {
		m_position =  new Vector (position.getVector());
	}


	public int getLocationByBase9() { return locationByBase9; }
	public String getLocationByBase25() { return locationByBase25 ; }

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
	
	
	private void setLocationByBases(Vector position)
	{
		this.locationByBase25 = getLocationByBase(position, 5);
		
		String locationByBase9 = getLocationByBase(position, 3);
		this.locationByBase9 = Integer.parseInt( locationByBase9 ); 
	}

	private String getLocationByBase(Vector position, int baseSquareRoot)
	{ 
		String locationString = "";
		
		int i;
		int sectorX;
		int sectorY;
		int sector;
		
		float range;
		
		float x = position.getX();
		float y = position.getY();
		
		for (i = 0; i < LOCATIONBYBASEMAXLENGTH; i++) {
			
			range = x / baseSquareRoot;
			sectorX = (int) ( x / range );
			x = x % range;
			
			range = y / baseSquareRoot;
			sectorY = (int) ( y / range );
			sectorY = sectorY - 1;
			y = y % range;
			
			sector = sectorY * baseSquareRoot + sectorX;
			
			locationString = locationString + getSectorCode(sector, baseSquareRoot) ;
		}
		
		return locationString;
		
	}
	
	private String getSectorCode(int sector, int baseSquareRoot ) {
		
		if (baseSquareRoot == 3) return Integer.toString(sector);
		
		if (baseSquareRoot == 5) 
			// 64 + 1 --> "A", ... , 64 + 25 --> "Y"
			return String.valueOf(Character.toChars(64 + sector  ));
		else
			return Integer.toString(0);
		
	}
}
