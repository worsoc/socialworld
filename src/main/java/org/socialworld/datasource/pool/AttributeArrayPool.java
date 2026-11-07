/*
 * Social World
 * Copyright (C) 2012  Mathias Sikos
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


import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;

public class AttributeArrayPool {

	public static final int COUNT_TEST_ENTRIES = 6;

	public static final int CAPACITY_AAP_ARRAY = 1000;

	private static AttributeArrayPool instance;
	
	private static AttributeArray attributeArrays[];
	private static AttributeArray testArrays[];
	
	private AttributeArrayPool () {
		attributeArrays = new AttributeArray[CAPACITY_AAP_ARRAY];
		
		initialize();
	}
	
	public static AttributeArrayPool getInstance() {
		if (instance == null) {
			instance = new AttributeArrayPool();
		}
		return instance;
	}

	private void setArray(int index, AttributeArray array) {
		if (index >= 0)
			if (CAPACITY_AAP_ARRAY > index ) 	attributeArrays[index] = array;
		
	}
	
	public AttributeArray getArray(int index) {
		if (index >= 0)
			if (CAPACITY_AAP_ARRAY > index ) 	return attributeArrays[index];
	   return AttributeArray.getObjectNothing();
	}

	/**
	 * The test arrays, independent of the database. The gauss pool uses them
	 * for every gauss index the database leaves empty.
	 */
	public AttributeArray getTestArray(int index) {
		return testArrays[Math.abs(index) % COUNT_TEST_ENTRIES];
	}

	/**
	 * Loads the arrays from the database. If nothing comes from there
	 * (no rows or no connection), the test data is used instead.
	 */
	private void initialize() {
		int countLoaded = 0;
		
		testArrays = createTestArrays();
		
		try {
			countLoaded = loadFromDB();
		}
		catch (RuntimeException e) {
			e.printStackTrace();
		}
		
		if (countLoaded == 0) {
			System.out.println("AttributeArrayPool: no attribute arrays from the database, the test data is used");
			initializeWithTestData();
		}
	}
	
	private void initializeWithTestData() {
		
		for (int i = 0; i <  COUNT_TEST_ENTRIES; i++) {
			setArray(i, testArrays[i]);
		}
	}
	
	private static AttributeArray[] createTestArrays() {
		
		AttributeArray arrays[] = new AttributeArray[COUNT_TEST_ENTRIES];
		int 		tupelsCount = COUNT_TEST_ENTRIES;
		String attributeTupels[] = new String[tupelsCount];
		
		int numberOfAttributes = 9;
		
		attributeTupels[0] = "(52,39,45,52,39,45,52,39,45)";
		attributeTupels[1] = "(52,41,44,52,41,44,52,41,44)";
		attributeTupels[2] = "(49,50,38,49,50,38,49,50,38)";
		attributeTupels[3] = "(52,39,46,52,39,46,52,39,46)";
		attributeTupels[4] = "(57,41,45,57,41,45,57,41,45)";
		attributeTupels[5] = "(9,13,14,9,13,14,9,13,14)";

		for (int i = 0; i <  tupelsCount; i++) {
			
			arrays[i] = new AttributeArray(attributeTupels[i], numberOfAttributes);

		}
		
		return arrays;
	}
	
	/**
	 * @return how many arrays came from the database
	 */
	private int loadFromDB() {
		int countLoaded = 0;
		TablePoolAttribute table;
		int rowCount;
		int row;
		
		int aa_id;
		int lastAAID;
		int maxAttribNr;
		int attrib_nr; 
		int attribIndex;
		int value;
		
		
		int values[];
		AttributeArray array = AttributeArray.getObjectNothing();
		
		table = new TablePoolAttribute();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY aa_id ASC, attrib_nr DESC");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			lastAAID = -1;
			values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
			
			for (row = 0; row < rowCount; row++) {
				aa_id = table.getAttributeArrayID(row);
				if (aa_id != lastAAID) {
					maxAttribNr = table.getAttribNr(row);
					values = new int[maxAttribNr];
					lastAAID = aa_id;
				}
				attrib_nr = table.getAttribNr(row);
				attribIndex = attrib_nr - 1;
				value = table.getValue(row);
				values[attribIndex] = value;

				if (attribIndex == 0) {
					array = new AttributeArray(values);
					setArray(aa_id, array);
					if (aa_id >= 0 && aa_id < CAPACITY_AAP_ARRAY) countLoaded++;
				}

			}
		}
		
		return countLoaded;
	}
	
		
}
