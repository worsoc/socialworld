/**
 * 
 */
package org.socialworld.attributes;

import org.socialworld.calculation.Vector;

/**
 * The class implements a vector's
 *         interpretation as a position. It is needed for locating simulation
 *         objects or calculation the distance between simulation objects.

 * @author Mathias Sikos (tyloesand)  
 */
public class Position extends Vector {

	public Position() {
		super();
	}

	public Position(Position original) {
		super(original);
	}
	
	public Position(int x, int y, int z) {
		super(x, y, z);
	}

	/**
	 * The method calculates the distance between two point vectors.
	 * 
	 * @param position
	 *            (a vector (x,y,z) )
	 * @return the distance = square root(x²+y²+z²)
	 */
	public double getDistance(Position position) {
		Vector vectorBetweenPoints;
		double distance;

		vectorBetweenPoints = getDifference(position);
		distance = vectorBetweenPoints.getLength();

		return distance;
	}

	/**
	 * The method calculates the direction between two point vectors. The result
	 * vector has the direction from parameter position to this position.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Direction getDirection(Position position) {
		Direction direction = new Direction(getDifference(position));
		return direction;
	}
	
	public int get_X() { return (int) super.getX(); }
	public int get_Y() { return (int) super.getY(); }
	public int get_Z() { return (int) super.getZ(); }
	
}
