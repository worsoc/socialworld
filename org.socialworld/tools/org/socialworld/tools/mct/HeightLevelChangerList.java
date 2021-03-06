/*
* Social World
* Copyright (C) 2021  Mathias Sikos
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
package org.socialworld.tools.mct;

import java.util.ArrayList;
import java.util.List;

public class HeightLevelChangerList {

	List<Integer> startTileRasterIndexs;
	
	List<HeightLevelChanger> allAllowedHLCs;
	
	List<HeightLevelChanger> bestHLCs;
	
	int maxNrHLCNeighbourCluster;
	
	void addStart(int startTileRasterIndex) {
		
		if (startTileRasterIndexs.indexOf(startTileRasterIndex) == -1) {
			startTileRasterIndexs.add(startTileRasterIndex);
		}
		
	}
	
	void addHLCs(List<HeightLevelChanger> hlcs) {
		
		for (HeightLevelChanger hlc : hlcs) {
			if (hlc.isValid()) {
				
				allAllowedHLCs.add(hlc);
				if (hlc.nrNeighbourCluster > maxNrHLCNeighbourCluster) {
					maxNrHLCNeighbourCluster = hlc.nrNeighbourCluster;
				}
				
			}
		}
	}
	
	int countBestHLCs() {
		return bestHLCs.size();
	}
	
	List<HeightLevelChanger> getBestHLCs() {
		return bestHLCs;
	}
	
	void reduceToBest() {
		
		int bestNr= -1;
		int bestCount = 0;
//		int countStartTiles = startTileRasterIndexs.size();
		
		int[] countNr = new int[maxNrHLCNeighbourCluster + 1];
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			countNr[i] = 0;
		}
		
		
		List<List<Integer>> starts;
		List<Integer> startsForNrNeighbourCluster;
		starts = new ArrayList<List<Integer>>();
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			startsForNrNeighbourCluster = new ArrayList<Integer>();
			starts.set(i, startsForNrNeighbourCluster);
		}

		List<List<Integer>> neighbourIndexs;
		List<Integer> neighbourIndexsForNrNeighbourCluster;
		neighbourIndexs = new ArrayList<List<Integer>>();
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			neighbourIndexsForNrNeighbourCluster = new ArrayList<Integer>();
			neighbourIndexs.set(i, neighbourIndexsForNrNeighbourCluster);
		}
		
		
		for (HeightLevelChanger hlc : allAllowedHLCs) {
			if (hlc.neighbourIndex > 0 && neighbourIndexs.get(hlc.nrNeighbourCluster).contains(hlc.neighbourIndex)) {
				// ignore it, because we only accept a neighbour index once
				// except neighbour index 0
				continue;
			}
			countNr[hlc.nrNeighbourCluster]++;
			if (hlc.neighbourIndex > 0) {
				starts.get(hlc.nrNeighbourCluster).set(hlc.neighbourIndex - 1, hlc.start + 100);
				neighbourIndexs.get(hlc.nrNeighbourCluster).set(hlc.neighbourIndex - 1, hlc.neighbourIndex);
			}
			else {
				starts.get(hlc.nrNeighbourCluster).set(hlc.start, hlc.start + 100);
				neighbourIndexs.get(hlc.nrNeighbourCluster).add(hlc.neighbourIndex);
			}
		}
		
		boolean isFirstEntry;
		// checking the start raster indices are successive (+1 for north and south; +9 for west and east)
		int startIndexBefore;
		int stepsForSuccession = 1;
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			if (countNr[i] == 0) continue;
			isFirstEntry = true;
			startIndexBefore = -1;
			startsForNrNeighbourCluster = starts.get(i);
			for (int startIndex : startsForNrNeighbourCluster) {
				// valid raster indices were inserted with offset 100
				if (startIndex < 100) continue; // no entry
				if (!isFirstEntry) {
					if ((startIndexBefore + stepsForSuccession) == startIndex) {
						// it's okay
					}
					else {
						// it's not okay, there is a gap
						// cancel the "HLC" : set count to 0
						countNr[i] = 0;
						break;
					}
				}
				else {
					isFirstEntry = false;
				}
				startIndexBefore = startIndex;
			}
		}

		// checking the neighbour indices for order without gaps (successive indices)
		int neighbourIndexBefore;
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			if (countNr[i] == 0) continue;
			isFirstEntry = true;
			neighbourIndexBefore = -1;
			neighbourIndexsForNrNeighbourCluster = neighbourIndexs.get(i);
			for (int neighbourIndex : neighbourIndexsForNrNeighbourCluster) {
				if (neighbourIndex == 0) continue;
				if (!isFirstEntry) {
					if ((neighbourIndexBefore + 1) == neighbourIndex) {
						// it's okay
					}
					else {
						// it's not okay, there is a gap
						// cancel the "HLC" : set count to 0
						countNr[i] = 0;
						break;
					}
				}
				else {
					isFirstEntry = false;
				}
				neighbourIndexBefore = neighbourIndex;
			}
		}
		
		for (int i = 0; i <= maxNrHLCNeighbourCluster; i++) {
			if (countNr[i] > bestCount) {
				bestCount = countNr[i];
				bestNr = i;
			}
		}
		
		bestHLCs = new ArrayList<HeightLevelChanger>();
		
		for (HeightLevelChanger hlc : allAllowedHLCs) {
			if (hlc.nrNeighbourCluster == bestNr) {
				bestHLCs.add(hlc);
			}
		}
		
		
		
	}
	
}
