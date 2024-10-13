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


import org.socialworld.collections.ValueArrayList;

public abstract class FunctionBase  implements IObjectReceiver {
	
	private boolean valid = true;

	private boolean withMinMaxCheck = false;

	private Value max;
	private Value min;

	// special floatingpoint
	private float max_as_float;
	private float min_as_float;
	
	protected Calculation calculation = Calculation.getInstance();

	protected ObjectRequester objectRequester = new ObjectRequester();

	protected boolean returnInvalidNothingvalue;
	
	
	public abstract Value calculate(ValueArrayList arguments);
	

	protected void setMinMaxCheckValue(Value min, Value max) {
		this.max = max;
		this.min = min;
		this.withMinMaxCheck = true;
	}	
	protected void setMinMaxCheckFloat(float min, float max) {
		this.max_as_float = max;
		this.min_as_float = min;
		this.withMinMaxCheck = true;
	}
	
	protected boolean isWithMinMaxCheck() {
		return withMinMaxCheck;
	}
	
	protected float getMinMaxedFloat(float value) {
		if (withMinMaxCheck) {
			if (value > max_as_float) value = max_as_float;
			if (value < min_as_float) value = min_as_float;
		}
		return value;
	}
	
	protected Value getMinMaxedValue(Value value) {
		Value result = value;
		if (withMinMaxCheck) {
			if (calculation.compareGreater(value,  max).isTrue() ) result = calculation.copy(max);
			if (calculation.compareLess(value,  min).isTrue() ) result = calculation.copy(min);
		}
		return result;
	}

	
	protected boolean isValid() {
		return valid;
	}
	
	protected void setInvalid() {
		valid = false;
	}

///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectReceiver ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int receiveObject(int requestID, Object object) {
		objectRequester.receive(requestID, object);
		return 0;
	}

}
