package org.socialworld.datasource;

import java.util.ArrayList;
import java.io.*;
import java.net.URL;

import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.AttributeArray;

public class AttributeArrayPool {

	public static final int CAPACITY_AAP_ARRAY = 100;

	private static AttributeArrayPool instance;
	
	private static ArrayList<AttributeArray> attributesForPositiveIndex;
	private static ArrayList<AttributeArray> attributesForNegativeIndex;
	
	private AttributeArrayPool () {
		attributesForPositiveIndex = new ArrayList<AttributeArray> ();
		attributesForPositiveIndex.ensureCapacity(CAPACITY_AAP_ARRAY);

		attributesForNegativeIndex = new ArrayList<AttributeArray> ();
		attributesForNegativeIndex.ensureCapacity(CAPACITY_AAP_ARRAY);
		
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
			if (attributesForPositiveIndex.size() > index ) 
				return attributesForPositiveIndex.get(index);
			else return null;
		else {
			index = index * -1;
			if (attributesForNegativeIndex.size() > index ) 
				return attributesForNegativeIndex.get(index);
			else return null;
		}	
		
	}

	private void initialize() {
		
		initializeFromFile();
		

	}
	
	
	
	private void initializeFromFile() {
		
		try
		{
			String line;
			String values[];
			int attribIndex;
			int array[];
			
			int index = 0;
			float deviation = 0;
			int vorzeichen = 1;
			int count = 0;
			
			array = new int[Attribute.NUMBER_OF_ATTRIBUTES];
			

			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_attributes.swp.txt").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{
				if (line.startsWith("["))
				{
					line = line.substring(1);
					line = line.replace("]", "");
					line = line.trim();
					
					deviation = Float.parseFloat(line);
				}
				
				if (line.startsWith("("))
				{
					line = line.substring(1);
					line = line.replace(")", "");
					line = line.trim();
					
					values = line.split(",");
					for (attribIndex = 0; attribIndex < Attribute.NUMBER_OF_ATTRIBUTES; attribIndex++){
						array[attribIndex] = Integer.parseInt(values[attribIndex]);
					}
					
					index = (int) deviation;
					vorzeichen = 1;
					count = 0;
					// find the nearest free index
					// but only CAPACITY_AAP_ARRAY tries
					if (index >= 0) {
						while (count < CAPACITY_AAP_ARRAY) 
							if (index < CAPACITY_AAP_ARRAY && index >= 0 && attributesForPositiveIndex.get(index) == null ) {
								attributesForPositiveIndex.set(index, new AttributeArray(array));
								break;
							}
							else {
								count++;
								index = index + vorzeichen * count;
								vorzeichen = vorzeichen * -1;
							}
					}
					else {
						index = index * -1;
						while (count < CAPACITY_AAP_ARRAY) 
							if (index < CAPACITY_AAP_ARRAY && index > 0 && attributesForNegativeIndex.get(index) == null ) {
								attributesForNegativeIndex.set(index, new AttributeArray(array));
								break;
							}
							else {
								count++;
								index = index + vorzeichen * count;
								vorzeichen = vorzeichen * -1;
							}
					}	
					
					
				}
			}
			lnr.close();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
	}
}
