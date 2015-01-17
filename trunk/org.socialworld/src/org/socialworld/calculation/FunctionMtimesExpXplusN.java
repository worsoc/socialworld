/*
* Social World
* Copyright (C) 2015  Mathias Sikos
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
 * @author Mathias Sikos
 *
 */
public class FunctionMtimesExpXplusN extends FunctionBase {

	
	private float base;
	private float m;
	private float n;
	
	private float max;
	private float min;
	
	private boolean withMinMaxCheck;
	
	public FunctionMtimesExpXplusN( float base, float m, float n, float min, float max, boolean withMinMaxCheck) {
		
		this.base = base;
		this.m = m;
		this.n = n;
		
		this.min = min;
		this.max = max;
		
		this.withMinMaxCheck = withMinMaxCheck;
		
	}

	/* (non-Javadoc)
	 * @see org.socialworld.calculation.FunctionBase#calculate(org.socialworld.calculation.Value[])
	 */
	@Override
	public Value calculate(Value[] arguments) {
		Value x;
		
		if ( arguments.length < 1) return new Value();
		x = arguments[0];
		
		if (!x.isValid()) return x;
		 
		if (!(x.type == Type.floatingpoint)) return new Value();
		
		return new Value (Type.floatingpoint, calculateFloatingPoint((float) x.getValue()));
			
		
	}

	public float calculateFloatingPoint(float x) {
		
		double result;
		
		result = m * Math.pow(base, x) + n;
		
		if (withMinMaxCheck) {
			if (result > max) result = max;
			if (result < min) result = min;
		}
		
		return (float) result;
	}

}
