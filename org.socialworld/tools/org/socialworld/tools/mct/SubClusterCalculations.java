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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.socialworld.tools.mct.isles.*;

public class SubClusterCalculations {

	private static final 	Integer nrsToNorth[]   = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	private static final 	Integer nrsToSouth[]   = {72, 73, 74, 75, 76, 77, 78, 79, 80};
	private static final 	Integer nrsToWest[]   = {0, 9, 18, 27, 36, 45, 54, 63, 72};
	private static final 	Integer nrsToEast[]   = {8, 17, 26, 35, 44, 53, 62, 71, 80};

	List<Integer> nrsBorderTilesToNorth = Arrays.asList(nrsToNorth);
	List<Integer> nrsBorderTilesToSouth = Arrays.asList(nrsToSouth);
	List<Integer> nrsBorderTilesToWest = Arrays.asList(nrsToWest);
	List<Integer> nrsBorderTilesToEast = Arrays.asList(nrsToEast);

	List<List<Integer>> subClustersSorted;
	private Tile[] tiles;

	boolean isInitialized = false;
	
	private Random random = new Random();

	void initWithTiles(Tile[] tiles) {
		this.tiles = tiles;
		findSubClusters();
		isInitialized = true;
	}
	
	void setBorderAdapters() {
		
		TileGrid sub;
		Tile neighbourNorth;
		Tile neighbourEast;
		Tile neighbourSouth;
		Tile neighbourWest;
		
		for (int index = 0; index < 81; index++) {
			if (this.tiles[index].getType() == TileType.sub) {
				sub = (TileGrid) this.tiles[index];
				if (index > 9) {
					neighbourNorth = this.tiles[index-9];
					if (neighbourNorth.getType() != TileType.sub) {
						sub.setBorderAdapterTypeNorth(neighbourNorth.getBorderAdapterTypeSouth());
					}
				}
				if ((index % 9 ) < 8) {
					neighbourEast = this.tiles[index+1];
					if (neighbourEast.getType() != TileType.sub) {
						sub.setBorderAdapterTypeEast(neighbourEast.getBorderAdapterTypeWest());
					}
				}
				if (index < 72) {
					neighbourSouth = this.tiles[index+9];
					if (neighbourSouth.getType() != TileType.sub) {
						sub.setBorderAdapterTypeSouth(neighbourSouth.getBorderAdapterTypeNorth());
					}
				}
				if ((index % 9 ) > 0) {
					neighbourWest = this.tiles[index-1];
					if (neighbourWest.getType() != TileType.sub) {
						sub.setBorderAdapterTypeWest(neighbourWest.getBorderAdapterTypeEast());
					}
				}
			}
		}
	}

