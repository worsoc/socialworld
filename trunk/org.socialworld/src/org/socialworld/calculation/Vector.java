/**
 * 
 */
package org.socialworld.calculation;

/**
 * @author Mathias Sikos (tyloesand) The class is the base for all simulation
 *         position and directions. The vector has 3 dimensions.
 */
public class Vector {

	protected double x;
	protected double y;
	protected double z;

	public Vector() {

	}

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * The method calculates the vector's length.
	 * 
	 * @return the length
	 */
	public double getLength() {
		double length;

		double xQuad = x * x;
		double yQuad = y * y;
		double zQuad = z * z;

		length = Math.sqrt(xQuad + yQuad + zQuad);

		return length;
	}

	/**
	 * The method calculates the subtraction of two vectors. The result vector
	 * has the direction from parameter vector to this vector.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDifference(Vector vector) {
		double deltaX;
		double deltaY;
		double deltaZ;

		Vector direction;

		deltaX = this.x - vector.getX();
		deltaY = this.y - vector.getY();
		deltaZ = this.z - vector.getZ();

		direction = new Vector(deltaX, deltaY, deltaZ);
		return direction;
	}

	@Override
	public String toString() {
		return "( " + this.x + ", " + this.y + ", " + this.z + " )";
	}

}
