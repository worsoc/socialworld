package org.socialworld.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.socialworld.attributes.AttributeArray;

public class AttributeArrayPool {

	private static final Logger logger = Logger.getLogger(AttributeArrayPool.class);
	private static AttributeArrayPool instance;
	
	private static List<AttributeArray> attributes;
	
	private AttributeArrayPool () {
		logger.debug("create AttributeArrayPool");
		attributes = new ArrayList<AttributeArray> ();

		initialize();
	}
	
	public static AttributeArrayPool getInstance() {
		if (instance == null) {
			instance = new AttributeArrayPool();
		}
		return instance;
	}
	
	public AttributeArray getArray(int index) {
		if (attributes.size() >= index) 
			return attributes.get(index);
		else {
			attributes.add(createArray());
			return attributes.get(attributes.size());
		}
		
	}

	private void initialize() {
		attributes.add(createArray());
	}
	
	private AttributeArray createArray() {
		byte[] array;
		array = getArray();
		AttributeArray attributes = new AttributeArray(array);
		
		return attributes;
	}
	
	private byte[] getArray() {
		byte[] array;
		byte[] array1 = {50,50,50,50,50,50,50,50};
		byte[] array2 = {40,60,30,50,60,50,30,70};
		byte[] array3 = {40,42,44,46,50,54,56,58};
		
		int count = attributes.size();
		
		switch (count) {
		case 1: 
			array = array2;
			logger.debug("getArray() returns array2");
			break;
		case 2: 
			array = array3;
			logger.debug("getArray() returns array3");
			break;
		default: 
			array = array1;
			logger.debug("getArray() returns array1");
			break;
		}
		return array;
	
	}
}