	void calculate() {
		
		if (isInitialized) {
			
			for (List<Integer> subCluster : subClustersSorted) {
				
				List<HeightLevelChanger> bestHLCs = null;
				List<Integer> hlcSubs = null;
				int maxCountHLC = 0;
				
				List<Integer> northTiles = getBorderTilesToNorth(subCluster);
				List<Integer> southTiles = getBorderTilesToSouth(subCluster);
				List<Integer> westTiles = getBorderTilesToWest(subCluster);
				List<Integer> eastTiles = getBorderTilesToEast(subCluster);

/*				
				HeightLevelChangerList hlcListN2S = getAllAllowedHLCfromNorthToSouth(
						northTiles, southTiles, subCluster);
				hlcListN2S.reduceToBest();
				int countHLCsN2S = hlcListN2S.countBestHLCs();
				if (countHLCsN2S > maxCountHLC) {
					bestHLCs = hlcListN2S.getBestHLCs();
					maxCountHLC = countHLCsN2S;
				}
				
				HeightLevelChangerList hlcListW2E = getAllAllowedHLCfromWestToEast(
						westTiles, eastTiles, subCluster);
				hlcListW2E.reduceToBest();
				int countHLCsW2E = hlcListW2E.countBestHLCs();
				if (countHLCsW2E > maxCountHLC) {
					bestHLCs = hlcListW2E.getBestHLCs();
					maxCountHLC = countHLCsW2E;
				}
				
				HeightLevelChangerList hlcListN2E = getAllAllowedHLCfromNorthToEast(
						northTiles, eastTiles, subCluster);
				hlcListN2E.reduceToBest();
				int countHLCsN2E = hlcListN2E.countBestHLCs();
				if (countHLCsN2E > maxCountHLC) {
					bestHLCs = hlcListN2E.getBestHLCs();
					maxCountHLC = countHLCsN2E;
				}

				HeightLevelChangerList hlcListN2W = getAllAllowedHLCfromNorthToWest(
						northTiles, westTiles, subCluster);
				hlcListN2W.reduceToBest();
				int countHLCsN2W = hlcListN2W.countBestHLCs();
				if (countHLCsN2W > maxCountHLC) {
					bestHLCs = hlcListN2W.getBestHLCs();
					maxCountHLC = countHLCsN2W;
				}

				HeightLevelChangerList hlcListS2E = getAllAllowedHLCfromSouthToEast(
						southTiles, eastTiles, subCluster);
				hlcListS2E.reduceToBest();
				int countHLCsS2E = hlcListS2E.countBestHLCs();
				if (countHLCsS2E > maxCountHLC) {
					bestHLCs = hlcListS2E.getBestHLCs();
					maxCountHLC = countHLCsS2E;
				}

				HeightLevelChangerList hlcListS2W = getAllAllowedHLCfromSouthToWest(
						southTiles, westTiles, subCluster);
				hlcListS2W.reduceToBest();
				int countHLCsS2W = hlcListS2W.countBestHLCs();
				if (countHLCsS2W > maxCountHLC) {
					bestHLCs = hlcListS2W.getBestHLCs();
					maxCountHLC = countHLCsS2W;
				}
*/
				
				if (maxCountHLC == 0) {
					hlcSubs = new ArrayList<Integer>();
				}
				else {
					hlcSubs = getHLCSubs(bestHLCs);
					setHLCtoTiles(bestHLCs);
				}
				
				List<Integer> isleSubs = getIsleSubs(hlcSubs, subCluster);
				List<HeightIsle>  bestIsles = new ArrayList<HeightIsle>();
				getBestSubIsles(isleSubs, bestIsles);
				
				setHeightIslesToTiles(bestIsles);
			}
			
		}
		
	}

	private void setHLCtoTiles(List<HeightLevelChanger> hlcs) {
		for (HeightLevelChanger hlc : hlcs) {
			List<Integer> rasterIndices = hlc.getRasterIndices();
			List<Integer> cornerMaximaNrs = hlc.getCornerMaximaNrs();
			for (Integer index : rasterIndices) {
				((TileGrid)tiles[index]).setCornerMaximaNr(cornerMaximaNrs.get(index));
			}
		}
	}

