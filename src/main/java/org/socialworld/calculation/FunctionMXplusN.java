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


import org.socialworld.GlobalSwitches;
import org.socialworld.collections.ValueArrayList;

/**
 * @author Mathias Sikos
 *
 */
public class FunctionMXplusN extends FunctionBase {

	Type type;
	
	private float m;
	private Value n;
	
	
	private boolean useFloatingPointCalculation = false;
	
	// special floatingpoint
	private float n_as_float;
	
	//special constructor for float values
	public FunctionMXplusN( float m, float n) {
		
		this.type = Type.floatingpoint;
		
		this.m = m;
	//	this.n = new Value(Type.floatingpoint, n);
		
		this.n_as_float = n;
		
		this.useFloatingPointCalculation = true;
	}

	public FunctionMXplusN(Type type, float m, float n, float min, float max) {
		
		this.type = type;
		
		this.m = m;
		this.n_as_float = n;
		setMinMaxCheckFloat(min, max);
		this.useFloatingPointCalculation = true;
		
			
	}
	
	public FunctionMXplusN(Type type, float m, Value n, Value min, Value max, boolean withMinMaxCheck) {
		
		this.type = type;
		
		this.m = m;
		this.n = n;
		
		setMinMaxCheckValue(min, max);
		
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.calculation.FunctionBase#calculate(org.socialworld.calculation.Value[])
	 */
	@Override
	public Value calculate(ValueArrayList arguments) {
			// assumption: value is at index 0 
			Value result;
			Value x;
			Object o;
			float resultFloat;
			
			if ( arguments.size() < 1) return Value.getValueNothing();
			x = arguments.get(0);
			
			if (!x.isValid()) return x;
			

			
			if (this.useFloatingPointCalculation) {
				
				
				o = x.getObject(Type.floatingpoint);
				if (o instanceof NoObject) {
					if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
						System.out.println("FunctionMXplusN.calculate > result: o (getObject(Type.floatingpoint)) is NoObject " + ((NoObject)o).getReason().toString() );
					}
					resultFloat = 0;
				}
				else {
					resultFloat = calculateFloatingPoint((float) calculation.createValue(Type.floatingpoint, (float) (o)).getObject(Type.floatingpoint));
				}

				
				if (type == Type.floatingpoint)  {
					result = calculation.createValue(Type.floatingpoint, resultFloat);
					return result;
				}
				if (type == Type.integer) {
					result = calculation.createValue(Type.integer, resultFloat);
					return result;
				}
				
			}

			
			if (x.getType() != n.getType()) return Value.getValueNothing();
					
			result = calculation.addition(
							calculation.multiplication(
											new Value(Type.floatingpoint,m),
											x),
							n);

			if (result.getType() != n.getType()) return Value.getValueNothing();

			result = getMinMaxedValue(result);
			return result;
	}


	public float calculateFloatingPoint(float x) {
		
		float result;
		
		result = m * x + n_as_float;
		result = getMinMaxedFloat(result);
		
		return result;
	}
	
}
