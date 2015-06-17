package org.socialworld.datasource.pool;


import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;

public class AttributeArrayPool {

	public static final int CAPACITY_AAP_ARRAY = 1000;

	private static AttributeArrayPool instance;
	
	private static AttributeArray attributeArrays[];
	
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
	   return null;
	}

	private void initialize() {
		
		loadFromDB();
		

	}
	
	private void loadFromDB() {
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
		AttributeArray array;
		
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
				}

			}
		}
	}
	
		
}
