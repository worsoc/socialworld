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
package org.socialworld.calculation.geometry;


/**
 * The class is the base for all simulation
 *         position and directions. The vector has 3 dimensions.
 * @author Mathias Sikos (tyloesand) 
 */
public class Vector {

	
	protected float x = 0;
	protected float y = 0;
	protected float z = 0;

	boolean normalized = false;
	
	public static Vector vector0;
	
	public Vector() {

	}

	public Vector (Vector original) {
		if (original != null) {
			this.x = original.getX();
			this.y = original.getY();
			this.z = original.getZ();
		}
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(float tripel[]) {
		this.x = tripel[0];
		this.y = tripel[1];
		this.z = tripel[2];
	}
	
	public Vector(String vector) {
		
		String tmp;
		String values[];
		
		tmp = vector;
		tmp = tmp.replace(" ", "");
		tmp = tmp.substring(1);
		tmp = tmp.replace(")", "");
		
		values = tmp.split(",");
		
		if (values.length == 3) {
			this.x = Float.parseFloat(values[0]);
			this.y = Float.parseFloat(values[1]);
			this.z = Float.parseFloat(values[2]);
		}
	}
	
	public static Vector get0Vector() {
		if (vector0 == null) {
			vector0 = new Vector(0,0,0);
		}
		return vector0;
	}
	
	public void normalize() {
		
		float length = length();
		
		this.x = this.x / length;
		this.y = this.y / length;
		this.z = this.z / length;
		
		normalized = true;
	}
	
	public boolean isNormalized() {
		return normalized;
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
		
		normalized = false;
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
		normalized = false;
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
		normalized = false;
	}

	/**
	 * The method calculates the vector's length.
	 * 
	 * @return the length
	 */
	public float length() {
		if 	(	normalized) return 1.0F;

		
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
		normalized = false;
		
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
		normalized = false;
		
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
	
	public boolean is000() {
		return (x == 0 & y == 0 & z == 0);
	}
	
	
	@Override
	public String toString() {
		return "( " + this.x + ", " + this.y + ", " + this.z + " )";
	}

	public void reset() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		normalized = false;
	}
	
	public static Vector crossProduct(Vector vect_A, Vector vect_B) {
	        Vector cross_P = new Vector(0, 0, 0);
	        cross_P.x = vect_A.y * vect_B.z - vect_A.z * vect_B.y;
	        cross_P.y = vect_A.z * vect_B.x - vect_A.x * vect_B.z;
	        cross_P.z = vect_A.x * vect_B.y - vect_A.y * vect_B.x;
	        return cross_P;
	}

}
