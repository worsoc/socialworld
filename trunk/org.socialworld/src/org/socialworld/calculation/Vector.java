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
package org.socialworld.calculation;


/**
 * The class is the base for all simulation
 *         position and directions. The vector has 3 dimensions.
 * @author Mathias Sikos (tyloesand) 
 */
public class Vector {

	protected float x;
	protected float y;
	protected float z;

	public Vector() {

	}

	public Vector (Vector original) {
		if (original != null) {
			this.x = original.getX();
			this.y = original.getY();
			this.z = original.getZ();
		}
		else {
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(String vector) {
		
		String tmp;
		String values[];
		
		tmp = vector;
		tmp = tmp.replace(" ", "");
		tmp = tmp.substring(1);
		tmp = tmp.replace(")", "");
		
		values = tmp.split(",");
		
		this.x = Float.parseFloat(values[0]);
		this.y = Float.parseFloat(values[1]);
		this.z = Float.parseFloat(values[2]);
	}
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * The method calculates the vector's length.
	 * 
	 * @return the length
	 */
	public float length() {
		float length;

		float xQuad = x * x;
		float yQuad = y * y;
		float zQuad = z * z;

		length = (float) Math.sqrt(xQuad + yQuad + zQuad);

		return length;
	}

	/**
	 * The method calculates the subtraction of two vectors. The result vector
	 * has the direction from parameter vector to this vector.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDirectionFrom(Vector vector) {
		float deltaX;
		float deltaY;
		float deltaZ;

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
	public void mul (float scalar) {
		this.x = this.x * scalar;
		this.y = this.y * scalar;
		this.z = this.z * scalar;
		
	}
	
	
	/**
	 * The method calculates the angle's cosine.
	 * The formula is: cos = a * b   /   |a| * |b|
	 * 
	 * 
	 * @param b
	 */
	public float getCosPhi (Vector b) {
		
		Vector a = this;
		
		float divisor = (a.length() * b.length());
		if (divisor == 0)			return Float.NaN;
		
		float divident = a.x * b.x + a.y * b.y + a.z * b.z;
	
		return divident / divisor;
	}
	
	/**
	 * The method calculates the tangent of the angle between two vectors. 
	 * The formula is: tan = (|a| - |b|) / ( |a - b| )
	 * 
	 * @param direction
	 * @return the tangent
	 */
	public float getTanPhi(Vector b) {
		Vector a = this;

		float divisor = a.getDirectionFrom(b).length();
		if (divisor == 0)			return Float.NaN;

		float divident = a.length() - b.length();
		
		return divident / divisor;
	}

	public boolean equals(Vector b) {
		return (x == b.getX() & y == b.getY() & z == b.getZ() );
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
