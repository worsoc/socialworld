/**
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
