/*
 * Social World
 * Copyright (C) 2015  Mathias Sikos
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
package org.socialworld.datasource.pool;


import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.datasource.tablesPool.TableGaussAA;

public class GaussPoolAttributeArray {

	public static final int CAPACITY_GPAA_ARRAY = 100;

	private static GaussPoolAttributeArray instance;
	
	private static AttributeArray attributesForPositiveIndex[];
	private static AttributeArray attributesForNegativeIndex[];
	
	private GaussPoolAttributeArray () {
		attributesForPositiveIndex = new AttributeArray[CAPACITY_GPAA_ARRAY];
		attributesForNegativeIndex = new AttributeArray[CAPACITY_GPAA_ARRAY];
		
		initialize();
	}
	
	public static GaussPoolAttributeArray getInstance() {
		if (instance == null) {
			instance = new GaussPoolAttributeArray();
		}
		return instance;
	}
	
	public Value getArrayAsValue(int index) {
		return new Value(Type.attributeArray, getArray(index));
	}
	
	public AttributeArray getArray(int index) {
		if (index >= 0)
			if (CAPACITY_GPAA_ARRAY > index ) 
				return attributesForPositiveIndex[index];
			else return AttributeArray.getObjectNothing();
		else {
			index = index * -1;
			if (CAPACITY_GPAA_ARRAY > index ) 
				return attributesForNegativeIndex[index];
			else return AttributeArray.getObjectNothing();
		}	
		
	}

	private void setArray(int index, AttributeArray array) {
		if (index >= 0)
			if (CAPACITY_GPAA_ARRAY > index ) 
				attributesForPositiveIndex[index] = array;
			else ;
		else {
			index = index * -1;
			if (CAPACITY_GPAA_ARRAY > index ) 
				attributesForNegativeIndex[index] = array;
			else;
		}	
		
	}
	
	/**
	 * Loads the mapping from the database. Every gauss index that stays empty
	 * (no row, no connection, or an aa_id without attributes) gets a test array.
	 */
	private void initialize() {
		
		try {
			loadFromDB();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		fillGapsWithTestData();
	}
	
	private void fillGapsWithTestData() {
		
		AttributeArray attribs;
		int countGaps = 0;
		
		int j=0;
		for (int i = -CAPACITY_GPAA_ARRAY + 1; i < CAPACITY_GPAA_ARRAY; i++) {
			
			attribs = getArray(i);
			if (attribs == null || attribs.checkIsObjectNothing()) {
				setArray(i, AttributeArrayPool.getInstance().getTestArray(j));
				countGaps++;
				
				j++;
				if (j == AttributeArrayPool.COUNT_TEST_ENTRIES) j = 0;
			}

		}
		
		if (countGaps > 0) {
			System.out.println("GaussPoolAttributeArray: " + countGaps + " gauss indices without an array from the database get test data");
		}
	}
	
	private void loadFromDB() {
		TableGaussAA table;
		int rowCount;
		int row;
	
		int gauss_index;
		int aa_id;
		
		AttributeArray array = AttributeArray.getObjectNothing();
		
		table = new TableGaussAA();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			
			for (row = 0; row < rowCount; row++) {
				gauss_index = table.getGaussIndex(row);
				aa_id = table.getAttributeArrayID(row);
				
				array =  AttributeArrayPool.getInstance().getArray(aa_id);
				setArray(gauss_index, array);
			}
		}
		
	}
	
		
}
