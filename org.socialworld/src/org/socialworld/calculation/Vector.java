/**
 * 
 */
package org.socialworld.calculation;

/**
 * @author Mathias Sikos (tyloesand)
 * 
 */
public class Vector {

	protected double x;
	protected double y;
	protected double z;

	// FIXME (circlesmiler) Anstatt die Länge jedesmal beim Setzen von X, Y oder
	// Z neu zu berechnen, wäre es besser das Attribut "length" einfach
	// wegzulassen und in der Methode getLength die Berechnung der Länge
	// durchzuführen.
	protected double length;

	public Vector() {

	}

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;

		calculateLength();
	}

	private void calculateLength() {
		double xQuad = x * x;
		double yQuad = y * y;
		double zQuad = z * z;

		this.length = Math.sqrt(xQuad + yQuad + zQuad);
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
		calculateLength();
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
		calculateLength();
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
		calculateLength();
	}

	public double getLength() {
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
