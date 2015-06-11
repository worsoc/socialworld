package org.socialworld.datasource.pool;


import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;

public class AttributeArrayPool {

	public static final int CAPACITY_AAP_ARRAY = 100;

	private static AttributeArrayPool instance;
	
	private static AttributeArray attributesForPositiveIndex[];
	private static AttributeArray attributesForNegativeIndex[];
	
	private AttributeArrayPool () {
		attributesForPositiveIndex = new AttributeArray[CAPACITY_AAP_ARRAY];
		attributesForNegativeIndex = new AttributeArray[CAPACITY_AAP_ARRAY];
		
		initialize();
	}
	
	public static AttributeArrayPool getInstance() {
		if (instance == null) {
			instance = new AttributeArrayPool();
		}
		return instance;
	}
	
	public AttributeArray getArray(int index) {
		if (index >= 0)
			if (CAPACITY_AAP_ARRAY > index ) 
				return attributesForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (CAPACITY_AAP_ARRAY > index ) 
				return attributesForNegativeIndex[index];
			else return null;
		}	
		
	}

	private void setArray(int index, AttributeArray array) {
		if (index >= 0)
			if (CAPACITY_AAP_ARRAY > index ) 
				attributesForPositiveIndex[index] = array;
			else ;
		else {
			index = index * -1;
			if (CAPACITY_AAP_ARRAY > index ) 
				attributesForNegativeIndex[index] = array;
			else;
		}	
		
	}
	
	private void initialize() {
		
		loadFromDB();
		

	}
	
	private void loadFromDB() {
		TablePoolAttribute table;
		int rowCount;
		int row;
		
		int index;
		int attrib_nr; 
		int attribIndex;
		int value;
		
		int lastIndex;
		
		int values[];
		AttributeArray array;
		
		table = new TablePoolAttribute();
		
		table.select(table.SELECT_ALL_COLUMNS, "", "ORDER BY index, attrib_nr ");
		rowCount = table.rowCount();
		
		if (rowCount > 0) {
			lastIndex = table.getIndex(0);
			values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
			
			for (row = 0; row < rowCount; row++) {
				index = table.getIndex(row);
				if (index != lastIndex) {
					array = new AttributeArray(values);
					setArray(lastIndex, array);
					values = new int[Attribute.NUMBER_OF_ATTRIBUTES];
					lastIndex = index;
				}
				attrib_nr = table.getAttribNr(row);
				attribIndex = attrib_nr - 1;
				value = table.getValue(row);
				values[attribIndex] = value;
				
			}
		}
	}
	
		
}
