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


public class Calculation {

	private static Calculation instance;
	
	static Value nothing;
	
	static Value zeroInteger;
	static Value zeroFloatingPoint;
	static Value zeroVector;
	
	public static Calculation getInstance() {
		if (instance == null) {
			instance = new Calculation();
		}
		return instance;
		
	}
	
	private Calculation() {
		nothing = new Value();
		
		// TODO implement methods for further types
		zeroInteger = new Value(Type.integer, 0);
		zeroFloatingPoint = new Value(Type.floatingpoint, 0F);
		zeroVector = new Value(Type.vector, new Vector(0F, 0F, 0F));
	}
	
	public  Value createValue(Type type, Object value) {
		return new Value(type, value);
	}
	
	public Value or(Value op1, Value op2) {
		if (op1.isValid() & op2.isValid())
			return createValue(Type.bool, op1.isTrue() | op2.isTrue());
		else
			return nothing;
	}

	public Value and(Value op1, Value op2) {
		
		if (op1.isValid() & op2.isValid())
			return createValue(Type.bool, op1.isTrue() & op2.isTrue());
		else
			return nothing;
	}

	public Value compareEqual(Value op1, Value op2) {
		Value tmp;
		tmp = subtraction(op1, op2);
		
		if ( tmp.isValid() ) 
				return createValue(Type.bool, equalsZero(tmp));
		return nothing;
	}

	public Value compareNotEqual(Value op1, Value op2) {
		Value tmp;
		tmp = subtraction(op1, op2);
		
		if ( tmp.isValid() ) 
				return createValue(Type.bool, !equalsZero(tmp));
		return nothing;
	}

	public Value compareGreater(Value op1, Value op2) {
		Value tmp;
		tmp = subtraction(op1, op2);
		
		if ( tmp.isValid() ) 
				return createValue(Type.bool, greaterToZero(tmp));
		return nothing;
	}

	public Value compareGreaterEqual(Value op1, Value op2) {
		Value tmp;
		tmp = subtraction(op2, op1);
		
		if ( tmp.isValid() ) 
				return createValue(Type.bool, !greaterToZero(tmp));
		return nothing;
	}

	public Value compareLessEqual(Value op1, Value op2) {
		return compareGreaterEqual(op2, op1);
	}

	public Value compareLess(Value op1, Value op2) {
		return compareGreater(op2, op1);
	}

	private boolean equalsZero(Value op1) {
		// assumption: op1 is valid !
		Type type;
		type = op1.getType();
		
		switch (type) {
		case integer:
			return ( (int) op1.getValue() == 0 /*(int) getZero(Type.integer).getValue()*/ );
		case floatingpoint:
			return ( (float) op1.getValue() == 0F /*(float) getZero(Type.floatingpoint).getValue() */ );
		case vector:
			Vector tmp;
			tmp = (Vector) op1.getValue();
			return (tmp.x == 0F & tmp.y == 0F & tmp.z == 0F);
		default:
			return false;
		}

	}
	
	private boolean greaterToZero(Value op1) {
		// assumption: op1 is valid !
		
		Type type;
		type = op1.getType();
		
		switch (type) {
		case integer:
			return ( (int) op1.getValue() > 0 /* (int) getZero(Type.integer).getValue()*/ );
		case floatingpoint:
			return ( (float) op1.getValue() > 0F/* (float) getZero(Type.floatingpoint).getValue() */ );
		case vector:
			Vector tmp;
			tmp = (Vector) op1.getValue();
			return tmp.length() > 0F /* ((Vector) getZero(Type.vector).getValue()).length() */;
		default:
			return false;
		}
	}
	
	public Value addition(Value op1, Value op2){
		switch (op1.getType() ) {
		case integer:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.integer, (int) op1.getValue() + (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (int) op1.getValue() + (double) op2.getValue() );
			default:
				return nothing;
			}
		case floatingpoint:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.floatingpoint, (double) op1.getValue() + (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (double) op1.getValue() + (double) op2.getValue() );
			default:
				return nothing;
			}
			
		default:
			return nothing;
		}
	}

	public Value subtraction(Value op1, Value op2) {
		return addition(op1, negate(op2));
	}

	public Value multiplication(Value op1, Value op2) {
		switch (op1.getType() ) {
		case integer:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.integer, (int) op1.getValue() * (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (int) op1.getValue() * (double) op2.getValue() );
			default:
				return null;
			}
		case floatingpoint:
			switch (op2.getType() ) {
			case integer:
				return createValue(Type.floatingpoint, (double) op1.getValue() * (int) op2.getValue() );
			case floatingpoint:
				return createValue(Type.floatingpoint, (double) op1.getValue() * (double) op2.getValue() );
			default:
				return null;
			}
		default:
			return null;
		}
	}

	
	public Value negate(Value op1) {
		switch (op1.getType() ) {
		case integer:
				return createValue(Type.integer, (int) op1.getValue() * (-1) );
		case floatingpoint:
			return createValue(Type.floatingpoint, (double) op1.getValue() * (-1F) );
		default:
			return null;
		}
		
	}
	
	public Value copy(Value original) {
		if (original.isValid())
			return createValue(original.getType(), original.getValueCopy() );
		else
			return new Value();
	}
	
	
	public static Value getNothing() {
		return nothing;
	}
	
	
	
	private Value getZero(Type type) {
		switch (type) {
		case integer:
			return zeroInteger;
		case floatingpoint:
			return zeroFloatingPoint;
		case vector:
			return zeroVector;
			
		default:
			return nothing;
		}
	}
}
