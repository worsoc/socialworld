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


import org.socialworld.attributes.AttributeArray;
import org.socialworld.collections.ValueArrayList;

public class FunctionByTable extends FunctionBase {

	private FunctionByTable_Type type;
	private int values[];

	private int maxValue;
	
	public FunctionByTable (FunctionByTable_Type type) {
		this.type = type;
		this.maxValue = AttributeArray.ATTRIBUTE_VALUE_MAX;
		initialize();
	}
	
	@Override
	public Value calculate(ValueArrayList arguments) {
		Value value;
		value = arguments.get(0);
		if (value.getType() == Type.integer) {
			return calculation.createValue(Type.integer, calculate((int) value.getValue()));
		}
		return null;
	}


	private int calculate(int inputValue) {
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
		    for(int i=0; i < maxValue; i++)
		    	values[i] =   i;  
			break;
		case negative_raise:
			values = new int[maxValue + 1];
		    for(int i=0; i < maxValue; i++)
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
		case u:			// assumption: maxValue = 100
			values = new int[maxValue + 1];
			values[0] = 99; values[1] = 89; values[2] = 80; values[3] = 71; values[4] = 63;
			values[5] = 55; values[6] = 48; values[7] = 41; values[8] = 35; values[9] = 29;
			values[10] = 24; values[11] = 19; values[12] = 15; values[13] = 11; values[14] = 8;
			values[15] = 5; values[16] = 3; values[17] = 1; 

		    for(int i=18; i <= 81; i++)
		    	values[i] =  0;  
			
			values[99] = 99; values[98] = 89; values[97] = 80; values[96] = 71; values[95] = 63;
			values[94] = 55; values[93] = 48; values[92] = 41; values[91] = 35; values[90] = 29;
			values[89] = 24; values[88] = 19; values[87] = 15; values[86] = 11; values[85] = 8;
			values[84] = 5; values[83] = 3; values[82] = 1; 

			break;
		case u_mirrored:    // assumption: maxValue = 99
			values = new int[maxValue+ 1];
			values[0] = 0; values[1] = 10; values[2] = 19; values[3] = 28; values[4] = 36;
			values[5] = 44; values[6] = 51; values[7] = 58; values[8] = 64; values[9] = 70;
			values[10] = 75; values[11] = 80; values[12] = 84; values[13] = 88; values[14] = 91;
			values[15] = 94; values[16] = 96; values[17] = 98; 

		    for(int i=18; i <= 81; i++)
		    	values[i] =  maxValue;  
			
			values[99] = 0; values[98] = 10; values[97] = 19; values[96] = 28; values[95] = 36;
			values[94] = 44; values[93] = 51; values[92] = 58; values[91] = 64; values[90] = 70;
			values[89] = 75; values[88] = 80; values[87] = 84; values[86] = 88; values[85] = 91;
			values[84] = 94; values[83] = 96; values[82] = 98; 

			break;


		default:
			// no array filled
			// for types identical, horizontal_min, horizontal_max
		}		
	}
	

}
