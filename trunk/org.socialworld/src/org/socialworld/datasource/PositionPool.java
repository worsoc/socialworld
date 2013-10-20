package org.socialworld.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;

import org.socialworld.attributes.Position;

public class PositionPool {
	public static final int CAPACITY_PosP_ARRAY = 100;

	private static PositionPool instance;
	
	private static ArrayList<Position> positionsForPositiveIndex;
	private static ArrayList<Position> positionsForNegativeIndex;

	private PositionPool () {
		positionsForPositiveIndex = new ArrayList<Position> ();
		positionsForPositiveIndex.ensureCapacity(CAPACITY_PosP_ARRAY);

		positionsForNegativeIndex = new ArrayList<Position> ();
		positionsForNegativeIndex.ensureCapacity(CAPACITY_PosP_ARRAY);
		
		initializeFromFile();
	}

	public static PositionPool getInstance() {
		if (instance == null) {
			instance = new PositionPool();
		}
		return instance;
	}

	public Position getPosition(int index) {
		if (index >= 0)
			if (positionsForPositiveIndex.size() > index ) 
				return positionsForPositiveIndex.get(index);
			else return null;
		else {
			index = index * -1;
			if (positionsForNegativeIndex.size() > index ) 
				return positionsForNegativeIndex.get(index);
			else return null;
		}	
	}
	
	private void initializeFromFile() {
		
		try
		{
			String line;
			String values[];
			int array[];
			
			int index = 0;
			float deviation = 0;
			int vorzeichen = 1;
			int count = 0;
			
			int i;

			array = new int[3];
			
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_positions.txt").openStream();
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
					for (i = 0; i < 3; i++){
						array[i] = Integer.parseInt(values[i]);
					}
					
					index = (int) deviation;
					vorzeichen = 1;
					count = 0;
					// find the nearest free index
					// but only CAPACITY_PosP_ARRAY tries
					if (index >= 0) {
						while (count < CAPACITY_PosP_ARRAY) 
							if (index < CAPACITY_PosP_ARRAY && index >= 0 && positionsForPositiveIndex.get(index) == null ) {
								positionsForPositiveIndex.set(index, new Position(array[1], array[2], array[3]));
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
						while (count < CAPACITY_PosP_ARRAY) 
							if (index < CAPACITY_PosP_ARRAY && index > 0 && positionsForNegativeIndex.get(index) == null ) {
								positionsForNegativeIndex.set(index, new Position(array[1], array[2], array[3]));
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
