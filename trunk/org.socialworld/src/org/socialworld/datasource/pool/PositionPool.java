package org.socialworld.datasource.pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;

public class PositionPool {
	public static final int CAPACITY_PosP_ARRAY = 10;

	private static PositionPool instance;
	
	private static Position positionsForPositiveIndex[];
	private static int capacityForPositiveIndex;
	private static Position positionsForNegativeIndex[];
	private static int capacityForNegativeIndex;

	private PositionPool () {
		positionsForPositiveIndex = new Position[CAPACITY_PosP_ARRAY];
		capacityForPositiveIndex = CAPACITY_PosP_ARRAY;

		positionsForNegativeIndex = new Position[CAPACITY_PosP_ARRAY];
		capacityForNegativeIndex = CAPACITY_PosP_ARRAY;
		
		
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
			if (capacityForPositiveIndex > index ) 
				return positionsForPositiveIndex[index];
			else return null;
		else {
			index = index * -1;
			if (capacityForNegativeIndex > index ) 
				return positionsForNegativeIndex[index];
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
			int vorzeichen = 1;
			int count = 0;
			
			int i;

			array = new int[3];
			
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_positions.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

	
			while ((line = lnr.readLine()) != null)
			{
				
				if (line.startsWith("("))
				{
					line = line.substring(1);
					line = line.replace(")", "");
					line = line.replace(" ", "");
					line = line.trim();
					
					values = line.split(",");
					for (i = 0; i < 3; i++){
						array[i] = Integer.parseInt(values[i]);
					}
					
					index = index + vorzeichen * count;
					count++;
					vorzeichen = vorzeichen * -1;
					if (index >= 0) 
							if (index < capacityForPositiveIndex && positionsForPositiveIndex[index] == null ) 
								positionsForPositiveIndex[index] = new Position(new Vector (array[0], array[1], array[2]) );
							else ;
					else {
						index = index * -1;
						if (index < capacityForNegativeIndex &&  positionsForNegativeIndex[index] == null ) 
								positionsForNegativeIndex[index] = new Position(new Vector (array[0], array[1], array[2]) );
						index = index * -1;
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
