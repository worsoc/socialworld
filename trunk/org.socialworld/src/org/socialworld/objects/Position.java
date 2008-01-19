/**
 * 
 */
package org.socialworld.objects;

/**
 * @author Mathias Sikos
 * 
 */
public class Position extends Vector {

	public Position() {
		super();
	}

	public Position(double x, double y, double z) {
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

		// TODO (tyloesand): vectorBetweenPoints löschen
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
		// TODO (tyloesand): getDifference der Klasse Vector nutzen
		// und aus Vector eine Direction machen

		double deltaX;
		double deltaY;
		double deltaZ;

		Direction direction;

		deltaX = this.x - position.getX();
		deltaY = this.y - position.getY();
		deltaZ = this.z - position.getZ();

		direction = new Direction(deltaX, deltaY, deltaZ);
		return direction;
	}
}
