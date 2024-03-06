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


import org.socialworld.collections.ValueArrayList;

/**
 * @author Mathias Sikos
 *
 */
public class FunctionMtimesExpXplusN extends FunctionBase {

	Type type;
	
	private float base;
	private float m;
	private float n;
	
	
	public FunctionMtimesExpXplusN(Type type,  float base, float m, float n ) {
		
		this.type = type;

		this.base = base;
		this.m = m;
		this.n = n;
		
	}
	
	public FunctionMtimesExpXplusN(Type type, float base, float m, float n, float min, float max) {
		
		this.type = type;

		this.base = base;
		this.m = m;
		this.n = n;
		
		setMinMaxCheckFloat(min, max);
		
	}

	/* (non-Javadoc)
	 * @see org.socialworld.calculation.FunctionBase#calculate(org.socialworld.calculation.Value[])
	 */
	@Override
	public Value calculate(ValueArrayList arguments) {
		Value x;
		float result;
		
		if ( arguments.size() < 1) return Value.getValueNothing();
		x = arguments.get(0);
		
		if (!x.isValid()) return x;
		 
		if (!(x.hasType(Type.floatingpoint))) return Value.getValueNothing();
		
		result = calculateFloatingPoint((float) calculation.createValue(Type.floatingpoint, x.getObject(Type.floatingpoint)).getObject(Type.floatingpoint));
		return calculation.createValue(this.type, result);
			
		
	}

	
	public float calculateFloatingPoint(float x) {
		
		double resultD;
		float resultF;
		
		resultD = m * Math.pow(base, x) + n;
		
		resultF = getMinMaxedFloat((float)resultD);
		
		return  resultF;
	}

}
