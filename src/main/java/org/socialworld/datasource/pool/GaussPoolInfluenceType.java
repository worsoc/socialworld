/*
* Social World
* Copyright (C) 2014  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.datasource.pool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.socialworld.core.EventType;

public class GaussPoolInfluenceType {
	public static final int CAPACITY_GPIT_ARRAY = 100;

	private static GaussPoolInfluenceType instance;
	
	private static int[][] influenceTypesForPositiveIndex;
	private static int[][] influenceTypesForNegativeIndex;
	
	private GaussPoolInfluenceType() {
		influenceTypesForPositiveIndex = new int[CAPACITY_GPIT_ARRAY][ EventType.MAX_EVENT_TYPE];
		influenceTypesForNegativeIndex = new int[CAPACITY_GPIT_ARRAY][ EventType.MAX_EVENT_TYPE];
		
		 Random random = new Random();

		    for (int j = 0; j < EventType.MAX_EVENT_TYPE; j++) {
		        // 1. Erstelle eine Liste mit allen Zahlen von 0 bis 99
		        List<Integer> coverageList = new ArrayList<>(100);
		        for (int n = 0; n < 100; n++) coverageList.add(n);
		        
		        // 2. Mische die Liste (Shuffling)
		        Collections.shuffle(coverageList, random);
		        
		        for (int i = 0; i < CAPACITY_GPIT_ARRAY; i++) {
		            // Da CAPACITY_GPIT_ARRAY = 100 ist, kriegt jeder Gauss-Index 
		            // genau eine der Zahlen aus der gemischten Liste.
		            // Damit ist JEDE Zahl zwischen 0-99 genau EINMAL pro EventType vertreten.
		            influenceTypesForPositiveIndex[i][j] = coverageList.get(i);
		            
		            // Für die negative Seite mischen wir neu
		            if (i == 0) Collections.shuffle(coverageList, random);
		            influenceTypesForNegativeIndex[i][j] = coverageList.get(i);
		        }
		    }
	}
	
	public static synchronized GaussPoolInfluenceType getInstance() {
		if (instance == null) {
			instance = new GaussPoolInfluenceType();
		}
		return instance;
	}

	public int[] getInfluenceTypes(int indexByGauss) {
		
	       if (indexByGauss >= 0) {
	            // Schutz gegen IndexOutOfBounds
	            int idx = Math.min(indexByGauss, CAPACITY_GPIT_ARRAY - 1);
	            return influenceTypesForPositiveIndex[idx];
	        } else {
	            // Umwandlung in positiven Index und Deckelung
	            int idx = Math.min(Math.abs(indexByGauss), CAPACITY_GPIT_ARRAY - 1);
	            return influenceTypesForNegativeIndex[idx];
	        }    
		
	}
	
}
