package org.socialworld.tools.mct;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CalculatePossibleTiles {

	static PossibleTiles possibleTiles = PossibleTiles.getInstance();

	static Set<Integer> getReducedSet(TileType actualTilesType, int actualTileTypeAlternative, 
			Tile tileFromEast, Tile tileFromWest, Tile tileFromNorth, Tile tileFromSouth) {
		
		
		int neighbourTileNumberWithoutOffset;
		int neighbourAlternative;
		
		Set<Integer> neighboursAllowedFromEast;
		Set<Integer> neighboursAllowedFromWest;
		Set<Integer> neighboursAllowedFromNorth;
		Set<Integer> neighboursAllowedFromSouth;
		
		Integer emptyset[] = {};						
		Set<Integer> empty = new HashSet<Integer>(Arrays.asList(emptyset));

		neighboursAllowedFromEast = empty;
		neighboursAllowedFromWest = empty;
		neighboursAllowedFromNorth = empty;
		neighboursAllowedFromSouth = empty;
		
		
		///////////////////////////////////////////
		
		if (tileFromEast.getType() != TileType.todo) {
			switch (actualTilesType) {
				case smallStandard:
					switch (tileFromEast.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallStandardToSmallStandardTile(tileFromEast.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case smallSpecial:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialToSmallStandardTile(neighbourTileNumberWithoutOffset);
							break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialAdapterToSmallStandardTile(neighbourTileNumberWithoutOffset);
							break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllSmallStandardTiles(); break;
						default: neighboursAllowedFromEast = empty; break;	
					}
					break;
				case smallAdapter:
					switch (tileFromEast.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForStandardToAdapter(tileFromEast.getNumber(), actualTileTypeAlternative); break;
						case smallAdapter:
							if ( tileFromEast.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromEast = possibleTiles.getWestNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecial:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialAdapterToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromEast = empty; break;	
					}
					break;
				case smallSpecial:
					switch (tileFromEast.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallStandardToSmallSpecialTile(tileFromEast.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							if ( tileFromEast.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllSmallSpecialTiles(); break;
						default: neighboursAllowedFromEast = empty; break;	
					}
					break;
				case smallSpecialAdapter:
					switch (tileFromEast.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallStandardToSmallSpecialAdapterTile(tileFromEast.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							if ( tileFromEast.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); 
							}
							break;
						case smallSpecialAdapter:
							if ( tileFromEast.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromEast = possibleTiles.getWestNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
						default: neighboursAllowedFromEast = empty; break;	
					}
					break;
				case mediumStandard:
					switch (tileFromEast.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForMediumStandardToMediumStandardTile(tileFromEast.getNumber()); break;
						case mediumAdapter:
							neighbourAlternative = tileFromEast.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllMediumStandardTiles(); break;
						default: neighboursAllowedFromEast = empty; break;	
					}	
					break;
				case mediumAdapter:
					switch (tileFromEast.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForStandardToAdapter(tileFromEast.getNumber(), actualTileTypeAlternative); break;
						case mediumAdapter:
							if ( tileFromEast.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromEast.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromEast = possibleTiles.getWestNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromEast = empty; break;
					}
					break;
				case largeStandard:
					switch (tileFromEast.getFeelAsTileType()) {
						case largeStandard:
							neighboursAllowedFromEast = possibleTiles.getWestNeighboursForLargeStandardToLargeStandardTile(tileFromEast.getNumber()); break;
						case todo:
							neighboursAllowedFromEast = possibleTiles.getAllLargeStandardTiles(); break;
						default: neighboursAllowedFromEast = empty; break;
					}					
					break;
				default: neighboursAllowedFromEast = empty; break;
			}
		}
		
		else {
			switch (actualTilesType) {
				case smallStandard: 
					neighboursAllowedFromEast = possibleTiles.getAllSmallStandardTiles(); break;
				case smallAdapter: 
					neighboursAllowedFromEast = possibleTiles.getAllAdapterTiles(); break;
				case smallSpecial: 
					neighboursAllowedFromEast = possibleTiles.getAllSmallSpecialTiles(); break;
				case smallSpecialAdapter: 
					neighboursAllowedFromEast = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
				case mediumStandard: 
					neighboursAllowedFromEast = possibleTiles.getAllMediumStandardTiles(); break;
				case mediumAdapter: 
					neighboursAllowedFromEast = possibleTiles.getAllAdapterTiles(); break;
				case largeStandard: 
					neighboursAllowedFromEast = possibleTiles.getAllLargeStandardTiles(); break;
				default: neighboursAllowedFromEast = empty; break;
			}
			
		}

/////////////////////////////////////////////////////////////////////////////////////////
		
		if (tileFromWest.getType() != TileType.todo) {
			switch (actualTilesType) {
				case smallStandard:
					switch (tileFromWest.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallStandardToSmallStandardTile(tileFromWest.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case smallSpecial:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialAdapterToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllSmallStandardTiles(); break;
						default: neighboursAllowedFromWest = empty; break;	
					}
					break;
				case smallAdapter:
					switch (tileFromWest.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForStandardToAdapter(tileFromWest.getNumber(), actualTileTypeAlternative); break;
						case smallAdapter:
							if ( tileFromWest.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromWest = possibleTiles.getEastNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecial:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialAdapterToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromWest = empty; break;	
					}
					break;
				case smallSpecial:
					switch (tileFromWest.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallStandardToSmallSpecialTile(tileFromWest.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							if ( tileFromWest.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllSmallSpecialTiles(); break;
						default: neighboursAllowedFromWest = empty; break;	
					}
					break;
				case smallSpecialAdapter:
					switch (tileFromWest.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallStandardToSmallSpecialAdapterTile(tileFromWest.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							if ( tileFromWest.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecialAdapter:
							if ( tileFromWest.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromWest = possibleTiles.getEastNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
						default: neighboursAllowedFromWest = empty; break;	
					}
					break;
				case mediumStandard:
					switch (tileFromWest.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForMediumStandardToMediumStandardTile(tileFromWest.getNumber()); break;
						case mediumAdapter:
							neighbourAlternative = tileFromWest.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllMediumStandardTiles(); break;
						default: neighboursAllowedFromWest = empty; break;	
					}			
					break;
				case mediumAdapter:
					switch (tileFromWest.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForStandardToAdapter(tileFromWest.getNumber(), actualTileTypeAlternative); break;
						case mediumAdapter:
							if ( tileFromWest.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromWest.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromWest = possibleTiles.getEastNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset); 
							}
							break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromWest = empty; break;
					}
					break;
				case largeStandard:
					switch (tileFromWest.getFeelAsTileType()) {
						case largeStandard:
							neighboursAllowedFromWest = possibleTiles.getEastNeighboursForLargeStandardToLargeStandardTile(tileFromWest.getNumber()); break;
						case todo:
							neighboursAllowedFromWest = possibleTiles.getAllLargeStandardTiles(); break;
						default: neighboursAllowedFromWest = empty; break;
					}					
					break;
				default: neighboursAllowedFromWest = empty; break;
			}
		}
		
		else {
			switch (actualTilesType) {
				case smallStandard: 
					neighboursAllowedFromWest = possibleTiles.getAllSmallStandardTiles(); break;
				case smallAdapter: 
					neighboursAllowedFromWest = possibleTiles.getAllAdapterTiles(); break;
				case smallSpecial: 
					neighboursAllowedFromWest = possibleTiles.getAllSmallSpecialTiles(); break;
				case smallSpecialAdapter: 
					neighboursAllowedFromWest = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
				case mediumStandard: 
					neighboursAllowedFromWest = possibleTiles.getAllMediumStandardTiles(); break;
				case mediumAdapter: 
					neighboursAllowedFromWest = possibleTiles.getAllAdapterTiles(); break;
				case largeStandard: 
					neighboursAllowedFromWest = possibleTiles.getAllLargeStandardTiles(); break;
				default: neighboursAllowedFromWest = empty; break;
			}
			
		}
	
////////////////////////////////////////////////////////////////////////////////////////		

		if (tileFromNorth.getType() != TileType.todo) {
			switch (actualTilesType) {
				case smallStandard:
					switch (tileFromNorth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallStandardToSmallStandardTile(tileFromNorth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case smallSpecial:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialAdapterToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllSmallStandardTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;	
					}
					break;
				case smallAdapter:
					switch (tileFromNorth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForStandardToAdapter(tileFromNorth.getNumber(), actualTileTypeAlternative); break;
						case smallAdapter:
							if ( tileFromNorth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}break;
						case smallSpecial:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialAdapterToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;	
					}
					break;
				case smallSpecial:
					switch (tileFromNorth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallStandardToSmallSpecialTile(tileFromNorth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							if ( tileFromNorth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllSmallSpecialTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;	
					}
					break;
				case smallSpecialAdapter:
					switch (tileFromNorth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallStandardToSmallSpecialAdapterTile(tileFromNorth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							if ( tileFromNorth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecialAdapter:
							if ( tileFromNorth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;	
					}
					break;
				case mediumStandard:
					switch (tileFromNorth.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForMediumStandardToMediumStandardTile(tileFromNorth.getNumber()); break;
						case mediumAdapter:
							neighbourAlternative = tileFromNorth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllMediumStandardTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;	
					}			
					break;
				case mediumAdapter:
					switch (tileFromNorth.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForStandardToAdapter(tileFromNorth.getNumber(), actualTileTypeAlternative); break;
						case mediumAdapter:
							if ( tileFromNorth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromNorth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset); 
							}
							break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;
					}
					break;
				case largeStandard:
					switch (tileFromNorth.getFeelAsTileType()) {
						case largeStandard:
							neighboursAllowedFromNorth = possibleTiles.getSouthNeighboursForLargeStandardToLargeStandardTile(tileFromNorth.getNumber()); break;
						case todo:
							neighboursAllowedFromNorth = possibleTiles.getAllLargeStandardTiles(); break;
						default: neighboursAllowedFromNorth = empty; break;
					}					
					break;
				default: neighboursAllowedFromNorth = empty; break;
			}
		}
		
		else {
			switch (actualTilesType) {
				case smallStandard: 
					neighboursAllowedFromNorth = possibleTiles.getAllSmallStandardTiles(); break;
				case smallAdapter: 
					neighboursAllowedFromNorth = possibleTiles.getAllAdapterTiles(); break;
				case smallSpecial: 
					neighboursAllowedFromNorth = possibleTiles.getAllSmallSpecialTiles(); break;
				case smallSpecialAdapter: 
					neighboursAllowedFromNorth = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
				case mediumStandard: 
					neighboursAllowedFromNorth = possibleTiles.getAllMediumStandardTiles(); break;
				case mediumAdapter: 
					neighboursAllowedFromNorth = possibleTiles.getAllAdapterTiles(); break;
				case largeStandard: 
					neighboursAllowedFromNorth = possibleTiles.getAllLargeStandardTiles(); break;
				default: neighboursAllowedFromNorth = empty; break;
			}
			
		}
		
////////////////////////////////////////////////////////////////////////////////////////
		
		if (tileFromSouth.getType() != TileType.todo) {
			switch (actualTilesType) {
				case smallStandard:
					switch (tileFromSouth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallStandardToSmallStandardTile(tileFromSouth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case smallSpecial:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialAdapterToSmallStandardTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllSmallStandardTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;	
					}
					break;
				case smallAdapter:
					switch (tileFromSouth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForStandardToAdapter(tileFromSouth.getNumber(), actualTileTypeAlternative); break;
						case smallAdapter:
							if ( tileFromSouth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecial:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialAdapterToSmallAdapterTile(neighbourTileNumberWithoutOffset); break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;	
					}
					break;
				case smallSpecial:
					switch (tileFromSouth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallStandardToSmallSpecialTile(tileFromSouth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialToSmallSpecialTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecialAdapter:
							if ( tileFromSouth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialAdapterToSmallSpecialTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllSmallSpecialTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;	
					}
					break;
				case smallSpecialAdapter:
					switch (tileFromSouth.getFeelAsTileType()) {
						case smallStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallStandardToSmallSpecialAdapterTile(tileFromSouth.getNumber()); break;
						case smallAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); break;
						case smallSpecial:
							if ( tileFromSouth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case smallSpecialAdapter:
							if ( tileFromSouth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForSmallSpecialAdapterToSmallSpecialAdapterTile(neighbourTileNumberWithoutOffset); 
							}
							break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;	
					}
					break;
				case mediumStandard:
					switch (tileFromSouth.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForMediumStandardToMediumStandardTile(tileFromSouth.getNumber()); break;
						case mediumAdapter:
							neighbourAlternative = tileFromSouth.getTileTypeAlternative();
							neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - neighbourAlternative * 100;
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForAdapterToStandardTile(neighbourTileNumberWithoutOffset, neighbourAlternative); break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllMediumStandardTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;	
					}			
					break;
				case mediumAdapter:
					switch (tileFromSouth.getFeelAsTileType()) {
						case mediumStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForStandardToAdapter(tileFromSouth.getNumber(), actualTileTypeAlternative); break;
						case mediumAdapter:
							if ( tileFromSouth.getTileTypeAlternative() == actualTileTypeAlternative) {
								neighbourTileNumberWithoutOffset = tileFromSouth.getNumber() - actualTileTypeAlternative * 100;
								neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForAdapterToAdapterTile(neighbourTileNumberWithoutOffset);
							}
							break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllAdapterTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;
					}
					break;
				case largeStandard:
					switch (tileFromSouth.getFeelAsTileType()) {
						case largeStandard:
							neighboursAllowedFromSouth = possibleTiles.getNorthNeighboursForLargeStandardToLargeStandardTile(tileFromSouth.getNumber()); break;
						case todo:
							neighboursAllowedFromSouth = possibleTiles.getAllLargeStandardTiles(); break;
						default: neighboursAllowedFromSouth = empty; break;
					}					
					break;
				default: neighboursAllowedFromSouth = empty; break;
			}
		}
		
		else {
			switch (actualTilesType) {
				case smallStandard: 
					neighboursAllowedFromSouth = possibleTiles.getAllSmallStandardTiles(); break;
				case smallAdapter: 
					neighboursAllowedFromSouth = possibleTiles.getAllAdapterTiles(); break;
				case smallSpecial: 
					neighboursAllowedFromSouth = possibleTiles.getAllSmallSpecialTiles(); break;
				case smallSpecialAdapter: 
					neighboursAllowedFromSouth = possibleTiles.getAllSmallSpecialAdapterTiles(); break;
				case mediumStandard: 
					neighboursAllowedFromSouth = possibleTiles.getAllMediumStandardTiles(); break;
				case mediumAdapter: 
					neighboursAllowedFromSouth = possibleTiles.getAllAdapterTiles(); break;
				case largeStandard: 
					neighboursAllowedFromSouth = possibleTiles.getAllLargeStandardTiles(); break;
				default: neighboursAllowedFromSouth = empty; break;
			}
			
		}
	
		return possibleTiles.getReducedTileSet(neighboursAllowedFromEast, neighboursAllowedFromWest, neighboursAllowedFromNorth, neighboursAllowedFromSouth);
				
	}

}
