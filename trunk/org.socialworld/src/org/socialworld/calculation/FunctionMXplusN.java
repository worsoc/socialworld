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
public class FunctionMXplusN extends FunctionBase {

	Type type;
	
	private float m;
	private Value n;
	
	private Value max;
	private Value min;
	
	private boolean withMinMaxCheck = false;
	
	// special floatingpoint
	private float n_as_float;
	private float max_as_float;
	private float min_as_float;
	
	//special constructor for float values
	public FunctionMXplusN( float m, float n) {
		
		this.type = Type.floatingpoint;
		
		this.m = m;
		this.n = new Value(Type.floatingpoint, n);
		
		this.n_as_float = n;
		
	}

	public FunctionMXplusN(Type type, float m, Value n, Value min, Value max, boolean withMinMaxCheck) {
		
		this.type = type;
		
		this.m = m;
		this.n = n;
		
		this.min = min;
		this.max = max;
		
		this.withMinMaxCheck = withMinMaxCheck;
		
		if (type == Type.floatingpoint) {
			n_as_float = (float) n.getValue();
			max_as_float = (float) max.getValue();
			min_as_float = (float) min.getValue();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.calculation.FunctionBase#calculate(org.socialworld.calculation.Value[])
	 */
	@Override
	public Value calculate(Value[] arguments) {
			// assumption: value is at index 0 
		
			Value x;
			
			if ( arguments.length < 1) return new Value();
			x = arguments[0];
			
			if (!x.isValid()) return x;
			 
			if (type == Type.floatingpoint) 
				return new Value (Type.floatingpoint, calculateFloatingPoint((float) x.getValue()));
				
			Value result;
			
			if (x.type != n.type) return new Value();
					
			result = calculation.addition(
							calculation.multiplication(
											new Value(Type.floatingpoint,m),
											x),
							n);

			if (result.type != n.type) return new Value();

			if (withMinMaxCheck) {
				if (calculation.compareGreater(result,  max).isTrue() ) result = calculation.copy(max);
				if (calculation.compareLess(result,  min).isTrue() ) result = calculation.copy(min);
			}
			
			return result;
	}

	public float calculateFloatingPoint(float x) {
		
		float result;
		
		result = m * x + n_as_float;
		
		if (withMinMaxCheck) {
			if (result > max_as_float) result = max_as_float;
			if (result < min_as_float) result = min_as_float;
		}
		
		return result;
	}
	
}