	private void setHeightIslesToTiles(List<HeightIsle> isles) {
		Integer rasterIndex;
		int isleID = 0;
		int levelDelta = 4;
		int isleRing;
		int isleRingeOffset;
		int cornerMaximaNr;
		
		int randomArrayIndex;
		int[] levelDeltas = {3, 4, 7};
		
		for (HeightIsle isle : isles) {
			
			List<Integer> rasterIndices = isle.getRasterIndices();
			List<Integer> cornerMaximaNrs = isle.getCornerMaximaNrs();
			List<Integer> isleRings = isle.getRings();
			
			isleID++;
			randomArrayIndex = getRandomIntBetween(0, 3);
			levelDelta = levelDeltas[randomArrayIndex];
			
			for (int index = 0; index < rasterIndices.size(); index++) {
				
				rasterIndex = rasterIndices.get(index);
				cornerMaximaNr = cornerMaximaNrs.get(index);
				((TileGrid)tiles[rasterIndex]).setCornerMaximaNr(cornerMaximaNr);
				
				isleRingeOffset = 0;
				isleRing = 1;
				if (isleRings.size() > 0) {
					
					isleRing = isleRings.get(index);
					
					if ( isleRing == 1 && ( (cornerMaximaNr == 99114) /* from east to west */ || 
							 (cornerMaximaNr == 91194) /* from south to north */ ||
							 (cornerMaximaNr == 91114) /* to corner north/west */||
							 (cornerMaximaNr == 11114) /* isle's inner tiles */ ) ) {
						isleRingeOffset = levelDelta;
					}
					else if ( isleRing == 1 /* other directions at ring 1 */) {
						isleRingeOffset = 0;
					}
					else if ( isleRing == 2 && ( (cornerMaximaNr == 99114) /* from east to west */ || 
							 (cornerMaximaNr == 91194) /* from south to north */ ||
							 (cornerMaximaNr == 91114) /* to corner north/west */||
							 (cornerMaximaNr == 11114) /* isle's inner tiles */ ) ) {
						isleRingeOffset = 2 * levelDelta;
					}
					else if ( isleRing == 2 /* other directions at ring 2 */) {
						isleRingeOffset = levelDelta;
					}
					else if ( isleRing == 3 && ( (cornerMaximaNr == 99114) /* from east to west */ || 
							 (cornerMaximaNr == 91194) /* from south to north */ ||
							 (cornerMaximaNr == 91114) /* to corner north/west */||
							 (cornerMaximaNr == 11114) /* isle's inner tiles */ ) ) {
						isleRingeOffset = 3 * levelDelta;
					}
					else if ( isleRing == 3 /* other directions at ring 3 */) {
						isleRingeOffset = 2 * levelDelta;
					}
				}
				else if ( isleRing == 1 && ( (cornerMaximaNr == 99114) /* from east to west */ || 
						 (cornerMaximaNr == 91194) /* from south to north */ ||
						 (cornerMaximaNr == 91114) /* to corner north/west */||
						 (cornerMaximaNr == 11114) /* isle's inner tiles */ ) ) {
					isleRingeOffset = levelDelta;
				}

				((TileGrid)tiles[rasterIndex]).setIsleLevelDelta(isleID, isleRing, isleRingeOffset, levelDelta);
				
			}
		}
	}
	
	private List<Integer> getHLCSubs(List<HeightLevelChanger> hlcs) {
		
		List<Integer> result = new ArrayList<Integer>();
		for (HeightLevelChanger hlc : hlcs) {
			List<Integer> rasterIndices = hlc.getRasterIndices();
			for (int rasterIndex : rasterIndices) {
				result.add(rasterIndex);
			}
		}
		return result;
		
	}

	private List<Integer> getIsleSubs(List<Integer> hlcSubs, List<Integer> subCluster) {
		
		List<Integer> result = new ArrayList<Integer>(subCluster);
		result.removeAll(hlcSubs);
		return result;
		
	}
	
