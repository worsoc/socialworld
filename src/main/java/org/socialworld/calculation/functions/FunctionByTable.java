/*
 * Social World
 * Copyright (C) 2014  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.calculation.functions;


import org.socialworld.GlobalSwitches;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.FunctionBase;
import org.socialworld.calculation.NoObject;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;

public class FunctionByTable extends FunctionBase {

	private FunctionByTable_Type type;
	private int values[];

	private int maxValue;
	
	/**
	 * The share of the range that the falling and the rising edge of u take each.
	 */
	static final double U_EDGE_SHARE = 0.18;
	/**
	 * The curvature of the edges of u: values fall like (1 - x/edge)^U_EXPONENT.
	 */
	static final double U_EXPONENT = 1.8;
	
	public FunctionByTable (FunctionByTable_Type type) {
		this(type, AttributeArray.ATTRIBUTE_VALUE_MAX);
	}
	
	/**
	 * For tests: the same function for another range of values.
	 */
	FunctionByTable (FunctionByTable_Type type, int maxValue) {
		this.type = type;
		this.maxValue = maxValue;
		initialize();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		Value value;
		value = arguments.get(0);
		
		Object o = value.getObject(Type.integer);
		if (o instanceof NoObject) {
			if (GlobalSwitches.OUTPUT_DEBUG_GETOBJECT) {
				System.out.println("FunctionByTable.calculate > calculation.createValue: o (getObject(Type.integer)) is NoObject " + ((NoObject)o).getReason().toString() );
			}
			return Value.getValueNothing();
		}
		else {
			return calculation.createValue(Type.integer, calculate((int) o));
		}
		
	}


	public int calculate(int inputValue) {
		int result;

		if (inputValue > maxValue)
			inputValue = maxValue;
		if (inputValue < 0)
			inputValue = 0;

		switch (type) {
		case identical:
			result = inputValue;
			break;
		case horizontal_min:
			result = 0;
			break;
		case horizontal_max:
			result = maxValue;
			break;
		default:
			result = values[inputValue];
		}	

		if (result > maxValue)
			result = maxValue;
		if (result < 0)
			result = 0;
		return (int) result;
	}

	private void initialize() {
		switch (type) {
		case positive_raise:
			values = new int[maxValue + 1];
		    for(int i=0; i <= maxValue; i++)
		    	values[i] =   i;  
			break;
		case negative_raise:
			values = new int[maxValue + 1];
		    for(int i=0; i <= maxValue; i++)
		    	values[i] =   (maxValue - i);  
			break;
		case v:
			values = new int[maxValue + 1];
		    for(int i=0; i <= maxValue/2; i++)
		    	values[i] =   (maxValue - 2 * i);  
		    for(int i=(int)(maxValue/2 + 1); i <= maxValue; i++)
		    	values[i] =   ( 2 * i - maxValue);  
			break;
		case v_mirrored:
			values = new int[maxValue + 1];
		    for(int i=0; i <= maxValue/2; i++)
		    	values[i] =   (2 * i);  
		    for(int i=(int)(maxValue/2 + 1); i <= maxValue; i++)
		    	values[i] =   (2 * (maxValue - i) );  
			break;
		case u:
			// high at both edges, 0 in the middle
			values = createU();
			break;
		case u_mirrored:
			// 0 at both edges, high in the middle
			values = createU();
		    for(int i=0; i <= maxValue; i++)
		    	values[i] =   (maxValue - values[i]);  
			break;
		case hinge_high:
			values = createHingeHigh();
			break;
		case hinge_low:
			values = createHingeLow();
			break;

		default:
			// no array filled
			// for types identical, horizontal_min, horizontal_max
		}		
	}

	/**
	 * 0 up to 60 % of the range, then rising to maxValue: acts only when the source is high.
	 */
	private int[] createHingeHigh() {
		int table[] = new int[maxValue + 1];
		int threshold = (maxValue + 1) * 6 / 10;

		for (int i = threshold + 1; i <= maxValue; i++) {
			table[i] = (int) ((long) (i - threshold) * maxValue / (maxValue - threshold));
		}
		return table;
	}

	/**
	 * maxValue at 0, falling to 0 at 40 % of the range: acts only when the source is low.
	 */
	private int[] createHingeLow() {
		int table[] = new int[maxValue + 1];
		int threshold = (maxValue + 1) * 4 / 10;

		for (int i = 0; i < threshold; i++) {
			table[i] = (int) ((long) (threshold - i) * maxValue / threshold);
		}
		return table;
	}

	/**
	 * The table of u: it falls from maxValue at 0 to 0 at the edge (18 % of the range),
	 * stays 0 in the middle and rises symmetrically to maxValue at maxValue.
	 * For maxValue 99 this gives the former hand-written table within 1.
	 */
	private int[] createU() {
		int table[] = new int[maxValue + 1];
		int edge = (int) Math.round(maxValue * U_EDGE_SHARE);

		for (int i = 0; i < edge; i++) {
			int value = (int) Math.round(maxValue * Math.pow(1.0 - (double) i / edge, U_EXPONENT));
			table[i] = value;
			table[maxValue - i] = value;
		}
		return table;
	}
	

}
