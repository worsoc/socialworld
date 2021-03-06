/*
* Social World
* Copyright (C) 2019  Josef Pribbernow
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PossibleTiles {

	private static PossibleTiles instance;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Set<Integer> emptySet;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Set<Integer> allMediumStandardTiles;
	Set<Integer> allAdapterTiles;			// it's the same adapter set for adapters from small to medium and from medium to large
	Set<Integer> allLargeStandardTiles;
	Set<Integer> allSmallStandardTiles;
	Set<Integer> allSmallSpecialTiles;  // !!! not really all: set without variant offsets
	Set<Integer> allSmallSpecialAdapterTiles;	// !!! not really all: set without variant offsets
	
		
	////////////////////////////////////////////////////////////////
	
//	Set<Integer> nbEast_LS_to_LS[]= new Set[16];  	// east neighbors for large standard tiles
//	Set<Integer> nbWest_LS_to_LS[]= new Set[16];  	// west  neighbors for large standard tiles
//	Set<Integer> nbSouth_LS_to_LS[]= new Set[16]; 	// south neighbors for large standard tiles
//	Set<Integer> nbNorth_LS_to_LS[]= new Set[16];  	// north neighbors for large standard tiles
	Set<Integer> nbEast_LS_to_LS[]= new Set[20];  	// east neighbors for large standard tiles
	Set<Integer> nbWest_LS_to_LS[]= new Set[20];  	// west  neighbors for large standard tiles
	Set<Integer> nbSouth_LS_to_LS[]= new Set[20]; 	// south neighbors for large standard tiles
	Set<Integer> nbNorth_LS_to_LS[]= new Set[20];  	// north neighbors for large standard tiles
	
	Set<Integer> nbEast_MS_to_MS[]= new Set[20];  	// east neighbors for medium standard tiles
	Set<Integer> nbWest_MS_to_MS[]= new Set[20];  	// west  neighbors for medium standard tiles
	Set<Integer> nbSouth_MS_to_MS[]= new Set[20];  	// south neighbors for medium standard tiles
	Set<Integer> nbNorth_MS_to_MS[]= new Set[20];  	// north neighbors for medium standard tiles
				
//	Set<Integer> nbEast_SS_to_SS[]= new Set[16];  	// east neighbors for small standard tiles
//	Set<Integer> nbWest_SS_to_SS[]= new Set[16];  	// west  neighbors for small standard tiles
//	Set<Integer> nbSouth_SS_to_SS[]= new Set[16];  	// south neighbors for small standard tiles
//	Set<Integer> nbNorth_SS_to_SS[]= new Set[16];  	// north neighbors for small standard tiles
	Set<Integer> nbEast_SS_to_SS[]= new Set[20];  	// east neighbors for small standard tiles
	Set<Integer> nbWest_SS_to_SS[]= new Set[20];  	// west  neighbors for small standard tiles
	Set<Integer> nbSouth_SS_to_SS[]= new Set[20];  	// south neighbors for small standard tiles
	Set<Integer> nbNorth_SS_to_SS[]= new Set[20];  	// north neighbors for small standard tiles
	
	Set<Integer> nbEast_SS_to_SXA[]= new Set[16];  	// east neighbors for small standard to small special adapter tiles
	Set<Integer> nbWest_SS_to_SXA[]= new Set[16];  	// west  neighbors for small standard to small special adapter tiles
	Set<Integer> nbSouth_SS_to_SXA[]= new Set[16];  	// south neighbors for small standard to small special adapter tiles
	Set<Integer> nbNorth_SS_to_SXA[]= new Set[16];  	// north neighbors for small standard to small special adapter tiles
	
	Set<Integer> nbEast_SS_to_SX[] = new Set[16];	//east neighbors for small standard to small special tiles
	Set<Integer> nbWest_SS_to_SX[] = new Set[16];	//west neighbors for small standard to small special tiles
	Set<Integer> nbSouth_SS_to_SX[] = new Set[16];	//south neighbors for small standard to small special tiles
	Set<Integer> nbNorth_SS_to_SX[] = new Set[16];	//north neighbors for small standard to small special tiles
	
	Set<Integer> nbEast_SXA_to_SS[] = new Set[29];	//east neighbors for small special adapter to small standard tiles
	Set<Integer> nbWest_SXA_to_SS[] = new Set[29];	//west neighbors for small special adapter to small standard tiles
	Set<Integer> nbSouth_SXA_to_SS[] = new Set[29];	//south neighbors for small special adapter to small standard  tiles
	Set<Integer> nbNorth_SXA_to_SS[] = new Set[29];	//north neighbors for small special adapter to small standard  tiles
	
	Set<Integer> nbEast_SXA_to_SX[] = new Set[29];	//east neighbors for small special adapter to small special tiles
	Set<Integer> nbWest_SXA_to_SX[] = new Set[29];	// west neighbors for small special adapter to small special tiles
	Set<Integer> nbSouth_SXA_to_SX[] = new Set[29];	//south neighbors for small special adapter to small special tiles
	Set<Integer> nbNorth_SXA_to_SX[] = new Set[29];	//north neighbors for small special adapter to small special tiles
	
	Set<Integer> nbEast_SXA_to_SXA[] = new Set[29];	//east neighbors for small special adapter to small special adapter tiles
	Set<Integer> nbWest_SXA_to_SXA[] = new Set[29];	// west neighbors for small special adapter to small special adapter tiles
	Set<Integer> nbSouth_SXA_to_SXA[] = new Set[29];	//south neighbors for small special adapter to small special adapter tiles
	Set<Integer> nbNorth_SXA_to_SXA[] = new Set[29];	//north neighbors for small special adapter to small special adapter tiles	
	
	Set<Integer> nbEast_SX_to_SS[] = new Set[13];	//east neighbors for small special to small standard  tiles
	Set<Integer> nbWest_SX_to_SS[] = new Set[13];	//west neighbors for small special to small standard tiles
	Set<Integer> nbSouth_SX_to_SS[] = new Set[13];	//south neighbors for small special to small standard  tiles
	Set<Integer> nbNorth_SX_to_SS[] = new Set[13];	//north neighbors for small special to small standard  tiles
	
	Set<Integer> nbEast_SX_to_SXA[] = new Set[13];	//east neighbors for small special to small special adapter  tiles
	Set<Integer> nbWest_SX_to_SXA[] = new Set[13];	//west neighbors for small special to small special adapter tiles
	Set<Integer> nbSouth_SX_to_SXA[] = new Set[13];	//south neighbors for small special to small special adapter tiles
	Set<Integer> nbNorth_SX_to_SXA[] = new Set[13];	//north neighbors for small special to small special adapter tiles
	
	Set<Integer> nbEast_SX_to_SX[] = new Set[13];	//east neighbors for small special to small special tiles
	Set<Integer> nbWest_SX_to_SX[] = new Set[13];	//west neighbors for small special to small special tiles
	Set<Integer> nbSouth_SX_to_SX[] = new Set[13];	//south neighbors for small special to small special tiles
	Set<Integer> nbNorth_SX_to_SX[] = new Set[13];	//north neighbors for small special to small special tiles
	
	Set<Integer> nbEast_A_to_A[] = new Set[80];	//east neighbors for adapter to adapter tiles
	Set<Integer> nbWest_A_to_A[] = new Set[80]; 	//west neighbors for adapter to adapter tiles
	Set<Integer> nbSouth_A_to_A[] = new Set[80];	//south neighbors for adapter to adapter tiles
	Set<Integer> nbNorth_A_to_A[] = new Set[80];	//north neighbors for adapter to adapter tiles
	
	Set<Integer> nbEast_aS_to_aA1[] = new Set[20];	//east neighbors for any standard tile to any adapter (alternative 1) tiles
	Set<Integer> nbWest_aS_to_aA1[] = new Set[20];	//west neighbors for any standard tile to any adapter (alternative 1) tiles
	Set<Integer> nbSouth_aS_to_aA1[] = new Set[20];	//south neighbors for any standard tile to any adapter (alternative 1) tiles
	Set<Integer> nbNorth_aS_to_aA1[] = new Set[20];	//north neighbors for any standard tile to any adapter (alternative 1) tiles
	
	Set<Integer> nbEast_aS_to_aA2[] = new Set[20];	//east neighbors for any standard tile to any adapter (alternative 2) tiles
	Set<Integer> nbWest_aS_to_aA2[] = new Set[20];	//west neighbors for any standard tile to any adapter (alternative 2) tiles
	Set<Integer> nbSouth_aS_to_aA2[] = new Set[20];	//south neighbors for any standard tile to any adapter (alternative 2) tiles
	Set<Integer> nbNorth_aS_to_aA2[] = new Set[20];	//north neighbors for any standard tile to any adapter (alternative 2) tiles
	
	Set<Integer> nbEast_aS_to_aA3[] = new Set[20];	//east neighbors for any standard tile to any adapter (alternative 3) tiles , equal to alternative 1 !
	Set<Integer> nbWest_aS_to_aA3[] = new Set[20];	//west neighbors for any standard tile to any adapter (alternative 3) tiles , equal to alternative 1 !
	Set<Integer> nbSouth_aS_to_aA3[] = new Set[20];	//south neighbors for any standard tile to any adapter (alternative 3) tiles , equal to alternative 1 !
	Set<Integer> nbNorth_aS_to_aA3[] = new Set[20];	//north neighbors for any standard tile to any adapter (alternative 3) tiles  , equal to alternative 1 !
	
	Set<Integer> nbEast_aA1_to_aS[] = new Set[80];	//east neighbors for any adapter (alternative 1) tiles to any standard tile
	Set<Integer> nbWest_aA1_to_aS[] = new Set[80];	//west neighbors for any adapter (alternative 1) tiles to any standard tile
	Set<Integer> nbSouth_aA1_to_aS[] = new Set[80];	//south neighbors for any adapter (alternative 1) tiles to any standard tile
	Set<Integer> nbNorth_aA1_to_aS[] = new Set[80];	//north neighbors for any adapter (alternative 1) tiles to any standard tile
	
	Set<Integer> nbEast_aA2_to_aS[] = new Set[80];	//east neighbors for any adapter (alternative 2) tiles to any standard tile
	Set<Integer> nbWest_aA2_to_aS[] = new Set[80];	//west neighbors for any adapter (alternative 2) tiles to any standard tile
	Set<Integer> nbSouth_aA2_to_aS[] = new Set[80];	//south neighbors for any adapter (alternative 2) tiles to any standard tile
	Set<Integer> nbNorth_aA2_to_aS[] = new Set[80];	//north neighbors for any adapter (alternative 2) tiles to any standard tile
	
	Set<Integer> nbEast_aA3_to_aS[] = new Set[80];	//east neighbors for any adapter (alternative 3) tiles to any standard tile
	Set<Integer> nbWest_aA3_to_aS[] = new Set[80];	//west neighbors for any adapter (alternative 3) tiles to any standard tile
	Set<Integer> nbSouth_aA3_to_aS[] = new Set[80];	//south neighbors for any adapter (alternative 3) tiles to any standard tile
	Set<Integer> nbNorth_aA3_to_aS[] = new Set[80];	//north neighbors for any adapter (alternative 3) tiles to any standard tile
	
	Set<Integer> nbEast_SX_to_SA[] = new Set[13];	//east neighbors for small special tile to small adapter (adapter alternatives 1 and 3)
	Set<Integer> nbWest_SX_to_SA[] = new Set[13];	//west neighbors for small special tile to small adapter (adapter alternatives 1 and 3)
	Set<Integer> nbSouth_SX_to_SA[] = new Set[13];	//south neighbors for small special tile to small adapter (adapter alternatives 1 and 3)
	Set<Integer> nbNorth_SX_to_SA[] = new Set[13];	//north neighbors for small special tile to small adapter (adapter alternatives 1 and 3)

	Set<Integer> nbEast_SA_to_SX[] = new Set[80];	//east neighbors for small adapter tile (adapter alternatives 1 and 3) to small special tile
	Set<Integer> nbWest_SA_to_SX[] = new Set[80];	//west neighbors for small adapter tile (adapter alternatives 1 and 3) to small special tile
	Set<Integer> nbSouth_SA_to_SX[] = new Set[80];	//south neighbors for small adapter tile (adapter alternatives 1 and 3) to small special tile
	Set<Integer> nbNorth_SA_to_SX[] = new Set[80];	//north neighbors for small adapter tile (adapter alternatives 1 and 3) to small special tile
	
	PossibleTiles() {
	
		Integer emptyset[] = {};						
		this.emptySet = new HashSet<Integer>(Arrays.asList(emptyset));
		
		
		int indexTmp = -1;
	
		Integer allMediumStandardTiles[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
		////////////////////////////////////////////////////////////////
		Integer allAdapterTiles[]  = new Integer[72];
		//for (int i = 1; i <= 3; i++) {
			for (int j = 0; j <= 7; j++) {
				for (int k = 1; k <= 9; k++) {
					indexTmp++;
					allAdapterTiles[indexTmp] =  10 * j + k;
				}
			}
		//}

		////////////////////////////////////////////////////////////////
		//Integer allLargeStandardTiles[]  = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		Integer allLargeStandardTiles[]  = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
		//Integer allSmallStandardTiles[]  = {0, 1, 2, 3, 4, 5, 7, 8, 10, 11, 12, 13, 14, 15};
		Integer allSmallStandardTiles[]  = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
		Integer allSmallSpecialTiles_withoutVariantOffset[]   = {3, 5, 10, 12};
		Integer allSmallSpecialAdapterTiles_withoutVariantOffset[]   = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
		
		
		this.allMediumStandardTiles = new HashSet<Integer>(Arrays.asList(allMediumStandardTiles));
		this.allAdapterTiles  = new HashSet<Integer>(Arrays.asList(allAdapterTiles));
		this.allLargeStandardTiles  = new HashSet<Integer>(Arrays.asList(allLargeStandardTiles));
		this.allSmallStandardTiles  = new HashSet<Integer>(Arrays.asList(allSmallStandardTiles));
		this.allSmallSpecialTiles   = new HashSet<Integer>(Arrays.asList(allSmallSpecialTiles_withoutVariantOffset));
		this.allSmallSpecialAdapterTiles   = new HashSet<Integer>(Arrays.asList(allSmallSpecialAdapterTiles_withoutVariantOffset));
		
	
		////////////////////////////////////////////////////////////////
		
		initNeighbourSetsForMediumStandardToMediumStandardTiles();    // !!! init before large and small tiles
		initNeighbourSetsForLargeStandardToLargeStandardTiles();
		initNeighbourSetsForSmallStandardToSmallStandardTiles();
		initNeighbourSetsForSmallStandardToSmallSpecialAdapterTiles();
		initNeighbourSetsForSmallStandardToSmallSpecialTiles();
		initNeighbourSetsForSmallSpecialAdapterToSmallStandardTiles();	
		initNeighbourSetsForSmallSpecialAdapterToSmallSpecialAdapterTiles();
		initNeighbourSetsForSmallSpecialAdapterToSmallSpecialTiles();
		initNeighbourSetsForSmallSpecialToSmallStandardTiles();
		initNeighbourSetsForSmallSpecialToSmallSpecialAdapterTiles();
		initNeighbourSetsForSmallSpecialToSmallSpecialTiles();
		initNeighbourSetsForSmallSpecialToSmallAdapterTiles();
		initNeighbourSetsForSmallAdapterToSmallSpecialTiles();

		initNeighbourSetsForStandardToAdapterTiles();
		initNeighbourSetsForAdapterToStandardTiles();
		
		initNeighbourSetsForAdapterToAdapter();
	}

	
	public static PossibleTiles getInstance() {
		if (instance == null) {
			instance = new PossibleTiles();
		}
		return instance;
	}
	
	Set<Integer> getEmptySet() {
		return emptySet;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Set<Integer> getAllMediumStandardTiles() {
		return this.allMediumStandardTiles;
	}
	
	Set<Integer> getAllLargeStandardTiles() {
		return this.allLargeStandardTiles;
	}
	
	Set<Integer> getAllSmallStandardTiles() {
		return this.allSmallStandardTiles;
	}
	Set<Integer> getAllSmallSpecialAdapterTiles() {
		return this.allSmallSpecialAdapterTiles;
	}
	Set<Integer> getAllSmallSpecialTiles() {
		return this.allSmallSpecialTiles;
	}
	Set<Integer> getAllAdapterTiles() {
		return this.allAdapterTiles;
	}
	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForMediumStandardToMediumStandardTile(int i) {
		return nbEast_MS_to_MS[i];
	}

	Set<Integer> getWestNeighboursForMediumStandardToMediumStandardTile(int i) {
		return nbWest_MS_to_MS[i];
	}

	Set<Integer> getSouthNeighboursForMediumStandardToMediumStandardTile(int i) {
		return nbSouth_MS_to_MS[i];
	}

	Set<Integer> getNorthNeighboursForMediumStandardToMediumStandardTile(int i) {
		return nbNorth_MS_to_MS[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForAdapterToStandardTile(int i, int adapterAlternative) {
		switch (adapterAlternative) {
			case 1: return this.nbEast_aA1_to_aS[i];
			case 2: return this.nbEast_aA2_to_aS[i];
			case 3: return this.nbEast_aA3_to_aS[i];
			default: return emptySet;
		}
	}
	
	Set<Integer> getWestNeighboursForAdapterToStandardTile(int i, int adapterAlternative) {
		switch (adapterAlternative) {
			case 1: return this.nbWest_aA1_to_aS[i];
			case 2: return this.nbWest_aA2_to_aS[i];
			case 3: return this.nbWest_aA3_to_aS[i];
			default: return emptySet;
		}
	}
	Set<Integer> getSouthNeighboursForAdapterToStandardTile(int i, int adapterAlternative) {
		switch (adapterAlternative) {
			case 1: return this.nbSouth_aA1_to_aS[i];
			case 2: return this.nbSouth_aA2_to_aS[i];
			case 3: return this.nbSouth_aA3_to_aS[i];
			default: return emptySet;
		}
	}
	Set<Integer> getNorthNeighboursForAdapterToStandardTile(int i, int adapterAlternative) {
		switch (adapterAlternative) {
			case 1: return this.nbNorth_aA1_to_aS[i];
			case 2: return this.nbNorth_aA2_to_aS[i];
			case 3: return this.nbNorth_aA3_to_aS[i];
			default: return emptySet;
		}
	}
	
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForAdapterToAdapterTile(int i) {
		return nbEast_A_to_A[i];
	}
	Set<Integer> getWestNeighboursForAdapterToAdapterTile(int i) {
		return nbWest_A_to_A[i];
	}
	Set<Integer> getSouthNeighboursForAdapterToAdapterTile(int i) {
		return nbSouth_A_to_A[i];
	}
	Set<Integer> getNorthNeighboursForAdapterToAdapterTile(int i) {
		return nbNorth_A_to_A[i];
	}
	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForLargeStandardToLargeStandardTile(int i) {
		return nbEast_LS_to_LS[i];
	}

	Set<Integer> getWestNeighboursForLargeStandardToLargeStandardTile(int i) {
		return nbWest_LS_to_LS[i];
	}
	
	Set<Integer> getSouthNeighboursForLargeStandardToLargeStandardTile(int i) {
		return nbSouth_LS_to_LS[i];
	}

	Set<Integer> getNorthNeighboursForLargeStandardToLargeStandardTile(int i) {
		return nbNorth_LS_to_LS[i];
	}
	
	////////////////////////////////////////////////////////////////
		
	Set<Integer> getEastNeighboursForSmallStandardToSmallStandardTile(int i) {
		return nbEast_SS_to_SS[i];
	}

	Set<Integer> getWestNeighboursForSmallStandardToSmallStandardTile(int i) {
		return nbWest_SS_to_SS[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallStandardToSmallStandardTile(int i) {
		return nbSouth_SS_to_SS[i];
	}

	Set<Integer> getNorthNeighboursForSmallStandardToSmallStandardTile(int i) {
		return nbNorth_SS_to_SS[i];
	}
	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForSmallStandardToSmallSpecialAdapterTile(int i) {
		return nbEast_SS_to_SXA[i];
	}

	Set<Integer> getWestNeighboursForSmallStandardToSmallSpecialAdapterTile(int i) {
		return nbWest_SS_to_SXA[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallStandardToSmallSpecialAdapterTile(int i) {
		return nbSouth_SS_to_SXA[i];
	}

	Set<Integer> getNorthNeighboursForSmallStandardToSmallSpecialAdapterTile(int i) {
		return nbNorth_SS_to_SXA[i];
	}

	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForSmallSpecialAdapterToSmallStandardTile(int i) {
		return nbEast_SXA_to_SS[i];
	}

	Set<Integer> getWestNeighboursForSmallSpecialAdapterToSmallStandardTile(int i) {
		return nbWest_SXA_to_SS[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallSpecialAdapterToSmallStandardTile(int i) {
		return nbSouth_SXA_to_SS[i];
	}

	Set<Integer> getNorthNeighboursForSmallSpecialAdapterToSmallStandardTile(int i) {
		return nbNorth_SXA_to_SS[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallSpecialToSmallSpecialAdapterTile(int i) {
		return nbEast_SX_to_SXA[i];
	}

	Set<Integer> getWestNeighboursForSmallSpecialToSmallSpecialAdapterTile(int i) {
		return nbWest_SX_to_SXA[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallSpecialToSmallSpecialAdapterTile(int i) {
		return nbSouth_SX_to_SXA[i];
	}

	Set<Integer> getNorthNeighboursForSmallSpecialToSmallSpecialAdapterTile(int i) {
		return nbNorth_SX_to_SXA[i];
	}

	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallSpecialToSmallAdapterTile(int i) {
		return nbEast_SX_to_SA[i];
	}

	Set<Integer> getWestNeighboursForSmallSpecialToSmallAdapterTile(int i) {
		return nbWest_SX_to_SA[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallSpecialToSmallAdapterTile(int i) {
		return nbSouth_SX_to_SA[i];
	}

	Set<Integer> getNorthNeighboursForSmallSpecialToSmallAdapterTile(int i) {
		return nbNorth_SX_to_SA[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallSpecialToSmallStandardTile(int i) {
		return nbEast_SX_to_SS[i];
	}

	Set<Integer> getWestNeighboursForSmallSpecialToSmallStandardTile(int i) {
		return nbWest_SX_to_SS[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallSpecialToSmallStandardTile(int i) {
		return nbSouth_SX_to_SS[i];
	}

	Set<Integer> getNorthNeighboursForSmallSpecialToSmallStandardTile(int i) {
		return nbNorth_SX_to_SS[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallStandardToSmallSpecialTile(int i) {
		return nbEast_SS_to_SX[i];
	}

	Set<Integer> getWestNeighboursForSmallStandardToSmallSpecialTile(int i) {
		return nbWest_SS_to_SX[i];
	}
	
	Set<Integer> getSouthNeighboursForSmallStandardToSmallSpecialTile(int i) {
		return nbSouth_SS_to_SX[i];
	}

	Set<Integer> getNorthNeighboursForSmallStandardToSmallSpecialTile(int i) {
		return nbNorth_SS_to_SX[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallSpecialAdapterToSmallSpecialTile(int i) {
		return nbEast_SXA_to_SX[i];
	}
	Set<Integer> getWestNeighboursForSmallSpecialAdapterToSmallSpecialTile(int i) {
		return nbWest_SXA_to_SX[i];
	}
	Set<Integer> getSouthNeighboursForSmallSpecialAdapterToSmallSpecialTile(int i) {
		return nbSouth_SXA_to_SX[i];
	}
	Set<Integer> getNorthNeighboursForSmallSpecialAdapterToSmallSpecialTile(int i) {
		return nbNorth_SXA_to_SX[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallAdapterToSmallSpecialTile(int i) {
		return nbEast_SA_to_SX[i];
	}
	Set<Integer> getWestNeighboursForSmallAdapterToSmallSpecialTile(int i) {
		return nbWest_SA_to_SX[i];
	}
	Set<Integer> getSouthNeighboursForSmallAdapterToSmallSpecialTile(int i) {
		return nbSouth_SA_to_SX[i];
	}
	Set<Integer> getNorthNeighboursForSmallAdapterToSmallSpecialTile(int i) {
		return nbNorth_SA_to_SX[i];
	}
	
	////////////////////////////////////////////////////////////////

	Set<Integer> getEastNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(int i) {
		return nbEast_SXA_to_SXA[i];
	}
	Set<Integer> getWestNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(int i) {
		return nbWest_SXA_to_SXA[i];
	}
	Set<Integer> getSouthNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(int i) {
		return nbSouth_SXA_to_SXA[i];
	}
	Set<Integer> getNorthNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(int i) {
		return nbNorth_SXA_to_SXA[i];
	}
	
	///////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForSmallSpecialToSmallSpecialTile(int i) {
		return nbEast_SX_to_SX[i];
	}
	Set<Integer> getWestNeighboursForSmallSpecialToSmallSpecialTile(int i) {
		return nbWest_SX_to_SX[i];
	}
	Set<Integer> getSouthNeighboursForSmallSpecialToSmallSpecialTile(int i) {
		return nbSouth_SX_to_SX[i];
	}
	Set<Integer> getNorthNeighboursForSmallSpecialToSmallSpecialTile(int i) {
		return nbNorth_SX_to_SX[i];
	}
		
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForStandardToAdapter(int i, int alternative) {	
		
		if (i <= 19) {
			switch (alternative) {
				case 1: return this.nbEast_aS_to_aA1[i];
				case 2: return this.nbEast_aS_to_aA2[i];
				case 3: return this.nbEast_aS_to_aA3[i];
				default: return emptySet;
			}
			
		}
		else return emptySet;
	}
	
	Set<Integer> getWestNeighboursForStandardToAdapter(int i, int alternative) {	
		
		if (i <= 19) {
			switch (alternative) {
				case 1: return this.nbWest_aS_to_aA1[i];
				case 2: return this.nbWest_aS_to_aA2[i];
				case 3: return this.nbWest_aS_to_aA3[i];
				default: return emptySet;
			}
			
		}
		else return emptySet;
	}
	
	Set<Integer> getSouthNeighboursForStandardToAdapter(int i, int alternative) {	
		
		if (i <= 19) {
			switch (alternative) {
				case 1: return this.nbSouth_aS_to_aA1[i];
				case 2: return this.nbSouth_aS_to_aA2[i];
				case 3: return this.nbSouth_aS_to_aA3[i];
				default: return emptySet;
			}
			
		}
		else return emptySet;
	}
	
	Set<Integer> getNorthNeighboursForStandardToAdapter(int i, int alternative) {	
		
		if (i <= 19) {
			switch (alternative) {
				case 1: return this.nbNorth_aS_to_aA1[i];
				case 2: return this.nbNorth_aS_to_aA2[i];
				case 3: return this.nbNorth_aS_to_aA3[i];
				default: return emptySet;
			}
			
		}
		else return emptySet;
	}
	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForSmallAdapterToSmallSpecialAdapterTile(int i) {
		return emptySet;
		//return nbEast_SA_to_SXA[i];
	}
	Set<Integer> getWestNeighboursForSmallAdapterToSmallSpecialAdapterTile(int i) {
		return emptySet;
		//return nbWest_SA_to_SXA[i];
	}
	Set<Integer> getSouthNeighboursForSmallAdapterToSmallSpecialAdapterTile(int i) {
		return emptySet;
		//return nbSouth_SA_to_SXA[i];
	}
	Set<Integer> getNorthNeighboursForSmallAdapterToSmallSpecialAdapterTile(int i) {
		return emptySet;
		//return nbNorth_SA_to_SXA[i];
	}
	
	////////////////////////////////////////////////////////////////
	
	Set<Integer> getEastNeighboursForSmallSpecialAdapterToSmallAdapterTile(int i) {
		return emptySet;
		//return nbEast_SXA_to_SA[i];
	}
	Set<Integer> getWestNeighboursForSmallSpecialAdapterToSmallAdapterTile(int i) {
		return emptySet;
		//return nbWest_SXA_to_SA[i];
	}
	Set<Integer> getSouthNeighboursForSmallSpecialAdapterToSmallAdapterTile(int i) {
		return emptySet;
		//return nbSouth_SXA_to_SA[i];
	}
	Set<Integer> getNorthNeighboursForSmallSpecialAdapterToSmallAdapterTile(int i) {
		return emptySet;
		//return nbNorth_SXA_to_SA[i];
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	Set<Integer> getReducedTileSet(Set<Integer> a, Set<Integer> b, Set<Integer> c, Set<Integer> d) {

		Set<Integer> result;
		
		result = new HashSet<Integer>(a);
		result.retainAll(b);
		result.retainAll(c);
		result.retainAll(d);
		
		return result;
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void initNeighbourSetsForMediumStandardToMediumStandardTiles() {
		
		Integer nbEast_M0[] = {0, 1, 4, 5, 10, 11, 14};
		Integer nbWest_M0[] = {0, 2, 5, 7, 8, 10, 13};
		Integer nbSouth_M0[] = {0, 1, 2, 3, 12, 13, 14};
		Integer nbNorth_M0[] = {0, 3, 4, 7, 8,  11, 12};

		this.nbEast_MS_to_MS[0] = new HashSet<Integer>(Arrays.asList(nbEast_M0));
		this.nbWest_MS_to_MS[0] = new HashSet<Integer>(Arrays.asList(nbWest_M0));
		this.nbSouth_MS_to_MS[0] = new HashSet<Integer>(Arrays.asList(nbSouth_M0));
		this.nbNorth_MS_to_MS[0] = new HashSet<Integer>(Arrays.asList(nbNorth_M0));

		
		Integer nbEast_M1[] = {2, 3, 6, 7, 17, 18};
		Integer nbSouth_M1[] = {4, 5, 6, 7, 16, 18};
		
		this.nbEast_MS_to_MS[1] = new HashSet<Integer>(Arrays.asList(nbEast_M1));
		this.nbWest_MS_to_MS[1] = this.nbWest_MS_to_MS[0]; // equal sets
		this.nbSouth_MS_to_MS[1] = new HashSet<Integer>(Arrays.asList(nbSouth_M1));
		this.nbNorth_MS_to_MS[1] = this.nbNorth_MS_to_MS[0]; // equal sets
		
		Integer nbWest_M2[] = {1, 3, 9, 11, 17, 18};
		Integer nbSouth_M2[] = {8, 9, 10, 11, 17, 19};
		
		this.nbEast_MS_to_MS[2] = this.nbEast_MS_to_MS[0]; // equal sets
		this.nbWest_MS_to_MS[2] = new HashSet<Integer>(Arrays.asList(nbWest_M2));
		this.nbSouth_MS_to_MS[2] = new HashSet<Integer>(Arrays.asList(nbSouth_M2));
		this.nbNorth_MS_to_MS[2] = this.nbNorth_MS_to_MS[0]; // equal sets
		
		this.nbEast_MS_to_MS[3] = this.nbEast_MS_to_MS[1]; // equal sets
		this.nbWest_MS_to_MS[3] = this.nbWest_MS_to_MS[2]; // equal sets
		this.nbSouth_MS_to_MS[3] = this.nbSouth_MS_to_MS[0]; // equal sets
		this.nbNorth_MS_to_MS[3] = this.nbNorth_MS_to_MS[0]; // equal sets
		
		Integer nbEast_M4[] = {8, 9, 12, 13, 16, 19};
		Integer nbNorth_M4[] = {1, 5, 9, 13, 16, 18};
		
		this.nbEast_MS_to_MS[4] = new HashSet<Integer>(Arrays.asList(nbEast_M4));
		this.nbWest_MS_to_MS[4] = this.nbWest_MS_to_MS[0]; // equal sets
		this.nbSouth_MS_to_MS[4] = this.nbSouth_MS_to_MS[0]; // equal sets
		this.nbNorth_MS_to_MS[4] = new HashSet<Integer>(Arrays.asList(nbNorth_M4));
		
		this.nbEast_MS_to_MS[5] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[5] = this.nbWest_MS_to_MS[0];
		this.nbSouth_MS_to_MS[5] = this.nbSouth_MS_to_MS[1];
		this.nbNorth_MS_to_MS[5] = this.nbNorth_MS_to_MS[4];
		
		this.nbEast_MS_to_MS[6] = this.nbEast_MS_to_MS[4];
		this.nbWest_MS_to_MS[6] = this.nbWest_MS_to_MS[2];
		this.nbSouth_MS_to_MS[6] = this.nbSouth_MS_to_MS[2];
		this.nbNorth_MS_to_MS[6] = this.nbNorth_MS_to_MS[4];
		
		this.nbEast_MS_to_MS[7] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[7] = this.nbWest_MS_to_MS[2];
		this.nbSouth_MS_to_MS[7] = this.nbSouth_MS_to_MS[0];
		this.nbNorth_MS_to_MS[7] = this.nbNorth_MS_to_MS[4];
		
		Integer nbWest_M8[] = {4, 6, 12, 14, 16, 19};
		Integer nbNorth_M8[] = {2, 6, 10, 14, 17, 19};
		
		this.nbEast_MS_to_MS[8] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[8] = new HashSet<Integer>(Arrays.asList(nbWest_M8));
		this.nbSouth_MS_to_MS[8] = this.nbSouth_MS_to_MS[0];
		this.nbNorth_MS_to_MS[8] = new HashSet<Integer>(Arrays.asList(nbNorth_M8));
		
		this.nbEast_MS_to_MS[9] = this.nbEast_MS_to_MS[1];
		this.nbWest_MS_to_MS[9] = this.nbWest_MS_to_MS[8];
		this.nbSouth_MS_to_MS[9] = this.nbSouth_MS_to_MS[1];
		this.nbNorth_MS_to_MS[9] = this.nbNorth_MS_to_MS[8];
		
		this.nbEast_MS_to_MS[10] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[10] = this.nbWest_MS_to_MS[0];
		this.nbSouth_MS_to_MS[10] = this.nbSouth_MS_to_MS[2];
		this.nbNorth_MS_to_MS[10] = this.nbNorth_MS_to_MS[8];
		
		this.nbEast_MS_to_MS[11] = this.nbEast_MS_to_MS[1];
		this.nbWest_MS_to_MS[11] = this.nbWest_MS_to_MS[0];
		this.nbSouth_MS_to_MS[11] = this.nbSouth_MS_to_MS[0];
		this.nbNorth_MS_to_MS[11] = this.nbNorth_MS_to_MS[8];
		
		this.nbEast_MS_to_MS[12] = this.nbEast_MS_to_MS[4];
		this.nbWest_MS_to_MS[12] = this.nbWest_MS_to_MS[8];
		this.nbSouth_MS_to_MS[12] = this.nbSouth_MS_to_MS[0];
		this.nbNorth_MS_to_MS[12] = this.nbNorth_MS_to_MS[0];
		
		this.nbEast_MS_to_MS[13] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[13] = this.nbWest_MS_to_MS[8];
		this.nbSouth_MS_to_MS[13] = this.nbSouth_MS_to_MS[1];
		this.nbNorth_MS_to_MS[13] = this.nbNorth_MS_to_MS[0];
		
		this.nbEast_MS_to_MS[14] = this.nbEast_MS_to_MS[4];
		this.nbWest_MS_to_MS[14] = this.nbWest_MS_to_MS[0];
		this.nbSouth_MS_to_MS[14] = this.nbSouth_MS_to_MS[2];
		this.nbNorth_MS_to_MS[14] = this.nbNorth_MS_to_MS[0];
		
		this.nbEast_MS_to_MS[15] = this.nbEast_MS_to_MS[0];
		this.nbWest_MS_to_MS[15] = this.nbWest_MS_to_MS[0];
		this.nbSouth_MS_to_MS[15] = this.nbSouth_MS_to_MS[0];
		this.nbNorth_MS_to_MS[15] = this.nbNorth_MS_to_MS[0];
		
		this.nbEast_MS_to_MS[16] = this.nbEast_MS_to_MS[4];
		this.nbWest_MS_to_MS[16] = this.nbWest_MS_to_MS[8];
		this.nbSouth_MS_to_MS[16] = this.nbSouth_MS_to_MS[1];
		this.nbNorth_MS_to_MS[16] = this.nbNorth_MS_to_MS[4];
		
		this.nbEast_MS_to_MS[17] = this.nbEast_MS_to_MS[1];
		this.nbWest_MS_to_MS[17] = this.nbWest_MS_to_MS[2];
		this.nbSouth_MS_to_MS[17] = this.nbSouth_MS_to_MS[2];
		this.nbNorth_MS_to_MS[17] = this.nbNorth_MS_to_MS[8];
		
		this.nbEast_MS_to_MS[18] = this.nbEast_MS_to_MS[1];
		this.nbWest_MS_to_MS[18] = this.nbWest_MS_to_MS[2];
		this.nbSouth_MS_to_MS[18] = this.nbSouth_MS_to_MS[1];
		this.nbNorth_MS_to_MS[18] = this.nbNorth_MS_to_MS[4];
		
		this.nbEast_MS_to_MS[19] = this.nbEast_MS_to_MS[4];
		this.nbWest_MS_to_MS[19] = this.nbWest_MS_to_MS[8];
		this.nbSouth_MS_to_MS[19] = this.nbSouth_MS_to_MS[2];
		this.nbNorth_MS_to_MS[19] = this.nbNorth_MS_to_MS[8];
				
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForLargeStandardToLargeStandardTiles() {
		//Integer removeTiles[] = {16, 17, 18 , 19};
		Integer removeTiles[] = {};
		Set<Integer> remove = new HashSet<Integer>(Arrays.asList(removeTiles));
		
		nbEast_LS_to_LS[0] = new  HashSet<Integer>(nbEast_MS_to_MS[0]);
		nbEast_LS_to_LS[0].removeAll(remove) ;
		
		nbWest_LS_to_LS[0] = new  HashSet<Integer>(nbWest_MS_to_MS[0]);
		nbWest_LS_to_LS[0].removeAll(remove) ;
		
		nbSouth_LS_to_LS[0] = new  HashSet<Integer>(nbSouth_MS_to_MS[0]);
		nbSouth_LS_to_LS[0].removeAll(remove) ;
		
		nbNorth_LS_to_LS[0] = new  HashSet<Integer>(nbNorth_MS_to_MS[0]);
		nbNorth_LS_to_LS[0].removeAll(remove) ;
		

		nbEast_LS_to_LS[1] = new  HashSet<Integer>(nbEast_MS_to_MS[1]);
		nbEast_LS_to_LS[1].removeAll(remove) ;
		
		nbWest_LS_to_LS[1] = new  HashSet<Integer>(nbWest_MS_to_MS[1]);
		nbWest_LS_to_LS[1].removeAll(remove) ;
		
		nbSouth_LS_to_LS[1] = new  HashSet<Integer>(nbSouth_MS_to_MS[1]);
		nbSouth_LS_to_LS[1].removeAll(remove) ;
		
		nbNorth_LS_to_LS[1] = new  HashSet<Integer>(nbNorth_MS_to_MS[1]);
		nbNorth_LS_to_LS[1].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[2] = new  HashSet<Integer>(nbEast_MS_to_MS[2]);
		nbEast_LS_to_LS[2].removeAll(remove) ;
		
		nbWest_LS_to_LS[2] = new  HashSet<Integer>(nbWest_MS_to_MS[2]);
		nbWest_LS_to_LS[2].removeAll(remove) ;
		
		nbSouth_LS_to_LS[2] = new  HashSet<Integer>(nbSouth_MS_to_MS[2]);
		nbSouth_LS_to_LS[2].removeAll(remove) ;
		
		nbNorth_LS_to_LS[2] = new  HashSet<Integer>(nbNorth_MS_to_MS[2]);
		nbNorth_LS_to_LS[2].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[3] = new  HashSet<Integer>(nbEast_MS_to_MS[3]);
		nbEast_LS_to_LS[3].removeAll(remove) ;
		
		nbWest_LS_to_LS[3] = new  HashSet<Integer>(nbWest_MS_to_MS[3]);
		nbWest_LS_to_LS[3].removeAll(remove) ;
		
		nbSouth_LS_to_LS[3] = new  HashSet<Integer>(nbSouth_MS_to_MS[3]);
		nbSouth_LS_to_LS[3].removeAll(remove) ;
		
		nbNorth_LS_to_LS[3] = new  HashSet<Integer>(nbNorth_MS_to_MS[3]);
		nbNorth_LS_to_LS[3].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[4] = new  HashSet<Integer>(nbEast_MS_to_MS[4]);
		nbEast_LS_to_LS[4].removeAll(remove) ;
		
		nbWest_LS_to_LS[4] = new  HashSet<Integer>(nbWest_MS_to_MS[4]);
		nbWest_LS_to_LS[4].removeAll(remove) ;

		nbSouth_LS_to_LS[4] = new  HashSet<Integer>(nbSouth_MS_to_MS[4]);
		nbSouth_LS_to_LS[4].removeAll(remove) ;
	
		nbNorth_LS_to_LS[4] = new  HashSet<Integer>(nbNorth_MS_to_MS[4]);
		nbNorth_LS_to_LS[4].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[5] = new  HashSet<Integer>(nbEast_MS_to_MS[5]);
		nbEast_LS_to_LS[5].removeAll(remove) ;
		
		nbWest_LS_to_LS[5] = new  HashSet<Integer>(nbWest_MS_to_MS[5]);
		nbWest_LS_to_LS[5].removeAll(remove) ;
		
		nbSouth_LS_to_LS[5] = new  HashSet<Integer>(nbSouth_MS_to_MS[5]);
		nbSouth_LS_to_LS[5].removeAll(remove) ;
		
		nbNorth_LS_to_LS[5] = new  HashSet<Integer>(nbNorth_MS_to_MS[5]);
		nbNorth_LS_to_LS[5].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[6] = new  HashSet<Integer>(nbEast_MS_to_MS[6]);
		nbEast_LS_to_LS[6].removeAll(remove) ;
		
		nbWest_LS_to_LS[6] = new  HashSet<Integer>(nbWest_MS_to_MS[6]);
		nbWest_LS_to_LS[6].removeAll(remove) ;
			
		nbSouth_LS_to_LS[6] = new  HashSet<Integer>(nbSouth_MS_to_MS[6]);
		nbSouth_LS_to_LS[6].removeAll(remove) ;
		
		nbNorth_LS_to_LS[6] = new  HashSet<Integer>(nbNorth_MS_to_MS[6]);
		nbNorth_LS_to_LS[6].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[7] = new  HashSet<Integer>(nbEast_MS_to_MS[7]);
		nbEast_LS_to_LS[7].removeAll(remove) ;
	
		nbWest_LS_to_LS[7] = new  HashSet<Integer>(nbWest_MS_to_MS[7]);
		nbWest_LS_to_LS[7].removeAll(remove) ;
		
		nbSouth_LS_to_LS[7] = new  HashSet<Integer>(nbSouth_MS_to_MS[7]);
		nbSouth_LS_to_LS[7].removeAll(remove) ;
		
		nbNorth_LS_to_LS[7] = new  HashSet<Integer>(nbNorth_MS_to_MS[7]);
		nbNorth_LS_to_LS[7].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[8] = new  HashSet<Integer>(nbEast_MS_to_MS[8]);
		nbEast_LS_to_LS[8].removeAll(remove) ;
		
		nbWest_LS_to_LS[8] = new  HashSet<Integer>(nbWest_MS_to_MS[8]);
		nbWest_LS_to_LS[8].removeAll(remove) ;
		
		nbSouth_LS_to_LS[8] = new  HashSet<Integer>(nbSouth_MS_to_MS[8]);
		nbSouth_LS_to_LS[8].removeAll(remove) ;
		
		nbNorth_LS_to_LS[8] = new  HashSet<Integer>(nbNorth_MS_to_MS[8]);
		nbNorth_LS_to_LS[8].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[9] = new  HashSet<Integer>(nbEast_MS_to_MS[9]);
		nbEast_LS_to_LS[9].removeAll(remove) ;
		
		nbWest_LS_to_LS[9] = new  HashSet<Integer>(nbWest_MS_to_MS[9]);
		nbWest_LS_to_LS[9].removeAll(remove) ;
		
		nbSouth_LS_to_LS[9] = new  HashSet<Integer>(nbSouth_MS_to_MS[9]);
		nbSouth_LS_to_LS[9].removeAll(remove) ;
		
		nbNorth_LS_to_LS[9] = new  HashSet<Integer>(nbNorth_MS_to_MS[9]);
		nbNorth_LS_to_LS[9].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[10] = new  HashSet<Integer>(nbEast_MS_to_MS[10]);
		nbEast_LS_to_LS[10].removeAll(remove) ;
		
		nbWest_LS_to_LS[10] = new  HashSet<Integer>(nbWest_MS_to_MS[10]);
		nbWest_LS_to_LS[10].removeAll(remove) ;
		
		nbSouth_LS_to_LS[10] = new  HashSet<Integer>(nbSouth_MS_to_MS[10]);
		nbSouth_LS_to_LS[10].removeAll(remove) ;
		
		nbNorth_LS_to_LS[10] = new  HashSet<Integer>(nbNorth_MS_to_MS[10]);
		nbNorth_LS_to_LS[10].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[11] = new  HashSet<Integer>(nbEast_MS_to_MS[11]);
		nbEast_LS_to_LS[11].removeAll(remove) ;
		
		nbWest_LS_to_LS[11] = new  HashSet<Integer>(nbWest_MS_to_MS[11]);
		nbWest_LS_to_LS[11].removeAll(remove) ;
	
		nbSouth_LS_to_LS[11] = new  HashSet<Integer>(nbSouth_MS_to_MS[11]);
		nbSouth_LS_to_LS[11].removeAll(remove) ;
		
		nbNorth_LS_to_LS[11] = new  HashSet<Integer>(nbNorth_MS_to_MS[11]);
		nbNorth_LS_to_LS[11].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[12] = new  HashSet<Integer>(nbEast_MS_to_MS[12]);
		nbEast_LS_to_LS[12].removeAll(remove) ;
		
		nbWest_LS_to_LS[12] = new  HashSet<Integer>(nbWest_MS_to_MS[12]);
		nbWest_LS_to_LS[12].removeAll(remove) ;
		
		nbSouth_LS_to_LS[12] = new  HashSet<Integer>(nbSouth_MS_to_MS[12]);
		nbSouth_LS_to_LS[12].removeAll(remove) ;
	
		nbNorth_LS_to_LS[12] = new  HashSet<Integer>(nbNorth_MS_to_MS[12]);
		nbNorth_LS_to_LS[12].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[13] = new  HashSet<Integer>(nbEast_MS_to_MS[13]);
		nbEast_LS_to_LS[13].removeAll(remove) ;
		
		nbWest_LS_to_LS[13] = new  HashSet<Integer>(nbWest_MS_to_MS[13]);
		nbWest_LS_to_LS[13].removeAll(remove) ;
		
		nbSouth_LS_to_LS[13] = new  HashSet<Integer>(nbSouth_MS_to_MS[13]);
		nbSouth_LS_to_LS[13].removeAll(remove) ;
		
		nbNorth_LS_to_LS[13] = new  HashSet<Integer>(nbNorth_MS_to_MS[13]);
		nbNorth_LS_to_LS[13].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[14] = new  HashSet<Integer>(nbEast_MS_to_MS[14]);
		nbEast_LS_to_LS[14].removeAll(remove) ;
		
		nbWest_LS_to_LS[14] = new  HashSet<Integer>(nbWest_MS_to_MS[14]);
		nbWest_LS_to_LS[14].removeAll(remove) ;
		
		nbSouth_LS_to_LS[14] = new  HashSet<Integer>(nbSouth_MS_to_MS[14]);
		nbSouth_LS_to_LS[14].removeAll(remove) ;
		
		nbNorth_LS_to_LS[14] = new  HashSet<Integer>(nbNorth_MS_to_MS[14]);
		nbNorth_LS_to_LS[14].removeAll(remove) ;
		
		
		nbEast_LS_to_LS[15] = new  HashSet<Integer>(nbEast_MS_to_MS[15]);
		nbEast_LS_to_LS[15].removeAll(remove) ;
		
		nbWest_LS_to_LS[15] = new  HashSet<Integer>(nbWest_MS_to_MS[15]);
		nbWest_LS_to_LS[15].removeAll(remove) ;
		
		nbSouth_LS_to_LS[15] = new  HashSet<Integer>(nbSouth_MS_to_MS[15]);
		nbSouth_LS_to_LS[15].removeAll(remove) ;
		
		nbNorth_LS_to_LS[15] = new  HashSet<Integer>(nbNorth_MS_to_MS[15]);
		nbNorth_LS_to_LS[15].removeAll(remove) ;
		
// we try 16,17,18,19 too	
		
		nbEast_LS_to_LS[16] = new  HashSet<Integer>(nbEast_MS_to_MS[16]);
		nbEast_LS_to_LS[16].removeAll(remove) ;
		
		nbWest_LS_to_LS[16] = new  HashSet<Integer>(nbWest_MS_to_MS[16]);
		nbWest_LS_to_LS[16].removeAll(remove) ;
		
		nbSouth_LS_to_LS[16] = new  HashSet<Integer>(nbSouth_MS_to_MS[16]);
		nbSouth_LS_to_LS[16].removeAll(remove) ;
		
		nbNorth_LS_to_LS[16] = new  HashSet<Integer>(nbNorth_MS_to_MS[16]);
		nbNorth_LS_to_LS[16].removeAll(remove) ;

		
		nbEast_LS_to_LS[17] = new  HashSet<Integer>(nbEast_MS_to_MS[17]);
		nbEast_LS_to_LS[17].removeAll(remove) ;
		
		nbWest_LS_to_LS[17] = new  HashSet<Integer>(nbWest_MS_to_MS[17]);
		nbWest_LS_to_LS[17].removeAll(remove) ;
		
		nbSouth_LS_to_LS[17] = new  HashSet<Integer>(nbSouth_MS_to_MS[17]);
		nbSouth_LS_to_LS[17].removeAll(remove) ;
		
		nbNorth_LS_to_LS[17] = new  HashSet<Integer>(nbNorth_MS_to_MS[17]);
		nbNorth_LS_to_LS[17].removeAll(remove) ;
	
		
		nbEast_LS_to_LS[18] = new  HashSet<Integer>(nbEast_MS_to_MS[18]);
		nbEast_LS_to_LS[18].removeAll(remove) ;
		
		nbWest_LS_to_LS[18] = new  HashSet<Integer>(nbWest_MS_to_MS[18]);
		nbWest_LS_to_LS[18].removeAll(remove) ;
		
		nbSouth_LS_to_LS[18] = new  HashSet<Integer>(nbSouth_MS_to_MS[18]);
		nbSouth_LS_to_LS[18].removeAll(remove) ;
		
		nbNorth_LS_to_LS[18] = new  HashSet<Integer>(nbNorth_MS_to_MS[18]);
		nbNorth_LS_to_LS[18].removeAll(remove) ;
		

		nbEast_LS_to_LS[19] = new  HashSet<Integer>(nbEast_MS_to_MS[19]);
		nbEast_LS_to_LS[19].removeAll(remove) ;
		
		nbWest_LS_to_LS[19] = new  HashSet<Integer>(nbWest_MS_to_MS[19]);
		nbWest_LS_to_LS[19].removeAll(remove) ;
		
		nbSouth_LS_to_LS[19] = new  HashSet<Integer>(nbSouth_MS_to_MS[19]);
		nbSouth_LS_to_LS[19].removeAll(remove) ;
		
		nbNorth_LS_to_LS[19] = new  HashSet<Integer>(nbNorth_MS_to_MS[19]);
		nbNorth_LS_to_LS[19].removeAll(remove) ;
		
		
	}
	
	////////////////////////////////////////////////////////////////

	void initNeighbourSetsForSmallStandardToSmallStandardTiles() {
		//Integer removeTiles[] = {6, 9, 16, 17, 18, 19};
		Integer removeTiles[] = {};

		Set<Integer> remove = new HashSet<Integer>(Arrays.asList(removeTiles));
		
		nbEast_SS_to_SS[0] = new  HashSet<Integer>(nbEast_MS_to_MS[0]);
		nbEast_SS_to_SS[0].removeAll(remove) ;
		
		nbWest_SS_to_SS[0] = new  HashSet<Integer>(nbWest_MS_to_MS[0]);
		nbWest_SS_to_SS[0].removeAll(remove) ;
		
		nbSouth_SS_to_SS[0] = new  HashSet<Integer>(nbSouth_MS_to_MS[0]);
		nbSouth_SS_to_SS[0].removeAll(remove) ;
		
		nbNorth_SS_to_SS[0] = new  HashSet<Integer>(nbNorth_MS_to_MS[0]);
		nbNorth_SS_to_SS[0].removeAll(remove) ;
		

		nbEast_SS_to_SS[1] = new  HashSet<Integer>(nbEast_MS_to_MS[1]);
		nbEast_SS_to_SS[1].removeAll(remove) ;
		
		nbWest_SS_to_SS[1] = new  HashSet<Integer>(nbWest_MS_to_MS[1]);
		nbWest_SS_to_SS[1].removeAll(remove) ;
		
		nbSouth_SS_to_SS[1] = new  HashSet<Integer>(nbSouth_MS_to_MS[1]);
		nbSouth_SS_to_SS[1].removeAll(remove) ;
		
		nbNorth_SS_to_SS[1] = new  HashSet<Integer>(nbNorth_MS_to_MS[1]);
		nbNorth_SS_to_SS[1].removeAll(remove) ;
		
	
		nbEast_SS_to_SS[2] = new  HashSet<Integer>(nbEast_MS_to_MS[2]);
		nbEast_SS_to_SS[2].removeAll(remove) ;
		
		nbWest_SS_to_SS[2] = new  HashSet<Integer>(nbWest_MS_to_MS[2]);
		nbWest_SS_to_SS[2].removeAll(remove) ;
		
		nbSouth_SS_to_SS[2] = new  HashSet<Integer>(nbSouth_MS_to_MS[2]);
		nbSouth_SS_to_SS[2].removeAll(remove) ;
		
		nbNorth_SS_to_SS[2] = new  HashSet<Integer>(nbNorth_MS_to_MS[2]);
		nbNorth_SS_to_SS[2].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[3] = new  HashSet<Integer>(nbEast_MS_to_MS[3]);
		nbEast_SS_to_SS[3].removeAll(remove) ;
		
		nbWest_SS_to_SS[3] = new  HashSet<Integer>(nbWest_MS_to_MS[3]);
		nbWest_SS_to_SS[3].removeAll(remove) ;
		
		nbSouth_SS_to_SS[3] = new  HashSet<Integer>(nbSouth_MS_to_MS[3]);
		nbSouth_SS_to_SS[3].removeAll(remove) ;
		
		nbNorth_SS_to_SS[3] = new  HashSet<Integer>(nbNorth_MS_to_MS[3]);
		nbNorth_SS_to_SS[3].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[4] = new  HashSet<Integer>(nbEast_MS_to_MS[4]);
		nbEast_SS_to_SS[4].removeAll(remove) ;
		
		nbWest_SS_to_SS[4] = new  HashSet<Integer>(nbWest_MS_to_MS[4]);
		nbWest_SS_to_SS[4].removeAll(remove) ;

		nbSouth_SS_to_SS[4] = new  HashSet<Integer>(nbSouth_MS_to_MS[4]);
		nbSouth_SS_to_SS[4].removeAll(remove) ;
	
		nbNorth_SS_to_SS[4] = new  HashSet<Integer>(nbNorth_MS_to_MS[4]);
		nbNorth_SS_to_SS[4].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[5] = new  HashSet<Integer>(nbEast_MS_to_MS[5]);
		nbEast_SS_to_SS[5].removeAll(remove) ;
		
		nbWest_SS_to_SS[5] = new  HashSet<Integer>(nbWest_MS_to_MS[5]);
		nbWest_SS_to_SS[5].removeAll(remove) ;
		
		nbSouth_SS_to_SS[5] = new  HashSet<Integer>(nbSouth_MS_to_MS[5]);
		nbSouth_SS_to_SS[5].removeAll(remove) ;
		
		nbNorth_SS_to_SS[5] = new  HashSet<Integer>(nbNorth_MS_to_MS[5]);
		nbNorth_SS_to_SS[5].removeAll(remove) ;
	
		
		nbEast_SS_to_SS[6] = new  HashSet<Integer>(nbEast_MS_to_MS[6]);
		nbEast_SS_to_SS[6].removeAll(remove) ;
		
		nbWest_SS_to_SS[6] = new  HashSet<Integer>(nbWest_MS_to_MS[6]);
		nbWest_SS_to_SS[6].removeAll(remove) ;
	
		nbSouth_SS_to_SS[6] = new  HashSet<Integer>(nbSouth_MS_to_MS[6]);
		nbSouth_SS_to_SS[6].removeAll(remove) ;
		
		nbNorth_SS_to_SS[6] = new  HashSet<Integer>(nbNorth_MS_to_MS[6]);
		nbNorth_SS_to_SS[6].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[7] = new  HashSet<Integer>(nbEast_MS_to_MS[7]);
		nbEast_SS_to_SS[7].removeAll(remove) ;
	
		nbWest_SS_to_SS[7] = new  HashSet<Integer>(nbWest_MS_to_MS[7]);
		nbWest_SS_to_SS[7].removeAll(remove) ;
		
		nbSouth_SS_to_SS[7] = new  HashSet<Integer>(nbSouth_MS_to_MS[7]);
		nbSouth_SS_to_SS[7].removeAll(remove) ;
		
		nbNorth_SS_to_SS[7] = new  HashSet<Integer>(nbNorth_MS_to_MS[7]);
		nbNorth_SS_to_SS[7].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[8] = new  HashSet<Integer>(nbEast_MS_to_MS[8]);
		nbEast_SS_to_SS[8].removeAll(remove) ;
		
		nbWest_SS_to_SS[8] = new  HashSet<Integer>(nbWest_MS_to_MS[8]);
		nbWest_SS_to_SS[8].removeAll(remove) ;
		
		nbSouth_SS_to_SS[8] = new  HashSet<Integer>(nbSouth_MS_to_MS[8]);
		nbSouth_SS_to_SS[8].removeAll(remove) ;
		
		nbNorth_SS_to_SS[8] = new  HashSet<Integer>(nbNorth_MS_to_MS[8]);
		nbNorth_SS_to_SS[8].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[9] = new  HashSet<Integer>(nbEast_MS_to_MS[9]);
		nbEast_SS_to_SS[9].removeAll(remove) ;
		
		nbWest_SS_to_SS[9] = new  HashSet<Integer>(nbWest_MS_to_MS[9]);
		nbWest_SS_to_SS[9].removeAll(remove) ;
		
		nbSouth_SS_to_SS[9] = new  HashSet<Integer>(nbSouth_MS_to_MS[9]);
		nbSouth_SS_to_SS[9].removeAll(remove) ;
		
		nbNorth_SS_to_SS[9] = new  HashSet<Integer>(nbNorth_MS_to_MS[9]);
		nbNorth_SS_to_SS[9].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[10] = new  HashSet<Integer>(nbEast_MS_to_MS[10]);
		nbEast_SS_to_SS[10].removeAll(remove) ;
		
		nbWest_SS_to_SS[10] = new  HashSet<Integer>(nbWest_MS_to_MS[10]);
		nbWest_SS_to_SS[10].removeAll(remove) ;
		
		nbSouth_SS_to_SS[10] = new  HashSet<Integer>(nbSouth_MS_to_MS[10]);
		nbSouth_SS_to_SS[10].removeAll(remove) ;
		
		nbNorth_SS_to_SS[10] = new  HashSet<Integer>(nbNorth_MS_to_MS[10]);
		nbNorth_SS_to_SS[10].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[11] = new  HashSet<Integer>(nbEast_MS_to_MS[11]);
		nbEast_SS_to_SS[11].removeAll(remove) ;
		
		nbWest_SS_to_SS[11] = new  HashSet<Integer>(nbWest_MS_to_MS[11]);
		nbWest_SS_to_SS[11].removeAll(remove) ;
	
		nbSouth_SS_to_SS[11] = new  HashSet<Integer>(nbSouth_MS_to_MS[11]);
		nbSouth_SS_to_SS[11].removeAll(remove) ;
		
		nbNorth_SS_to_SS[11] = new  HashSet<Integer>(nbNorth_MS_to_MS[11]);
		nbNorth_SS_to_SS[11].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[12] = new  HashSet<Integer>(nbEast_MS_to_MS[12]);
		nbEast_SS_to_SS[12].removeAll(remove) ;
		
		nbWest_SS_to_SS[12] = new  HashSet<Integer>(nbWest_MS_to_MS[12]);
		nbWest_SS_to_SS[12].removeAll(remove) ;
		
		nbSouth_SS_to_SS[12] = new  HashSet<Integer>(nbSouth_MS_to_MS[12]);
		nbSouth_SS_to_SS[12].removeAll(remove) ;
	
		nbNorth_SS_to_SS[12] = new  HashSet<Integer>(nbNorth_MS_to_MS[12]);
		nbNorth_SS_to_SS[12].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[13] = new  HashSet<Integer>(nbEast_MS_to_MS[13]);
		nbEast_SS_to_SS[13].removeAll(remove) ;
		
		nbWest_SS_to_SS[13] = new  HashSet<Integer>(nbWest_MS_to_MS[13]);
		nbWest_SS_to_SS[13].removeAll(remove) ;
		
		nbSouth_SS_to_SS[13] = new  HashSet<Integer>(nbSouth_MS_to_MS[13]);
		nbSouth_SS_to_SS[13].removeAll(remove) ;
		
		nbNorth_SS_to_SS[13] = new  HashSet<Integer>(nbNorth_MS_to_MS[13]);
		nbNorth_SS_to_SS[13].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[14] = new  HashSet<Integer>(nbEast_MS_to_MS[14]);
		nbEast_SS_to_SS[14].removeAll(remove) ;
		
		nbWest_SS_to_SS[14] = new  HashSet<Integer>(nbWest_MS_to_MS[14]);
		nbWest_SS_to_SS[14].removeAll(remove) ;
		
		nbSouth_SS_to_SS[14] = new  HashSet<Integer>(nbSouth_MS_to_MS[14]);
		nbSouth_SS_to_SS[14].removeAll(remove) ;
		
		nbNorth_SS_to_SS[14] = new  HashSet<Integer>(nbNorth_MS_to_MS[14]);
		nbNorth_SS_to_SS[14].removeAll(remove) ;
		
		
		nbEast_SS_to_SS[15] = new  HashSet<Integer>(nbEast_MS_to_MS[15]);
		nbEast_SS_to_SS[15].removeAll(remove) ;
		
		nbWest_SS_to_SS[15] = new  HashSet<Integer>(nbWest_MS_to_MS[15]);
		nbWest_SS_to_SS[15].removeAll(remove) ;
		
		nbSouth_SS_to_SS[15] = new  HashSet<Integer>(nbSouth_MS_to_MS[15]);
		nbSouth_SS_to_SS[15].removeAll(remove) ;
		
		nbNorth_SS_to_SS[15] = new  HashSet<Integer>(nbNorth_MS_to_MS[15]);
		nbNorth_SS_to_SS[15].removeAll(remove) ;

	
		// we try 16,17,18,19 too	
		
		nbEast_SS_to_SS[16] = new  HashSet<Integer>(nbEast_MS_to_MS[16]);
		nbEast_SS_to_SS[16].removeAll(remove) ;
		
		nbWest_SS_to_SS[16] = new  HashSet<Integer>(nbWest_MS_to_MS[16]);
		nbWest_SS_to_SS[16].removeAll(remove) ;
		
		nbSouth_SS_to_SS[16] = new  HashSet<Integer>(nbSouth_MS_to_MS[16]);
		nbSouth_SS_to_SS[16].removeAll(remove) ;
		
		nbNorth_SS_to_SS[16] = new  HashSet<Integer>(nbNorth_MS_to_MS[16]);
		nbNorth_SS_to_SS[16].removeAll(remove) ;

		
		nbEast_SS_to_SS[17] = new  HashSet<Integer>(nbEast_MS_to_MS[17]);
		nbEast_SS_to_SS[17].removeAll(remove) ;
		
		nbWest_SS_to_SS[17] = new  HashSet<Integer>(nbWest_MS_to_MS[17]);
		nbWest_SS_to_SS[17].removeAll(remove) ;
		
		nbSouth_SS_to_SS[17] = new  HashSet<Integer>(nbSouth_MS_to_MS[17]);
		nbSouth_SS_to_SS[17].removeAll(remove) ;
		
		nbNorth_SS_to_SS[17] = new  HashSet<Integer>(nbNorth_MS_to_MS[17]);
		nbNorth_SS_to_SS[17].removeAll(remove) ;
	
		
		nbEast_SS_to_SS[18] = new  HashSet<Integer>(nbEast_MS_to_MS[18]);
		nbEast_SS_to_SS[18].removeAll(remove) ;
		
		nbWest_SS_to_SS[18] = new  HashSet<Integer>(nbWest_MS_to_MS[18]);
		nbWest_SS_to_SS[18].removeAll(remove) ;
		
		nbSouth_SS_to_SS[18] = new  HashSet<Integer>(nbSouth_MS_to_MS[18]);
		nbSouth_SS_to_SS[18].removeAll(remove) ;
		
		nbNorth_SS_to_SS[18] = new  HashSet<Integer>(nbNorth_MS_to_MS[18]);
		nbNorth_SS_to_SS[18].removeAll(remove) ;
		

		nbEast_SS_to_SS[19] = new  HashSet<Integer>(nbEast_MS_to_MS[19]);
		nbEast_SS_to_SS[19].removeAll(remove) ;
		
		nbWest_SS_to_SS[19] = new  HashSet<Integer>(nbWest_MS_to_MS[19]);
		nbWest_SS_to_SS[19].removeAll(remove) ;
		
		nbSouth_SS_to_SS[19] = new  HashSet<Integer>(nbSouth_MS_to_MS[19]);
		nbSouth_SS_to_SS[19].removeAll(remove) ;
		
		nbNorth_SS_to_SS[19] = new  HashSet<Integer>(nbNorth_MS_to_MS[19]);
		nbNorth_SS_to_SS[19].removeAll(remove) ;
		
	
	}

	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForAdapterToAdapter() {

		//0 nicht belegt, weil Adapter Kacheln ab 1 nummeriert sind
		this.nbEast_A_to_A[0] = this.emptySet;
		this.nbWest_A_to_A[0] = this.emptySet;
		this.nbSouth_A_to_A[0] = this.emptySet;
		this.nbNorth_A_to_A[0] = this.emptySet;
		
		Integer nbEast_A1[] = {2, 31, 41};
		Integer nbWest_A1[] = {21,22,23,24,25,26,27,28,29,31,41,42,43,44,45,46,47,48,49,71};
		Integer nbSouth_A1[] = {11,21,61,62,63,64,65,66,67,68,69,71,72,73,74,75,76,77,78,79};
		Integer nbNorth_A1[] = {11,52,61};

		this.nbEast_A_to_A[1] = new HashSet<Integer>(Arrays.asList(nbEast_A1));
		this.nbWest_A_to_A[1] = new HashSet<Integer>(Arrays.asList(nbWest_A1));
		this.nbSouth_A_to_A[1] = new HashSet<Integer>(Arrays.asList(nbSouth_A1));
		this.nbNorth_A_to_A[1] = new HashSet<Integer>(Arrays.asList(nbNorth_A1));

		Integer nbEast_A2[] = {3, 32};
		Integer nbWest_A2[] = {1,32,51};
		Integer nbNorth_A2[] = {62};

		this.nbEast_A_to_A[2] = new HashSet<Integer>(Arrays.asList(nbEast_A2));
		this.nbWest_A_to_A[2] = new HashSet<Integer>(Arrays.asList(nbWest_A2));
		this.nbSouth_A_to_A[2] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[2] = new HashSet<Integer>(Arrays.asList(nbNorth_A2));

		Integer nbEast_A3[] = {4, 33};
		Integer nbWest_A3[] = {2,33};
		Integer nbNorth_A3[] = {63};

		this.nbEast_A_to_A[3] = new HashSet<Integer>(Arrays.asList(nbEast_A3));
		this.nbWest_A_to_A[3] = new HashSet<Integer>(Arrays.asList(nbWest_A3));
		this.nbSouth_A_to_A[3] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[3] = new HashSet<Integer>(Arrays.asList(nbNorth_A3));

		Integer nbEast_A4[] = {5, 34};
		Integer nbWest_A4[] = {3,34};
		Integer nbNorth_A4[] = {64};

		this.nbEast_A_to_A[4] = new HashSet<Integer>(Arrays.asList(nbEast_A4));
		this.nbWest_A_to_A[4] = new HashSet<Integer>(Arrays.asList(nbWest_A4));
		this.nbSouth_A_to_A[4] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[4] = new HashSet<Integer>(Arrays.asList(nbNorth_A4));

		Integer nbEast_A5[] = {6, 35};
		Integer nbWest_A5[] = {4,35};
		Integer nbNorth_A5[] = {65};

		this.nbEast_A_to_A[5] = new HashSet<Integer>(Arrays.asList(nbEast_A5));
		this.nbWest_A_to_A[5] = new HashSet<Integer>(Arrays.asList(nbWest_A5));
		this.nbSouth_A_to_A[5] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[5] = new HashSet<Integer>(Arrays.asList(nbNorth_A5));
	
		Integer nbEast_A6[] = {7, 36};
		Integer nbWest_A6[] = {5,36};
		Integer nbNorth_A6[] = {66};

		this.nbEast_A_to_A[6] = new HashSet<Integer>(Arrays.asList(nbEast_A6));
		this.nbWest_A_to_A[6] = new HashSet<Integer>(Arrays.asList(nbWest_A6));
		this.nbSouth_A_to_A[6] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[6] = new HashSet<Integer>(Arrays.asList(nbNorth_A6));
	
		Integer nbEast_A7[] = {8, 37};
		Integer nbWest_A7[] = {6,37};
		Integer nbNorth_A7[] = {67};

		this.nbEast_A_to_A[7] = new HashSet<Integer>(Arrays.asList(nbEast_A7));
		this.nbWest_A_to_A[7] = new HashSet<Integer>(Arrays.asList(nbWest_A7));
		this.nbSouth_A_to_A[7] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[7] = new HashSet<Integer>(Arrays.asList(nbNorth_A7));
	
		Integer nbEast_A8[] = {9, 38};
		Integer nbWest_A8[] = {7,38};
		Integer nbNorth_A8[] = {68};

		this.nbEast_A_to_A[8] = new HashSet<Integer>(Arrays.asList(nbEast_A8));
		this.nbWest_A_to_A[8] = new HashSet<Integer>(Arrays.asList(nbWest_A8));
		this.nbSouth_A_to_A[8] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[8] = new HashSet<Integer>(Arrays.asList(nbNorth_A8));

		Integer nbEast_A9[] = {39};
		Integer nbWest_A9[] = {8,39};
		Integer nbNorth_A9[] = {69};

		this.nbEast_A_to_A[9] = new HashSet<Integer>(Arrays.asList(nbEast_A9));
		this.nbWest_A_to_A[9] = new HashSet<Integer>(Arrays.asList(nbWest_A9));
		this.nbSouth_A_to_A[9] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[9] = new HashSet<Integer>(Arrays.asList(nbNorth_A9));
		
		///
		
		this.nbEast_A_to_A[10] = this.emptySet;
		this.nbWest_A_to_A[10] = this.emptySet;
		this.nbSouth_A_to_A[10] = this.emptySet;
		this.nbNorth_A_to_A[10] = this.emptySet;
		
		Integer nbEast_A11[] = {21, 62, 71};
		Integer nbSouth_A11[] = {1, 12, 51};
		Integer nbNorth_A11[] = {1,2,3,4,5,6,7,8,9,31,32,33,34,35,36,37,38,39,41,51};

		this.nbEast_A_to_A[11] = new HashSet<Integer>(Arrays.asList(nbEast_A11));
		this.nbWest_A_to_A[11] = this.nbWest_A_to_A[1];
		this.nbSouth_A_to_A[11] = new HashSet<Integer>(Arrays.asList(nbSouth_A11));
		this.nbNorth_A_to_A[11] = new HashSet<Integer>(Arrays.asList(nbNorth_A11));
		
		Integer nbEast_A12[] = {22};
		Integer nbSouth_A12[] = {13, 52};
		Integer nbNorth_A12[] = {11,52,61};

		this.nbEast_A_to_A[12] = new HashSet<Integer>(Arrays.asList(nbEast_A12));
		this.nbWest_A_to_A[12] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[12] = new HashSet<Integer>(Arrays.asList(nbSouth_A12));
		this.nbNorth_A_to_A[12] = new HashSet<Integer>(Arrays.asList(nbNorth_A12));

		Integer nbEast_A13[] = {23};
		Integer nbSouth_A13[] = {14, 53};
		Integer nbNorth_A13[] = {12,53};

		this.nbEast_A_to_A[13] = new HashSet<Integer>(Arrays.asList(nbEast_A13));
		this.nbWest_A_to_A[13] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[13] = new HashSet<Integer>(Arrays.asList(nbSouth_A13));
		this.nbNorth_A_to_A[13] = new HashSet<Integer>(Arrays.asList(nbNorth_A13));

		Integer nbEast_A14[] = {24};
		Integer nbSouth_A14[] = {14, 54};
		Integer nbNorth_A14[] = {13,54};

		this.nbEast_A_to_A[14] = new HashSet<Integer>(Arrays.asList(nbEast_A14));
		this.nbWest_A_to_A[14] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[14] = new HashSet<Integer>(Arrays.asList(nbSouth_A14));
		this.nbNorth_A_to_A[14] = new HashSet<Integer>(Arrays.asList(nbNorth_A14));

		Integer nbEast_A15[] = {25};
		Integer nbSouth_A15[] = {15, 55};
		Integer nbNorth_A15[] = {14,55};

		this.nbEast_A_to_A[15] = new HashSet<Integer>(Arrays.asList(nbEast_A15));
		this.nbWest_A_to_A[15] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[15] = new HashSet<Integer>(Arrays.asList(nbSouth_A15));
		this.nbNorth_A_to_A[15] = new HashSet<Integer>(Arrays.asList(nbNorth_A15));

		Integer nbEast_A16[] = {26};
		Integer nbSouth_A16[] = {16, 56};
		Integer nbNorth_A16[] = {15,56};

		this.nbEast_A_to_A[16] = new HashSet<Integer>(Arrays.asList(nbEast_A16));
		this.nbWest_A_to_A[16] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[16] = new HashSet<Integer>(Arrays.asList(nbSouth_A16));
		this.nbNorth_A_to_A[16] = new HashSet<Integer>(Arrays.asList(nbNorth_A16));
		
		Integer nbEast_A17[] = {27};
		Integer nbSouth_A17[] = {17, 57};
		Integer nbNorth_A17[] = {16,57};

		this.nbEast_A_to_A[17] = new HashSet<Integer>(Arrays.asList(nbEast_A17));
		this.nbWest_A_to_A[17] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[17] = new HashSet<Integer>(Arrays.asList(nbSouth_A17));
		this.nbNorth_A_to_A[17] = new HashSet<Integer>(Arrays.asList(nbNorth_A17));

		Integer nbEast_A18[] = {28};
		Integer nbSouth_A18[] = {18, 58};
		Integer nbNorth_A18[] = {17,58};

		this.nbEast_A_to_A[18] = new HashSet<Integer>(Arrays.asList(nbEast_A18));
		this.nbWest_A_to_A[18] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[18] = new HashSet<Integer>(Arrays.asList(nbSouth_A18));
		this.nbNorth_A_to_A[18] = new HashSet<Integer>(Arrays.asList(nbNorth_A18));

		Integer nbEast_A19[] = {29};
		Integer nbSouth_A19[] = {59};
		Integer nbNorth_A19[] = {18,59};

		this.nbEast_A_to_A[19] = new HashSet<Integer>(Arrays.asList(nbEast_A19));
		this.nbWest_A_to_A[19] = this.nbWest_A_to_A[11];
		this.nbSouth_A_to_A[19] = new HashSet<Integer>(Arrays.asList(nbSouth_A19));
		this.nbNorth_A_to_A[19] = new HashSet<Integer>(Arrays.asList(nbNorth_A19));
	
		Integer nbEast_A21[] = {1,11,12,13,14,15,16,17,18,19,51,52,53,54,55,56,57,58,59, 61};
		Integer nbWest_A21[] = {11,61,72};
		Integer nbSouth_A21[] = {22,31,41};

		this.nbEast_A_to_A[21] = new HashSet<Integer>(Arrays.asList(nbEast_A21));
		this.nbWest_A_to_A[21] = new HashSet<Integer>(Arrays.asList(nbWest_A21));
		this.nbSouth_A_to_A[21] = new HashSet<Integer>(Arrays.asList(nbSouth_A21));
		this.nbNorth_A_to_A[21] = this.nbNorth_A_to_A[11];
		
		Integer nbWest_A22[] = {12};
		Integer nbSouth_A22[] = {23,42};
		Integer nbNorth_A22[] = {21,42,71};

		this.nbEast_A_to_A[22] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[22] = new HashSet<Integer>(Arrays.asList(nbWest_A22));
		this.nbSouth_A_to_A[22] = new HashSet<Integer>(Arrays.asList(nbSouth_A22));
		this.nbNorth_A_to_A[22] = new HashSet<Integer>(Arrays.asList(nbNorth_A22));

		Integer nbWest_A23[] = {13};
		Integer nbSouth_A23[] = {24,43};
		Integer nbNorth_A23[] = {22,43};

		this.nbEast_A_to_A[23] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[23] = new HashSet<Integer>(Arrays.asList(nbWest_A23));
		this.nbSouth_A_to_A[23] = new HashSet<Integer>(Arrays.asList(nbSouth_A23));
		this.nbNorth_A_to_A[23] = new HashSet<Integer>(Arrays.asList(nbNorth_A23));

		Integer nbWest_A24[] = {14};
		Integer nbSouth_A24[] = {25,44};
		Integer nbNorth_A24[] = {23,44};

		this.nbEast_A_to_A[24] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[24] = new HashSet<Integer>(Arrays.asList(nbWest_A24));
		this.nbSouth_A_to_A[24] = new HashSet<Integer>(Arrays.asList(nbSouth_A24));
		this.nbNorth_A_to_A[24] = new HashSet<Integer>(Arrays.asList(nbNorth_A24));

		Integer nbWest_A25[] = {15};
		Integer nbSouth_A25[] = {26,45};
		Integer nbNorth_A25[] = {24,45};

		this.nbEast_A_to_A[25] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[25] = new HashSet<Integer>(Arrays.asList(nbWest_A25));
		this.nbSouth_A_to_A[25] = new HashSet<Integer>(Arrays.asList(nbSouth_A25));
		this.nbNorth_A_to_A[25] = new HashSet<Integer>(Arrays.asList(nbNorth_A25));

		Integer nbWest_A26[] = {16};
		Integer nbSouth_A26[] = {27,46};
		Integer nbNorth_A26[] = {25,46};

		this.nbEast_A_to_A[26] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[26] = new HashSet<Integer>(Arrays.asList(nbWest_A26));
		this.nbSouth_A_to_A[26] = new HashSet<Integer>(Arrays.asList(nbSouth_A26));
		this.nbNorth_A_to_A[26] = new HashSet<Integer>(Arrays.asList(nbNorth_A26));
		
		Integer nbWest_A27[] = {17};
		Integer nbSouth_A27[] = {28,47};
		Integer nbNorth_A27[] = {26,47};

		this.nbEast_A_to_A[27] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[27] = new HashSet<Integer>(Arrays.asList(nbWest_A27));
		this.nbSouth_A_to_A[27] = new HashSet<Integer>(Arrays.asList(nbSouth_A27));
		this.nbNorth_A_to_A[27] = new HashSet<Integer>(Arrays.asList(nbNorth_A27));

		Integer nbWest_A28[] = {18};
		Integer nbSouth_A28[] = {29,48};
		Integer nbNorth_A28[] = {27,48};

		this.nbEast_A_to_A[28] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[28] = new HashSet<Integer>(Arrays.asList(nbWest_A28));
		this.nbSouth_A_to_A[28] = new HashSet<Integer>(Arrays.asList(nbSouth_A28));
		this.nbNorth_A_to_A[28] = new HashSet<Integer>(Arrays.asList(nbNorth_A28));

		Integer nbWest_A29[] = {19};
		Integer nbSouth_A29[] = {49};
		Integer nbNorth_A29[] = {28,49};

		this.nbEast_A_to_A[29] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[29] = new HashSet<Integer>(Arrays.asList(nbWest_A29));
		this.nbSouth_A_to_A[29] = new HashSet<Integer>(Arrays.asList(nbSouth_A29));
		this.nbNorth_A_to_A[29] = new HashSet<Integer>(Arrays.asList(nbNorth_A29));

		Integer nbWest_A31[] = {1,51};
		Integer nbNorth_A31[] = {21,42,71};

		this.nbEast_A_to_A[31] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[31] = new HashSet<Integer>(Arrays.asList(nbWest_A31));
		this.nbSouth_A_to_A[31] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[31] = new HashSet<Integer>(Arrays.asList(nbNorth_A31));
		
		Integer nbEast_A32[] = {2,31,41};
		Integer nbWest_A32[] = {2,33};
		Integer nbNorth_A32[] = {72};

		this.nbEast_A_to_A[32] = new HashSet<Integer>(Arrays.asList(nbEast_A32));
		this.nbWest_A_to_A[32] = new HashSet<Integer>(Arrays.asList(nbWest_A32));
		this.nbSouth_A_to_A[32] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[32] = new HashSet<Integer>(Arrays.asList(nbNorth_A32));

		Integer nbEast_A33[] = {3,32};
		Integer nbWest_A33[] = {3,34};
		Integer nbNorth_A33[] = {73};

		this.nbEast_A_to_A[33] = new HashSet<Integer>(Arrays.asList(nbEast_A33));
		this.nbWest_A_to_A[33] = new HashSet<Integer>(Arrays.asList(nbWest_A33));
		this.nbSouth_A_to_A[33] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[33] = new HashSet<Integer>(Arrays.asList(nbNorth_A33));
		
		Integer nbEast_A34[] = {4,33};
		Integer nbWest_A34[] = {4,35};
		Integer nbNorth_A34[] = {74};

		this.nbEast_A_to_A[34] = new HashSet<Integer>(Arrays.asList(nbEast_A34));
		this.nbWest_A_to_A[34] = new HashSet<Integer>(Arrays.asList(nbWest_A34));
		this.nbSouth_A_to_A[34] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[34] = new HashSet<Integer>(Arrays.asList(nbNorth_A34));

		Integer nbEast_A35[] = {5,34};
		Integer nbWest_A35[] = {5,36};
		Integer nbNorth_A35[] = {75};

		this.nbEast_A_to_A[35] = new HashSet<Integer>(Arrays.asList(nbEast_A35));
		this.nbWest_A_to_A[35] = new HashSet<Integer>(Arrays.asList(nbWest_A35));
		this.nbSouth_A_to_A[35] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[35] = new HashSet<Integer>(Arrays.asList(nbNorth_A35));

		Integer nbEast_A36[] = {6,35};
		Integer nbWest_A36[] = {6,37};
		Integer nbNorth_A36[] = {76};

		this.nbEast_A_to_A[36] = new HashSet<Integer>(Arrays.asList(nbEast_A36));
		this.nbWest_A_to_A[36] = new HashSet<Integer>(Arrays.asList(nbWest_A36));
		this.nbSouth_A_to_A[36] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[36] = new HashSet<Integer>(Arrays.asList(nbNorth_A36));
		
		Integer nbEast_A37[] = {7,36};
		Integer nbWest_A37[] = {7,38};
		Integer nbNorth_A37[] = {77};
		
		this.nbEast_A_to_A[37] = new HashSet<Integer>(Arrays.asList(nbEast_A37));
		this.nbWest_A_to_A[37] = new HashSet<Integer>(Arrays.asList(nbWest_A37));
		this.nbSouth_A_to_A[37] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[37] = new HashSet<Integer>(Arrays.asList(nbNorth_A37));

		Integer nbEast_A38[] = {8,37};
		Integer nbWest_A38[] = {8,39};
		Integer nbNorth_A38[] = {78};

		this.nbEast_A_to_A[38] = new HashSet<Integer>(Arrays.asList(nbEast_A38));
		this.nbWest_A_to_A[38] = new HashSet<Integer>(Arrays.asList(nbWest_A38));
		this.nbSouth_A_to_A[38] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[38] = new HashSet<Integer>(Arrays.asList(nbNorth_A38));

		Integer nbEast_A39[] = {9,38};
		Integer nbWest_A39[] = {9};
		Integer nbNorth_A39[] = {79};

		this.nbEast_A_to_A[39] = new HashSet<Integer>(Arrays.asList(nbEast_A39));
		this.nbWest_A_to_A[39] = new HashSet<Integer>(Arrays.asList(nbWest_A39));
		this.nbSouth_A_to_A[39] = this.nbSouth_A_to_A[31];
		this.nbNorth_A_to_A[39] = new HashSet<Integer>(Arrays.asList(nbNorth_A39));
		
		Integer nbWest_A41[] = {1,32,51};

		this.nbEast_A_to_A[41] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[41] = new HashSet<Integer>(Arrays.asList(nbWest_A41));
		this.nbSouth_A_to_A[41] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[41] = this.nbNorth_A_to_A[31];
		
		Integer nbWest_A42[] = {52};
		Integer nbSouth_A42[] = {22,31,41};
		Integer nbNorth_A42[] = {22,43};

		this.nbEast_A_to_A[42] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[42] = new HashSet<Integer>(Arrays.asList(nbWest_A42));
		this.nbSouth_A_to_A[42] = new HashSet<Integer>(Arrays.asList(nbSouth_A42));
		this.nbNorth_A_to_A[42] = new HashSet<Integer>(Arrays.asList(nbNorth_A42));

		Integer nbWest_A43[] = {53};
		Integer nbSouth_A43[] = {23,42};
		Integer nbNorth_A43[] = {23,44};

		this.nbEast_A_to_A[43] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[43] = new HashSet<Integer>(Arrays.asList(nbWest_A43));
		this.nbSouth_A_to_A[43] = new HashSet<Integer>(Arrays.asList(nbSouth_A43));
		this.nbNorth_A_to_A[43] = new HashSet<Integer>(Arrays.asList(nbNorth_A43));
		
		Integer nbWest_A44[] = {54};
		Integer nbSouth_A44[] = {24,43};
		Integer nbNorth_A44[] = {24,45};

		this.nbEast_A_to_A[44] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[44] = new HashSet<Integer>(Arrays.asList(nbWest_A44));
		this.nbSouth_A_to_A[44] = new HashSet<Integer>(Arrays.asList(nbSouth_A44));
		this.nbNorth_A_to_A[44] = new HashSet<Integer>(Arrays.asList(nbNorth_A44));

		Integer nbWest_A45[] = {55};
		Integer nbSouth_A45[] = {25,44};
		Integer nbNorth_A45[] = {25,46};

		this.nbEast_A_to_A[45] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[45] = new HashSet<Integer>(Arrays.asList(nbWest_A45));
		this.nbSouth_A_to_A[45] = new HashSet<Integer>(Arrays.asList(nbSouth_A45));
		this.nbNorth_A_to_A[45] = new HashSet<Integer>(Arrays.asList(nbNorth_A45));

		Integer nbWest_A46[] = {56};
		Integer nbSouth_A46[] = {26,45};
		Integer nbNorth_A46[] = {26,47};

		this.nbEast_A_to_A[46] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[46] = new HashSet<Integer>(Arrays.asList(nbWest_A46));
		this.nbSouth_A_to_A[46] = new HashSet<Integer>(Arrays.asList(nbSouth_A46));
		this.nbNorth_A_to_A[46] = new HashSet<Integer>(Arrays.asList(nbNorth_A46));
		
		Integer nbWest_A47[] = {57};
		Integer nbSouth_A47[] = {27,46};
		Integer nbNorth_A47[] = {27,48};
		
		this.nbEast_A_to_A[47] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[47] = new HashSet<Integer>(Arrays.asList(nbWest_A47));
		this.nbSouth_A_to_A[47] = new HashSet<Integer>(Arrays.asList(nbSouth_A47));
		this.nbNorth_A_to_A[47] = new HashSet<Integer>(Arrays.asList(nbNorth_A47));

		Integer nbWest_A48[] = {58};
		Integer nbSouth_A48[] = {28,47};
		Integer nbNorth_A48[] = {28,49};

		this.nbEast_A_to_A[48] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[48] = new HashSet<Integer>(Arrays.asList(nbWest_A48));
		this.nbSouth_A_to_A[48] = new HashSet<Integer>(Arrays.asList(nbSouth_A48));
		this.nbNorth_A_to_A[48] = new HashSet<Integer>(Arrays.asList(nbNorth_A48));

		Integer nbWest_A49[] = {59};
		Integer nbSouth_A49[] = {29,48};
		Integer nbNorth_A49[] = {29};

		this.nbEast_A_to_A[49] = this.nbEast_A_to_A[41];
		this.nbWest_A_to_A[49] = new HashSet<Integer>(Arrays.asList(nbWest_A49));
		this.nbSouth_A_to_A[49] = new HashSet<Integer>(Arrays.asList(nbSouth_A49));
		this.nbNorth_A_to_A[49] = new HashSet<Integer>(Arrays.asList(nbNorth_A49));
		
		Integer nbEast_A51[] = {2,31,41};
		Integer nbNorth_A51[] = {11,52,61};

		this.nbEast_A_to_A[51] = new HashSet<Integer>(Arrays.asList(nbEast_A51));
		this.nbWest_A_to_A[51] = this.nbWest_A_to_A[1];
		this.nbSouth_A_to_A[51] = this.nbSouth_A_to_A[1];
		this.nbNorth_A_to_A[51] = new HashSet<Integer>(Arrays.asList(nbNorth_A51));
		
		Integer nbEast_A52[] = {42};
		Integer nbSouth_A52[] = {1,12,51};
		Integer nbNorth_A52[] = {12,53};

		this.nbEast_A_to_A[52] = new HashSet<Integer>(Arrays.asList(nbEast_A52));
		this.nbWest_A_to_A[52] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[52] = new HashSet<Integer>(Arrays.asList(nbSouth_A52));
		this.nbNorth_A_to_A[52] = new HashSet<Integer>(Arrays.asList(nbNorth_A52));

		Integer nbEast_A53[] = {43};
		Integer nbSouth_A53[] = {13,52};
		Integer nbNorth_A53[] = {13,54};

		this.nbEast_A_to_A[53] = new HashSet<Integer>(Arrays.asList(nbEast_A53));
		this.nbWest_A_to_A[53] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[53] = new HashSet<Integer>(Arrays.asList(nbSouth_A53));
		this.nbNorth_A_to_A[53] = new HashSet<Integer>(Arrays.asList(nbNorth_A53));
		
		Integer nbEast_A54[] = {44};
		Integer nbSouth_A54[] = {14,53};
		Integer nbNorth_A54[] = {14,55};

		this.nbEast_A_to_A[54] = new HashSet<Integer>(Arrays.asList(nbEast_A54));
		this.nbWest_A_to_A[54] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[54] = new HashSet<Integer>(Arrays.asList(nbSouth_A54));
		this.nbNorth_A_to_A[54] = new HashSet<Integer>(Arrays.asList(nbNorth_A54));

		Integer nbEast_A55[] = {45};
		Integer nbSouth_A55[] = {15,54};
		Integer nbNorth_A55[] = {15,56};

		this.nbEast_A_to_A[55] = new HashSet<Integer>(Arrays.asList(nbEast_A55));
		this.nbWest_A_to_A[55] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[55] = new HashSet<Integer>(Arrays.asList(nbSouth_A55));
		this.nbNorth_A_to_A[55] = new HashSet<Integer>(Arrays.asList(nbNorth_A55));

		Integer nbEast_A56[] = {46};
		Integer nbSouth_A56[] = {16,55};
		Integer nbNorth_A56[] = {16,57};

		this.nbEast_A_to_A[56] = new HashSet<Integer>(Arrays.asList(nbEast_A56));
		this.nbWest_A_to_A[56] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[56] = new HashSet<Integer>(Arrays.asList(nbSouth_A56));
		this.nbNorth_A_to_A[56] = new HashSet<Integer>(Arrays.asList(nbNorth_A56));
	
		Integer nbEast_A57[] = {47};
		Integer nbSouth_A57[] = {17,56};
		Integer nbNorth_A57[] = {17,58};
		
		this.nbEast_A_to_A[57] = new HashSet<Integer>(Arrays.asList(nbEast_A57));
		this.nbWest_A_to_A[57] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[57] = new HashSet<Integer>(Arrays.asList(nbSouth_A57));
		this.nbNorth_A_to_A[57] = new HashSet<Integer>(Arrays.asList(nbNorth_A57));

		Integer nbEast_A58[] = {48};
		Integer nbSouth_A58[] = {18,57};
		Integer nbNorth_A58[] = {18,59};

		this.nbEast_A_to_A[58] = new HashSet<Integer>(Arrays.asList(nbEast_A58));
		this.nbWest_A_to_A[58] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[58] = new HashSet<Integer>(Arrays.asList(nbSouth_A58));
		this.nbNorth_A_to_A[58] = new HashSet<Integer>(Arrays.asList(nbNorth_A58));

		Integer nbEast_A59[] = {49};
		Integer nbSouth_A59[] = {19,58};
		Integer nbNorth_A59[] = {19};

		this.nbEast_A_to_A[59] = new HashSet<Integer>(Arrays.asList(nbEast_A59));
		this.nbWest_A_to_A[59] = this.nbWest_A_to_A[51];
		this.nbSouth_A_to_A[59] = new HashSet<Integer>(Arrays.asList(nbSouth_A59));
		this.nbNorth_A_to_A[59] = new HashSet<Integer>(Arrays.asList(nbNorth_A59));
		
		Integer nbEast_A61[] = {21,62,71};
		Integer nbSouth_A61[] = {1,12,51};

		this.nbEast_A_to_A[61] = new HashSet<Integer>(Arrays.asList(nbEast_A61));
		this.nbWest_A_to_A[61] = this.nbWest_A_to_A[1];
		this.nbSouth_A_to_A[61] = new HashSet<Integer>(Arrays.asList(nbSouth_A61));
		this.nbNorth_A_to_A[61] = this.nbNorth_A_to_A[11];
		
		Integer nbEast_A62[] = {63,72};
		Integer nbWest_A62[] = {11,61,72};
		Integer nbSouth_A62[] = {2};

		this.nbEast_A_to_A[62] = new HashSet<Integer>(Arrays.asList(nbEast_A62));
		this.nbWest_A_to_A[62] = new HashSet<Integer>(Arrays.asList(nbWest_A62));
		this.nbSouth_A_to_A[62] = new HashSet<Integer>(Arrays.asList(nbSouth_A62));
		this.nbNorth_A_to_A[62] = this.nbNorth_A_to_A[61];

		Integer nbEast_A63[] = {64,73};
		Integer nbWest_A63[] = {62,73};
		Integer nbSouth_A63[] = {3};

		this.nbEast_A_to_A[63] = new HashSet<Integer>(Arrays.asList(nbEast_A63));
		this.nbWest_A_to_A[63] = new HashSet<Integer>(Arrays.asList(nbWest_A63));
		this.nbSouth_A_to_A[63] = new HashSet<Integer>(Arrays.asList(nbSouth_A63));
		this.nbNorth_A_to_A[63] = this.nbNorth_A_to_A[61];
		
		Integer nbEast_A64[] = {65,74};
		Integer nbWest_A64[] = {63,74};
		Integer nbSouth_A64[] = {4};

		this.nbEast_A_to_A[64] = new HashSet<Integer>(Arrays.asList(nbEast_A64));
		this.nbWest_A_to_A[64] = new HashSet<Integer>(Arrays.asList(nbWest_A64));
		this.nbSouth_A_to_A[64] = new HashSet<Integer>(Arrays.asList(nbSouth_A64));
		this.nbNorth_A_to_A[64] = this.nbNorth_A_to_A[61];

		Integer nbEast_A65[] = {66,75};
		Integer nbWest_A65[] = {64,75};
		Integer nbSouth_A65[] = {5};

		this.nbEast_A_to_A[65] = new HashSet<Integer>(Arrays.asList(nbEast_A65));
		this.nbWest_A_to_A[65] = new HashSet<Integer>(Arrays.asList(nbWest_A65));
		this.nbSouth_A_to_A[65] = new HashSet<Integer>(Arrays.asList(nbSouth_A65));
		this.nbNorth_A_to_A[65] = this.nbNorth_A_to_A[61];

		Integer nbEast_A66[] = {67,76};
		Integer nbWest_A66[] = {65,76};
		Integer nbSouth_A66[] = {6};
		
		this.nbEast_A_to_A[66] = new HashSet<Integer>(Arrays.asList(nbEast_A66));
		this.nbWest_A_to_A[66] = new HashSet<Integer>(Arrays.asList(nbWest_A66));
		this.nbSouth_A_to_A[66] = new HashSet<Integer>(Arrays.asList(nbSouth_A66));
		this.nbNorth_A_to_A[66] = this.nbNorth_A_to_A[61];
	
		Integer nbEast_A67[] = {68,77};
		Integer nbWest_A67[] = {66,77};
		Integer nbSouth_A67[] = {7};
		
		this.nbEast_A_to_A[67] = new HashSet<Integer>(Arrays.asList(nbEast_A67));
		this.nbWest_A_to_A[67] = new HashSet<Integer>(Arrays.asList(nbWest_A67));
		this.nbSouth_A_to_A[67] = new HashSet<Integer>(Arrays.asList(nbSouth_A67));
		this.nbNorth_A_to_A[67] = this.nbNorth_A_to_A[61];

		Integer nbEast_A68[] = {69,78};
		Integer nbWest_A68[] = {67,78};
		Integer nbSouth_A68[] = {8};

		this.nbEast_A_to_A[68] = new HashSet<Integer>(Arrays.asList(nbEast_A68));
		this.nbWest_A_to_A[68] = new HashSet<Integer>(Arrays.asList(nbWest_A68));
		this.nbSouth_A_to_A[68] = new HashSet<Integer>(Arrays.asList(nbSouth_A68));
		this.nbNorth_A_to_A[68] = this.nbNorth_A_to_A[61];

		Integer nbEast_A69[] = {79};
		Integer nbWest_A69[] = {68,79};
		Integer nbSouth_A69[] = {9};

		this.nbEast_A_to_A[69] = new HashSet<Integer>(Arrays.asList(nbEast_A69));
		this.nbWest_A_to_A[69] = new HashSet<Integer>(Arrays.asList(nbWest_A69));
		this.nbSouth_A_to_A[69] = new HashSet<Integer>(Arrays.asList(nbSouth_A69));
		this.nbNorth_A_to_A[69] = this.nbNorth_A_to_A[61];
		
		Integer nbWest_A71[] = {11,61,72};
		Integer nbSouth_A71[] = {22,31,41};

		this.nbEast_A_to_A[71] = this.nbEast_A_to_A[21];
		this.nbWest_A_to_A[71] = new HashSet<Integer>(Arrays.asList(nbWest_A71));
		this.nbSouth_A_to_A[71] = new HashSet<Integer>(Arrays.asList(nbSouth_A71));
		this.nbNorth_A_to_A[71] = this.nbNorth_A_to_A[11];
		
		Integer nbEast_A72[] = {21,62,71};
		Integer nbWest_A72[] = {62,73};
		Integer nbSouth_A72[] = {32};

		this.nbEast_A_to_A[72] = new HashSet<Integer>(Arrays.asList(nbEast_A72));
		this.nbWest_A_to_A[72] = new HashSet<Integer>(Arrays.asList(nbWest_A72));
		this.nbSouth_A_to_A[72] = new HashSet<Integer>(Arrays.asList(nbSouth_A72));
		this.nbNorth_A_to_A[72] = this.nbNorth_A_to_A[71];

		Integer nbEast_A73[] = {63,72};
		Integer nbWest_A73[] = {63,74};
		Integer nbSouth_A73[] = {33};

		this.nbEast_A_to_A[73] = new HashSet<Integer>(Arrays.asList(nbEast_A73));
		this.nbWest_A_to_A[73] = new HashSet<Integer>(Arrays.asList(nbWest_A73));
		this.nbSouth_A_to_A[73] = new HashSet<Integer>(Arrays.asList(nbSouth_A73));
		this.nbNorth_A_to_A[73] = this.nbNorth_A_to_A[71];
		
		Integer nbEast_A74[] = {64,73};
		Integer nbWest_A74[] = {64,75};
		Integer nbSouth_A74[] = {34};

		this.nbEast_A_to_A[74] = new HashSet<Integer>(Arrays.asList(nbEast_A74));
		this.nbWest_A_to_A[74] = new HashSet<Integer>(Arrays.asList(nbWest_A74));
		this.nbSouth_A_to_A[74] = new HashSet<Integer>(Arrays.asList(nbSouth_A74));
		this.nbNorth_A_to_A[74] = this.nbNorth_A_to_A[71];

		Integer nbEast_A75[] = {65,74};
		Integer nbWest_A75[] = {65,76};
		Integer nbSouth_A75[] = {35};

		this.nbEast_A_to_A[75] = new HashSet<Integer>(Arrays.asList(nbEast_A75));
		this.nbWest_A_to_A[75] = new HashSet<Integer>(Arrays.asList(nbWest_A75));
		this.nbSouth_A_to_A[75] = new HashSet<Integer>(Arrays.asList(nbSouth_A75));
		this.nbNorth_A_to_A[75] = this.nbNorth_A_to_A[71];

		Integer nbEast_A76[] = {66,75};
		Integer nbWest_A76[] = {66,77};
		Integer nbSouth_A76[] = {36};
		
		this.nbEast_A_to_A[76] = new HashSet<Integer>(Arrays.asList(nbEast_A76));
		this.nbWest_A_to_A[76] = new HashSet<Integer>(Arrays.asList(nbWest_A76));
		this.nbSouth_A_to_A[76] = new HashSet<Integer>(Arrays.asList(nbSouth_A76));
		this.nbNorth_A_to_A[76] = this.nbNorth_A_to_A[71];
	
		Integer nbEast_A77[] = {67,76};
		Integer nbWest_A77[] = {67,78};
		Integer nbSouth_A77[] = {37};
	
		this.nbEast_A_to_A[77] = new HashSet<Integer>(Arrays.asList(nbEast_A77));
		this.nbWest_A_to_A[77] = new HashSet<Integer>(Arrays.asList(nbWest_A77));
		this.nbSouth_A_to_A[77] = new HashSet<Integer>(Arrays.asList(nbSouth_A77));
		this.nbNorth_A_to_A[77] = this.nbNorth_A_to_A[71];

		Integer nbEast_A78[] = {68,77};
		Integer nbWest_A78[] = {68,79};
		Integer nbSouth_A78[] = {38};

		this.nbEast_A_to_A[78] = new HashSet<Integer>(Arrays.asList(nbEast_A78));
		this.nbWest_A_to_A[78] = new HashSet<Integer>(Arrays.asList(nbWest_A78));
		this.nbSouth_A_to_A[78] = new HashSet<Integer>(Arrays.asList(nbSouth_A78));
		this.nbNorth_A_to_A[78] = this.nbNorth_A_to_A[71];

		Integer nbEast_A79[] = {69,78};
		Integer nbWest_A79[] = {69};
		Integer nbSouth_A79[] = {39};

		this.nbEast_A_to_A[79] = new HashSet<Integer>(Arrays.asList(nbEast_A79));
		this.nbWest_A_to_A[79] = new HashSet<Integer>(Arrays.asList(nbWest_A79));
		this.nbSouth_A_to_A[79] = new HashSet<Integer>(Arrays.asList(nbSouth_A79));
		this.nbNorth_A_to_A[79] = this.nbNorth_A_to_A[71];
		
		
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForMediumStandardToMediumAdapterTiles() {
		
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallStandardToSmallSpecialAdapterTiles() {
		
		
		Integer nbEast_S0[] = {2, 3, 4, 8, 13, 14, 15, 16, 22, 25, 26, 28};
		Integer nbWest_S0[] = {1,5,6,7,15,16,13,14,21,23,24,27};
		Integer nbSouth_S0[] = {1,2,3,5,11,12,17,18,23,25,27,28};
		Integer nbNorth_S0[] = {4,6,7,8,17,18,11,12,21,22,24,26};

		this.nbEast_SS_to_SXA[0] = new HashSet<Integer>(Arrays.asList(nbEast_S0));
		this.nbWest_SS_to_SXA[0] = new HashSet<Integer>(Arrays.asList(nbWest_S0));
		this.nbSouth_SS_to_SXA[0] = new HashSet<Integer>(Arrays.asList(nbSouth_S0));
		this.nbNorth_SS_to_SXA[0] = new HashSet<Integer>(Arrays.asList(nbNorth_S0));
		
		Integer nbEast_S1[] = {5,12,24};
		Integer nbSouth_S1[] = {13,21,8};
		
		this.nbEast_SS_to_SXA[1] = new HashSet<Integer>(Arrays.asList(nbEast_S1));
		this.nbWest_SS_to_SXA[1] = this.nbWest_SS_to_SXA[0];
		this.nbSouth_SS_to_SXA[1] = new HashSet<Integer>(Arrays.asList(nbSouth_S1));
		this.nbNorth_SS_to_SXA[1] = this.nbNorth_SS_to_SXA[0];
		
		Integer nbWest_S2[] = {3,11,26};
		Integer nbSouth_S2[] = {7,15,22};
		
		this.nbEast_SS_to_SXA[2] = this.nbEast_SS_to_SXA[0];
		this.nbWest_SS_to_SXA[2] = new HashSet<Integer>(Arrays.asList(nbWest_S2));
		this.nbSouth_SS_to_SXA[2] = new HashSet<Integer>(Arrays.asList(nbSouth_S2));
		this.nbNorth_SS_to_SXA[2] = this.nbNorth_SS_to_SXA[0];
		
		this.nbEast_SS_to_SXA[3] = this.nbEast_SS_to_SXA[1];
		this.nbWest_SS_to_SXA[3] = this.nbWest_SS_to_SXA[2];
		this.nbSouth_SS_to_SXA[3] = this.nbSouth_SS_to_SXA[0];
		this.nbNorth_SS_to_SXA[3] = this.nbNorth_SS_to_SXA[0];
		
		Integer nbEast_S4[] = {6,18,23};
		Integer nbNorth_S4[] = {2,14,27};
		
		this.nbEast_SS_to_SXA[4] = new HashSet<Integer>(Arrays.asList(nbEast_S4));
		this.nbWest_SS_to_SXA[4] = this.nbWest_SS_to_SXA[0];
		this.nbSouth_SS_to_SXA[4] = this.nbSouth_SS_to_SXA[0];
		this.nbNorth_SS_to_SXA[4] = new HashSet<Integer>(Arrays.asList(nbNorth_S4));
		
		this.nbEast_SS_to_SXA[5] = this.nbEast_SS_to_SXA[0];
		this.nbWest_SS_to_SXA[5] = this.nbWest_SS_to_SXA[0];
		this.nbSouth_SS_to_SXA[5] = this.nbSouth_SS_to_SXA[1];
		this.nbNorth_SS_to_SXA[5] = this.nbNorth_SS_to_SXA[4];
		
		this.nbEast_SS_to_SXA[7] = this.nbEast_SS_to_SXA[5];
		this.nbWest_SS_to_SXA[7] = this.nbWest_SS_to_SXA[2];
		this.nbSouth_SS_to_SXA[7] = this.nbSouth_SS_to_SXA[3];
		this.nbNorth_SS_to_SXA[7] = this.nbNorth_SS_to_SXA[4];
		
		Integer nbWest_S8[] = {4,17,25};
		Integer nbNorth_S8[] = {1,16,28};
	
		this.nbEast_SS_to_SXA[8] = this.nbEast_SS_to_SXA[0];
		this.nbWest_SS_to_SXA[8] = new HashSet<Integer>(Arrays.asList(nbWest_S8));
		this.nbSouth_SS_to_SXA[8] = this.nbSouth_SS_to_SXA[0];
		this.nbNorth_SS_to_SXA[8] = new HashSet<Integer>(Arrays.asList(nbNorth_S8));
		
		this.nbEast_SS_to_SXA[10] = this.nbEast_SS_to_SXA[0];
		this.nbWest_SS_to_SXA[10] = this.nbWest_SS_to_SXA[0];
		this.nbSouth_SS_to_SXA[10] = this.nbSouth_SS_to_SXA[2];
		this.nbNorth_SS_to_SXA[10] = this.nbNorth_SS_to_SXA[8];
		
		this.nbEast_SS_to_SXA[11] = this.nbEast_SS_to_SXA[1];
		this.nbWest_SS_to_SXA[11] = this.nbWest_SS_to_SXA[10];
		this.nbSouth_SS_to_SXA[11] = this.nbSouth_SS_to_SXA[3];
		this.nbNorth_SS_to_SXA[11] = this.nbNorth_SS_to_SXA[8];
		
		this.nbEast_SS_to_SXA[12] = this.nbEast_SS_to_SXA[4];
		this.nbWest_SS_to_SXA[12] = this.nbWest_SS_to_SXA[8];
		this.nbSouth_SS_to_SXA[12] = this.nbSouth_SS_to_SXA[0];
		this.nbNorth_SS_to_SXA[12] = this.nbNorth_SS_to_SXA[0];
		
		this.nbEast_SS_to_SXA[13] = this.nbEast_SS_to_SXA[7];
		this.nbWest_SS_to_SXA[13] = this.nbWest_SS_to_SXA[8];
		this.nbSouth_SS_to_SXA[13] = this.nbSouth_SS_to_SXA[1];
		this.nbNorth_SS_to_SXA[13] = this.nbNorth_SS_to_SXA[12];
		
		this.nbEast_SS_to_SXA[14] = this.nbEast_SS_to_SXA[4];
		this.nbWest_SS_to_SXA[14] = this.nbWest_SS_to_SXA[10];
		this.nbSouth_SS_to_SXA[14] = this.nbSouth_SS_to_SXA[2];
		this.nbNorth_SS_to_SXA[14] = this.nbNorth_SS_to_SXA[12];
		
		this.nbEast_SS_to_SXA[15] = this.nbEast_SS_to_SXA[5];
		this.nbWest_SS_to_SXA[15] = this.nbWest_SS_to_SXA[10];
		this.nbSouth_SS_to_SXA[15] = this.nbSouth_SS_to_SXA[11];
		this.nbNorth_SS_to_SXA[15] = this.nbNorth_SS_to_SXA[12];
		
	
		
		
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialAdapterToSmallStandardTiles() {
		
		
		//0 nicht belegt, weil Adapter Kacheln von 1 bis 28 nummeriert sind
		
		this.nbEast_SXA_to_SS[0] = this.emptySet;
		this.nbWest_SXA_to_SS[0] = this.emptySet;
		this.nbSouth_SXA_to_SS[0] = this.emptySet;
		this.nbNorth_SXA_to_SS[0] = this.emptySet;
		
		Integer nbEast_SXA1[] = {0, 1, 4, 5, 10, 1, 14, 15};
		Integer nbSouth_SXA1[] = {8, 10 , 11};
		Integer nbNorth_SXA1[] = {0, 3, 4, 7, 8, 10, 12, 15};
		
		this.nbEast_SXA_to_SS[1] = new HashSet<Integer>(Arrays.asList(nbEast_SXA1));
		this.nbWest_SXA_to_SS[1] = this.emptySet;
		this.nbSouth_SXA_to_SS[1] = new HashSet<Integer>(Arrays.asList(nbSouth_SXA1));
		this.nbNorth_SXA_to_SS[1] = new HashSet<Integer>(Arrays.asList(nbNorth_SXA1));
		
		Integer nbWest_SXA2[] = {0, 2, 5, 7, 8, 10, 13, 15};
		Integer nbSouth_SXA2[] = {4, 5, 7};
		
		this.nbEast_SXA_to_SS[2] = this.emptySet;
		this.nbWest_SXA_to_SS[2] = new HashSet<Integer>(Arrays.asList(nbWest_SXA2));
		this.nbSouth_SXA_to_SS[2] = new HashSet<Integer>(Arrays.asList(nbSouth_SXA2));
		this.nbNorth_SXA_to_SS[2] = this.nbNorth_SXA_to_SS[1];
		
		Integer nbEast_SXA3[] = {2, 3, 7};
		
		this.nbEast_SXA_to_SS[3] = new HashSet<Integer>(Arrays.asList(nbEast_SXA3));
		this.nbWest_SXA_to_SS[3] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[3] = this.emptySet;
		this.nbNorth_SXA_to_SS[3] = this.nbNorth_SXA_to_SS[1];
		
		Integer nbEast_SXA4[] = {8, 12, 13};
		Integer nbSouth_SXA4[] = {0, 1, 2, 3, 12, 13, 14, 15};
		
		this.nbEast_SXA_to_SS[4] = new HashSet<Integer>(Arrays.asList(nbEast_SXA4));
		this.nbWest_SXA_to_SS[4] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[4] = new HashSet<Integer>(Arrays.asList(nbSouth_SXA4));
		this.nbNorth_SXA_to_SS[4] = this.emptySet;
		
		Integer nbWest_SXA5[] = {1, 3, 11};
		
		this.nbEast_SXA_to_SS[5] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[5] = new HashSet<Integer>(Arrays.asList(nbWest_SXA5));
		this.nbSouth_SXA_to_SS[5] = this.emptySet;
		this.nbNorth_SXA_to_SS[5] = this.nbNorth_SXA_to_SS[1];
		
		Integer nbWest_SXA6[] = {4, 12, 14};
		
		this.nbEast_SXA_to_SS[6] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[6] = new HashSet<Integer>(Arrays.asList(nbWest_SXA6));
		this.nbSouth_SXA_to_SS[6] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[6] = this.emptySet;
		
		Integer nbNorth_SXA7[] = {2, 10, 14};
		
		this.nbEast_SXA_to_SS[7] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[7] = this.emptySet;
		this.nbSouth_SXA_to_SS[7] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[7] = new HashSet<Integer>(Arrays.asList(nbNorth_SXA7));
		
		Integer nbNorth_SXA8[] = {1, 5, 13};
		
		this.nbEast_SXA_to_SS[8] = this.emptySet;
		this.nbWest_SXA_to_SS[8] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[8] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[8] = new HashSet<Integer>(Arrays.asList(nbNorth_SXA8));
		
		this.nbEast_SXA_to_SS[11] = this.nbEast_SXA_to_SS[3];
		this.nbWest_SXA_to_SS[11] = this.emptySet;
		this.nbSouth_SXA_to_SS[11] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[11] = this.nbNorth_SXA_to_SS[1];
		
		Integer nbWest_SXA12[] = {1, 3, 11};
		
		this.nbEast_SXA_to_SS[12] = this.emptySet;
		this.nbWest_SXA_to_SS[12] = new HashSet<Integer>(Arrays.asList(nbWest_SXA12));
		this.nbSouth_SXA_to_SS[12] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[12] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[13] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[13] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[13] = this.emptySet;
		this.nbNorth_SXA_to_SS[13] = this.nbNorth_SXA_to_SS[8];
		
		this.nbEast_SXA_to_SS[14] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[14] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[14] = this.nbSouth_SXA_to_SS[2];
		this.nbNorth_SXA_to_SS[14] = this.emptySet;
		
		this.nbEast_SXA_to_SS[15] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[15] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[15] = this.emptySet;
		this.nbNorth_SXA_to_SS[15] = this.nbNorth_SXA_to_SS[7];
		
		this.nbEast_SXA_to_SS[16] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[16] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[16] = this.nbSouth_SXA_to_SS[1];
		this.nbNorth_SXA_to_SS[16] = this.emptySet;
		
		this.nbEast_SXA_to_SS[17] = this.nbEast_SXA_to_SS[4];
		this.nbWest_SXA_to_SS[17] = this.emptySet;
		this.nbSouth_SXA_to_SS[17] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[17] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[18] = this.emptySet;
		this.nbWest_SXA_to_SS[18] = this.nbWest_SXA_to_SS[6];
		this.nbSouth_SXA_to_SS[18] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[18] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[21] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[21] = this.emptySet;
		this.nbSouth_SXA_to_SS[21] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[21] = this.nbNorth_SXA_to_SS[8];
		
		this.nbEast_SXA_to_SS[22] = this.emptySet;
		this.nbWest_SXA_to_SS[22] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[22] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[22] = this.nbNorth_SXA_to_SS[7];
		
		this.nbEast_SXA_to_SS[23] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[23] = this.nbWest_SXA_to_SS[6];
		this.nbSouth_SXA_to_SS[23] = this.emptySet;
		this.nbNorth_SXA_to_SS[23] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[24] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[24] = this.nbWest_SXA_to_SS[5];
		this.nbSouth_SXA_to_SS[24] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[24] = this.emptySet;
		
		this.nbEast_SXA_to_SS[25] = this.nbEast_SXA_to_SS[4];
		this.nbWest_SXA_to_SS[25] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[25] = this.emptySet;
		this.nbNorth_SXA_to_SS[25] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[26] = this.nbEast_SXA_to_SS[3];
		this.nbWest_SXA_to_SS[26] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[26] = this.nbSouth_SXA_to_SS[4];
		this.nbNorth_SXA_to_SS[26] = this.emptySet;
		
		this.nbEast_SXA_to_SS[27] = this.nbEast_SXA_to_SS[1];
		this.nbWest_SXA_to_SS[27] = this.emptySet;
		this.nbSouth_SXA_to_SS[27] = this.nbSouth_SXA_to_SS[2];
		this.nbNorth_SXA_to_SS[27] = this.nbNorth_SXA_to_SS[1];
		
		this.nbEast_SXA_to_SS[28] = this.emptySet;
		this.nbWest_SXA_to_SS[28] = this.nbWest_SXA_to_SS[2];
		this.nbSouth_SXA_to_SS[28] = this.nbSouth_SXA_to_SS[1];
		this.nbNorth_SXA_to_SS[28] = this.nbNorth_SXA_to_SS[1];	
	
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallStandardToSmallSpecialTiles() {
		
		
		Integer sx_5_10[] = {5, 10};
		Integer sx_3_12[] = {3, 12};
		
		this.nbEast_SS_to_SX[0] = new HashSet<Integer>(Arrays.asList(sx_5_10));
		this.nbWest_SS_to_SX[0] = new HashSet<Integer>(Arrays.asList(sx_5_10));
		this.nbSouth_SS_to_SX[0] = new HashSet<Integer>(Arrays.asList(sx_3_12));
		this.nbNorth_SS_to_SX[0] = new HashSet<Integer>(Arrays.asList(sx_3_12));
				
		this.nbEast_SS_to_SX[1] = this.emptySet;
		this.nbWest_SS_to_SX[1] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[1] = this.emptySet;
		this.nbNorth_SS_to_SX[1] = this.nbNorth_SS_to_SX[0];

		this.nbEast_SS_to_SX[2] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[2] = this.emptySet;
		this.nbSouth_SS_to_SX[2] = this.emptySet;
		this.nbNorth_SS_to_SX[2] = this.nbNorth_SS_to_SX[0];

		this.nbEast_SS_to_SX[3] = this.emptySet;
		this.nbWest_SS_to_SX[3] = this.emptySet;
		this.nbSouth_SS_to_SX[3] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[3] = this.nbNorth_SS_to_SX[0];
		
		this.nbEast_SS_to_SX[4] = this.emptySet;
		this.nbWest_SS_to_SX[4] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[4] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[4] = this.emptySet;

		this.nbEast_SS_to_SX[5] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[5] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[5] = this.emptySet;
		this.nbNorth_SS_to_SX[5] = this.emptySet;
		
		this.nbEast_SS_to_SX[7] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[7] = this.emptySet;
		this.nbSouth_SS_to_SX[7] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[7] = this.emptySet;
	
		this.nbEast_SS_to_SX[8] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[8] = this.emptySet;
		this.nbSouth_SS_to_SX[8] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[8] = this.emptySet;
		
		this.nbEast_SS_to_SX[10] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[10] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[10] = this.emptySet;
		this.nbNorth_SS_to_SX[10] = this.emptySet;
		
		this.nbEast_SS_to_SX[11] = this.emptySet;
		this.nbWest_SS_to_SX[11] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[11] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[11] = this.emptySet;
		
		this.nbEast_SS_to_SX[12] = this.emptySet;
		this.nbWest_SS_to_SX[12] = this.emptySet;
		this.nbSouth_SS_to_SX[12] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[12] = this.nbNorth_SS_to_SX[0];
		
		this.nbEast_SS_to_SX[13] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[13] = this.emptySet;
		this.nbSouth_SS_to_SX[13] = this.emptySet;
		this.nbNorth_SS_to_SX[13] = this.nbNorth_SS_to_SX[0];
	
		this.nbEast_SS_to_SX[14] = this.emptySet;
		this.nbWest_SS_to_SX[14] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[14] = this.emptySet;
		this.nbNorth_SS_to_SX[14] = this.nbNorth_SS_to_SX[0];

		this.nbEast_SS_to_SX[15] = this.nbEast_SS_to_SX[0];
		this.nbWest_SS_to_SX[15] = this.nbWest_SS_to_SX[0];
		this.nbSouth_SS_to_SX[15] = this.nbSouth_SS_to_SX[0];
		this.nbNorth_SS_to_SX[15] = this.nbNorth_SS_to_SX[0];
		
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialToSmallStandardTiles() {
		
		Integer nbEast_SX_to_SS[] = {0, 1, 4, 5, 10, 11, 14, 15};
		Integer nbWest_SX_to_SS[] = {0, 2, 5, 7, 8, 10, 13, 15};
		Integer nbSouth_SX_to_SS[] = {0, 1, 2, 3, 12, 13, 14, 15};
		Integer nbNorth_SX_to_SS[] = {0, 3, 4, 7, 8, 11, 12, 15};
	
		this.nbEast_SX_to_SS[3] = this.emptySet;
		this.nbWest_SX_to_SS[3] = this.emptySet;
		this.nbSouth_SX_to_SS[3] = new HashSet<Integer>(Arrays.asList(nbSouth_SX_to_SS));
		this.nbNorth_SX_to_SS[3] = new HashSet<Integer>(Arrays.asList(nbNorth_SX_to_SS));
				
		this.nbEast_SX_to_SS[5] = new HashSet<Integer>(Arrays.asList(nbEast_SX_to_SS));
		this.nbWest_SX_to_SS[5] = new HashSet<Integer>(Arrays.asList(nbWest_SX_to_SS));
		this.nbSouth_SX_to_SS[5] = this.emptySet;
		this.nbNorth_SX_to_SS[5] = this.emptySet;

		this.nbEast_SX_to_SS[10] = this.nbEast_SX_to_SS[5];
		this.nbWest_SX_to_SS[10] = this.nbWest_SX_to_SS[5];
		this.nbSouth_SX_to_SS[10] = this.emptySet;
		this.nbNorth_SX_to_SS[10] = this.emptySet;
	
		this.nbEast_SX_to_SS[12] = this.emptySet;
		this.nbWest_SX_to_SS[12] = this.emptySet;
		this.nbSouth_SX_to_SS[12] = this.nbSouth_SX_to_SS[3];
		this.nbNorth_SX_to_SS[12] = this.nbNorth_SX_to_SS[3];
		
	}
	
	////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialToSmallSpecialAdapterTiles() {
		
		Integer nbEast_SX3[] = {1, 11, 21};
		Integer nbWest_SX3[] = {2, 12, 22};
		Integer nbSouth_SX3[] = {1,2,3,5,11,12,17,18,23,25,27,28};
		Integer nbNorth_SX3[] = {4,6,7,8,11,12,17,18,21,22,24,26};

		this.nbEast_SX_to_SXA[3] = new HashSet<Integer>(Arrays.asList(nbEast_SX3));
		this.nbWest_SX_to_SXA[3] = new HashSet<Integer>(Arrays.asList(nbWest_SX3));
		this.nbSouth_SX_to_SXA[3] = new HashSet<Integer>(Arrays.asList(nbSouth_SX3));
		this.nbNorth_SX_to_SXA[3] = new HashSet<Integer>(Arrays.asList(nbNorth_SX3));

		Integer nbEast_SX5[] = {2,3,4,8,13,14,15,16,22,25,26,28};
		Integer nbWest_SX5[] = {1,5,6,7,13,14,15,16,21,23,24,27};
		Integer nbSouth_SX5[] = {4,14,24};
		Integer nbNorth_SX5[] = {3,13,23};

		this.nbEast_SX_to_SXA[5] = new HashSet<Integer>(Arrays.asList(nbEast_SX5));
		this.nbWest_SX_to_SXA[5] = new HashSet<Integer>(Arrays.asList(nbWest_SX5));
		this.nbSouth_SX_to_SXA[5] = new HashSet<Integer>(Arrays.asList(nbSouth_SX5));
		this.nbNorth_SX_to_SXA[5] = new HashSet<Integer>(Arrays.asList(nbNorth_SX5));
	
		Integer nbSouth_SX10[] = {6,16,26};
		Integer nbNorth_SX10[] = {5,15,25};
	
		this.nbEast_SX_to_SXA[10] = this.nbEast_SX_to_SXA[5];
		this.nbWest_SX_to_SXA[10] = this.nbWest_SX_to_SXA[5];
		this.nbSouth_SX_to_SXA[10] = new HashSet<Integer>(Arrays.asList(nbSouth_SX10));
		this.nbNorth_SX_to_SXA[10] = new HashSet<Integer>(Arrays.asList(nbNorth_SX10));
	
		Integer nbEast_SX12[] = {7,17,27};
		Integer nbWest_SX12[] = {8,18,28};
	
		this.nbEast_SX_to_SXA[12] = new HashSet<Integer>(Arrays.asList(nbEast_SX12));
		this.nbWest_SX_to_SXA[12] = new HashSet<Integer>(Arrays.asList(nbWest_SX12));
		this.nbSouth_SX_to_SXA[12] = this.nbSouth_SX_to_SXA[3];
		this.nbNorth_SX_to_SXA[12] = this.nbNorth_SX_to_SXA[3];
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void initNeighbourSetsForSmallSpecialAdapterToSmallSpecialTiles() {
		
		Integer nb_5_10[] = {5,10};
		Integer nb_5[] = {5};
		Integer nb_10[] = {10};
		Integer nb_3_12[] = {3,12};
		Integer nb_3[] = {3};
		Integer nb_12[] = {12};

		this.nbEast_SXA_to_SX[1] = new HashSet<Integer>(Arrays.asList(nb_5_10));
		this.nbWest_SXA_to_SX[2] = new HashSet<Integer>(Arrays.asList(nb_5_10));
		this.nbSouth_SXA_to_SX[4] = new HashSet<Integer>(Arrays.asList(nb_3_12));
		this.nbNorth_SXA_to_SX[1] = new HashSet<Integer>(Arrays.asList(nb_3_12));
		
		for (int i = 1; i <= 28; i++) {
			
			if ((i==9) | (i==10) | (i==19) | (i==20)) continue;
			
			this.nbEast_SXA_to_SX[i] = this.nbEast_SXA_to_SX[1];
			this.nbWest_SXA_to_SX[i] = this.nbWest_SXA_to_SX[2];
			this.nbSouth_SXA_to_SX[i] = this.nbSouth_SXA_to_SX[4];
			this.nbNorth_SXA_to_SX[i] = this.nbNorth_SXA_to_SX[1];
	
		}
		
		this.nbEast_SXA_to_SX[2] = new HashSet<Integer>(Arrays.asList(nb_3));
		this.nbWest_SXA_to_SX[1] = new HashSet<Integer>(Arrays.asList(nb_3));
		this.nbEast_SXA_to_SX[8] = new HashSet<Integer>(Arrays.asList(nb_12));
		this.nbWest_SXA_to_SX[7] = new HashSet<Integer>(Arrays.asList(nb_12));

		this.nbSouth_SXA_to_SX[3] = new HashSet<Integer>(Arrays.asList(nb_5));
		this.nbNorth_SXA_to_SX[4] = new HashSet<Integer>(Arrays.asList(nb_5));
		this.nbSouth_SXA_to_SX[5] = new HashSet<Integer>(Arrays.asList(nb_10));
		this.nbNorth_SXA_to_SX[6] = new HashSet<Integer>(Arrays.asList(nb_10));
		
		this.nbSouth_SXA_to_SX[1] = emptySet;
		this.nbSouth_SXA_to_SX[2] = emptySet;
		this.nbEast_SXA_to_SX[3] = emptySet;
		this.nbEast_SXA_to_SX[4] = emptySet;
		this.nbWest_SXA_to_SX[5] = emptySet;
		this.nbWest_SXA_to_SX[6] = emptySet;
		this.nbNorth_SXA_to_SX[7] = emptySet;
		this.nbNorth_SXA_to_SX[8] = emptySet;
		
		this.nbEast_SXA_to_SX[11] = emptySet;
		this.nbWest_SXA_to_SX[11] = this.nbWest_SXA_to_SX[1]; // nb_3
		this.nbEast_SXA_to_SX[12] = this.nbEast_SXA_to_SX[2]; // nb_3
		this.nbWest_SXA_to_SX[12] = emptySet;
		this.nbSouth_SXA_to_SX[13] = this.nbSouth_SXA_to_SX[3]; // nb_5
		this.nbNorth_SXA_to_SX[13] = emptySet;
		this.nbSouth_SXA_to_SX[14] = emptySet;
		this.nbNorth_SXA_to_SX[14] = this.nbNorth_SXA_to_SX[4]; // nb_5
		this.nbSouth_SXA_to_SX[15] = this.nbSouth_SXA_to_SX[5]; // nb_10
		this.nbNorth_SXA_to_SX[15] = emptySet;
		this.nbSouth_SXA_to_SX[16] = emptySet;
		this.nbNorth_SXA_to_SX[16] = this.nbNorth_SXA_to_SX[6]; // nb_10
		this.nbEast_SXA_to_SX[17] = emptySet;
		this.nbWest_SXA_to_SX[17] = this.nbWest_SXA_to_SX[7]; // nb_12
		this.nbEast_SXA_to_SX[18] = this.nbEast_SXA_to_SX[8]; // nb_12
		this.nbWest_SXA_to_SX[18] = emptySet;
		
		this.nbWest_SXA_to_SX[21] = this.nbWest_SXA_to_SX[1]; // nb_3
		this.nbNorth_SXA_to_SX[21] = emptySet;
		this.nbEast_SXA_to_SX[22] = this.nbEast_SXA_to_SX[2]; // nb_3
		this.nbNorth_SXA_to_SX[22] = emptySet;
		this.nbWest_SXA_to_SX[23] = emptySet;
		this.nbSouth_SXA_to_SX[23] = this.nbSouth_SXA_to_SX[3]; // nb_5
		this.nbWest_SXA_to_SX[24] = emptySet;
		this.nbNorth_SXA_to_SX[24] = this.nbNorth_SXA_to_SX[4]; // nb_5
		this.nbEast_SXA_to_SX[25] = emptySet;
		this.nbSouth_SXA_to_SX[25] = this.nbSouth_SXA_to_SX[5]; // nb_10
		this.nbEast_SXA_to_SX[26] = emptySet;
		this.nbNorth_SXA_to_SX[26] = this.nbNorth_SXA_to_SX[6]; // nb_10
		this.nbWest_SXA_to_SX[27] = this.nbWest_SXA_to_SX[7]; // nb_12
		this.nbSouth_SXA_to_SX[27] = emptySet;
		this.nbEast_SXA_to_SX[28] = this.nbEast_SXA_to_SX[8]; // nb_12
		this.nbSouth_SXA_to_SX[28] = emptySet;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialToSmallSpecialTiles() {
		
		Integer nb_5_10[] = {5,10};
		Integer nb_3_12[] = {3,12};

		this.nbEast_SX_to_SX[5] = new HashSet<Integer>(Arrays.asList(nb_5_10));
		this.nbWest_SX_to_SX[5] = new HashSet<Integer>(Arrays.asList(nb_5_10));
		this.nbSouth_SX_to_SX[3] = new HashSet<Integer>(Arrays.asList(nb_3_12));
		this.nbNorth_SX_to_SX[3] = new HashSet<Integer>(Arrays.asList(nb_3_12));

		this.nbEast_SX_to_SX[10] = this.nbEast_SX_to_SX[5]; 
		this.nbWest_SX_to_SX[10] = this.nbWest_SX_to_SX[5]; 
		this.nbSouth_SX_to_SX[12] = this.nbSouth_SX_to_SX[3]; 
		this.nbNorth_SX_to_SX[12] = this.nbNorth_SX_to_SX[3]; 
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialAdapterToSmallSpecialAdapterTiles() {
	
		
		for (int i = 1; i <= 28; i++) {
			
			this.nbEast_SXA_to_SXA[i] = emptySet;
			this.nbWest_SXA_to_SXA[i] = emptySet;
			this.nbSouth_SXA_to_SXA[i] = emptySet;
			this.nbNorth_SXA_to_SXA[i] = emptySet;
			
		}

		
		for (int i = 0; i <= 2; i++) {

			this.nbEast_SXA_to_SXA[i * 10 + 1] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 1].add(i * 10 + 1 + 1);
			this.nbWest_SXA_to_SXA[i * 10 + 2] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 2].add(i * 10 + 2 - 1);

			this.nbWest_SXA_to_SXA[i * 10 + 1] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 1].add(i * 10 + 1 + 1);
			this.nbEast_SXA_to_SXA[i * 10 + 2] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 2].add(i * 10 + 2 - 1);
			
			this.nbNorth_SXA_to_SXA[i * 10 + 3] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 3].add(i * 10 + 3 + 1);
			this.nbSouth_SXA_to_SXA[i * 10 + 4] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 4].add(i * 10 + 4 - 1);

			this.nbSouth_SXA_to_SXA[i * 10 + 3] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 3].add(i * 10 + 3 + 1);
			this.nbNorth_SXA_to_SXA[i * 10 + 4] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 4].add(i * 10 + 4 - 1);
			
			this.nbNorth_SXA_to_SXA[i * 10 + 5] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 5].add(i * 10 + 5 + 1);
			this.nbSouth_SXA_to_SXA[i * 10 + 6] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 6].add(i * 10 + 6 - 1);

			this.nbSouth_SXA_to_SXA[i * 10 + 5] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 5].add(i * 10 + 5 + 1);
			this.nbNorth_SXA_to_SXA[i * 10 + 6] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 6].add(i * 10 + 6 - 1);
			
			this.nbEast_SXA_to_SXA[i * 10 + 7] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 7].add(i * 10 + 7 + 1);
			this.nbWest_SXA_to_SXA[i * 10 + 8] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 8].add(i * 10 + 8 - 1);

			this.nbWest_SXA_to_SXA[i * 10 + 7] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 7].add(i * 10 + 7 + 1);
			this.nbEast_SXA_to_SXA[i * 10 + 8] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 8].add(i * 10 + 8 - 1);
			
		}
		  
		for (int i = 0; i <= 2; i++) {

			this.nbSouth_SXA_to_SXA[i * 10 + 1] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 1].add(i * 10 + 7);
			this.nbNorth_SXA_to_SXA[i * 10 + 1] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 1].add(i * 10 + 7);

			this.nbSouth_SXA_to_SXA[i * 10 + 2] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 2].add(i * 10 + 8);
			this.nbNorth_SXA_to_SXA[i * 10 + 2] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 2].add(i * 10 + 8);
			
			this.nbEast_SXA_to_SXA[i * 10 + 3] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 3].add(i * 10 + 5);
			this.nbWest_SXA_to_SXA[i * 10 + 3] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 3].add(i * 10 + 5);
			
			this.nbEast_SXA_to_SXA[i * 10 + 4] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 4].add(i * 10 + 6);
			this.nbWest_SXA_to_SXA[i * 10 + 4] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 4].add(i * 10 + 6);

			this.nbEast_SXA_to_SXA[i * 10 + 5] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 5].add(i * 10 + 3);
			this.nbWest_SXA_to_SXA[i * 10 + 5] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 5].add(i * 10 + 3);

			this.nbEast_SXA_to_SXA[i * 10 + 6] = new HashSet<Integer>(); 
			this.nbEast_SXA_to_SXA[i * 10 + 6].add(i * 10 + 4);
			this.nbWest_SXA_to_SXA[i * 10 + 6] = new HashSet<Integer>(); 
			this.nbWest_SXA_to_SXA[i * 10 + 6].add(i * 10 + 4);
			
			this.nbSouth_SXA_to_SXA[i * 10 + 7] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 7].add(i * 10 + 1);
			this.nbNorth_SXA_to_SXA[i * 10 + 7] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 7].add(i * 10 + 1);

			this.nbSouth_SXA_to_SXA[i * 10 + 8] = new HashSet<Integer>(); 
			this.nbSouth_SXA_to_SXA[i * 10 + 8].add(i * 10 + 2);
			this.nbNorth_SXA_to_SXA[i * 10 + 8] = new HashSet<Integer>(); 
			this.nbNorth_SXA_to_SXA[i * 10 + 8].add(i * 10 + 2);
			
		}		
 	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForStandardToAdapterTiles() {
		// it's for small and medium tiles
		
		// alternative 1:
		
		Integer nbEast_aS0[] = {11,12,13,14,15,16,17,18,19,51,52,53,54,55,56,57,58,59};
		Integer nbWest_aS0[] = {21,22,23,24,25,26,27,28,29,41,42,43,44,45,46,47,48,49};
		Integer nbSouth_aS0[] = {61,62,63,64,65,66,67,68,69,71,72,73,74,75,76,77,78,79};
		Integer nbNorth_aS0[] = {1,2,3,4,5,6,7,8,9,31,32,33,34,35,36,37,38,39};

		this.nbEast_aS_to_aA1[0] = new HashSet<Integer>(Arrays.asList(nbEast_aS0));
		this.nbWest_aS_to_aA1[0] = new HashSet<Integer>(Arrays.asList(nbWest_aS0));
		this.nbSouth_aS_to_aA1[0] = new HashSet<Integer>(Arrays.asList(nbSouth_aS0));
		this.nbNorth_aS_to_aA1[0] = new HashSet<Integer>(Arrays.asList(nbNorth_aS0));
		
		this.nbEast_aS_to_aA1[1] = emptySet;
		this.nbWest_aS_to_aA1[1] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[1] = emptySet;
		this.nbNorth_aS_to_aA1[1] = this.nbNorth_aS_to_aA1[0];
	
		this.nbEast_aS_to_aA1[2] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[2] = emptySet;
		this.nbSouth_aS_to_aA1[2] = emptySet;
		this.nbNorth_aS_to_aA1[2] = this.nbNorth_aS_to_aA1[0];
		
		this.nbEast_aS_to_aA1[3] = emptySet;
		this.nbWest_aS_to_aA1[3] = emptySet;
		this.nbSouth_aS_to_aA1[3] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[3] = this.nbNorth_aS_to_aA1[0];
	
		this.nbEast_aS_to_aA1[4] = emptySet;
		this.nbWest_aS_to_aA1[4] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[4] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[4] = emptySet;
		
		this.nbEast_aS_to_aA1[5] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[5] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[5] = emptySet;
		this.nbNorth_aS_to_aA1[5] = emptySet;
	
		this.nbEast_aS_to_aA1[6] = emptySet;
		this.nbWest_aS_to_aA1[6] = emptySet;
		this.nbSouth_aS_to_aA1[6] = emptySet;
		this.nbNorth_aS_to_aA1[6] = emptySet;
		
		this.nbEast_aS_to_aA1[7] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[7] = emptySet;
		this.nbSouth_aS_to_aA1[7] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[7] = emptySet;
		
		this.nbEast_aS_to_aA1[8] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[8] = emptySet;
		this.nbSouth_aS_to_aA1[8] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[8] = emptySet;
	
		this.nbEast_aS_to_aA1[9] = emptySet;
		this.nbWest_aS_to_aA1[9] = emptySet;
		this.nbSouth_aS_to_aA1[9] = emptySet;
		this.nbNorth_aS_to_aA1[9] = emptySet;
		
		this.nbEast_aS_to_aA1[10] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[10] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[10] = emptySet;
		this.nbNorth_aS_to_aA1[10] = emptySet;
		
		this.nbEast_aS_to_aA1[11] = emptySet;
		this.nbWest_aS_to_aA1[11] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[11] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[11] = emptySet;
		
		this.nbEast_aS_to_aA1[12] = emptySet;
		this.nbWest_aS_to_aA1[12] = emptySet;
		this.nbSouth_aS_to_aA1[12] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[12] = this.nbNorth_aS_to_aA1[0];
		
		this.nbEast_aS_to_aA1[13] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[13] = emptySet;
		this.nbSouth_aS_to_aA1[13] = emptySet;
		this.nbNorth_aS_to_aA1[13] = this.nbNorth_aS_to_aA1[0];
		
		this.nbEast_aS_to_aA1[14] = emptySet;
		this.nbWest_aS_to_aA1[14] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[14] = emptySet;
		this.nbNorth_aS_to_aA1[14] = this.nbNorth_aS_to_aA1[0];
		
		this.nbEast_aS_to_aA1[15] = this.nbEast_aS_to_aA1[0];
		this.nbWest_aS_to_aA1[15] = this.nbWest_aS_to_aA1[0];
		this.nbSouth_aS_to_aA1[15] = this.nbSouth_aS_to_aA1[0];
		this.nbNorth_aS_to_aA1[15] = this.nbNorth_aS_to_aA1[0];

		this.nbEast_aS_to_aA1[16] = emptySet;
		this.nbWest_aS_to_aA1[16] = emptySet;
		this.nbSouth_aS_to_aA1[16] = emptySet;
		this.nbNorth_aS_to_aA1[16] = emptySet;

		this.nbEast_aS_to_aA1[17] = emptySet;
		this.nbWest_aS_to_aA1[17] = emptySet;
		this.nbSouth_aS_to_aA1[17] = emptySet;
		this.nbNorth_aS_to_aA1[17] = emptySet;

		this.nbEast_aS_to_aA1[18] = emptySet;
		this.nbWest_aS_to_aA1[18] = emptySet;
		this.nbSouth_aS_to_aA1[18] = emptySet;
		this.nbNorth_aS_to_aA1[18] = emptySet;

		this.nbEast_aS_to_aA1[19] = emptySet;
		this.nbWest_aS_to_aA1[19] = emptySet;
		this.nbSouth_aS_to_aA1[19] = emptySet;
		this.nbNorth_aS_to_aA1[19] = emptySet;

		// alternative 2:
		
		this.nbEast_aS_to_aA2[0] = emptySet;
		this.nbWest_aS_to_aA2[0] = emptySet;
		this.nbSouth_aS_to_aA2[0] = emptySet;
		this.nbNorth_aS_to_aA2[0] = emptySet;
		
		Integer nbEast_aS1[] = {11,12,13,14,15,16,17,18,19};
		Integer nbSouth_aS1[] = {61,62,63,64,65,66,67,68,69};

		this.nbEast_aS_to_aA2[1] = new HashSet<Integer>(Arrays.asList(nbEast_aS1));
		this.nbWest_aS_to_aA2[1] = emptySet;
		this.nbSouth_aS_to_aA2[1] = new HashSet<Integer>(Arrays.asList(nbSouth_aS1));
		this.nbNorth_aS_to_aA2[1] = emptySet;
				
		Integer nbWest_aS2[] = {21,22,23,24,25,26,27,28,29};
		Integer nbSouth_aS2[] = {71,72,73,74,75,76,77,78,79};
	
		this.nbEast_aS_to_aA2[2] = emptySet;
		this.nbWest_aS_to_aA2[2] = new HashSet<Integer>(Arrays.asList(nbWest_aS2));
		this.nbSouth_aS_to_aA2[2] = new HashSet<Integer>(Arrays.asList(nbSouth_aS2));
		this.nbNorth_aS_to_aA2[2] = emptySet;

		this.nbEast_aS_to_aA2[3] = this.nbEast_aS_to_aA2[1];
		this.nbWest_aS_to_aA2[3] = this.nbWest_aS_to_aA2[2];
		this.nbSouth_aS_to_aA2[3] = emptySet;
		this.nbNorth_aS_to_aA2[3] = emptySet;
	
		Integer nbEast_aS4[] = {51,52,53,54,55,56,57,58,59};
		Integer nbNorth_aS4[] = {1,2,3,4,5,6,7,8,9};
		
		this.nbEast_aS_to_aA2[4] = new HashSet<Integer>(Arrays.asList(nbEast_aS4));
		this.nbWest_aS_to_aA2[4] = emptySet;
		this.nbSouth_aS_to_aA2[4] = emptySet;
		this.nbNorth_aS_to_aA2[4] = new HashSet<Integer>(Arrays.asList(nbNorth_aS4));

		this.nbEast_aS_to_aA2[5] = emptySet;
		this.nbWest_aS_to_aA2[5] = emptySet;
		this.nbSouth_aS_to_aA2[5] = this.nbSouth_aS_to_aA2[1];
		this.nbNorth_aS_to_aA2[5] = this.nbNorth_aS_to_aA2[4];

		this.nbEast_aS_to_aA2[6] = this.nbEast_aS_to_aA2[4];
		this.nbWest_aS_to_aA2[6] = this.nbWest_aS_to_aA2[2];
		this.nbSouth_aS_to_aA2[6] = this.nbSouth_aS_to_aA2[2];
		this.nbNorth_aS_to_aA2[6] = this.nbNorth_aS_to_aA2[4];
		
		this.nbEast_aS_to_aA2[7] = emptySet;
		this.nbWest_aS_to_aA2[7] = this.nbWest_aS_to_aA2[2];
		this.nbSouth_aS_to_aA2[7] = emptySet;
		this.nbNorth_aS_to_aA2[7] = this.nbNorth_aS_to_aA2[4];
		
		Integer nbWest_aS8[] = {41,42,43,44,45,46,47,48,49};
		Integer nbNorth_aS8[] = {31,32,33,34,35,36,37,38,39};
		
		this.nbEast_aS_to_aA2[8] = emptySet;
		this.nbWest_aS_to_aA2[8] = new HashSet<Integer>(Arrays.asList(nbWest_aS8));
		this.nbSouth_aS_to_aA2[8] = emptySet;
		this.nbNorth_aS_to_aA2[8] = new HashSet<Integer>(Arrays.asList(nbNorth_aS8));
		
		this.nbEast_aS_to_aA2[9] = this.nbEast_aS_to_aA2[1];
		this.nbWest_aS_to_aA2[9] = this.nbWest_aS_to_aA2[8];
		this.nbSouth_aS_to_aA2[9] = this.nbSouth_aS_to_aA2[1];
		this.nbNorth_aS_to_aA2[9] = this.nbNorth_aS_to_aA2[8];
		
		this.nbEast_aS_to_aA2[10] = emptySet;
		this.nbWest_aS_to_aA2[10] = emptySet;
		this.nbSouth_aS_to_aA2[10] = this.nbSouth_aS_to_aA2[2];
		this.nbNorth_aS_to_aA2[10] = this.nbNorth_aS_to_aA2[8];
		
		this.nbEast_aS_to_aA2[11] = this.nbEast_aS_to_aA2[1];
		this.nbWest_aS_to_aA2[11] = emptySet;
		this.nbSouth_aS_to_aA2[11] = emptySet;
		this.nbNorth_aS_to_aA2[11] = this.nbNorth_aS_to_aA2[8];
		
		this.nbEast_aS_to_aA2[12] = this.nbEast_aS_to_aA2[4];
		this.nbWest_aS_to_aA2[12] = this.nbWest_aS_to_aA2[8];
		this.nbSouth_aS_to_aA2[12] = emptySet;
		this.nbNorth_aS_to_aA2[12] = emptySet;

		this.nbEast_aS_to_aA2[13] = emptySet;
		this.nbWest_aS_to_aA2[13] = this.nbWest_aS_to_aA2[8];
		this.nbSouth_aS_to_aA2[13] = this.nbSouth_aS_to_aA2[1];
		this.nbNorth_aS_to_aA2[13] = emptySet;
	
		this.nbEast_aS_to_aA2[14] = this.nbEast_aS_to_aA2[4];
		this.nbWest_aS_to_aA2[14] = emptySet;
		this.nbSouth_aS_to_aA2[14] = this.nbSouth_aS_to_aA2[2];
		this.nbNorth_aS_to_aA2[14] = emptySet;
		
		this.nbEast_aS_to_aA2[15] = emptySet;
		this.nbWest_aS_to_aA2[15] = emptySet;
		this.nbSouth_aS_to_aA2[15] = emptySet;
		this.nbNorth_aS_to_aA2[15] = emptySet;
		
		this.nbEast_aS_to_aA2[16] = this.nbEast_aS_to_aA2[4];
		this.nbWest_aS_to_aA2[16] = this.nbWest_aS_to_aA2[8];
		this.nbSouth_aS_to_aA2[16] = this.nbSouth_aS_to_aA2[1];
		this.nbNorth_aS_to_aA2[16] = this.nbNorth_aS_to_aA2[4];

		this.nbEast_aS_to_aA2[17] = this.nbEast_aS_to_aA2[1];
		this.nbWest_aS_to_aA2[17] = this.nbWest_aS_to_aA2[2];
		this.nbSouth_aS_to_aA2[17] = this.nbSouth_aS_to_aA2[2];
		this.nbNorth_aS_to_aA2[17] = this.nbNorth_aS_to_aA2[8];

		this.nbEast_aS_to_aA2[18] = this.nbEast_aS_to_aA2[1];
		this.nbWest_aS_to_aA2[18] = this.nbWest_aS_to_aA2[2];
		this.nbSouth_aS_to_aA2[18] = this.nbSouth_aS_to_aA2[1];
		this.nbNorth_aS_to_aA2[18] = this.nbNorth_aS_to_aA2[4];

		this.nbEast_aS_to_aA2[19] = this.nbEast_aS_to_aA2[4];
		this.nbWest_aS_to_aA2[19] = this.nbWest_aS_to_aA2[8];
		this.nbSouth_aS_to_aA2[19] = this.nbSouth_aS_to_aA2[2];
		this.nbNorth_aS_to_aA2[19] = this.nbNorth_aS_to_aA2[8];

		// alternative 3:
		// is equal to alternative 1
		
		for (int i = 0; i <= 19; i++) {
			
			this.nbEast_aS_to_aA3[i] = this.nbEast_aS_to_aA1[i];
			this.nbWest_aS_to_aA3[i] = this.nbWest_aS_to_aA1[i];
			this.nbSouth_aS_to_aA3[i] = this.nbSouth_aS_to_aA1[i];
			this.nbNorth_aS_to_aA3[i] = this.nbNorth_aS_to_aA1[i];
			
		}
		
	}
	
	
	void initNeighbourSetsForAdapterToStandardTiles() {
		
		for (int i = 1; i <= 79; i++) {
			
			// empty sets for alternative 1
			this.nbEast_aA1_to_aS[i] = emptySet;
			this.nbWest_aA1_to_aS[i] = emptySet;
			this.nbSouth_aA1_to_aS[i] = emptySet;
			this.nbNorth_aA1_to_aS[i] = emptySet;
			
			// empty sets for alternative 2
			this.nbEast_aA2_to_aS[i] = emptySet;
			this.nbWest_aA2_to_aS[i] = emptySet;
			this.nbSouth_aA2_to_aS[i] = emptySet;
			this.nbNorth_aA2_to_aS[i] = emptySet;

			// empty sets for alternative 3
			this.nbEast_aA3_to_aS[i] = emptySet;
			this.nbWest_aA3_to_aS[i] = emptySet;
			this.nbSouth_aA3_to_aS[i] = emptySet;
			this.nbNorth_aA3_to_aS[i] = emptySet;
			
		}
		
		Integer nbEast[] = {0,1,4,5,10,11,14,15};
		Integer nbWest[] = {0,2,5,7,8,10,13,15};
		Integer nbSouth[] = {0,1,2,3,12,13,14,15};
		Integer nbNorth[] = {0,3,4,7,8,11,12,15};
		
		this.nbEast_aA1_to_aS[21] = new HashSet<Integer>(Arrays.asList(nbEast));
		this.nbWest_aA1_to_aS[11] = new HashSet<Integer>(Arrays.asList(nbWest));
		this.nbSouth_aA1_to_aS[1] = new HashSet<Integer>(Arrays.asList(nbSouth));
		this.nbNorth_aA1_to_aS[61] = new HashSet<Integer>(Arrays.asList(nbNorth));
		
		this.nbEast_aA3_to_aS[21] = new HashSet<Integer>(Arrays.asList(nbEast));
		this.nbWest_aA3_to_aS[11] = new HashSet<Integer>(Arrays.asList(nbWest));
		this.nbSouth_aA3_to_aS[1] = new HashSet<Integer>(Arrays.asList(nbSouth));
		this.nbNorth_aA3_to_aS[61] = new HashSet<Integer>(Arrays.asList(nbNorth));

		Integer nbEast_21_29[] = {2,3,7,6,17,18};
		Integer nbEast_41_49[] = {8,12,13,9,16,19};
		Integer nbWest_11_19[] = {1,3,11,9,17,18};
		Integer nbWest_51_59[] = {4,12,14,6,16,19};
		Integer nbSouth_01_09[] = {4,5,7,6,16,18};
		Integer nbSouth_31_39[] = {8,10,11,9,17,19};
		Integer nbNorth_61_69[] = {1,5,13,9,16,18};
		Integer nbNorth_71_79[] = {2,10,14,6,17,19};
		
		this.nbEast_aA2_to_aS[21] = new HashSet<Integer>(Arrays.asList(nbEast_21_29));
		this.nbWest_aA2_to_aS[11] = new HashSet<Integer>(Arrays.asList(nbWest_11_19));
		this.nbSouth_aA2_to_aS[1] = new HashSet<Integer>(Arrays.asList(nbSouth_01_09));
		this.nbNorth_aA2_to_aS[61] = new HashSet<Integer>(Arrays.asList(nbNorth_61_69));

		this.nbEast_aA2_to_aS[41] = new HashSet<Integer>(Arrays.asList(nbEast_41_49));
		this.nbWest_aA2_to_aS[51] = new HashSet<Integer>(Arrays.asList(nbWest_51_59));
		this.nbSouth_aA2_to_aS[31] = new HashSet<Integer>(Arrays.asList(nbSouth_31_39));
		this.nbNorth_aA2_to_aS[71] = new HashSet<Integer>(Arrays.asList(nbNorth_71_79));
		
		for (int i = 1; i <= 9; i++) {
			
			this.nbEast_aA1_to_aS[20 + i] = this.nbEast_aA1_to_aS[21];
			this.nbEast_aA1_to_aS[40 + i] = this.nbEast_aA1_to_aS[21];
			this.nbWest_aA1_to_aS[10 + i] = this.nbWest_aA1_to_aS[11];
			this.nbWest_aA1_to_aS[50 + i] = this.nbWest_aA1_to_aS[11];
			this.nbSouth_aA1_to_aS[i] 	   = this.nbSouth_aA1_to_aS[1];
			this.nbSouth_aA1_to_aS[30 + i] = this.nbSouth_aA1_to_aS[1];
			this.nbNorth_aA1_to_aS[60 + i] = this.nbNorth_aA1_to_aS[61];
			this.nbNorth_aA1_to_aS[70 + i] = this.nbNorth_aA1_to_aS[61];
			
			this.nbEast_aA3_to_aS[20 + i] = this.nbEast_aA3_to_aS[21];
			this.nbEast_aA3_to_aS[40 + i] = this.nbEast_aA3_to_aS[21];
			this.nbWest_aA3_to_aS[10 + i] = this.nbWest_aA3_to_aS[11];
			this.nbWest_aA3_to_aS[50 + i] = this.nbWest_aA3_to_aS[11];
			this.nbSouth_aA3_to_aS[i] 	   = this.nbSouth_aA3_to_aS[1];
			this.nbSouth_aA3_to_aS[30 + i] = this.nbSouth_aA3_to_aS[1];
			this.nbNorth_aA3_to_aS[60 + i] = this.nbNorth_aA3_to_aS[61];
			this.nbNorth_aA3_to_aS[70 + i] = this.nbNorth_aA3_to_aS[61];

			this.nbEast_aA2_to_aS[20 + i] = this.nbEast_aA2_to_aS[21];
			this.nbEast_aA2_to_aS[40 + i] = this.nbEast_aA2_to_aS[41];
			this.nbWest_aA2_to_aS[10 + i] = this.nbWest_aA2_to_aS[11];
			this.nbWest_aA2_to_aS[50 + i] = this.nbWest_aA2_to_aS[51];
			this.nbSouth_aA2_to_aS[i] 	   = this.nbSouth_aA2_to_aS[1];
			this.nbSouth_aA2_to_aS[30 + i] = this.nbSouth_aA2_to_aS[31];
			this.nbNorth_aA2_to_aS[60 + i] = this.nbNorth_aA2_to_aS[61];
			this.nbNorth_aA2_to_aS[70 + i] = this.nbNorth_aA2_to_aS[71];

		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	void initNeighbourSetsForSmallSpecialToSmallAdapterTiles() {
		
		//it is only implemented for adapter alternatives 1 and 3
		
		Integer nbEast[] = {11,12,13,14,15,16,17,18,19,51,52,53,54,55,56,57,58,59};
		Integer nbWest[] = {21,22,23,24,25,26,27,28,29,41,42,43,44,45,46,47,48,49};
		Integer nbSouth[] = {61,62,63,64,65,66,67,68,69,71,72,73,74,75,76,77,78,79};
		Integer nbNorth[] = {1,2,3,4,5,6,7,8,9,31,32,33,34,35,36,37,38,39};

		this.nbEast_SX_to_SA[3] = emptySet;
		this.nbWest_SX_to_SA[3] = emptySet;
		this.nbSouth_SX_to_SA[3] = new HashSet<Integer>(Arrays.asList(nbSouth));
		this.nbNorth_SX_to_SA[3] = new HashSet<Integer>(Arrays.asList(nbNorth));
		
		this.nbEast_SX_to_SA[5] = new HashSet<Integer>(Arrays.asList(nbEast));
		this.nbWest_SX_to_SA[5] = new HashSet<Integer>(Arrays.asList(nbWest));
		this.nbSouth_SX_to_SA[5] = emptySet;
		this.nbNorth_SX_to_SA[5] = emptySet;
		
		this.nbEast_SX_to_SA[10] = this.nbEast_SX_to_SA[5];
		this.nbWest_SX_to_SA[10] = this.nbWest_SX_to_SA[5];
		this.nbSouth_SX_to_SA[10] = emptySet;
		this.nbNorth_SX_to_SA[10] = emptySet;

		this.nbEast_SX_to_SA[12] = emptySet;
		this.nbWest_SX_to_SA[12] = emptySet;
		this.nbSouth_SX_to_SA[12] = this.nbSouth_SX_to_SA[3];
		this.nbNorth_SX_to_SA[12] = this.nbNorth_SX_to_SA[3];
		
	}
	
	void initNeighbourSetsForSmallAdapterToSmallSpecialTiles() {
		
		//it is only implemented for adapter alternatives 1 and 3
		
		Integer nbEast[] = {5,10};
		Integer nbWest[] = {5,10};
		Integer nbSouth[] = {3,12};
		Integer nbNorth[] = {3,12};
		
		for (int i = 1; i < 80; i++ ) {
			this.nbEast_SA_to_SX[i] = emptySet;
			this.nbWest_SA_to_SX[i] = emptySet;
			this.nbSouth_SA_to_SX[i] = emptySet;
			this.nbNorth_SA_to_SX[i] = emptySet;
		}
		
		this.nbEast_SA_to_SX[21] = new HashSet<Integer>(Arrays.asList(nbEast));
		this.nbWest_SA_to_SX[11] = new HashSet<Integer>(Arrays.asList(nbWest));
		this.nbSouth_SA_to_SX[1] = new HashSet<Integer>(Arrays.asList(nbSouth));
		this.nbNorth_SA_to_SX[61] = new HashSet<Integer>(Arrays.asList(nbNorth));
		
		for (int i = 1; i <= 9; i++ ) {
			
			this.nbEast_SA_to_SX[i+20] = this.nbEast_SA_to_SX[21];
			this.nbEast_SA_to_SX[i+40] = this.nbEast_SA_to_SX[21];
			
			this.nbWest_SA_to_SX[i+10] = this.nbWest_SA_to_SX[11];
			this.nbWest_SA_to_SX[i+50] = this.nbWest_SA_to_SX[11];

			this.nbSouth_SA_to_SX[i] 	= this.nbSouth_SA_to_SX[1];
			this.nbSouth_SA_to_SX[i+30] = this.nbSouth_SA_to_SX[1];

			this.nbNorth_SA_to_SX[i+60] = this.nbNorth_SA_to_SX[61];
			this.nbNorth_SA_to_SX[i+70] = this.nbNorth_SA_to_SX[61];

		}
		

		
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}