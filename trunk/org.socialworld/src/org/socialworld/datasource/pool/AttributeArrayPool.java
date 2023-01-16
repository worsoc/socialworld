package org.socialworld.datasource.pool;


import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.datasource.tablesPool.TablePoolAttribute;

public class AttributeArrayPool {

	public static final int COUNT_TEST_ENTRIES = 6;

	public static final int CAPACITY_AAP_ARRAY = 1000;

	private static AttributeArrayPool instance;
	
	private static AttributeArray attributeArrays[];
	
	private AttributeArrayPool () {
		attributeArrays = new AttributeArray[CAPACITY_AAP_ARRAY];
		
		initializeWithTestData();
		//initialize();
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
	
	private void initializeWithTestData() {
		
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
			
			setArray(i, new AttributeArray(attributeTupels[i], numberOfAttributes));

		}
		
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
				}

			}
		}
	}
	
		
}
