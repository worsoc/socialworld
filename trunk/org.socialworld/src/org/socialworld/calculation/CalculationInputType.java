package org.socialworld.calculation;

/**
 * The enumeration collects all calculation input types.
 * @author Mathias Sikos (tyloesand)
 *   NewAttributeValue - the input value is a new attribute value,
 *   	 that means it is the attribute of the last attribute calculation
 *   	it corresponds to AttributeArray.attributes[i]	
 *   AttributeChange - the input value is the difference of new and old attribute value
 *   	it corresponds to AttributeArray.differences[i]	
 *   OldAttributeValue - the input value is an old attribute value,
 *   	 that means it is the attribute value of the attribute calculation before
 *   	it corresponds to ( AttributeArray.attributes[i] - AttributeArray.differences[i] )	
 *   	  
 */
public enum CalculationInputType {
	NewAttributeValue, AttributeChange, OldAttributeValue 

}
