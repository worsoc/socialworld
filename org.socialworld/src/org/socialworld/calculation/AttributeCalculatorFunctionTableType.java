package org.socialworld.calculation;


public enum AttributeCalculatorFunctionTableType {
	horizontal_min(0), identical(1), negative_raise(2), 
	v(3), v_mirrored(4), u(5), u_mirrored(6), horizontal_max(7), positive_raise(8);
	
	/**
	 * The constant holds the informations how many attributes are simulated.
	 * The constant is used for iteration about all attributes and for creation
	 * of attribute arrays.
	 */
	public static final int NUMBER_OF_AFCT_TYPES = 9;

	private int arrayIndex;

	private AttributeCalculatorFunctionTableType(int index) {
		this.arrayIndex = index;
	}

	/**
	 * The method returns the index of the attribute calculator function table.
	 * 
	 * @return attribute's index
	 */
	public int getIndex() {
		return arrayIndex;
	}
	
	/**
	 * The method returns the acft type which belongs to the parameter index.
	 * 
	 * @param arrayIndex
	 *            attribute index
	 * @return attribute name
	 */
	public AttributeCalculatorFunctionTableType getType(int arrayIndex) {
		for (AttributeCalculatorFunctionTableType type : AttributeCalculatorFunctionTableType.values())
			if (type.arrayIndex == arrayIndex)
				return type;
		return null;
	}
}
