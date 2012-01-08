/**
 * 
 */
package org.socialworld.calculation;

import org.socialworld.attributes.Attribute;

/**
 * The class implements the calculation function as a table.
 *  The attribute value is interpreted as table's index.
 *  For the identical function no array is filled.
 *  For the other functions an array is initialized. 
 *  Getting the function value means to look into the array at the index given by the argument value
 * @author Mathias Sikos (tyloesand) 
 */
public class AttributeCalculatorFunctionTable extends
		AttributeCalculatorFunction {

	private AttributeCalculatorFunctionTableType acftType;
	private byte values[];

	private byte range = Attribute.ATTRIBUTE_RANGE;

	public AttributeCalculatorFunctionTable(AttributeCalculatorFunctionTableType type) {
		acftType = type;
		
		initialize();
	}

	/**
	 * The method interprets the function as a table. The input value is
	 * understood as the index of a table and table's value at the index is the
	 * function value.
	 */
	public byte calculate(byte inputValue) {
		int result;

		if (inputValue > range)
			inputValue = range;
		if (inputValue < 0)
			inputValue = 0;

		switch (acftType) {
		case identical:
			result = inputValue;
			break;
		case horizontal_min:
			result = 0;
			break;
		case horizontal_max:
			result = range;
			break;
		default:
			result = values[inputValue];
		}	

		if (result > range)
			result = range;
		if (result < 0)
			result = 0;
		return (byte) result;
	}
	
	private void initialize() {
		switch (acftType) {
		case negative_raise:
			values = new byte[range + 1];
		    for(byte i=0; i < range; i++)
		    	values[i] =  (byte) (range - i);  
			break;
		case v:
			values = new byte[range + 1];
		    for(byte i=0; i <= range/2; i++)
		    	values[i] =  (byte) (range - 2 * i);  
		    for(byte i=(byte)(range/2 + 1); i <= range; i++)
		    	values[i] =  (byte) ( 2 * i - range);  
			break;
		case v_mirrored:
			values = new byte[range + 1];
		    for(byte i=0; i <= range/2; i++)
		    	values[i] =  (byte) (2 * i);  
		    for(byte i=(byte)(range/2 + 1); i <= range; i++)
		    	values[i] =  (byte) (2 * (range - i) );  
			break;
		case u:
			values = new byte[range + 1];
			values[0] = 99; values[1] = 89; values[2] = 80; values[3] = 71; values[4] = 63;
			values[5] = 55; values[6] = 48; values[7] = 41; values[8] = 35; values[9] = 29;
			values[10] = 24; values[11] = 19; values[12] = 15; values[13] = 11; values[14] = 8;
			values[15] = 5; values[16] = 3; values[17] = 1; 

		    for(byte i=18; i <= 81; i++)
		    	values[i] =  0;  
			
			values[99] = 99; values[98] = 89; values[97] = 80; values[96] = 71; values[95] = 63;
			values[94] = 55; values[93] = 48; values[92] = 41; values[91] = 35; values[90] = 29;
			values[89] = 24; values[88] = 19; values[87] = 15; values[86] = 11; values[85] = 8;
			values[84] = 5; values[83] = 3; values[82] = 1; 

			break;
		case u_mirrored:
			values = new byte[range + 1];
			values[0] = 0; values[1] = 10; values[2] = 19; values[3] = 28; values[4] = 36;
			values[5] = 44; values[6] = 51; values[7] = 58; values[8] = 64; values[9] = 70;
			values[10] = 75; values[11] = 80; values[12] = 84; values[13] = 88; values[14] = 91;
			values[15] = 94; values[16] = 96; values[17] = 98; 

		    for(byte i=18; i <= 81; i++)
		    	values[i] =  range;  
			
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