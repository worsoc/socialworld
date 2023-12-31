/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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

import org.socialworld.attributes.Position;

public class Rectangle {

	private Position m = Position.getObjectNothing();  
	private Vector p1;
	private Vector p2;
	private Vector p3;
	private Vector p4;
	private Vector a;
	private Vector b;
	private Vector perpendicular;
	
	boolean withStandardPerpendicular = false;
	int nrStandardPerpendicular = VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS;
	
	private double area;
	
	private VectorMapper vectorMapper;
	
	public Rectangle(Position m, Vector p1, Vector p2) {
		
		vectorMapper = VectorMapper.getInstance();
		this.m = m;
		this.p1 = p1;
		this.p2 = p2;
		this.perpendicular = this.vectorMapper.vectorProduct(p1, p2);
		this.p3 = vectorMapper.reverseVector(p1);
		this.p4 = vectorMapper.reverseVector(p2);
		this.a = vectorMapper.subtraction(p1, p2);
		this.b = vectorMapper.subtraction(p1, this.p4);
		this.area = a.length() * b.length();
		
	}

	public Rectangle(Vector a, Vector b, Position m) {
		
		vectorMapper = VectorMapper.getInstance();
		this.m = m;
		this.a = a;
		this.b = b;
		this.perpendicular = this.vectorMapper.vectorProduct(a, b);
		this.area = a.length() * b.length();
		
		Vector halfA;
		Vector halfB;
		
		halfA = vectorMapper.multiplicationWithScalar(a, 0.5F);
		halfB = vectorMapper.multiplicationWithScalar(b, 0.5F);
		this.p1 = vectorMapper.addition(halfA, halfB);
		this.p2 = vectorMapper.subtraction(halfB, halfA);
		this.p3 = vectorMapper.reverseVector(vectorMapper.addition(halfB, halfA));
		this.p4 = vectorMapper.subtraction(halfA, halfB);
		
	}

	public Rectangle (Position m, int nrPerpendicular, float lengthA, float lengthB) {
		
		vectorMapper = VectorMapper.getInstance();
		this.m = m;
		this.perpendicular = vectorMapper.getPerpendicular(nrPerpendicular);
		this.withStandardPerpendicular = true;
		this.nrStandardPerpendicular = nrPerpendicular;
		
		Vector a;
		Vector b;
		
		a = vectorMapper.getRectangleAForPerpendicular(nrPerpendicular);
		a.mul(lengthA);

		b = vectorMapper.getRectangleBForPerpendicular(nrPerpendicular);
		b.mul(lengthB);

		this.a = a;
		this.b = b;
		
		this.area = lengthA * lengthB;
		
		Vector halfA;
		Vector halfB;
		
		halfA = vectorMapper.multiplicationWithScalar(a, 0.5F);
		halfB = vectorMapper.multiplicationWithScalar(b, 0.5F);
		this.p1 = vectorMapper.addition(halfA, halfB);
		this.p2 = vectorMapper.subtraction(halfB, halfA);
		this.p3 = vectorMapper.reverseVector(vectorMapper.addition(halfB, halfA));
		this.p4 = vectorMapper.subtraction(halfA, halfB);
		
	}
	
	public void changeMiddlePoint(Position m) {
		this.m = m;
	}
	
	public double getArea() {
		return this.area;
	}
	
	public Vector getPerpendicular() {
		return new Vector(this.perpendicular);
	}
}
