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
package org.socialworld.attributes;

import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.ValueProperty;

/**
 * The class implements an attribute array. It
 *         has an byte array for the value of every attribute and set and get
 *         methods to manipulate and assign the values.
 *         There also is an array for the difference of every attribute's new value
 *         to its old value, that means how the attribute has changed by the last calculation
 * @author Mathias Sikos (tyloesand) 
 */
public class AttributeArray extends SimProperty {
	/**
	 * The constant holds the informations about the range of values. The range
	 * of values is from 0 to the constant's value.
	 */
	public static final byte ATTRIBUTE_VALUE_MAX = 99;
	// !!! if the constant ATTRIBUTE_RANGE is changed the following methods have to be conformed :
	// - AttributeCalculatorFunctionTable.initialize()
	public static final byte ATTRIBUTE_VALUE_MIDDLE = 50;
	
	private int numberOfAttributes;

	/**
	 * an array for every attribute value.
	 */
	private int attributes[];

	/**
	 * an array for every attribute's value change.
	 */
	private int differences[];
	                         
	public AttributeArray(int numberOfAttributes) {
		setPropertyName(PropertyName.simobj_attributeArray);
		this.numberOfAttributes = numberOfAttributes;
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		for (int i = 0; i < numberOfAttributes; i++ ) {
			attributes[i] = 0;
			differences[i] = 0;
		}
	}

	public AttributeArray(int[] array) {
		setPropertyName(PropertyName.simobj_attributeArray);
		numberOfAttributes = array.length;
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			attributes[i] = array[i];
			differences[i] = 0;
		}
	}
	
	public AttributeArray(String tupel, int numberOfAttributes) {

		setPropertyName(PropertyName.simobj_attributeArray);
		this.numberOfAttributes = numberOfAttributes;
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		String line = tupel.substring(1, tupel.length() - 1);
		String[] values = line.split(",");
		
		for(int i = 0; i < values.length; i++ )
		{
			this.attributes[i] =  Integer.parseInt(values[i]);
			this.differences[i] = 0;
		}
		


	}
	
	public AttributeArray(AttributeArray original) {
		setPropertyName(PropertyName.simobj_attributeArray);
		numberOfAttributes = original.length();
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			differences[i] = 0;
			attributes[i] = original.attributes[i];
		}
	}
	
	private AttributeArray(AttributeArray original, PropertyProtection protectionOriginal, SimulationCluster cluster ) {
		super(protectionOriginal, cluster);
		setPropertyName(PropertyName.simobj_attributeArray);
		numberOfAttributes = original.length();
		attributes 	= new int[numberOfAttributes];
		differences = new int[numberOfAttributes];
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			differences[i] = 0;
			attributes[i] = original.attributes[i];
		}
	}

	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    ISavedValues  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public SimProperty copyForProperty(SimulationCluster cluster) {
		return new AttributeArray(this, getPropertyProtection(), cluster);
	}

	public  ValueProperty getProperty(SimulationCluster cluster, PropertyName prop, String valueName) {
		switch (prop) {
		// TODO switch property names
		default:
			return ValueProperty.getInvalid();
		}

	}

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    Attribute Array  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute index.
	 * 
	 * @param attributeIndex
	 * @param attributeValue
	 */
	public void set(int attributeIndex, int attributeValue) {
		if (attributeValue > ATTRIBUTE_VALUE_MAX) attributeValue = ATTRIBUTE_VALUE_MAX;
		if (attributeValue < 0) attributeValue = 0;
		
		differences[attributeIndex] = (attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] 	= attributeValue;
	}

	/**
	 * The method sets an attribute value. The attribute is addressed by the
	 * attribute name.
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	public void set(Attribute attributeName, int attributeValue) {
		int attributeIndex = attributeName.getIndex();
		
		if (attributeValue > ATTRIBUTE_VALUE_MAX) attributeValue = ATTRIBUTE_VALUE_MAX;
		if (attributeValue < 0) attributeValue = 0;
		
		differences[attributeIndex] = (attributeValue - attributes[attributeIndex]);
		attributes[attributeIndex] = attributeValue;
	}

	
	public void set(Value attributeArray) {
		if (attributeArray.isValid() && (attributeArray.getType() == Type.attributeArray)) {
			set((AttributeArray)attributeArray.getValue());
		}
	}
	
	/**
	 * The method sets all attribute values. 
	 * 
	 * @param attributeArray
	 */
	private void set(AttributeArray attributeArray) {
		
		for (int i = 0; i < numberOfAttributes; i++ ) {
			set(i, attributeArray.get(i));
		}
	}

	/**
	 * The method returns an attribute value. The attribute is addressed by the
	 * attribute's name.
	 * 
	 * @param attibuteName
	 *            attribute name
	 * @return attribute value
	 */
	public int get(Attribute attributeName) {
		int attributeIndex = attributeName.getIndex();
		return get(attributeIndex);
	}
	
	/**
	 * The method returns an attribute value. The attribute is addressed by the
	 * attribute's index.
	 * 
	 * @param attibuteIndex
	 *            attribute array index
	 * @return attribute value
	 */
	public int get(int attibuteIndex) {
		return attributes[attibuteIndex];
	}


	public Value getAsValue(int attributeIndex) {
		return new Value(Type.integer, get(attributeIndex));
	}
	
	/**
	 * The method returns an attribute's value difference. The attribute is addressed by the
	 * attribute's index.
	 * 
	 * @param attibuteIndex
	 *            attribute array index
	 * @return last attribute's value change 
	 */
	public int getDifference(int attibuteIndex) {
	return differences[attibuteIndex];
	}
	
	public Value getDifferenceAsValue(int attributeIndex) {
		return new Value(Type.integer, getDifference(attributeIndex));
	}
	
	public int length() {
		return numberOfAttributes;
	}
	
	public boolean equals(AttributeArray anotherOne) {
		
		for (int i = 0; i < numberOfAttributes; i++) {
			if (this.attributes[i] != anotherOne.attributes[i]) return false;
		}
		
		return true;
		
	}
	
	public String toString() {
		String returnValue = "("; 
		
		for (int i = 0; i < numberOfAttributes - 1; i++ ) 
			returnValue = returnValue + attributes[i] + ", ";
		returnValue = returnValue + attributes[numberOfAttributes - 1] + ")";
		return returnValue;
		
	}
}
