/**
 * 
 */
package org.socialworld.calculation;

/**
 * The class is the base for all simulation
 *         position and directions. The vector has 3 dimensions.
 * @author Mathias Sikos (tyloesand) 
 */
public class Vector {

	protected double x;
	protected double y;
	protected double z;

	public Vector() {

	}

	public Vector (Vector original) {
		this.x = original.getX();
		this.y = original.getY();
		this.z = original.getZ();
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

	
	/**
	 * The method calculates the addition by a second vector.
	 * 
	 * @param xyz
	 */
	public void add (Vector xyz) {
		this.x = this.x + xyz.getX();
		this.y = this.y + xyz.getY();
		this.z = this.z + xyz.getZ();
		
	}
	/**
	 * The method calculates the scalar multiplication.
	 * 
	 * @param scalar
	 */
	public void multiply (double scalar) {
		this.x = this.x * scalar;
		this.y = this.y * scalar;
		this.z = this.z * scalar;
		
	}
	
	/**
	 * The method multiplies the vector by another vector.
	 *  In this calculation every single value is multiplied by the corresponding single value.
	 *  The result is a vector again.
	 *  (x1,y1,z1) * (x2,y2,z2) = (x1*x2, y1*y2, z1*z2)
	 * 
	 * @param relativeVector
	 */
	public void multiply (Vector relativeVector) {
		this.x = this.x * relativeVector.x;
		this.y = this.y * relativeVector.y;
		this.z = this.z * relativeVector.z;
		
	}
	
	@Override
	public String toString() {
		return "( " + this.x + ", " + this.y + ", " + this.z + " )";
	}

	public void reset() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
}
