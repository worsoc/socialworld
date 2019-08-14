package org.socialworld.datasource.pool;


import org.socialworld.attributes.AttributeArray;
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
	
	public AttributeArray getArray(int index) {
		if (index >= 0)
			if (CAPACITY_GPAA_ARRAY > index ) 
				return attributesForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (CAPACITY_GPAA_ARRAY > index ) 
				return attributesForNegativeIndex[index];
			else return null;
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
	
	private void initialize() {
		
		//loadFromDB();
		loadTestData();
		

	}
	
	private void loadTestData() {
		
		AttributeArray attribs;
		
		int j=0;
		for (int i = -CAPACITY_GPAA_ARRAY + 1; i < CAPACITY_GPAA_ARRAY; i++) {
			
			attribs =  AttributeArrayPool.getInstance().getArray(j);
			setArray(i, attribs);
			
			j++;
			
			if (j == AttributeArrayPool.COUNT_TEST_ENTRIES) j = 0;

		}
	}
	
	private void loadFromDB() {
		TableGaussAA table;
		int rowCount;
		int row;
	
		int gauss_index;
		int aa_id;
		
		AttributeArray array;
		
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
