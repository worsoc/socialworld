/**
 * 
 */
package org.socialworld.objects;

/**
 * @author Mathias Sikos
 * 
 */
public class Position {

	private double posX;
	private double posY;
	private double posZ;

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getPosZ() {
		return posZ;
	}

	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}

	/**
	 * The method calculates the distance between two point vectors.
	 * 
	 * @param position
	 * @return the distance
	 */
	public double getDistance(Position position) {
		// TODO calculation
		return 0;
	}

	/**
	 * The method calculates the direction between two point vectors.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Direction getDirection(Position position) {
		// TODO calculation
		return null;
	}
}