	private void getBestSubIsles(List<Integer> isleSubs, List<HeightIsle> bestHeightIsles) {
		
		
		int countSubs = isleSubs.size();
		
		if (countSubs > 49) countSubs = 49;
		
		switch (countSubs) {
		case 49: 
			if (addBestSubIsles(SubIsle49.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 48: 
		case 47: 
		case 46: 
		case 45: 
		case 44: 
		case 43: 
		case 42: 
			if (addBestSubIsles(SubIsle48.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle47.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 41: 
		case 40: 
		case 39: 
		case 38: 
		case 37: 
		case 36: 
			if (addBestSubIsles(SubIsle46.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 35: 
			if (addBestSubIsles(SubIsle45.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle44.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 34: 
		case 33: 
		case 32: 
		case 31: 
		case 30: 
			if (addBestSubIsles(SubIsle43.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle42.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 29: 
		case 28: 
			if (addBestSubIsles(SubIsle40.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle39.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 27: 
		case 26: 
		case 25: 
			if (addBestSubIsles(SubIsle41.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 24: 
			if (addBestSubIsles(SubIsle38.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle37.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 23: 
		case 22: 
		case 21: 
			if (addBestSubIsles(SubIsle33.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle32.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 20: 
			if (addBestSubIsles(SubIsle36.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle35.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 19: 
		case 18: 
			if (addBestSubIsles(SubIsle31.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle30.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 17: 
		case 16: 
			if (addBestSubIsles(SubIsle34.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 15: 
			if (addBestSubIsles(SubIsle29.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle28.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 14: 
			if (addBestSubIsles(SubIsle24.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle23.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 13: 
		case 12: 
			if (addBestSubIsles(SubIsle27.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle26.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle22.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle21.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 11: 
		case 10: 
			if (addBestSubIsles(SubIsle20.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle19.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 9: 
			if (addBestSubIsles(SubIsle25.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 8: 
			if (addBestSubIsles(SubIsle18.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle17.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 7: 
		case 6: 
			if (addBestSubIsles(SubIsle16.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
			if (addBestSubIsles(SubIsle15.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		case 5: 
		case 4: 
			if (addBestSubIsles(SubIsle14.getInstance(), isleSubs, bestHeightIsles)) {
				break;
			}
		default:
		}
	}
	
	private boolean addBestSubIsles(
			SubIsle subIsle, List<Integer> isleSubs, List<HeightIsle> bestHeightIsles) {
		
		boolean heightIsleAdded = false;
		HeightIsle heightIsle;
		int rasterIndex;
		
		rasterIndex = subIsle.checkForIsle(isleSubs);
		if (rasterIndex >= 0) {
			heightIsle = subIsle.getIsleAtRasterIndex(rasterIndex);
			isleSubs.removeAll(heightIsle.getRasterIndices());
			getBestSubIsles(isleSubs, bestHeightIsles);
			bestHeightIsles.add(heightIsle);
			heightIsleAdded = true;
		}
		return heightIsleAdded;
	}
	
	private HeightLevelChangerList getAllAllowedHLCfromNorthToSouth(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromNorthToSouth.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}
	
	private HeightLevelChangerList getAllAllowedHLCfromWestToEast(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromWestToEast.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}

	private HeightLevelChangerList getAllAllowedHLCfromNorthToEast(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromNorthToEast.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}

	private HeightLevelChangerList getAllAllowedHLCfromNorthToWest(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromNorthToWest.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}

	private HeightLevelChangerList getAllAllowedHLCfromSouthToEast(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromNorthToEast.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}

	private HeightLevelChangerList getAllAllowedHLCfromSouthToWest(
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		SubHLC hlcDefintion = HLCfromNorthToWest.getInstance();
		return getAllAllowedHLC(hlcDefintion, startTiles, endTiles, subClusterSorted);
	}

	private HeightLevelChangerList getAllAllowedHLC(SubHLC hlcDefintion,
			List<Integer> startTiles, List<Integer> endTiles, List<Integer> subClusterSorted) {
		
		HeightLevelChangerList result = new HeightLevelChangerList();
		
		for (int start : startTiles) {
			for (int end : endTiles) {
				if (hlcDefintion.checkExistAllowedHLC(start, end)) {
					List<HeightLevelChanger> allowedHLCs =
							hlcDefintion.getAllowedHLC(start, end, subClusterSorted);	
					if (allowedHLCs.size() > 0) {
						result.addStart(start);
						result.addHLCs(allowedHLCs);
					}
				}
			}
		}
		return result;
	}

////////////////////////////////////////////////////////////////////////////////////
	
	private List<Integer> getBorderTilesToNorth( List<Integer> subClusterSorted) {
		return getBorderTiles(nrsBorderTilesToNorth, subClusterSorted);
	}

	private List<Integer> getBorderTilesToSouth( List<Integer> subClusterSorted) {
		return getBorderTiles(nrsBorderTilesToSouth, subClusterSorted);
	}

	private List<Integer> getBorderTilesToWest( List<Integer> subClusterSorted) {
		return getBorderTiles(nrsBorderTilesToWest, subClusterSorted);
	}

	private List<Integer> getBorderTilesToEast( List<Integer> subClusterSorted) {
		return getBorderTiles(nrsBorderTilesToEast, subClusterSorted);
	}

	private List<Integer> getBorderTiles(List<Integer> nrsBorderTiles, List<Integer> subClusterSorted) {
		List<Integer> result = new ArrayList<Integer>();
		int start = 0;
		int indexCluster;
		int nrFromCluster;
		for (int indexRaster : nrsBorderTiles) {
			for (indexCluster = start; indexCluster < subClusterSorted.size(); indexCluster++) {
				nrFromCluster = subClusterSorted.get(indexCluster);
				if (indexRaster == nrFromCluster) {
					// subClusterSorted contains indexRaster
					start = indexCluster + 1;
					result.add(indexRaster);
					break;
				}
				else
				if (nrFromCluster > indexRaster) {
					// subClusterSorted doesn't contain indexRaster
					break;
				}
				else {
					// continue with search for indexRaster
				}
			}
			
		}
		return result;
	}

/////////////////////////////////////////////////////////////////////////////////////////
// creating list of sorted sub clusters
/////////////////////////////////////////////////////////////////////////////////////////
	
	private void findSubClusters() {
		
		List<List<Integer>> 	subClusters = new ArrayList<List<Integer>>();
		boolean checkIndexRaster = true;
		int indexRaster;
		int indexClusters;
		
		for (indexRaster = 0; indexRaster < 81; indexRaster++) {
			
			if (tiles[indexRaster].getType() == TileType.sub) {
				
				checkIndexRaster = true;
				for (indexClusters = 0; indexClusters < subClusters.size(); indexClusters++ ) {
					if (subClusters.get(indexClusters).contains(indexRaster)) {
						checkIndexRaster = false;
						break;
					}
				}
				
				if (checkIndexRaster == true) {
					List<Integer> newCluster = new ArrayList<Integer>();
					newCluster.add(indexRaster);
					fillSubCluster(0, newCluster);
					subClusters.add(newCluster);
				}
				
			}
		}
		
		this.subClustersSorted = getSortedSubClusters(subClusters);
	}
	
	private void fillSubCluster(int indexSubClaster, List<Integer> subCluster) {
		
		if (indexSubClaster < subCluster.size()) {
			List<Integer> neighbourSubs = getNeighbourSubs(subCluster.get(indexSubClaster));
			for (int neighbourSub : neighbourSubs) {
				if (!subCluster.contains(neighbourSub)) {
					subCluster.add(neighbourSub);
				}
			}
			indexSubClaster++;
			fillSubCluster(indexSubClaster, subCluster);
		}
		
	}

	private List<Integer> getNeighbourSubs(int indexRaster) {
		
		List<Integer> result = new ArrayList<Integer>();
		
		if ((indexRaster % 9 > 0) && (tiles[indexRaster - 1].getType() == TileType.sub) ) {
			result.add(indexRaster - 1);
		}
		if ((indexRaster % 9 < 8) && (tiles[indexRaster + 1].getType() == TileType.sub) ) {
			result.add(indexRaster + 1);
		}
		if ((indexRaster > 8) && (tiles[indexRaster - 9].getType() == TileType.sub) ) {
			result.add(indexRaster - 9);
		}
		if ((indexRaster < 72) && (tiles[indexRaster + 9].getType() == TileType.sub) ) {
			result.add(indexRaster + 9);
		}
		
		return result;
	}

	private List<List<Integer>> getSortedSubClusters(List<List<Integer>> 	subClusters) {
		
		List<List<Integer>> subClustersSorted = new ArrayList<List<Integer>>(subClusters.size());
		for (int clusterNr = 0; clusterNr < subClusters.size(); clusterNr++) {
			subClustersSorted.add(getSortedCluster(subClusters.get(clusterNr)));
		}
		return subClustersSorted;
	}

	private List<Integer> getSortedCluster(List<Integer> clusterUnsorted) {
		
		int steps = clusterUnsorted.size();
		List<Integer> result = new ArrayList<Integer>(steps);
		int min, last;
		int i, j;
		int jForMin;
		
		for (i = 0; i < steps; i++) {
			min = 82;
			jForMin = clusterUnsorted.size() - 1;
			last = clusterUnsorted.get(jForMin);
			for (j = 0; j < clusterUnsorted.size(); j++) {
				if (clusterUnsorted.get(j) < min) {
					min = clusterUnsorted.get(j);
					jForMin = j;
				}
			}
			clusterUnsorted.remove(clusterUnsorted.size() - 1);
			if (last != min) {
				clusterUnsorted.set(jForMin, last);
			}

			result.add( min);
		}
		
		return result;
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////
	
	private int getRandomIntBetween(int a, int b) {
		int value = random.nextInt(b - a) + a ;
		return value;
	}


}
